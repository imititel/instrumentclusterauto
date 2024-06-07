package com.example.instrumentclusterauto;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class InternationalizationActivity extends AppCompatActivity {
    private static final int LANG_EN = Menu.FIRST;
    private static final int LANG_DE = Menu.FIRST + 1;
    private static final int LANG_ES = Menu.FIRST + 2;
    private static final int LANG_RO = Menu.FIRST + 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internationalization);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, LANG_EN, Menu.NONE, R.string.language_english);
        menu.add(0, LANG_DE, Menu.NONE, R.string.language_german);
        menu.add(0, LANG_ES, Menu.NONE, R.string.language_spanish);
        menu.add(0, LANG_RO, Menu.NONE, R.string.language_romanian);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Locale locale;
        switch (item.getItemId()) {
            case LANG_EN:
                locale = new Locale("en");
                break;
            case LANG_DE:
                locale = new Locale("de");
                break;
            case LANG_ES:
                locale = new Locale("es");
                break;
            case LANG_RO:
                locale = new Locale("ro");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        recreate();
        return true;
    }
}
