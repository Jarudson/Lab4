package com.example.lab4v3;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4v3.TaskFragment.onListFragmentClickInteraction;
import com.example.lab4v3.tasks.TaskListContent.Task;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Task} and makes a call to the
 * specified {@link onListFragmentClickInteraction}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    private final onListFragmentClickInteraction mListener;

    public MyTaskRecyclerViewAdapter(List<Task> items, onListFragmentClickInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Bind the ViewHolder to the element at position
        Task task = mValues.get(position);
        holder.mItem = task;
        // Set the task title
        holder.getmContentView.setText(task.title);
        // Set the task image
        final String picPath = task.picPath;
        Context context = holder.mView.getContext(); // Retrieve the Context of the application
        if (picPath != null && !picPath.isEmpty()) {
            // if picPath is set
            if (picPath.contains("drawable")) {
                // if picPath contains word "drawable" get an appropriate drawable resource
                Drawable taskDrawable;
                switch (picPath) {
                    case "drawable1":
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_green);
                        break;
                    case "drawable2":
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_orange);
                        break;
                    case "drawable3":
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_red);
                        break;
                    default:
                        taskDrawable = context.getResources().getDrawable(R.drawable.circle_drawable_green);
                }
                holder.mItemImageView.setImageDrawable(taskDrawable);
            }
        } else {
            // if picPath is not set, use a default drawable
            holder.mItemImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.circle_drawable_green));
        }
        // Set an onClickListener for the list element
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface(the activity, if the
                    // fragment is attached to one) tat an item hsa been selected
                    mListener.onListFragmentClickInteraction(holder.mItem, position);
                }
            }
        });
        // Set an onLongClickListener for the list element
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected
                mListener.onListFragmentLongClickInteraction(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView getmContentView;
        public final ImageView mItemImageView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mItemImageView = view.findViewById(R.id.item_image);
            getmContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + getmContentView.getText() + "'";
        }
    }
}
