package ru.ck.zetmy_java.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo
    String name;

    public Task(String name, String performance_time, String tag, String description, boolean isPinned) {
        this.name = name;
        this.performance_time = performance_time;
        this.tag = tag;
        this.description = description;
        this.isPinned = isPinned;
    }

    @ColumnInfo
    String performance_time;

    public Integer getId() {
        return id;
    }

    @Ignore
    public String getName() {
        return name;
    }

    @Ignore
    public String getPerformance_time() {
        return performance_time;
    }

    @Ignore
    public String getTag() {
        return tag;
    }

    @Ignore
    public String getDescription() {
        return description;
    }

    @Ignore
    public boolean isPinned() {
        return isPinned;
    }

    @ColumnInfo
    String tag;

    @ColumnInfo
    String description;

    @ColumnInfo
    boolean isPinned;
}
