package edu.toronto.csc301.anonclass;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.retMsg;

public class AnonClassActivity extends AppCompatActivity
        implements EnrolledClassFragment.OnListFragmentInteractionListener,
        CreateClassFragment.OnFragmentInteractionListener,
        JoinClassFragment.OnFragmentInteractionListener,
        ClassStarterFragment.OnClassStarterFragmentInteractionListener{

    /**
     * current fragment loaded
     */
    private Fragment current_fragment = null;
    private User user;
    private GetEnrolledClassTask mGetInfoTask;
    private JoinClassTask mJoinClassTask;

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
                current_fragment = null;
                break;
            case R.id.navigation_notifications:
                current_fragment = null;
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
        if (savedInstanceState != null){
            json = savedInstanceState.getString("user");
        } else {
            json = getIntent().getStringExtra("user");
        }

        this.user = User.deSerialize(json);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        displaySelectedScreen(R.id.navigation_home);
        navigation.getMenu().getItem(0).setChecked(true);

        attemptGetInfo();
    }

    private void attemptGetInfo() {
        if (mGetInfoTask != null) {
            return;
        }

        mGetInfoTask = new GetEnrolledClassTask(user.getEmail());
        mGetInfoTask.execute((Void) null);
    }

    private void attemptJoinClass(Course course) {
        if (mJoinClassTask!= null) {
            return;
        }

        mJoinClassTask = new JoinClassTask(user.getEmail(), course.getCourse_id());
        mJoinClassTask.execute((Void) null);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        System.out.println("onSaveInstanceState");
        savedInstanceState.putString("user", user.serialize());
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
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
        assert manager != null: "getFragmentManager failed, get null object instead";
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

    private void postExecute(){
        if (current_fragment instanceof EnrolledClassFragment){
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
    public void onClassClickedFromJoinClassFragment(Course course) {
        attemptJoinClass(course);
    }

    @Override
    public void onFragmentInteractionFromClassStarterFrag() {

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
            // TODO: mEmail attempt to request join course_id, expect success or fail

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }

            return retMsg.getErrorRet(0);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mGetInfoTask = null;

            if (ret.getErrorCode() == 0) {
                AnonClassActivity.this.attemptGetInfo();

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
            // TODO: attempt to get enrolled courses, expect to get a user object

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }
            User user = User.userFromServer("csc301@test.com", "abcde123",
                    "Henry", "Liao",false, Course.getDummyCourses());

            return retMsg.getUserRet(0, user);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mGetInfoTask = null;

            if (ret.getErrorCode() == 0) {
                AnonClassActivity.this.user = ret.getUser();

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
