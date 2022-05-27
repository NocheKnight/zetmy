package ru.ck.zetmy_java.ui.new_task;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import ru.ck.zetmy_java.R;
import ru.ck.zetmy_java.data.DatabaseWorker;
import ru.ck.zetmy_java.data.Task;
import ru.ck.zetmy_java.databinding.EditTaskFragmentBinding;
import ru.ck.zetmy_java.databinding.FragmentNewTaskBinding;
import ru.ck.zetmy_java.ui.dashboard.DashboardFragment;
import ru.ck.zetmy_java.ui.home.HomeFragment;

public class EditTaskFragment extends Fragment {
    private EditTaskFragmentBinding binding;

    private DatabaseWorker dbWorker;

    private int task_idd;
    private Task task_getter;
    private TextView change_data;
    private Button save_new_task;
    private Button delete_button;
    private Switch aSwitch;
    private Boolean isPin;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int recieveInfo = 0;
        if (bundle != null) {
            recieveInfo = bundle.getInt("task_id");
            System.out.println(recieveInfo);
        }

        task_idd = recieveInfo;


        binding = EditTaskFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbWorker = new DatabaseWorker(getContext());

        Task task = null;

        try {

            task = dbWorker.findById(recieveInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        task_getter = task;

        delete_button = binding.deleteButton;
        delete_button.setOnClickListener(v -> deleteTask());


        TextView perfro_date = binding.performanceDataEdit;
        perfro_date.setText(task.getPerformance_time());

        EditText newTaskName = binding.newTaskNameEdit;
        newTaskName.setText(task.getName());

        EditText description = binding.descriptionEdit;
        description.setText(task.getDescription());

        aSwitch = binding.switch1Edit;
        aSwitch.setChecked(task.isPinned());
        isPin = task.isPinned();
        aSwitch.setOnCheckedChangeListener(this::isPinned);

        save_new_task = binding.saveNewTaskEdit;
        save_new_task.setOnClickListener(v -> saveTask());


        return root;
    }

    private void isPinned(CompoundButton buttonView, boolean isChecked) {
        isPin = isChecked;
    }

    private void deleteTask() {
        dbWorker.delete(task_getter);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment).commit();
    }

    private void saveTask() {

        String newTaskName = binding.newTaskNameEdit.getText().toString();
        String description = binding.descriptionEdit.getText().toString();

        dbWorker.updateTasks(task_idd, newTaskName, description, isPin);

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