package app.mma.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText input_firstname, input_lastname, input_age;
    Button btn_save, btn_load;

    SharedPreferences pref;
    SharedPreferences appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        pref = getPreferences(MODE_PRIVATE);
        pref = getSharedPreferences("myprefs", MODE_PRIVATE);
        initViews();
        appPref = PreferenceManager.getDefaultSharedPreferences(this);
        loadAppPrefValues();
    }


    // pref_screen
    public void loadAppPrefValues() {
        String username = appPref.getString("username", "");
        String userbio = appPref.getString("userbio", "");
        boolean viewimages = appPref.getBoolean("viewimages", false);
        boolean notifications = appPref.getBoolean("notifications", true);
        TextView tv = (TextView) findViewById(R.id.pref_text);
        tv.setText("username : \n\t" + username + "\n");
        tv.append("userbio  : \n\t" + userbio + "\n");
        tv.append("view images : \n\t" + viewimages + "\n");
        tv.append("notifications : \n\t" + notifications);
    }

    private void initViews() {
        input_firstname = (EditText) findViewById(R.id.input_firstname);
        input_lastname = (EditText) findViewById(R.id.input_lastname);
        input_age = (EditText) findViewById(R.id.input_age);
        btn_load = (Button) findViewById(R.id.btn_load);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = input_firstname.getText().toString().trim();
                String lastname = input_lastname.getText().toString().trim();
                String ageStr = input_age.getText().toString();
                if(! firstname.isEmpty() && ! lastname.isEmpty() && ! ageStr.isEmpty()) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("firstname", firstname);
                    editor.putString("lastname", lastname);
                    editor.putInt("age", Integer.valueOf(ageStr));
                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                    editor.apply();
                }
            }
        });

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = pref.getString("firstname", "NOT-FOUND");
                String lname = pref.getString("lastname", "NOT-FOUND");
                int age = pref.getInt("age", -1);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("pref-values")
                        .setMessage("firstname :    " + fname + "\nlastname :    " + lname +
                                "\nage :    " + (age == -1 ? "NOT-FOUND" : age))
                        .show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("SecondActivity").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("Settings").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("load App Prefs...").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                loadAppPrefValues();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
