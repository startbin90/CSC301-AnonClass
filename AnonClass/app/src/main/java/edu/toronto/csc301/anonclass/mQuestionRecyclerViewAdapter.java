package edu.toronto.csc301.anonclass;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.toronto.csc301.anonclass.util.Question;

public class mQuestionRecyclerViewAdapter extends RecyclerView.Adapter<mQuestionRecyclerViewAdapter.ViewHolder> {
    private final List<Question> mQuestions;
    private final ChatRoomFragment.OnChatRoomFragmentInteractionListener mEnrolledClassListener;

    public mQuestionRecyclerViewAdapter(List<Question> questions, ChatRoomFragment.OnChatRoomFragmentInteractionListener listener) {
        mQuestions = questions;
        mEnrolledClassListener = listener;
    }

    @Override
    public mQuestionRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_style, parent, false);
        return new mQuestionRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final mQuestionRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mQuestions.get(position);
        holder.mName.setText(mQuestions.get(position).getEmail());
        holder.mQuestion.setText(mQuestions.get(position).getQuestion());
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mName;
        public final TextView mQuestion;
        public Question mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mName = view.findViewById(R.id.name);
            mQuestion = view.findViewById(R.id.question_body);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + ": "+ mQuestion.getText() + "'";
        }
    }
}
