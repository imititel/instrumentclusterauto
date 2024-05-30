package com.example.instrumentclusterauto;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);  // Make sure this is the correct layout file name

        // Call fetchDataFromUrl when activity starts
        fetchDataFromUrl();
    }

    private void fetchDataFromUrl() {
        new Thread(() -> {
            try {
                URL url = new URL("https://feeds.bbci.co.uk/news/rss.xml"); // BBC News RSS feed
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                // Here we invoke the XML parsing right after fetching the data
                parseRSSFeed(inputStream);

                connection.disconnect();  // Close the connection after parsing
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void parseRSSFeed(InputStream inputStream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            // Set the input for the parser
            xpp.setInput(inputStream, "UTF-8");

            boolean insideItem = false;
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem) {
                            final String title = xpp.nextText();
                            Log.d("Title:", title);  // Use this data as needed, e.g., update UI
                            // For UI update, remember to run on UI thread
                            runOnUiThread(() -> {
                                // update your TextView or other UI elements here
                                // Example: someTextView.setText(title);
                            });
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }
                eventType = xpp.next();  // Move to the next event
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }  // This closes the parseRSSFeed method

}  // This closes the DashboardActivity class