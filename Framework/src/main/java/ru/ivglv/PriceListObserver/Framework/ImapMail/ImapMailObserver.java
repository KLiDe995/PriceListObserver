package ru.ivglv.PriceListObserver.Framework.ImapMail;

import com.sun.mail.iap.Response;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.util.FolderClosedIOException;
import org.jetbrains.annotations.NotNull;
import ru.ivglv.PriceListObserver.Adapter.Port.IncomingFileHandler;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;

import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public final class ImapMailObserver implements Runnable {
    @NotNull
    private final MailConfig mailConfig;
    private final Set<String> permittedEmails;

    private Store store;
    private IMAPFolder imapFolder;

    public ImapMailObserver(@NotNull MailConfig mailConfig, Set<String> permittedEmails) {
        this.mailConfig = mailConfig;
        this.permittedEmails = permittedEmails;
    }

    public void openSession() throws MailException
    {
        System.out.println("Opening IMAP session...");
        try {
            Properties prop = new Properties();
            prop.put("mail.debug", "false");
            prop.put("mail.store.protocol", "imaps");
            prop.put("mail.imaps.partialfetch", "false");
            prop.put("mail.imaps.fetchsize", "1048576");
            prop.put("mail.imap.partialfetch", "false");
            prop.put("mail.imap.fetchsize", "1048576");
            prop.put("mail.imap.ssl.enable", mailConfig.getImapSslEnable().toString());
            prop.put("mail.imap.port", mailConfig.getImapPort().toString());

            Session session = Session.getInstance(prop);

            store = session.getStore();
            store.connect(mailConfig.getImapAuthServer(), mailConfig.getImapAuthEmail(), mailConfig.getImapAuthPassword());
            imapFolder = (IMAPFolder) store.getFolder(mailConfig.getImapFolderName());
            imapFolder.open(Folder.READ_ONLY);
            System.out.println("IMAP Session opened");
        } catch (MessagingException ex) {
            throw new MailException(ex.getMessage());
        }
    }

    public void addMessageListener(IncomingFileHandler handler)
    {
        System.out.println("Initializing message listener...");
        if(imapFolder != null && imapFolder.isOpen())
            imapFolder.addMessageCountListener(new MessageCountAdapter() {
                @Override
                public void messagesAdded(MessageCountEvent e) {
                    try {
                        Message[] msgs = e.getMessages();
                        //System.out.println("Got " + msgs.length + " new messages");
                        if(msgs.length > 0)
                            for(Message msg : msgs)
                                if(emailPermitted(msg.getFrom()[0].toString()))
                                    handleMessage(msg, handler);
                    } catch (Exception exception) {
                        exception.printStackTrace(); //TODO: подумать о логировании
                    }
                }
            });
        System.out.println("Message listener initialized");
    }

    private boolean emailPermitted(String sender)
    {
        for(String email : permittedEmails) {
            if (sender.contains(email))
                return true;
        }
        return false;
    }

    private void handleMessage(Message msg, IncomingFileHandler fileHandler) throws MessagingException, IOException
    {
        Multipart multipart = (Multipart) msg.getContent();
        for(int i = 0 ; i < multipart.getCount(); i++)
        {
            MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
            if(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())
                    && bodyPart.getFileName() != ""
                    && bodyPart.getFileName().contains(mailConfig.getImapFileExt()))
            {
                System.out.println("Got message with CSV file from permitted source. Processing...");
                System.out.println("Downloading CSV file...");
                try {
                    File incomingFile = File.createTempFile("PLO", mailConfig.getImapFileExt());
                    bodyPart.saveFile(incomingFile);
                    System.out.println("CSV file downloaded. Handling...");
                    fileHandler.handle(incomingFile, msg.getFrom()[0].toString());
                } catch (IOException | MessagingException ex) {
                    System.out.println("File cannot be downloaded or handled. Error: " + ex.getMessage());
                }
            }
        }
    }

    public void closeSession()
    {
        try {
            if(imapFolder != null) {
                imapFolder.close(true);
            }
            if(store != null)
                store.close();
        } catch (MessagingException e) {}
        catch (IllegalStateException e) {}
    }

    @Override
    public void run() {
        System.out.println("Mail observing started");
        try {
            while (imapFolder != null && imapFolder.isOpen())
                try {
                    imapFolder.idle();
                } catch (FolderClosedException ex) {
                    System.out.println("Server closed folder... Reopening");
                    imapFolder.open(Folder.READ_ONLY);
                }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Mail observing stopped");
        }
    }
}
