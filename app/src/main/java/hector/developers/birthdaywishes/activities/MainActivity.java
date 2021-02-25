package hector.developers.birthdaywishes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import hector.developers.birthdaywishes.R;
import hector.developers.birthdaywishes.list.BirthListActivity;
import hector.developers.birthdaywishes.list.WorkListActivity;

public class MainActivity extends AppCompatActivity {
ImageView mBirthday, mWorkAnniversary;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");

        mWorkAnniversary = findViewById(R.id.imgWorkAnniversary);
        mBirthday = findViewById(R.id.imgBirthday);

        mBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent birthIntent = new Intent(MainActivity.this, BirthListActivity.class);
                startActivity(birthIntent);
                finish();
            }
        });
        mWorkAnniversary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workIntent = new Intent(MainActivity.this, WorkListActivity.class);
                startActivity(workIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.main_add_staff) {
            addStaff();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addStaff() {
        Intent intent = new Intent(getApplicationContext(), StaffActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}