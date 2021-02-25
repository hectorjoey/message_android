package hector.developers.birthdaywishes.list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hector.developers.birthdaywishes.R;
import hector.developers.birthdaywishes.activities.BirthDetailActivity;
import hector.developers.birthdaywishes.activities.LoginActivity;
import hector.developers.birthdaywishes.activities.MainActivity;
import hector.developers.birthdaywishes.adapter.StaffAdapter;
import hector.developers.birthdaywishes.adapter.WorkAdapter;
import hector.developers.birthdaywishes.api.RetrofitClient;
import hector.developers.birthdaywishes.model.Staff;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkListActivity extends AppCompatActivity {

    //    String TAG = "BirthList ACTIVITY";
    private Toolbar mToolbar;

    ProgressDialog loadingBar;
    private RecyclerView rv;
    private List<Staff> staffList;
    private WorkAdapter adapter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        setTitle("");
//        loadingBar = new ProgressDialog(this);

        date = String.valueOf(android.text.format.DateFormat.format("dd/M", new Date()));
        loadData("https://fhi360-message-service.herokuapp.com/api/v1/staffs");
    }

    public void loadData(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        AndroidNetworking.get(url)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            staffList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Staff model = new Staff();
                                    JSONObject obj = response.getJSONObject(i);
                                    String id = String.valueOf(obj.getLong("id"));
                                    String firstname = obj.getString("firstname");
                                    String lastname = obj.getString("lastname");
                                    String email = obj.getString("email");
                                    String dateOfBirth = obj.getString("dateOfBirth");
                                    String gender = obj.getString("gender");
                                    String employmentDate = obj.getString("employmentDate");
                                    String phone = obj.getString("phone");
                                    String designation = obj.getString("designation");
                                    model.setId(Long.valueOf(id));
                                    model.setFirstname(firstname);
                                    model.setLastname(lastname);
                                    model.setEmail(email);
                                    model.setGender(gender);
                                    model.setDateOfBirth(dateOfBirth);
                                    model.setEmploymentDate(employmentDate);
                                    model.setPhone(phone);
                                    model.setDesignation(designation);

                                    if (date.equals(employmentDate)) {
                                        staffList.add(model);
                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                adapter = new WorkAdapter(staffList, WorkListActivity.this);
                                rv.setAdapter(adapter);
                            }

                            Toast.makeText(getApplicationContext(), "Work Anniversary!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getMessage());
                        Log.d("Error::", anError.getMessage());
                    }
                });
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(WorkListActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}