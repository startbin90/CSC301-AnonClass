package edu.toronto.csc301.anonclass;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.PassingData;
import edu.toronto.csc301.anonclass.util.Session;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.retMsg;

/**
 *
 */
public class ClassStarterFragment extends BottomSheetDialogFragment {

    private OnClassStarterFragmentInteractionListener mListener;
    private attendClassTask mAttendClassTask;
    private TextView mCourseCode;
    private TextView mCourseName;
    private TextView mSection;
    private TextView mInstructor;
    private TextView mTime;
    private Course course;
    private User user;
    private boolean isLocationSet = false;
    private double latitude;
    private double longitude;


    public static ClassStarterFragment newInstance(User user, Course course){
        ClassStarterFragment obj = new ClassStarterFragment();
        obj.course = course;
        obj.user = user;
        return obj;
    }
    public ClassStarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_starter, container, false);
        mCourseCode = view.findViewById(R.id.course);
        mCourseName = view.findViewById(R.id.course_name);
        mSection = view.findViewById(R.id.section);
        mInstructor = view.findViewById(R.id.instructor);
        mTime = view.findViewById(R.id.time);

        mCourseCode.setText(course.getCourse_code());
        mCourseName.setText(course.getCourse_name());
        mSection.setText(course.getSection_number());
        mInstructor.setText(course.getInstructor_name());
        mTime.setText(course.getTime_created());

        Button location = view.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: attempt to get user's location and set latitude and longitude attributes
                mListener.onGetLocationFromClassStarterFrag();

                setLocation(0,0);
            }
        });

        Button start = view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptJoinClass();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClassStarterFragmentInteractionListener) {
            mListener = (OnClassStarterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnClassStarterFragmentInteractionListener {
        void onGetLocationFromClassStarterFrag();
    }

    public void onLocationUpdated(Location loc){
        this.isLocationSet = true;
        System.out.println(loc.getLongitude());
        System.out.println(loc.getLatitude());
    }
    private void setLocation(float latitude, float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLocationSet = true;
    }

    private void attemptJoinClass(){
        if (mAttendClassTask != null) {
            return;
        }

        if (!isLocationSet) {
            Toast.makeText(getContext(), "Location not set", Toast.LENGTH_SHORT).show();
            return;
        }

       mAttendClassTask = new attendClassTask(Session.requestSession(user.getEmail(), course.getCourse_id(), latitude, longitude));
        mAttendClassTask.execute((Void) null);
    }

    public class attendClassTask extends AsyncTask<Void, Void, retMsg> {

        private final Session session;

        attendClassTask(Session session) {
            this.session = session;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            // TODO: attempt to request join or open a class, expect a session number

            return PassingData.JoinSession(session);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mAttendClassTask = null;

            if (ret.getErrorCode() == 0) {
                Intent launch = new Intent(ClassStarterFragment.this.getContext(),  InClassActivity.class);
                launch.putExtra("user", user.serialize());
                launch.putExtra("course", course.serialize());
                ClassStarterFragment.this.startActivity(launch);
            } else {
                Toast.makeText(ClassStarterFragment.this.getContext(),
                        "attend class failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAttendClassTask = null;
            //showProgress(false);
        }
    }

}
