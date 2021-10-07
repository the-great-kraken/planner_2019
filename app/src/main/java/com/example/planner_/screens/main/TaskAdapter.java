package com.example.planner_.screens.main;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.daimajia.swipe.SwipeLayout;
import com.example.planner_.App;
import com.example.planner_.R;
import com.example.planner_.model.TaskModel;
import com.nhaarman.async.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.planner_.screens.tasks.Tasks.recyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private SortedList<TaskModel> sortedList;

    public TaskAdapter() {

        sortedList = new SortedList<>(TaskModel.class, new SortedList.Callback<TaskModel>() {
            @Override
            public int compare(TaskModel o1, TaskModel o2) {
                return (int) (o2.date - o1.date);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(TaskModel oldItem, TaskModel newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(TaskModel item1, TaskModel item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        //int pos = holder.getAdapterPosition();
        holder.bind(sortedList.get(position));
        //holder.itemView.setTag(R.id.);
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<TaskModel> tasks) {
        sortedList.replaceAll(tasks);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        TextView title;
        TextView dateHour;
        TextView dateYear;
        TextView dateMonth;
        ImageView doneButton;
        View delete;

        TaskModel task;

        public TaskViewHolder(@NonNull final View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.task_text);
            title = itemView.findViewById(R.id.title);
            dateHour = itemView.findViewById(R.id.dateHour);
            dateMonth = itemView.findViewById(R.id.dateMonth);
            dateYear = itemView.findViewById(R.id.dateYear);

            /*doneButton = (ImageView) itemView.findViewById(R.id.doneB);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = recyclerView.getChildLayoutPosition((View) v.getParent());
                    TaskModel a = sortedList.get(position);
                    a.done = true;
                    App.getInstance().getTaskDao().update(a);
                }
            });

*/

            /*doneButton = itemView.findViewById(R.id.doneB);
            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //final int position = recyclerView.getChildLayoutPosition(v);
                    int position = (int) itemView.getTag();
                    SwipeLayout s = (SwipeLayout) recyclerView.getChildAt(position);
                    TaskModel a = sortedList.get(position);
                    a.done = true;
                    App.getInstance().getTaskDao().update(a);
                    s.close(true);
                }
            });*/


            // delete = delete.findViewById(R.id.doneB);

            /*delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getTaskDao().delete(task);
                }
            });*/
        }

        public void bind(TaskModel task) {
            this.task = task;

            taskText.setText(task.txt);
            title.setText(task.title);
            dateHour.setText(getTime(task.date));
            dateMonth.setText(getMonth(task.date));
            dateYear.setText(getYear(task.date));
        }

        public String getTime(long date)
        {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm");
            String MySDate = newDateFormat.format(date);
            return MySDate;
        }

        public String getMonth(long date)
        {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM");
            String MySDate = newDateFormat.format(date);
            return MySDate;
        }

        public String getYear(long date)
        {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy");
            String MySDate = newDateFormat.format(date);
            return MySDate;
        }

    }
}
