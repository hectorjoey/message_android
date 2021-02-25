package hector.developers.birthdaywishes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hector.developers.birthdaywishes.R;
import hector.developers.birthdaywishes.model.Staff;

public class WorkDetailActivity extends AppCompatActivity {
    TextView detailFirstname;
    TextView detailLastname;
    TextView detailDob;
    TextView detailPhone;
    TextView detailEmail;
    Button mBtnCall, mBtnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
        detailFirstname = findViewById(R.id.detailFirstnames);
        detailLastname = findViewById(R.id.detailsLastnames);
        detailDob = findViewById(R.id.detailsDoe);
        detailEmail = findViewById(R.id.detailEmails);
        detailPhone = findViewById(R.id.detailPhones);

        mBtnCall = findViewById(R.id.btnEmails);
        mBtnText = findViewById(R.id.btnTexts);
        displayDetails();

        mBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        mBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }

    private void displayDetails() {
        Staff staff;
        staff = (Staff) getIntent().getSerializableExtra("key");
        assert staff != null;
        detailFirstname.setText(staff.getFirstname());
        detailLastname.setText(staff.getLastname());
        detailDob.setText(staff.getDateOfBirth());
        detailPhone.setText(staff.getPhone());
        detailEmail.setText(staff.getEmail());
    }

    private void sendEmail() {
        String recipientEmail = detailEmail.getText().toString();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");

        i.putExtra(Intent.EXTRA_EMAIL , new String[]{recipientEmail});
        i.putExtra(Intent.EXTRA_SUBJECT, "HAPPY ANNIVERSARY TO YOU");
        i.putExtra(Intent.EXTRA_TEXT , "Happy anniversary ");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(WorkDetailActivity.this, "No email client configured. Please check.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSms() {
        Uri uri = Uri.parse("smsto: " + detailPhone.getText().toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "From the office of the Country Director, We wish you happy anniversary! ");
        startActivity(intent);
    }
}