package com.example.iyeppeo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OnDataBaseAction {

    @Query("SELECT * FROM Helper4Class")
    List<Helper4Class> getAllTasksList();

    @Query("DELETE FROM Helper4Class")
    void truncateTheList();

    @Insert
    void insertDataIntoTaskList(Helper4Class task);

    @Query("DELETE FROM Helper4Class WHERE taskId = :taskId")
    void deleteTaskFromId(int taskId);

    @Query("SELECT * FROM Helper4Class WHERE taskId = :taskId")
    Helper4Class selectDataFromAnId(int taskId);

    @Query("UPDATE Helper4Class SET taskTitle = :taskTitle, taskDescription = :taskDescription, date = :taskDate, " +
            "lastAlarm = :taskTime, event = :taskEvent WHERE taskId = :taskId")
    void updateAnExistingRow(int taskId, String taskTitle, String taskDescription , String taskDate, String taskTime,
                            String taskEvent);

}
