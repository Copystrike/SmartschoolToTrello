package dev.copystrike.smtr.core;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import dev.copystrike.smtr.core.email.EmailManager;
import dev.copystrike.smtr.google.Auth;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Nick on 12/15/2020 18:56.
 */
public class Sl2To {

    private static final String APPLICATION_NAME = "Smartschool 2 Trello";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Gmail service;

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
        EmailManager emailManager = new EmailManager();
        emailManager.getEmailEvents().add(new BuildInEvents());
        emailManager.start();
    }

    public static JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
}
