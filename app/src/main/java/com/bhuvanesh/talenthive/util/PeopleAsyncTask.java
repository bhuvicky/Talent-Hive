package com.bhuvanesh.talenthive.util;

import android.content.Context;
import android.os.AsyncTask;

import com.bhuvanesh.talenthive.R;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;
import java.util.List;

public class PeopleAsyncTask extends AsyncTask<String, Void, List<Person>> {

    private Context mContext;

    public PeopleAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected List<Person> doInBackground(String... params) {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        String clientId = mContext.getString(R.string.oauth2_0_web_client_google_people_api);
        String clientSecret = mContext.getString(R.string.google_web_client_secret);
        String redirectUrl = mContext.getString(R.string.redirect_url);
        String scope = "https://www.googleapis.com/auth/contacts.readonly";

        /*String authorizationUrl = new GoogleBrowserClientRequestUrl(clientId, redirectUrl, Arrays.asList(scope)).build();

        System.out.println("Go to the following link in your browser:");
        System.out.println(authorizationUrl);

        // Read the authorization code from the standard input stream.
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What is the authorization code?");
        String code = in.readLine();
        System.out.println("log auth code = " + code);*/
        // End of Step 1 <--

        // Step 2: Exchange -->
        //use server auth code we got while signin, to get token
        GoogleTokenResponse tokenResponse = null;
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    httpTransport, jsonFactory, clientId, clientSecret, params[0], redirectUrl).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // End of Step 2 <--

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setClientSecrets(clientId, clientSecret)
                .build()
                .setFromTokenResponse(tokenResponse);

        People peopleService = new People.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("Talent Hive")
                .build();

        ListConnectionsResponse response = null;
        try {
            response = peopleService.people().connections()
                    .list("people/me")
                    .setPageSize(200)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        List<Person> connections = response.getConnections();
        return response.getConnections();
    }

    @Override
    protected void onPostExecute(List<Person> persons) {
        super.onPostExecute(persons);
        if (persons != null && persons.size() > 0) {
            for (Person person : persons) {
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
