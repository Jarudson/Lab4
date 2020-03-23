package com.example.lab4v3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteDialog extends DialogFragment {

    private OnDeleteDialogInteractionListener mListener;

    public DeleteDialog() {
        // Required empty public constructor
    }

    static DeleteDialog newInstance(){
        return new DeleteDialog();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        // AlertDialog.Builder will be used to create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the message displayed in the dialog
        builder.setMessage(getString(R.string.delete_question));
        // Set the text and cation for the positive button click
        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notify the OnDeleteDialogInteractionListener interface of postivie button click
                try{
                    mListener.onDialogPostivieClick(DeleteDialog.this);
                }
                catch (NullPointerException a){

                }
            }
        });
        // Set the text and cation for the negative button click
        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Notify the OnDeleteDialogInteractionListener interface of negative button click
                try {
                    mListener.onDialogNegativeClick(DeleteDialog.this);
                }
                catch (NullPointerException a){
                }
            }
        });
        return builder.create();
    }

    public interface OnDeleteDialogInteractionListener {
        void onDialogPostivieClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }
}
