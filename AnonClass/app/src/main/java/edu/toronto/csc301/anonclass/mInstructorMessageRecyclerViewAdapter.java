package edu.toronto.csc301.anonclass;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.toronto.csc301.anonclass.InstructorMessageFragment.OnInstructorMessageListFragmentInteractionListener;
import edu.toronto.csc301.anonclass.dummy.DummyContent.DummyItem;
import edu.toronto.csc301.anonclass.util.instructorMessage;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnInstructorMessageListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class mInstructorMessageRecyclerViewAdapter extends RecyclerView.Adapter<mInstructorMessageRecyclerViewAdapter.ViewHolder> {

    private final List<instructorMessage> mValues;
    private final OnInstructorMessageListFragmentInteractionListener mListener;

    public mInstructorMessageRecyclerViewAdapter(List<instructorMessage> items, OnInstructorMessageListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_instructormessage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mInstructor.setText(mValues.get(position).getInstructor());
        holder.mDate.setText(mValues.get(position).getDate().toString());
        holder.mMessage.setText(mValues.get(position).getMessage());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onInstructorMessageListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mInstructor;
        public final TextView mDate;
        public final TextView mMessage;
        public instructorMessage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mInstructor = view.findViewById(R.id.instructor);
            mDate =  view.findViewById(R.id.time);
            mMessage = view.findViewById(R.id.message);
        }
    }
}
