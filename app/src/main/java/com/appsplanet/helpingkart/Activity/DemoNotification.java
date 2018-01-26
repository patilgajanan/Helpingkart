package com.appsplanet.helpingkart.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appsplanet.helpingkart.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DemoNotification extends AppCompatActivity {
    private Button btn_send;
    public static String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    OkHttpClient mClient;

    String dataTitle, dataMessage;
    EditText title, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_notification);
        btn_send = (Button) findViewById(R.id.btn_send);
        //String device_registration_id = FirebaseInstanceId.getInstance().getToken();
        String devicei="eLIyYASakBM:APA91bF3CxchUmGuwxB4s4evuA2xWORS4GToCb9d1R6Dzxav6Cbjz2dykMYhzoV8wHjGpaJGiWCTEwku9L6a9jeZROO1mXmqTkD7QPtxYk32xLYlSvL_m7sJZR4R14UPwlOYSyYl0VeG";
        mClient = new OkHttpClient();
        String refreshedToken = devicei;//add your user refresh tokens who are logged in with firebase.
        final JSONArray jsonArray = new JSONArray();
        jsonArray.put(refreshedToken);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(jsonArray, "Hey", "Dips", "S", "Yes Jal");
            }
        });
    }

    public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon, final String message) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", icon);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", recipients);

                    String result = postToFCM(root.toString());
                    Log.d("Main Activity", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    Toast.makeText(DemoNotification.this, "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DemoNotification.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + "AAAAxHAYm9k:APA91bG3e6-ADxfJS_MgU9imBqF-2AV9PctgZSpxgyrDK8aIEooDeEqAeqQ_9QsjlYYZMnarmP8eYU-Pzrxzp6LsS6Xth70iab2cYWtYr-bfHKO0cgBwwb2N9B6EtWmeC6YNLFeH4psm")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
