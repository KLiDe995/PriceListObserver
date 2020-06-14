package ru.ivglv.PriceListObserver.Framework.ImapMail;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.ivglv.PriceListObserver.Adapter.Port.IncomingFileHandler;
import ru.ivglv.PriceListObserver.Configuration.Properties.MailConfig;

import javax.mail.MessagingException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ImapMailObserverTest {
    private ImapMailObserver imapMailObserver;

    @BeforeClass
    public void setUp() throws MessagingException {
        MailConfig config = new MailConfig(
                "imap.yandex.ru"
                , "993"
                , "true"
                , "**@yandex.ru"
                , "**"
                , "INBOX"
                , ".csv"
        );
        HashSet<String> set = new HashSet<>();
        set.add("**@yandex.ru");
        imapMailObserver = new ImapMailObserver(config, set);
        imapMailObserver.openSession();
    }

    @AfterClass(enabled = false)
    public void tearDown() {
        imapMailObserver.closeSession();
    }

    @Test(enabled = false)
    public void testOpenSession() throws MessagingException {
        imapMailObserver.openSession();
        imapMailObserver.closeSession();
        Assert.assertTrue(true);
    }

    @Test(enabled = false)
    public void testRun() throws InterruptedException {
        Thread thread = new Thread(imapMailObserver);
        List<String> expected = List.of(
                "NSIN0018472693;555;SA1712L;????? ???????? | ????? ??? |;1,68;1;1343,68;2123,14;2;0;SA-1712L;\"SA-1712R;51376;0501-050;0501-051;0505-DEM;0524-DEM;D201-34-350A;HCA-3814L;\";MAZDA Demio DW3W 00- , KIA AVELLA 94- LOW L;SA1712L"
        );
        List<String> actual = new ArrayList<>();
        IncomingFileHandler handler = (File file, String sender) ->
        {
            try {
                actual.add(Files.lines(file.toPath()).findFirst().orElseThrow());
                imapMailObserver.closeSession();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        imapMailObserver.addMessageListener(handler);

        thread.start();
        //while(actual.size() == 0);
        //thread.interrupt();
        thread.join();

        Assert.assertEquals(actual, expected);
    }
}