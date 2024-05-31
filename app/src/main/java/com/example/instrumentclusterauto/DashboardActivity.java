package com.example.instrumentclusterauto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {
    private TextView rssFeedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_dashboard);

        rssFeedTextView = findViewById(R.id.rssFeedTextView);

        // Using Executors to fetch data
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String result = fetchData("https://feeds.bbci.co.uk/news/rss.xml");
            runOnUiThread(() -> rssFeedTextView.setText(result));
        });

        FloatingActionButton fab = findViewById(R.id.openDashboardButton);
        if (fab != null) {
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, FullDashboardActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("DashboardActivity", "FloatingActionButton is null");
        }
    }

    private String fetchData(String urlString) {
        String result = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            result = parseRSSFeed(inputStream);
            connection.disconnect();
        } catch (IOException e) {
            Log.e("DashboardActivity", "Network connection failed", e);
        }
        return result;
    }

    private String parseRSSFeed(InputStream inputStream) {
        StringBuilder titles = new StringBuilder();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, "UTF-8");
            boolean insideItem = false;
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem) {
                            String title = xpp.nextText();
                            titles.append(title).append("\n");
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e("DashboardActivity", "Error parsing data", e);
        }
        return titles.toString();
    }
}
