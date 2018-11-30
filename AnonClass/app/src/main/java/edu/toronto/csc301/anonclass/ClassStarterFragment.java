package edu.toronto.csc301.anonclass;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;

import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.PassingData;
import edu.toronto.csc301.anonclass.util.Session;
import edu.toronto.csc301.anonclass.util.User;
import edu.toronto.csc301.anonclass.util.mLocationGetter;
import edu.toronto.csc301.anonclass.util.retMsg;

/**
 *
 */
public class ClassStarterFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

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
    private Location location;
    private static String TAG = "ClassStarterFragment";
    private static String MAP_VIEW_BOUDLE_KEY = "mapViewBundleKey";

    private MapView mMapView;
    private GoogleMap map;
    private mLocationGetter.LocationResult locationResult;
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

        mMapView = view.findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BOUDLE_KEY);
        }
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);


        locationResult = new mLocationGetter.LocationResult(){
            @Override
            public void gotLocation(Location location){
                //Got the location!
                onLocationUpdated(location);
                if (location != null){
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                            .zoom(16)                   // Sets the zoom
                            .bearing(0)                // Sets the orientation of the camera to east
                            .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(), location.getLongitude()));
                    circleOptions.radius(30);
                    circleOptions.strokeColor(R.color.dividerGrey);
                    circleOptions.fillColor(R.color.transBlue);
                    circleOptions.strokeWidth(2);
                    map.addCircle(circleOptions);
                }
                Log.d(TAG, String.format("lat: %.2f; long: %.2f", location.getLatitude(), location.getLongitude()));
            }
        };

        ImageButton location = view.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationGetter myLocation = new mLocationGetter();
                myLocation.getLocation(getContext(), locationResult);

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BOUDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BOUDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            final View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            final View view = getView();
            view.post(new Runnable() {
                @Override
                public void run() {
                    View parent = (View) view.getParent();
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                    CoordinatorLayout.Behavior behavior = params.getBehavior();
                    BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
                    bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
                    //((View)bottomSheet.getParent()).setBackgroundColor(Color.TRANSPARENT);
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        mLocationGetter myLocation = new mLocationGetter();
        myLocation.getLocation(getContext(), locationResult);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
        this.location = loc;
    }

    private void attemptJoinClass(){
        if (mAttendClassTask != null) {
            return;
        }

        if (!isLocationSet) {
            Toast.makeText(getContext(), "Location not set", Toast.LENGTH_SHORT).show();
            return;
        }

        mAttendClassTask = new attendClassTask(Session.requestSession(user.getEmail(),
                course.getCourse_id(), location.getLatitude(), location.getLongitude()));
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

            if (LoginActivity.DEBUG == 1){
                return retMsg.getErrorRet(0);
            }
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
                ClassStarterFragment.this.dismiss();
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
