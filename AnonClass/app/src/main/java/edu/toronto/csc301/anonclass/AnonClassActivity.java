package edu.toronto.csc301.anonclass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import edu.toronto.csc301.anonclass.dummy.DummyContent;

public class AnonClassActivity extends AppCompatActivity
        implements EnrolledClassFragment.OnListFragmentInteractionListener {

    /**
     * current fragment loaded
     */
    private Fragment current_fragment = null;

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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        displaySelectedScreen(R.id.navigation_home);
        navigation.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
