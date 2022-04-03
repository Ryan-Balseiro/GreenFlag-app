package com.example.greenflag;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    private Button btnNext;
    private EditText email;
    private EditText password;
    private EditText passwordConfirm;
    private static final String TAG = "CreateAccount";
    private View backgroundEmail;
    private View backgroundPass1;
    private View backgroundPass2;

    //check marks
    private View emailCheck;
    private View pass1Check;
    private View pass2Check;

    //x marks
    private View emailX;
    private View pass1X;
    private View pass2X;

    private static final Pattern[] inputRegexes = new Pattern[3];

    static {
        inputRegexes[0] = Pattern.compile(".*[A-Z].*");
        inputRegexes[1] = Pattern.compile(".*[a-z].*");
        inputRegexes[2] = Pattern.compile(".*\\d.*");
        //inputRegexes[3] = Pattern.compile("8,"); //gave issues so I made a separate check for length
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

//        //get string from strings.xml
//        String title = getString(R.string.title);
//
//        //create back button
//        if (getSupportActionBar() != null) {
//            //call action bar
//            ActionBar ab = getSupportActionBar();
//            //customize back button
//            ab.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);
//            //show back button
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//            //set title
//            ab.setTitle(title);
//        }

        initViews();

    }

    //initialize views
    private void initViews(){
        //borders for textViews
        backgroundEmail = findViewById(R.id.background_email);
        backgroundPass1 = findViewById(R.id.background_pass1);
        backgroundPass2 = findViewById(R.id.background_pass2);

        //check mark imageViews
        emailCheck = findViewById(R.id.iv_email_check);
        pass1Check = findViewById(R.id.iv_pass1_check);
        pass2Check = findViewById(R.id.iv_pass2_check);

        //x mark imageViews
        emailX = findViewById(R.id.iv_email_x);
        pass1X = findViewById(R.id.iv_pass1_x);
        pass2X = findViewById(R.id.iv_pass2_x);

        //editTextViews
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        passwordConfirm = findViewById(R.id.et_password_repeat);

        //button and button listener
        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                runCheckManager(v);
            }
        });
    }

    private void runCheckManager(View view) {
        //check if 3 fields are correct
        //check email
        boolean emailTest = checkEmail();
        //check password 1
        boolean passwordTest = checkPassword();
        //check password 2
        boolean password2Test = checkPassword2();
        //if all passed, show victory toast
        if (emailTest == true && passwordTest == true && password2Test == true){
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "RunCheckManager: Account created");
        }
        else{//send message to log and display a toast saying which tests failed
            if(emailTest == false){
                Log.d(TAG, "RunCheckManager: Email failed test");
                Toast.makeText(this, "Email is incorrect", Toast.LENGTH_LONG).show();
            }
            if(passwordTest == false){
                Log.d(TAG, "RunCheckManager: password failed test");
                Toast.makeText(this, "Password doesn't meet requirements", Toast.LENGTH_LONG).show();
            }
            if(password2Test == false){
                Log.d(TAG, "RunCheckManager: password confirmation failed test");
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();
            }
        }

    }



    private boolean checkPassword() {
        //need to have an upper, lower, number, and 8 characters
        String testPass = password.getText().toString().trim();
        boolean inputMatches = true;

        //test for upper case, lower case, and number
        for (Pattern inputRegex : inputRegexes) {
            if (!inputRegex.matcher(testPass).matches()) {
                inputMatches = false;
            }
        }
        //test length
        if(inputMatches == true){
            if (testPass.length() >= 8){
                inputMatches = true;
            }
            else{
                inputMatches = false;
            }
        }
        //output to logcat and change background color
        if(inputMatches == true){
            Log.d(TAG, "checkPassword: Passed");
            changeBackground(2, "p");
        }
        else{
            Log.d(TAG, "checkPassword: Failed");
            changeBackground(2, "f");
        }
        return inputMatches;
    }

    //checks if both password entries match
    private boolean checkPassword2() {
        String pass1 = password.getText().toString().trim();
        String pass2 = passwordConfirm.getText().toString().trim();

        //test if password 1 matches password 2
        if (pass1.equals(pass2) == true){
            Log.d(TAG, "checkPassword2: Passed");
            changeBackground(3, "p");
            return true;
        }
        else{
            Log.d(TAG, "checkPassword2: Failed");
            changeBackground(3, "f");
            return false;
        }
    }

    //checks if email is a valid format
    private boolean checkEmail() {
        String testEmail = email.getText().toString().trim();
        if (Patterns.EMAIL_ADDRESS.matcher(testEmail).matches()){
            Log.d(TAG, "checkEmail: Passed");
                changeBackground(1,"p");
                return true;
        }
        else
        {
            changeBackground(1,"f");
            Log.d(TAG, "checkEmail: Failed");
            return false;
        }
    }

//    //logic for back button
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    private void changeBackground (int viewidID, String passFail){
        //passFail: p = pass / f = fail
        //viewID: 1 = email / 2 = password 1 / 3 = password 2

        //email
        if (viewidID == 1){
            if (passFail == "p"){
                //background green
                backgroundEmail.setBackgroundColor(Color.parseColor("#59CC00"));
                //toggle x mark off and check mark on
                emailCheck.setVisibility(View.VISIBLE);
                emailX.setVisibility(View.INVISIBLE);
            }
            else{
                //background red
                backgroundEmail.setBackgroundColor(Color.parseColor("#FF0000"));
                //toggle x mark on and check mark off
                emailCheck.setVisibility(View.INVISIBLE);
                emailX.setVisibility(View.VISIBLE);
            }
        }
        //pass1
        if (viewidID == 2){
            if (passFail == "p"){
                //background green
                backgroundPass1.setBackgroundColor(Color.parseColor("#00FF00"));
                pass1Check.setVisibility(View.VISIBLE);
                pass1X.setVisibility(View.INVISIBLE);
            }
            else{
                //background red
                backgroundPass1.setBackgroundColor(Color.parseColor("#FF0000"));
                pass1Check.setVisibility(View.INVISIBLE);
                pass1X.setVisibility(View.VISIBLE);
            }
        }
        //pass2
        if (viewidID == 3){
            if (passFail == "p"){
                //background green
                backgroundPass2.setBackgroundColor(Color.parseColor("#00FF00"));
                pass2Check.setVisibility(View.VISIBLE);
                pass2X.setVisibility(View.INVISIBLE);
            }
            else{
                //background red
                backgroundPass2.setBackgroundColor(Color.parseColor("#FF0000"));
                pass2Check.setVisibility(View.INVISIBLE);
                pass2X.setVisibility(View.VISIBLE);
            }
        }
    }//end changeBackground
}//endCreateAccount