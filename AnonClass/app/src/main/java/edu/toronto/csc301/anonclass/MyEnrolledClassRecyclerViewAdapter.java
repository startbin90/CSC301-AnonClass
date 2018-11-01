package edu.toronto.csc301.anonclass;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import edu.toronto.csc301.anonclass.EnrolledClassFragment.OnListFragmentInteractionListener;
import edu.toronto.csc301.anonclass.util.Course;

/**

 */
public class MyEnrolledClassRecyclerViewAdapter extends RecyclerView.Adapter<MyEnrolledClassRecyclerViewAdapter.ViewHolder> {

    private final List<Course> mCourses;
    private final OnListFragmentInteractionListener mEnrolledClassListener;
    private final JoinClassFragment.OnFragmentInteractionListener mJoinClassListener;

    public MyEnrolledClassRecyclerViewAdapter(List<Course> items, OnListFragmentInteractionListener listener) {
        mCourses = items;
        mJoinClassListener = null;
        mEnrolledClassListener = listener;
    }

    public MyEnrolledClassRecyclerViewAdapter(List<Course> items, JoinClassFragment.OnFragmentInteractionListener listener) {
        mCourses = items;
        mEnrolledClassListener = null;
        mJoinClassListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_enrolledclass, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mCourses.get(position);
        holder.mCourse.setText(mCourses.get(position).getCourse_code());
        holder.mSection.setText(mCourses.get(position).getSection_number());
        holder.mInstructor.setText(mCourses.get(position).getInstructor_name());
        Date date = mCourses.get(position).getTime_created();
        holder.mTime.setText(date.toString());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mEnrolledClassListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mEnrolledClassListener.onClassClickedFromEnrolledClassFragment(holder.mItem);
                } else {
                    mJoinClassListener.onClassClickedFromJoinClassFragment(holder.mItem);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mCourse;
        public final TextView mSection;
        public final TextView mInstructor;
        public final TextView mTime;
        public Course mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCourse = view.findViewById(R.id.course);
            mSection = view.findViewById(R.id.section);
            mInstructor = view.findViewById(R.id.instructor);
            mTime = view.findViewById(R.id.time);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCourse.getText() + "'";
        }
    }
}
