package ru.ivglv.PriceListObserver.Framework.ImapMail;

import com.sun.mail.imap.IMAPFolder;
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

public class ImapMailObserver implements Runnable {
    @NotNull
    private final MailConfig mailConfig;

    private Store store;
    private IMAPFolder imapFolder;
    private boolean running;

    public ImapMailObserver(@NotNull MailConfig mailConfig) {
        this.mailConfig = mailConfig;
        running = false;
    }

    public void openSession() throws NoSuchProviderException, MessagingException
    {
        Properties prop = new Properties();
        prop.put("mail.debug", "false");
        prop.put("mail.store.protocol", "imaps");
        prop.put("mail.imap.ssl.enable", mailConfig.getImapSslEnable().toString());
        prop.put("mail.imap.port", mailConfig.getImapPort().toString());

        Session session = Session.getDefaultInstance(prop, null);

        store = session.getStore();
        store.connect(mailConfig.getImapAuthServer(), mailConfig.getImapAuthEmail(), mailConfig.getImapAuthPassword());
        imapFolder = (IMAPFolder) store.getFolder(mailConfig.getImapFolderName());
        imapFolder.open(Folder.READ_ONLY);
    }

    public void addMessageListener(IncomingFileHandler handler)
    {
        if(imapFolder != null && imapFolder.isOpen())
            imapFolder.addMessageCountListener(new MessageCountAdapter() {
                @Override
                public void messagesAdded(MessageCountEvent e) {
                    try {
                        Message[] msgs = e.getMessages();
                        //System.out.println("Got " + msgs.length + " new messages");
                        if(msgs.length > 0)
                            for(Message msg : msgs)
                                handleMessage(msg, handler);
                    } catch (Exception exception) {
                        exception.printStackTrace(); //TODO: подумать о логировании
                    }
                }
            });
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
                File incomingFile = File.createTempFile("PLO", mailConfig.getImapFileExt());
                bodyPart.saveFile(incomingFile);
                fileHandler.handle(incomingFile, msg.getFrom()[0].toString());
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
        try {
            while (imapFolder != null && imapFolder.isOpen())
                imapFolder.idle();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
