package edu.toronto.csc301.anonclass;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {

    private View mProgressView;
    private View mRegisterView;
    private EditText mEmailField;
    private EditText mUtorid;
    private RadioGroup mStudentFlag;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPassword;
    private EditText mConfirm;
    private Button submit;
    private AsyncTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmailField = findViewById(R.id.signIn_emailField);
        mUtorid = findViewById(R.id.signIn_utoridField);
        mStudentFlag = findViewById(R.id.signIn_studentFlag);
        mFirstName = findViewById(R.id.signIn_firstNameField);
        mLastName = findViewById(R.id.signIn_lastNameField);
        mPassword = findViewById(R.id.signIn_pwdField);
        mConfirm = findViewById(R.id.signIn_pwdconfirmField);

        submit = findViewById(R.id.signIn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        mProgressView = findViewById(R.id.progress_bar);
        mRegisterView = findViewById(R.id.signIn_registerForm);
    }

    private void attemptRegister(){
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailField.setError(null);
        mUtorid.setError(null);
        mFirstName.setError(null);
        mLastName.setError(null);
        mPassword.setError(null);
        mConfirm.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.
        String email = mEmailField.getText().toString();
        String utorid = mUtorid.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String pwd = mPassword.getText().toString();
        String confirm = mConfirm.getText().toString();
        int indicator = mStudentFlag.getCheckedRadioButtonId();
        Boolean flag;
        switch (indicator) {
            case R.id.signIn_student:
                flag = true;
                break;
            case R.id.signIn_teacher:
                flag = false;
                break;
            default:
                Toast.makeText(this, "student or teacher", Toast.LENGTH_SHORT).show();
                focusView = mStudentFlag;
                cancel = true;
                break;
        }




        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.error_field_required));
            focusView = mEmailField;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailField.setError(getString(R.string.error_invalid_email));
            focusView = mEmailField;
            cancel = true;
        }

        // Check for a first name
        if (!TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getString(R.string.error_incorrect_firstName));
            focusView = mFirstName;
            cancel = true;
        }
        // Check for a last name
        if (!TextUtils.isEmpty(lastName)) {
            mLastName.setError(getString(R.string.error_incorrect_lastName));
            focusView = mLastName;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pwd) && !isPasswordValid(pwd)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }
        // Check for confirmed pwd
        if (!confirm.equals(pwd)) {
            mConfirm.setError(getString(R.string.error_incorrect_confirmPwd));
            focusView = mConfirm;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new registerActivity.UserRegisterTask("","");
            mAuthTask.execute((Void) null);
        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserRegisterTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
