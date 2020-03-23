package com.example.lab4v3;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4v3.tasks.TaskListContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment {

    private FragmentActivity FragmentActivity;

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    public void displayTask(TaskListContent.Task task){
        FragmentActivity = getActivity(); // get the hold ing Activity
        // Find the elements used to display the data of the Task
        TextView taskInfoTitle = getActivity().findViewById(R.id.taskInfoTitle);
        TextView taskInfoDescription = getActivity().findViewById(R.id.taskInfoDescription);
        ImageView taskInfoImage = getActivity().findViewById(R.id.taskInfoImage);

        // Apply the data

        taskInfoTitle.setText(task.title);
        taskInfoDescription.setText(task.details);
        if(task.picPath != null && !task.picPath.isEmpty()){
            // if picPath is set
            if(task.picPath.contains("drawable")){
                // if picPath contains word "drawable" get an appropriate drawable resource
                Drawable taskDrawable;
                switch (task.picPath){
                    case "drawable 1":
                        taskDrawable = getActivity().getResources().getDrawable(R.drawable.circle_drawable_green);
                        break;
                    case "drawable 2":
                        taskDrawable = getActivity().getResources().getDrawable(R.drawable.circle_drawable_orange);
                        break;
                    case "drawable 3":
                        taskDrawable = getActivity().getResources().getDrawable(R.drawable.circle_drawable_red);
                        break;
                    default:
                        taskDrawable = getActivity().getResources().getDrawable(R.drawable.circle_drawable_green);
                }
                taskInfoImage.setImageDrawable(taskDrawable);
            }
        }else{
            // if picPath is not set, use a default drawable
            taskInfoImage.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.circle_drawable_green));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get the intenet holding the Task details and display them
        Intent intent = getActivity().getIntent();
        if(intent != null){
            TaskListContent.Task receivedTask = intent.getParcelableExtra(MainActivity.taskExtra);
            if(receivedTask != null){
                displayTask(receivedTask);
            }
        }
    }
}
