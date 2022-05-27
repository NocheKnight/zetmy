package ru.ck.zetmy_java.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.ck.zetmy_java.R;
import ru.ck.zetmy_java.data.DatabaseWorker;
import ru.ck.zetmy_java.data.Task;
import ru.ck.zetmy_java.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private DatabaseWorker dbWorker;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbWorker = new DatabaseWorker(getContext());


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(10, 30, 10, 10);

        LinearLayout framesContainer = root.findViewById(R.id.home_task_container);


        List<Task> frames = null;

        try {
            frames =  dbWorker.getAllPinned();
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

            frame.setLayoutParams(layoutParams);
            framesContainer.addView(frame);
        }

        return root;

    }
}