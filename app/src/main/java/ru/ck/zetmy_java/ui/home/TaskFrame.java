package ru.ck.zetmy_java.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.ck.zetmy_java.R;
import ru.ck.zetmy_java.ui.new_task.EditTaskFragment;
import ru.ck.zetmy_java.ui.new_task.NewTaskFragment;

public class TaskFrame extends RelativeLayout {
    private int taskId;
    private FragmentManager parrentFragMan;

    private ImageView imagePin;
    private TextView task_name;
    private TextView performance_time;
    private TextView task_description;
    private RelativeLayout task_frame;

    private final OnClickListener frameListener = view -> {
        EditTaskFragment editTaskFragment = new EditTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("task_id", taskId);
        editTaskFragment.setArguments(bundle);
        FragmentTransaction transaction = parrentFragMan.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main,  editTaskFragment).commit();
    };

    public TaskFrame(Context context) {
        super(context);
        initComponent();
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.task_background, this);
        task_name = findViewById(R.id.task_name);
        imagePin = findViewById(R.id.imagePin);
        performance_time = findViewById(R.id.performance_time);
        task_description = findViewById(R.id.task_description);
        task_frame = findViewById(R.id.task_frame);
        task_frame.setOnClickListener(frameListener);
    }

    public void setTaskName(String name) {
        task_name.setText(name);
    }

    public void setPinLogo(boolean logo) {
        if (!logo)
            imagePin.setImageResource(0);
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setParrentFragMan(FragmentManager parrentFragMan) {
        this.parrentFragMan = parrentFragMan;
    }

    public void setTaskPerformanceTime(String time) {
        performance_time.setText(time);
    }

    public void setTaskDescription(String name) {
        task_description.setText(name);
    }

}
