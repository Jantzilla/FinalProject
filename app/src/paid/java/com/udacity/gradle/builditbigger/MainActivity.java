package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


class EndpointsAsyncTask extends android.os.AsyncTask<android.util.Pair<android.content.Context, String>, Void, String> {
    private static com.udacity.gradle.builditbigger.backend.myApi.MyApi myApiService = null;
    private android.content.Context context;

    @Override
    protected String doInBackground(android.util.Pair<android.content.Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            com.udacity.gradle.builditbigger.backend.myApi.MyApi.Builder builder = new com.udacity.gradle.builditbigger.backend.myApi.MyApi.Builder(com.google.api.client.extensions.android.http.AndroidHttp.newCompatibleTransport(),
                    new com.google.api.client.extensions.android.json.AndroidJsonFactory(), null)
                // options for running against local devappserver
                // - 10.0.2.2 is localhost's IP address in Android emulator
                // - turn off compression when running against local devappserver
                .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new com.google.api.client.googleapis.services.GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws java.io.IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
                // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.tellJoke(name).execute().getData();
        } catch (java.io.IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new EndpointsAsyncTask().execute(new android.util.Pair<android.content.Context, String>(this, "Manfred"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
    }


}
