package ru.ck.zetmy_java.ui.new_task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

import ru.ck.zetmy_java.R;
import ru.ck.zetmy_java.databinding.FragmentNewTaskBinding;
import ru.ck.zetmy_java.databinding.FragmentSetTaskDateBinding;

public class SetTaskDate extends Fragment {

    private final Calendar calendar = Calendar.getInstance();
    private FragmentSetTaskDateBinding binding;
    private TextView currentDatetime;


    TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };

    private Button timeButton;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetTaskDateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentDatetime = binding.currentDatetime;
        saveButton = binding.saveTimeButton;
        timeButton = binding.buttonTime2;
//
        saveButton.setOnClickListener(v -> saveDate());
        timeButton.setOnClickListener(v -> setTime());

        CalendarView calendarView = binding.calendarView;
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            setInitialDateTime();
        });

        return root;
    }

    private void setTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), R.style.TimePickerTheme, t,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void setInitialDateTime() {

        currentDatetime.setText(DateUtils.formatDateTime(this.getContext(),
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    private void saveDate() {
        NewTaskFragment newTaskFragment = new NewTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", currentDatetime.getText().toString());
        newTaskFragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_activity_main,  newTaskFragment).commit();

//        onBackPressed();
    }

    private void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}