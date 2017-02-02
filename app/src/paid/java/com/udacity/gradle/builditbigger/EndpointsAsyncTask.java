package com.udacity.gradle.builditbigger;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.surya.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.surya.jokeactivity.JokeActivity;

import java.io.IOException;

/**
 * Created by Surya on 02-02-2017.
 */
class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();
    private MyApi myApiService = null;
    private Context context;
    private Dialog dialog;
    String jokeString;
    public EndpointsAsyncTask(Context context,Dialog dialog) {
        this.dialog = dialog;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8008/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.sayHi().execute().getData();
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        jokeString = result;
        if (result == null)
            Toast.makeText(context, "Error in fetching Joke", Toast.LENGTH_SHORT).show();
        else{
            beginActivity();
        }
    }

    private void beginActivity() {
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra("Joke", jokeString);
        context.startActivity(intent);
    }

}