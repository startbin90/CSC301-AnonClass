package edu.toronto.csc301.anonclass;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
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
        JoinClassFragment.OnFragmentInteractionListener{

    /**
     * current fragment loaded
     */
    private Fragment current_fragment = null;
    private User user;
    private GetEnrolledClassTask mGetInfoTask;

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

        String json = getIntent().getStringExtra("user");
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

    @Override
    public void onRefreshInfo() {
        attemptGetInfo();
    }

    @Override
    public void onClassClicked(Course course) {

    }

    @Override
    public List<Course> onRequestCourses() {
        return user.getCourses();
    }

    @Override
    public boolean isUserStudent() {
        return user.getIsStudent();
    }

    private void postExecute(){
        if (current_fragment instanceof EnrolledClassFragment){
            ((EnrolledClassFragment) current_fragment).onRefreshFinished();
        }
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return null;
            }
            User user = User.userFromServer("csc301@test.com", "abcde123",
                    "Henry", "Liao",false, Course.getDummyCourses());

            return new retMsg(0, user);
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
