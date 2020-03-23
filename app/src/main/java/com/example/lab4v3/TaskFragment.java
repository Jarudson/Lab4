package com.example.lab4v3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab4v3.tasks.TaskListContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link onListFragmentClickInteraction}
 * interface.
 */
public class TaskFragment extends Fragment {

    // TODO: Customize parameters
    private MyTaskRecyclerViewAdapter mRecyclerVuewAdapter;

    private onListFragmentClickInteraction mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    public void notifyDataChange(){
        mRecyclerVuewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            // Set the LayoutManager to linear i.e. list mode
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            // Create the adapter and set it to RecyclerView
            mRecyclerVuewAdapter = new MyTaskRecyclerViewAdapter(TaskListContent.ITEMS, mListener);
            recyclerView.setAdapter(mRecyclerVuewAdapter);

        }
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onListFragmentClickInteraction) {
            mListener = (onListFragmentClickInteraction) context;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface onListFragmentClickInteraction {
        // TODO: Update argument type and name
        void onListFragmentClickInteraction(TaskListContent.Task task, int position);
        void onListFragmentLongClickInteraction(int position);
    }


}
