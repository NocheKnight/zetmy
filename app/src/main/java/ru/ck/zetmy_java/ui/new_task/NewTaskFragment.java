package ru.ck.zetmy_java.ui.new_task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.ck.zetmy_java.R;
import ru.ck.zetmy_java.data.DatabaseWorker;
import ru.ck.zetmy_java.data.Task;
import ru.ck.zetmy_java.data.TaskDao;
import ru.ck.zetmy_java.data.TaskDatabase;
import ru.ck.zetmy_java.databinding.FragmentNewTaskBinding;
import ru.ck.zetmy_java.ui.dashboard.DashboardFragment;

public class NewTaskFragment extends Fragment {
    private FragmentNewTaskBinding binding;

    private DatabaseWorker dbWorker;

    private TextView change_data;
    private Button save_new_task;
    private Switch aSwitch;
    private Boolean isPin;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String recieveInfo = "";
        if (bundle != null) {
            recieveInfo = bundle.getString("tag");
            System.out.println(recieveInfo);
        }


        binding = FragmentNewTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbWorker = new DatabaseWorker(getContext());

        aSwitch = binding.switch1;
        aSwitch.setOnCheckedChangeListener(this::isPinned);

        isPin = false;

        save_new_task = binding.saveNewTask;
        save_new_task.setOnClickListener(v -> saveTask());

        change_data = binding.performanceData;
        change_data.setOnClickListener(v -> changeFragment());

        if (!recieveInfo.equals(""))
            change_data.setText(recieveInfo);

        return root;
    }

    private void isPinned(CompoundButton buttonView, boolean isChecked) {
        isPin = isChecked;
    }

    private void saveTask() {

        String newTaskName = binding.newTaskName.getText().toString();
        String performanceData = binding.performanceData.getText().toString();
        String description = binding.description.getText().toString();

        Task new_task = new Task(newTaskName, performanceData, " ", description, isPin);

        dbWorker.insertTasks(new_task);

        DashboardFragment fragment = new DashboardFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment).commit();

    }

    private void changeFragment() {
        Fragment setTaskDate = new SetTaskDate();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.nav_host_fragment_activity_main, setTaskDate);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        try {
            dbWorker.closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}