package hector.developers.birthdaywishes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hector.developers.birthdaywishes.R;

public class StaffActivity extends AppCompatActivity {
EditText mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        mDate = findViewById(R.id.edit_date);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   initialize date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(StaffActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //   store date in string

                        month = month + 1;
                        String sDate = month + "/" + dayOfMonth + "/" + year;
                        // set date on view
                        mDate.setText(sDate);
                        String myFormat = "MM/dd/yy";   //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        mDate.setText(sdf.format(calendar.getTime()));

                    }
                }, year, month, day
                );
//                //disable future dates
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
//                //disable past dates
//                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                //show date
                datePickerDialog.show();
            }
        });
    }
}