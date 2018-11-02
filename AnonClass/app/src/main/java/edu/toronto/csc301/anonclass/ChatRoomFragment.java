package edu.toronto.csc301.anonclass;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class ChatRoomFragment extends Fragment {

    private OnChatRoomFragmentInteractionListener mListener;

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
        return view;
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
        void onChatRoomFragmentInteraction(Uri uri);
    }
}
