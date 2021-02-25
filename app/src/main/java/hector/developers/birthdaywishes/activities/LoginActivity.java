package hector.developers.birthdaywishes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hector.developers.birthdaywishes.R;
import hector.developers.birthdaywishes.api.RetrofitClient;
import hector.developers.birthdaywishes.model.User;
import hector.developers.birthdaywishes.utils.SessionManagement;
import hector.developers.birthdaywishes.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mSubmit;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mSubmit = findViewById(R.id.btn_login);
        util = new Util();

        SessionManagement sessionManagement;
        sessionManagement = new SessionManagement(this);

        sessionManagement.getLoginEmail();
        sessionManagement.getLoginPassword();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                if (util.isNetworkAvailable(getApplicationContext())) {
                    if (TextUtils.isEmpty(email)) {
                        mEmail.setError("Enter Email!");
                        mEmail.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        mPassword.setError("Enter Password!");
                        mPassword.requestFocus();

                    }
                    loginUser(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Check your network connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String email, String password) {

        //do registration API call
        Call<User> call = RetrofitClient
                .getInstance()
                .getApi().login(email, password);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Throwing >>>>" + t);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}