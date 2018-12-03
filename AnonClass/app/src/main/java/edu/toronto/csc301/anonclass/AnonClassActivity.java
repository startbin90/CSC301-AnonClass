package edu.toronto.csc301.anonclass;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.PassingData;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.instructorMessage;
import edu.toronto.csc301.anonclass.util.retMsg;

/**
 * LoginActivity -> this
 *
 * There are six fragments communicating back with this
 * parent activity through interface callback
 *
 * This activity has three child fragments:
 * 1. EnrolledClassFragment
 * 2. SettingsFragment
 * 3. InstructorMessageFragment
 *
 * Also contains two AsyncTask sub class
 * 1. JoinClassTask used to join/enroll a new course
 * 2. GetEnrolledClassTask used to retrieve student's enrolled class list
 *
 */
public class AnonClassActivity extends AppCompatActivity
        implements EnrolledClassFragment.OnListFragmentInteractionListener,
        CreateClassFragment.OnFragmentInteractionListener,
        JoinClassFragment.OnFragmentInteractionListener,
        ClassStarterFragment.OnClassStarterFragmentInteractionListener,
        SettingsFragment.OnSettingsFragmentInteractionListener,
        InstructorMessageFragment.OnInstructorMessageListFragmentInteractionListener {

    /**
     * current fragment loaded
     */
    private Fragment current_fragment = null;
    private User user;
    private GetEnrolledClassTask mGetInfoTask;
    private JoinClassTask mJoinClassTask;

    private FusedLocationProviderClient mFusedLocationClient;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            //calling the method displaySelectedScreen and passing the id of selected menu
            displaySelectedScreen(id);
            //make this method blank
            return true;
        }
    };

    /**
     * set current_fragment and commit changes
     */
    public void displaySelectedScreen(int itemId) {
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.navigation_home:
                current_fragment = EnrolledClassFragment.newInstance(this);
                break;
            case R.id.navigation_dashboard:
                current_fragment = new SettingsFragment();
                break;
            case R.id.navigation_notifications:
                current_fragment = new InstructorMessageFragment();
                break;

        }

        //replacing the fragment
        if (current_fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_holder, current_fragment);
            ft.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anon_class);
        String json;
        if (savedInstanceState != null) {
            json = savedInstanceState.getString("user");
        } else {
            json = getIntent().getStringExtra("user");
        }

        this.user = User.deSerialize(json);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        displaySelectedScreen(R.id.navigation_home);
        navigation.getMenu().getItem(1).setChecked(true);

        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(AnonClassActivity.this,
                        permissions,
                        1234);
            }
        } else {
            ActivityCompat.requestPermissions(AnonClassActivity.this,
                    permissions,
                    1234);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void attemptGetInfo() {
        if (mGetInfoTask != null) {
            return;
        }

        mGetInfoTask = new GetEnrolledClassTask(user.getEmail());
        mGetInfoTask.execute((Void) null);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        System.out.println("onSaveInstanceState");
        savedInstanceState.putString("user", user.serialize());
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        System.out.println("onRestoreInstanceState");
    }

    @Override
    public void onRefreshInfo() {
        attemptGetInfo();
    }

    @Override
    public void onClassClickedFromEnrolledClassFragment(Course course) {
        FragmentManager manager = getSupportFragmentManager();
        BottomSheetDialogFragment bottomSheet = ClassStarterFragment.newInstance(user, course);
        assert manager != null : "getFragmentManager failed, get null object instead";
        bottomSheet.show(manager, "ClassStarterFragment");
    }

    @Override
    public List<Course> onRequestCourses() {
        return user.getCourses();
    }

    @Override
    public boolean isUserStudent() {
        return user.getStudentFlag();
    }

    private void postExecute() {
        if (current_fragment instanceof EnrolledClassFragment) {
            ((EnrolledClassFragment) current_fragment).onRefreshFinished();
        }
    }

    @Override
    public User requestUserInfoFromCreateClassFrag() {
        return user;
    }

    @Override
    public void onRequestRefreshFromCreateClassFrag() {
        attemptGetInfo();
    }

    @Override
    public void onClassClickedFromJoinClassFragment(final Course course) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Join this class?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        attemptJoinClass(course);
                        ((BottomSheetDialogFragment) getSupportFragmentManager().
                                findFragmentByTag("JoinClassFragment")).dismiss();
                    }
                });
        alertDialog.show();

    }

    private void attemptJoinClass(Course course) {
        if (mJoinClassTask != null) {
            return;
        }

        mJoinClassTask = new JoinClassTask(user.getEmail(), course.getCourse_id());
        mJoinClassTask.execute((Void) null);
    }

    @Override
    public void onGetLocationFromClassStarterFrag() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    ClassStarterFragment frag = (ClassStarterFragment) getSupportFragmentManager().findFragmentByTag("ClassStarterFragment");
                    frag.onLocationUpdated(location);
                }
            });
        }

    }

    @Override
    public void onInstructorMessageListFragmentInteraction(instructorMessage message) {

    }

    @Override
    public void onSettingsFragmentRequestLogOff() {
        this.finish();
        Intent launch = new Intent(AnonClassActivity.this, LoginActivity.class);
        AnonClassActivity.this.startActivity(launch);

    }


    /**
     * Represents an asynchronous task requesting join a class
     */
    public class JoinClassTask extends AsyncTask<Void, Void, retMsg> {

        private final String mEmail;
        private final int course_id;

        JoinClassTask(String email, int course_id) {
            this.mEmail = email;
            this.course_id = course_id;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            // mEmail attempt to request join course_id, expect success or fail
            if (LoginActivity.DEBUG == 1){
                return retMsg.getErrorRet(0);
            }
            return PassingData.EnrolCourse(mEmail, course_id);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mJoinClassTask = null;

            if (ret.getErrorCode() == 0) {
                AnonClassActivity.this.attemptGetInfo();

            } else {
                Toast.makeText(AnonClassActivity.this, "join class failed", Toast.LENGTH_SHORT).show();
            }
            postExecute();
        }

        @Override
        protected void onCancelled() {
            mJoinClassTask = null;
            //showProgress(false);
        }
    }
    /**
     * Represents an asynchronous task getting users enrolled class or created class
     */
    public class GetEnrolledClassTask extends AsyncTask<Void, Void, retMsg> {

        private final String mEmail;

        GetEnrolledClassTask(String email) {
            mEmail = email;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            //       attempt to get enrolled courses, expect to get a retMsg with error code and
            //       user obj, this is similar to what was done in login process.
            //       we update the user object all together here

            if (LoginActivity.DEBUG == 1){
                User user = User.fakeUserFromServer("csc301@test.com", "abcde123",
                        "Henry", "Liao",true, Course.getUpdatedEnrolledDummyCourses());
                return retMsg.getUserRet(0, user);
            }

            return PassingData.DisplayCourses(mEmail);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mGetInfoTask = null;

            if (ret.getErrorCode() == 0) {
                AnonClassActivity.this.user.getCourses().clear();
                AnonClassActivity.this.user.getCourses().addAll(ret.getUser().getCourses());
            } else {
                Toast.makeText(AnonClassActivity.this, "get info failed", Toast.LENGTH_SHORT).show();
            }
            postExecute();
        }

        @Override
        protected void onCancelled() {
            mGetInfoTask = null;
            //showProgress(false);
        }
    }
}
