package com.main.smssample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.main.ezipsmsclient.SMSClient;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private SMSClient smsClient ;
    private String AuthKey = "1b2220e99512f33b77f47c64fa8c248";
    private String Message = "";
    private String PhoneNumber = "";
    private String SenderId = "DEMOOS";
    private String RouteId = "1";
    private String SMSContentType = "english";
    private Response response;
    private String url;
    private EditText phoneNumberedt,messageedt;
    private OkHttpClient client = new OkHttpClient();

    private Button sendSMSBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberedt = findViewById(R.id.phone_numberEditText);
        messageedt = findViewById(R.id.messageEditText);
        smsClient = new SMSClient(this);

        sendSMSBtn = findViewById(R.id.send_btn);
        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumber = phoneNumberedt.getText().toString();
                Message = messageedt.getText().toString();
                new AsyncCaller().execute();
            }
        });



    }
    private  class  AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {


            // Get the string by calling the get url and add the user info //
            url = smsClient.getUrl(AuthKey,Message,SenderId,PhoneNumber,RouteId,SMSContentType);
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("Cache-Control", "no-cache")
                    .build();

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (response.isSuccessful()){
                Toast.makeText(MainActivity.this, "SMS SENT", Toast.LENGTH_SHORT).show();
            }
            pdLoading.dismiss();
        }

    }
}
