package edu.toronto.csc301.anonclass;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import edu.toronto.csc301.anonclass.dummy.DummyContent.DummyItem;
import edu.toronto.csc301.anonclass.util.FileItem;

/**
 * recyclerView adapter for FileFragment inside InClassActivity
 *
 */
public class mFileRecyclerViewAdapter extends RecyclerView.Adapter<mFileRecyclerViewAdapter.ViewHolder> {

    private final List<FileItem> mFiles;
    private final FileFragment.OnFileFragmentInteractionListener mListener;

    public mFileRecyclerViewAdapter(List<FileItem> items, FileFragment.OnFileFragmentInteractionListener listener) {
        mFiles = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_file_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.file = mFiles.get(position);
        holder.fileNameView.setText(mFiles.get(position).getFileName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    /*holder.progressBar.setVisibility(View.VISIBLE);
                    holder.progressBar.setIndeterminate(true);*/
                    mListener.onListFragmentInteraction(holder.file);
                }
            }
        });
    }
    public void refresh(int position, FileItem item){
        mFiles.set(position, item);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView fileNameView;
        public FileItem file;
        public ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            fileNameView = view.findViewById(R.id.file_name);
            progressBar = view.findViewById(R.id.progressBar);
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }
    }
}
