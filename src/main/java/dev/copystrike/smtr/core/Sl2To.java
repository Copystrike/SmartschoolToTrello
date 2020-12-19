package dev.copystrike.smtr.core;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import dev.copystrike.smtr.core.email.EmailHelper;
import dev.copystrike.smtr.google.Auth;
import dev.copystrike.smtr.utils.MockData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

/**
 * Created by Nick on 12/15/2020 18:56.
 */
public class Sl2To {

    private static final String APPLICATION_NAME = "Smartschool 2 Trello";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Gmail service;
    private EmailHelper emailHelper;

    /**
     * Here is where the magic happens
     *
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public void initialize() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        service = new Gmail.Builder(httpTransport, JSON_FACTORY, Auth.getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
        emailHelper = new EmailHelper();
        fetchMails();
    }

    public void fetchMails() {
//            ListMessagesResponse watchResponse = service.users().messages().list("me").execute();
//            String messageID = watchResponse.getMessages().get(7).getId();
//            Message message = service.users().messages().get("me", messageID).execute();
//
//            byte[] imgBytes = message.getPayload().getBody().decodeData();
//            System.out.println(new String(imgBytes));

        Document document = Jsoup.parse(MockData.HTML_EMAIL);
        Elements list = document.select("body > div > table > tbody > tr > td > table > tbody > tr:nth-child(4) > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > ul > *");
        HashMap<String, String> emailBodyMap = emailHelper.elementsFromEmailBodyToMap(list);
        emailHelper.hashmapToEmail(emailBodyMap);
    }

    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
}
