package ru.ck.zetmy_java.data;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DatabaseWorker {
    private TaskDatabase db;
    private TaskDao taskDao;

    private ExecutorService taskExecutor = Executors.newSingleThreadExecutor();

    public DatabaseWorker(Context context) {
        db = Room.databaseBuilder(context,
                TaskDatabase.class, "tasks-database").build();

        taskDao = db.taskDao();
    }

    public List<Task> getAll() throws ExecutionException, InterruptedException {
        final Future<List<Task>> result = taskExecutor.submit(new getAllCallable());
        return result.get();
    }

    private List<Task> getAllCall() {
        return taskDao.getAll();
    }

    private class getAllCallable implements Callable<List<Task>> {
        @Override
        public List<Task> call() throws Exception {
            return getAllCall();
        }
    }

    public List<Task> getAllPinned() throws ExecutionException, InterruptedException {
        final Future<List<Task>> result = taskExecutor.submit(new getAllPinnedCallable());
        return result.get();
    }

    private List<Task> getAllPinnedCall() {
        return taskDao.getAllPinned();
    }

    private class getAllPinnedCallable implements Callable<List<Task>> {
        @Override
        public List<Task> call() throws Exception {
            return getAllPinnedCall();
        }
    }

    public void insertTasks(Task... new_tasks) {
        taskExecutor.execute(() -> taskDao.insertTasks(new_tasks));
    }

    public void updateTasks(int task_id, String name, String desc, boolean isPinned) {
        taskExecutor.execute(() -> taskDao.updateTasks(task_id, name, desc, isPinned));
    }

    public void delete(Task task) {
        taskExecutor.execute(() -> taskDao.delete(task));
    }

    public Task findById(int taskId) throws ExecutionException, InterruptedException {
        final Future<Task> result = taskExecutor.submit(new findByIdCallable(taskId));
        return result.get();
    }

    private Task findByIdCall(int taskId) {
        return taskDao.findById(taskId);
    }

    private class findByIdCallable implements Callable<Task> {
        int taskId;

        public findByIdCallable(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public Task call() throws Exception {
            return findByIdCall(taskId);
        }

    }

    public void closeDb() throws Exception {
        db.close();
    }
}


