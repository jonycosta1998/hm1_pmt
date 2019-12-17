package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4.tasks.TaskListContent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContact extends AppCompatActivity implements TaskInfoFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Button addButtonnew = (Button)findViewById(R.id.button);
        addButtonnew.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                EditText taskNameEditTxt = findViewById(R.id.editText_name);
                EditText taskSurnameEditTxt = findViewById(R.id.editText_surname);
                EditText taskBirthdayEditTxt = findViewById(R.id.editText_birthday);
                EditText taskPhoneNumberEditTxt = findViewById(R.id.editText_phoneNumber);
                Spinner drawableSpinner_new = findViewById(R.id.drawableSpinnerNew);

                String taskName = taskNameEditTxt.getText().toString();
                String taskSurname = taskSurnameEditTxt.getText().toString();
                String taskBirthday = taskBirthdayEditTxt.getText().toString();

                /* check inputs                                                         */
                //check if birthday follows pattern
                if(!DATE_PATTERN.matcher(taskBirthday).matches()) taskBirthday = "error";

                //date_today
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();

                //check if birthday is before today's date
                try {
                    Date date_birthday = new SimpleDateFormat("dd-MM-yyyy").parse(taskBirthday);
                    if (date.before(date_birthday)) taskBirthday = "error";
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /* check inputs                                                         */

                String taskPhoneNumber = taskPhoneNumberEditTxt.getText().toString();

                String selectedSong = drawableSpinner_new.getSelectedItem().toString();

                int random = new Random().nextInt(16) + 1;

                if ((taskName.isEmpty() && taskSurname.isEmpty()) || taskBirthday.equals("error")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                } else {
                    TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1,
                            taskName, taskSurname,
                            taskBirthday,
                            taskPhoneNumber, selectedSong, "avatar_" + String.valueOf(random)));
                }

                taskNameEditTxt.setText("");
                taskSurnameEditTxt.setText("");
                taskPhoneNumberEditTxt.setText("");
                taskBirthdayEditTxt.setText("");


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                finish();
                }

        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface DateMatcher {
        boolean matches(String date);
    }

    public Pattern DATE_PATTERN = Pattern.compile(
            "^(29-02-(2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26]))))$"
                    + "|^((0[1-9]|1[0-9]|2[0-8])-02-((19|2[0-9])[0-9]{2}))$"
                    + "|^((0[1-9]|[12][0-9]|3[01])-(0[13578]|10|12)-((19|2[0-9])[0-9]{2}))$"
                    + "|^((0[1-9]|[12][0-9]|30)-(0[469]|11)-((19|2[0-9])[0-9]{2}))$");




}

