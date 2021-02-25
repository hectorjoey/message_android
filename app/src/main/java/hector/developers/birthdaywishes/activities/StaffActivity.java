package hector.developers.birthdaywishes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hector.developers.birthdaywishes.R;
import hector.developers.birthdaywishes.api.RetrofitClient;
import hector.developers.birthdaywishes.model.Staff;
import hector.developers.birthdaywishes.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffActivity extends AppCompatActivity {

    EditText mDateOfBirth, mDateOfEmployment, mLastName, mFirstName, mEmail, mPhone, mDesignation;
    Button mSave;
    Spinner mGender;

    //     int day,month,year;
    final Calendar cldr = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        mDateOfBirth = findViewById(R.id.dateOfBirth);
        mDateOfEmployment = findViewById(R.id.doEmployment);
        mFirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastname);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        mDesignation = findViewById(R.id.designation);
        mSave = findViewById(R.id.btnSave);
        mGender = findViewById(R.id.genderSpinner);

        mDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(StaffActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                mDateOfBirth.setText(dayOfMonth + "/" + monthOfYear);
                            }
                        }, year, month, day);
                //disable past dates
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                // show date dialog
                datePicker.show();
            }
        });

        mDateOfEmployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(StaffActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                mDateOfEmployment.setText(dayOfMonth + "/" + monthOfYear);
                            }
                        }, year, month, day);
                //disable past dates
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                //show date dialog
                datePicker.show();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstname = mFirstName.getText().toString().trim();
                final String lastname = mLastName.getText().toString().trim();
                final String dateOfBirth = mDateOfBirth.getText().toString().trim();
                final String dateOfEmployment = mDateOfEmployment.getText().toString().trim();
                final String phone = mPhone.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String designation = mDesignation.getText().toString().trim();
                final String gender = String.valueOf(mGender.getSelectedItem());

                if (TextUtils.isEmpty(firstname)) {
                    mFirstName.setError("first name is required!");
                    mFirstName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(lastname)) {
                    mLastName.setError("last name is required!");
                    mLastName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    mPhone.setError("Phone number is required!");
                    mPhone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required!");
                    mEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmail.setError("Enter a valid email!");
                    mEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(dateOfBirth)) {
                    mDateOfBirth.setError("Date of Birth is required!");
                    mDateOfBirth.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(designation)) {
                    mDesignation.setError("Designation is required!");
                    mDesignation.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(dateOfEmployment)) {
                    mDateOfEmployment.setError("Date of Employment is required!");
                    mDateOfEmployment.requestFocus();
                }
                saveUser(firstname, lastname, gender, dateOfBirth, dateOfEmployment, phone, email, designation);
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mGender.setAdapter(genderAdapter);
    }

    private void saveUser(String firstname, String lastname, String gender, String dateOfBirth,
                          String dateOfEmployment, String phone, String email, String designation) {
//do registration API call
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating ...");
        progressDialog.show();
        Call<Staff> call = RetrofitClient
                .getInstance()
                .getApi()
                .createStaff(firstname, lastname, gender, dateOfBirth, dateOfEmployment, phone, email, designation);
        call.enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                progressDialog.dismiss();
                Toast.makeText(StaffActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                System.out.println("Responding ::: " + response);
                clearFields();
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(StaffActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                System.out.println("throwing " + t);
            }
        });

    }

    private void clearFields() {
        mDateOfBirth.setText("");
        mDateOfEmployment.setText("");
        mLastName.setText("");
        mFirstName.setText("");
        mEmail.setText("");
        mPhone.setText("");
        mDesignation.setText("");
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(StaffActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}