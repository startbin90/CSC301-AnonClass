package edu.toronto.csc301.anonclass;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import edu.toronto.csc301.anonclass.util.Question;

/**
 *
 */
public class ChatRoomFragment extends Fragment {

    private OnChatRoomFragmentInteractionListener mListener;
    private mQuestionRecyclerViewAdapter adapter;
    EditText message;


    public static ChatRoomFragment newInstance(){
        ChatRoomFragment frag = new ChatRoomFragment();
        return frag;
    }
    public ChatRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatroom, container, false);
        View found = view.findViewById(R.id.question_list);
        if (found instanceof RecyclerView){
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) found;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new mQuestionRecyclerViewAdapter(mListener.requestQuestions(), mListener);
            recyclerView.setAdapter(adapter);

        }
        message = view.findViewById(R.id.chat_box);
        Button send = view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(message.getText().toString())){
                    mListener.onSendClicked(message.getText().toString());
                }
            }
        });
        return view;
    }
    public void refreshList(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChatRoomFragmentInteractionListener) {
            mListener = (OnChatRoomFragmentInteractionListener) context;
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
    public interface OnChatRoomFragmentInteractionListener {
        void onSendClicked(String question);
        List<Question> requestQuestions();
    }
}
