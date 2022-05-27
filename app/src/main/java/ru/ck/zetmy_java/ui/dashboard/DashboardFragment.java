package ru.ck.zetmy_java.ui.dashboard;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import ru.ck.zetmy_java.R;
import ru.ck.zetmy_java.data.DatabaseWorker;
import ru.ck.zetmy_java.data.Task;
import ru.ck.zetmy_java.databinding.FragmentDashboardBinding;
import ru.ck.zetmy_java.ui.home.TaskFrame;
import ru.ck.zetmy_java.ui.new_task.NewTaskFragment;

public class DashboardFragment extends Fragment {

    private Button button;

    private FragmentDashboardBinding binding;

    private DatabaseWorker dbWorker;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbWorker = new DatabaseWorker(getContext());


        button = binding.addTask;
        button.setOnClickListener(v -> changeFragment());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(10, 30, 10, 10);

        LinearLayout framesContainer = root.findViewById(R.id.dashboard_task_container);

        List<Task> frames = null;

        try {
            frames =  dbWorker.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }



        for (Task task: frames) {
            TaskFrame frame = new TaskFrame(getContext());
            frame.setTaskName(task.getName());
            frame.setTaskDescription(task.getDescription());
            frame.setTaskPerformanceTime(task.getPerformance_time());
            frame.setParrentFragMan(getParentFragmentManager());
            frame.setTaskId(task.getId());
            frame.setPinLogo(false);

            frame.setLayoutParams(layoutParams);
            framesContainer.addView(frame);
        }

        return root;
    }

    private void changeFragment() {
        Fragment newTaskFragment = new NewTaskFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.nav_host_fragment_activity_main, newTaskFragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}