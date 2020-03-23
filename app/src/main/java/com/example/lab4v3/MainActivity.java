package com.example.lab4v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lab4v3.tasks.TaskListContent;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements
        TaskFragment.onListFragmentClickInteraction,
        DeleteDialog.OnDeleteDialogInteractionListener
{

    public static final String taskExtra = "taskExtra";
    private int currentItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addClick(View view) {
        // Retrieve data from EditTExts and Spinner
        EditText taskTitleEditTxt = findViewById(R.id.taskTitle);
        EditText taskDescriptionEditTxt = findViewById(R.id.taskDescription);
        Spinner drawableSpinner = findViewById(R.id.drawableSpinner);
        String taskTitle = taskTitleEditTxt.getText().toString();
        String taskDescription = taskDescriptionEditTxt.getText().toString();
        String selectedImage = drawableSpinner.getSelectedItem().toString();

        if(taskTitle.isEmpty() && taskDescription.isEmpty()){
            // Add a default Task object to myTask list if no data is input in EditTexts
            TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1,
                    getString(R.string.default_title),
                    getString(R.string.default_description),
                    selectedImage));
        }else{
            if(taskTitle.isEmpty()) // Use default title if the taskTitle EditText is empty
                taskTitle = getString(R.string.default_title);
            if(taskDescription.isEmpty()) // Use default description if the taskDescription EditText is empty
                taskDescription = getString(R.string.default_description);
            // Add a Task to the TaskListContent list
            TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1,
                    taskTitle,
                    taskDescription,
                    selectedImage));
        }
        // Notify the TaskFragment adapter that the dataset changed
        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();

        // Clear the EditTexts
        taskTitleEditTxt.setText("");
        taskDescriptionEditTxt.setText("");

        // Automatically hide they keyboard after AddButton press
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void startSecondActivity(TaskListContent.Task task, int position){
        Intent intent = new Intent(this, TaskInfoActivity.class);
        intent.putExtra(taskExtra, task);
        startActivity(intent);
    }

    private void displayTaskInFragment(TaskListContent.Task task){
        // Finde the displayFragment
        TaskInfoFragment taskInfoFragment =  (TaskInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment);
        if(taskInfoFragment != null){
            // Display the task if displayFragment exists
            taskInfoFragment.displayTask(task);
        }
    }

    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }


    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
        Toast.makeText(this, getString(R.string.item_selected_msg), Toast.LENGTH_SHORT).show();
        startSecondActivity(task, position);
    }

    @Override
    public void onListFragmentLongClickInteraction(int position) {
        Toast.makeText(this, getString(R.string.long_click_msg) + position, Toast.LENGTH_SHORT).show();
        // Show the delete confirmation dialog
        showDeleteDialog();
        // Store the position of the slected element
        currentItemPosition = position;
    }

    @Override
    public void onDialogPostivieClick(DialogFragment dialog) {
        // If the user confirmed the deletion of an item
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size()){
            // If the currentItemPosition is correct. Remove the element from the TaskListContent list
            TaskListContent.removeItem(currentItemPosition);
            // Notify the TaskFragment adapter of the dataset change
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // If the user cancelled the deletion of the item
        View v = findViewById(R.id.addButton);
        if(v != null){
            // show a Snackbar displaying a message and allowing the user to retry the deletion of an item
            Snackbar.make(v, getString(R.string.delete_cancel_msg), Snackbar.LENGTH_LONG).setAction(getString(R.string.retry_msg), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // The user pressed "Retry" button. Show the deleteDialog once again
                    showDeleteDialog();
                }
            }).show();
        }
    }
}
