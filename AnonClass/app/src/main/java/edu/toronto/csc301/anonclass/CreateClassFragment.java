package edu.toronto.csc301.anonclass;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.retMsg;


/**
 *
 */
public class CreateClassFragment extends BottomSheetDialogFragment {

    private OnFragmentInteractionListener mListener;
    private EditText mCourseCode;
    private EditText mCourseName;
    private EditText mSection;
    private EditText mLocation;
    private CreateClassTask mTask;
    public static CreateClassFragment newInstance(){
        return new CreateClassFragment();
    }
    public CreateClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_class, container, false);
        mCourseCode = view.findViewById(R.id.course_codeField);
        mCourseName = view.findViewById(R.id.course_nameField);
        mSection = view.findViewById(R.id.sectionField);
        mLocation = view.findViewById(R.id.locationField);

        Button submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreate();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    private void attemptCreate() {
        if (mTask != null) {
            return;
        }

        // Reset errors.
        mCourseCode.setError(null);
        mCourseName.setError(null);
        mSection.setError(null);
        mLocation.setError(null);

        // Store values at the time of the login attempt.
        String course_code = mCourseCode.getText().toString();
        String course_name = mCourseName.getText().toString();
        String section = mSection.getText().toString();
        String location = mLocation.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid course code, should be 6 digits long
        if (course_code.length() != 6) {
            mCourseCode.setError(getString(R.string.error_field_required));
            focusView = mCourseCode;
            cancel = true;
        }

        // Check for a course name. should not be empty
        if (TextUtils.isEmpty(course_name)) {
            mCourseName.setError(getString(R.string.error_field_required));
            focusView = mCourseName;
            cancel = true;
        }

        // check for section. cannot be empty
        if (TextUtils.isEmpty(section)) {
            mSection.setError(getString(R.string.error_field_required));
            focusView = mSection;
            cancel = true;
        }

        // check for location. cannot be empty
        if (TextUtils.isEmpty(location)) {
            mLocation.setError(getString(R.string.error_field_required));
            focusView = mLocation;
            cancel = true;
        }

        User teacher = mListener.requestUserInfoFromCreateClassFrag();
        if (teacher.getStudentFlag()){
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);
            Course course = Course.getCreatedCourse(course_code,course_name, section, teacher.getEmail(),
                    teacher.getFirstName() + " " + teacher.getLastName(), location);
            mTask = new CreateClassTask(course);
            mTask.execute((Void) null);
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        User requestUserInfoFromCreateClassFrag();
        void onRequestRefreshFromCreateClassFrag();
    }

    public class CreateClassTask extends AsyncTask<Void, Void, retMsg> {

        private final Course mCourse;

        CreateClassTask(Course course) {
            mCourse = course;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            // TODO: attempt to request to create a class.

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
            mTask = null;
            //showProgress(false);

            if (ret.getErrorCode() == 0) {
                CreateClassFragment.this.dismiss();
                mListener.onRequestRefreshFromCreateClassFrag();

            } else {
                Toast.makeText(CreateClassFragment.this.getContext(), "Create Class failed",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mTask = null;
            // showProgress(false);
        }
    }
}
