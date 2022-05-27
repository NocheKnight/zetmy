package ru.ck.zetmy_java.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE isPinned=1")
    List<Task> getAllPinned();

//    @Query("SELECT * FROM tasks WHERE id IN (:userIds)")
//    List<Task> findAllByTags(int[] tags);

    @Query("SELECT * FROM tasks WHERE id=:taskId LIMIT 1")
    Task findById(int taskId);

    @Insert
    void insertTasks(Task... tasks);

    @Query("UPDATE tasks SET name = :name , description = :desc , isPinned = :isPinned WHERE id = :task_id")
    void updateTasks(int task_id, String name, String desc, boolean isPinned);


    @Delete
    void delete(Task task);
}
