package com.bhuvanesh.talenthive.util;

import android.os.AsyncTask;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.Photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class PeopleAsyncTaskNew extends AsyncTask<Void, Void, List<Person>> {

    /** Application name. */
    private static final String APPLICATION_NAME =
            "People API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/people.googleapis.com-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/people.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(PeopleScopes.CONTACTS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    protected List<Person> doInBackground(Void... params) {
        People service = null;
        try {
            service = getPeopleService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Request 10 connections.
        ListConnectionsResponse response = null;
        try {
            response = service.people().connections()
                    .list("people/me")
                    .setPageSize(10)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print display name of connections if available.
        List<Person> connections = response.getConnections();

        return connections;
    }

    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                PeopleAsyncTaskNew.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        System.out.println("log code flow = " + flow);

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "log Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static People getPeopleService() throws IOException {
        Credential credential = authorize();
        return new People.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    protected void onPostExecute(List<Person> persons) {
        super.onPostExecute(persons);
        if (persons != null && persons.size() > 0) {
            for (Person person : persons) {
                Photo photo = person.getPhotos().get(0);
                System.out.println("person photo url = " + photo.getUrl());

                List<Name> names = person.getNames();
                if (names != null && names.size() > 0) {
                    System.out.println("Name: " + person.getNames().get(0)
                            .getDisplayName());
                } else {
                    System.out.println("No names available for connection.");
                }
            }
        } else {
            System.out.println("No connections found.");
        }
    }
}
