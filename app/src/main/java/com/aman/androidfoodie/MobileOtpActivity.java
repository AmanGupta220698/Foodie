package com.aman.androidfoodie;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MobileOtpActivity extends AppCompatActivity {

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;
    String verificationcode;
    private FirebaseAuth auth;
    EditText mobile_login_module;
    Button getotp;
    String mobilelength;
    int lenthflag=0;


    private static final long START_TIME_IN_MILLIS=60000;

    private Button mLogin;
    private CountDownTimer mCountDownTimer;
    private boolean mtimerRunning;
    private long mTimeLeftInMills=START_TIME_IN_MILLIS;
    EditText  e1,e2,e3,e4,e5,e6;
    int flag=1;
    Boolean b=true;
    TextView reotp,renum ,countdown;



    private void updateCountDownText() {
        int minutes=(int)(mTimeLeftInMills/1000)/60;
        int seconds=(int)(mTimeLeftInMills/1000)%60;

        String timeleftFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        countdown.setText(timeleftFormatted);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp);
        mobile_login_module=findViewById(R.id.edit_mobile_login_module);
        getotp=findViewById(R.id.btn_otp_login_module);
        countdown=findViewById(R.id.countdown_login_module);
        countdown.setVisibility(View.INVISIBLE);
        reotp=findViewById(R.id.btn_reotp_login_module);
        renum=findViewById(R.id.btn_renum_login_module);
        /*getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);*/
        auth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        resendOtp();
        reotp.setClickable(false);

        renum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile_login_module.setEnabled(true);
                Toast.makeText(MobileOtpActivity.this, "Edit Your Number", Toast.LENGTH_SHORT).show();


            }
        });

        reotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0) {
                    countdown.setVisibility(View.VISIBLE);

                    String number = mobile_login_module.getText().toString().trim();
                    startPhoneNumberVerification(number);


                    /*timer();
                    resendOtp()*/
                    ;
                    mCountDownTimer.start();
                    Toast.makeText(MobileOtpActivity.this, "dtjj", Toast.LENGTH_SHORT).show();

                }


            }
        });

      /*  getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






            }
        });*/



        e1 = findViewById(R.id.edit_text1_otp_login_module);
        e2 = findViewById(R.id.edit_text2_otp_login_module);
        e3 = findViewById(R.id.edit_text3_otp_login_module);
        e4 = findViewById(R.id.edit_text4_otp_login_module);
        e5 = findViewById(R.id.edit_text5_otp_login_module);
        e6 = findViewById(R.id.edit_text6_otp_login_module);



        e1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(e1.getText().toString().trim().length() == 1){
                    e2.requestFocus();

                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }
            public void afterTextChanged(Editable s) {

            }
        });

        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (e2.getText().toString().trim().length() == 1)     //size as per your requirement
                {
                    e3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    if (e2.length() == 0) {
                        e1.requestFocus();
                    }

                }}
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (e3.getText().toString().trim().length() == 1)     //size as per your requirement
                {
                    e4.requestFocus();
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    if (e3.length() == 0) {
                        e2.requestFocus();
                    }

                }

            }
        });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (e4.getText().toString().trim().length() == 1)     //size as per your requirement
                {
                    e5.requestFocus();
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    if (e4.length() == 0) {
                        e3.requestFocus();
                    }

                }

            }
        });
        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (e5.getText().toString().trim().length() == 1)     //size as per your requirement
                {
                    e6.requestFocus();
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    if (e5.length() == 0) {
                        e4.requestFocus();
                    }

                }

            }
        });
        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String digit1 = e1.getText().toString();
                String digit2 = e2.getText().toString();
                String digit3 = e3.getText().toString();
                String digit4 = e4.getText().toString();
                String digit5 = e5.getText().toString();
                String digit6 = e6.getText().toString();
                String otp=digit1+digit2+digit3+digit4+digit5+digit6;
                String mobile=mobile_login_module.getText().toString().trim();
                if (mobile.length()<10){
                    mobile_login_module.setError(getString(R.string.enter_mobile));
                    return;
                }
                verifyPhoneNumber(verificationcode, otp);


                if (otp.equals(verificationcode)) {
                    if (e6.getText().toString().trim().length() == 1)     //size as per your requirement
                    {
                        Intent login = new Intent(MobileOtpActivity.this, MainActivity.class);
                        mCountDownTimer.cancel();

                        startActivity(login);
                    }
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    if (e6.length() == 0) {
                        e5.requestFocus();
                    }}
            }
        });








    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mcallback);}
    public void sendsms(View v){
        flag=1;

        String number= mobile_login_module.getText().toString().trim();
        if(number.length()==10){
            countdown.setVisibility(View.VISIBLE);
            mobile_login_module.setEnabled(false);
            Toast.makeText(MobileOtpActivity.this, "Disable", Toast.LENGTH_SHORT).show();
            timer();
            startPhoneNumberVerification(number);




        }else mobile_login_module.setError("Enter 10 digit Mobile Number");
    }
    private void resendOtp() {
        mcallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {


            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationcode=s;
                Toast.makeText(MobileOtpActivity.this, "Code sent to this number", Toast.LENGTH_SHORT).show();
            }
        };
    }


    public void signInWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MobileOtpActivity.this, "OTP Verified", Toast.LENGTH_SHORT).show();

                    if(b==true){
                        Intent i =new Intent(MobileOtpActivity.this,MainActivity.class);
                        startActivity(i);}
                    else if(b==false){
                        startActivity(new Intent(MobileOtpActivity.this, MainActivity.class));
                    }
                }
                else
                    //inputotp.setError(getString(R.string.Invalid_Otp));
                    Toast.makeText(MobileOtpActivity.this, "OTP Not Verified", Toast.LENGTH_SHORT).show();

            }
        });
    }
    /*public void verify(View view){
        String input_code=inputotp.getText().toString();
        String mobile=inputMobile.getText().toString().trim();
        if (mobile.length()<10){
            inputMobile.setError(getString(R.string.enter_mobile));
            return;
        }
        verifyPhoneNumber(verificationcode, input_code);


    }*/


    public void verifyPhoneNumber(String verificationcode, String input_code) {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationcode,input_code);
        signInWithPhone(credential);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }


    private void timer() {
        if(flag==1){
            mCountDownTimer=new CountDownTimer(mTimeLeftInMills,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMills=millisUntilFinished;
                    updateCountDownText();

                }

                @Override
                public void onFinish() {
                    mtimerRunning=false;
                    Toast.makeText(MobileOtpActivity.this, "Sessipon expired", Toast.LENGTH_SHORT).show();
                   /* Intent back=new Intent(MobileOtpActivity.this,PopupActivity.class);
                    startActivity(back);*/
                   flag=0;

                   reotp.setClickable(true);
                }
            }.start();
            mtimerRunning=true;


        }
    }
}
