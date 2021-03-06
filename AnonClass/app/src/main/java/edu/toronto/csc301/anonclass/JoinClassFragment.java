package edu.toronto.csc301.anonclass;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.toronto.csc301.anonclass.util.Course;
import edu.toronto.csc301.anonclass.util.PassingData;
import edu.toronto.csc301.anonclass.util.retMsg;


/**
 * a bottomSheetDialogFragment pops up from the bottom of the screen when the user clicks the
 * "Enroll" button in the home tab
 *
 * contains searchClassTask AsyncTask request a set of related course based on what the user has
 * typed
 */
public class JoinClassFragment extends BottomSheetDialogFragment {

    private OnFragmentInteractionListener mListener;
    private int mColumnCount = 2;
    private List<Course> searched = new ArrayList<>();
    private searchClassTask mSearchTask;
    private mEnrolledClassRecyclerViewAdapter mAdapter;

    public static JoinClassFragment newInstance(){
        return new JoinClassFragment();
    }

    public JoinClassFragment() {
        // Required empty public constructor
    }

    public void updateSearched(List<Course> lst){
        searched.clear();
        searched.addAll(lst);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_class, container, false);
        View found = view.findViewById(R.id.searchedRecyclerView);
        // Set the adapter
        if (found instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) found;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new mEnrolledClassRecyclerViewAdapter(searched, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        SearchView search = view.findViewById(R.id.search_view);
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("submitted");
                attemptSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private void attemptSearch(String str){
        if (mSearchTask != null) {
            return;
        }

        mSearchTask = new searchClassTask(str);
        mSearchTask.execute((Void) null);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onClassClickedFromJoinClassFragment(Course course);
    }

    /**
     * Represents an asynchronous task getting users enrolled class or created class
     */
    public class searchClassTask extends AsyncTask<Void, Void, retMsg> {

        private final String search;

        searchClassTask(String str) {
            search = str;
        }

        @Override
        protected retMsg doInBackground(Void... params) {
            //       takes a search string and send it to server
            //       server returns a retMsg obj which gives an error code and list of courses
            //       related to the search string

            return PassingData.ShowRelatedCourses(search);
        }

        @Override
        protected void onPostExecute(final retMsg ret) {
            mSearchTask = null;

            if (ret.getErrorCode() == 0) {
                JoinClassFragment.this.updateSearched(ret.getSearched());

            } else {
                JoinClassFragment.this.updateSearched(new ArrayList<Course>());
                Toast.makeText(JoinClassFragment.this.getContext(), "No results", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mSearchTask = null;
        }
    }
}
