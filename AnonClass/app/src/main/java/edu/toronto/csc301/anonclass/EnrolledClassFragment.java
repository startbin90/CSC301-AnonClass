package edu.toronto.csc301.anonclass;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import edu.toronto.csc301.anonclass.util.Course;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EnrolledClassFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 2;
    private Activity context;
    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout mRefreshLayout;

    public static EnrolledClassFragment newInstance(Activity act){
        EnrolledClassFragment instance = new EnrolledClassFragment();
        instance.context = act;
        return instance;
    }
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EnrolledClassFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enrolledclass_list, container, false);
        View found = view.findViewById(R.id.enrolled_class_list);
        // Set the adapter
        if (found instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) found;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyEnrolledClassRecyclerViewAdapter(mListener.onRequestCourses(), mListener));
        }
        Button create = view.findViewById(R.id.create);
        Button enroll = view.findViewById(R.id.enroll);
        if (mListener.isUserStudent()){
            create.setVisibility(View.GONE);
            enroll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    BottomSheetDialogFragment bottomSheet = JoinClassFragment.newInstance();
                    assert manager != null: "getFragmentManager failed, get null object instead";
                    bottomSheet.show(manager, "CreateClassFragment");
                }
            });
        } else {
            enroll.setVisibility(View.GONE);
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    BottomSheetDialogFragment bottomSheet = CreateClassFragment.newInstance();

                    assert manager != null: "getFragmentManager failed, get null object instead";
                    bottomSheet.show(manager, "JoinClassFragment");
                }
            });
        }
        mRefreshLayout = view.findViewById(R.id.account_detail_swipeRefreshLayout);
        mRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary
                , R.color.green
                , R.color.dividerGrey
                , R.color.colorAccent);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                mListener.onRefreshInfo();
            }
        });
        return view;
    }

    void onRefreshFinished(){
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
    public interface OnListFragmentInteractionListener {
        void onRefreshInfo();
        void onClassClickedFromEnrolledClassFragment(Course course);
        List<Course> onRequestCourses();
        boolean isUserStudent();
    }

}
