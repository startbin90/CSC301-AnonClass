package edu.toronto.csc301.anonclass;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.Session;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.retMsg;

/**
 *
 */
public class ClassStarterFragment extends BottomSheetDialogFragment {

    private OnClassStarterFragmentInteractionListener mListener;
    private GetClassStatusTask mGetClassStatusTask;
    private attendClassTask mAttendClassTask;
    private TextView mCourseCode;
    private TextView mCourseName;
    private TextView mSection;
    private TextView mInstructor;
    private TextView mTime;
    private TextView statusView;
    private int classStatus = -1; // -1 unknown, 0 on, 1 off
    private Course course;
    private User user;
    private boolean isLocationSet = false;
    private float latitude;
    private float longitude;

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
        statusView = view.findViewById(R.id.status);

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
        attemptGetClassStatus();
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
        void onFragmentInteractionFromClassStarterFrag();
    }

    private void setLocation(float latitude, float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLocationSet = true;
    }
    private void updateStatus(int status){
        if (status == 0){
            this.statusView.setText("On");
            this.classStatus = 0;
        } else if (status == 1){
            this.statusView.setText("Off");
            this.classStatus = 1;
        }
    }

    private void attemptJoinClass(){
        if (mAttendClassTask != null) {
            return;
        }

        if (!isLocationSet) {
            return;
        }

        mAttendClassTask = new attendClassTask(Session.requestSeesion(user.getEmail(), course, latitude, longitude));
        mAttendClassTask.execute((Void) null);
    }

    private void attemptGetClassStatus(){
        if (mGetClassStatusTask != null) {
            return;
        }

        mGetClassStatusTask = new GetClassStatusTask(course.getCourse_id());
        mGetClassStatusTask.execute((Void) null);
    }

    public class attendClassTask extends AsyncTask<Void, Void, retMsg> {

        private final Session session;

        attendClassTask(Session session) {
            this.session = session;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            // TODO: attempt to request join or open a class

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
            mGetClassStatusTask = null;

            if (ret.getErrorCode() == 0) {
                Intent launch = new Intent(ClassStarterFragment.this.getContext(),  InClassActivity.class);
                launch.putExtra("user", user.serialize());
                ClassStarterFragment.this.startActivity(launch);
            } else {
                Toast.makeText(ClassStarterFragment.this.getContext(),
                        "get class status failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mGetClassStatusTask = null;
            //showProgress(false);
        }
    }
    /**
     * Represents an asynchronous task getting class status
     */
    public class GetClassStatusTask extends AsyncTask<Void, Void, retMsg> {

        private final int class_id;

        GetClassStatusTask(int class_id) {
            this.class_id = class_id;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            // TODO: attempt to get class status 0 on 1 off, -1 error

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
            mGetClassStatusTask = null;

            if (!(ret.getErrorCode() == -1)) {
                ClassStarterFragment.this.updateStatus(ret.getErrorCode());
            } else {
                Toast.makeText(ClassStarterFragment.this.getContext(),
                        "get class status failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mGetClassStatusTask = null;
            //showProgress(false);
        }
    }
}
