package com.example.planner_.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class TaskModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Title")
    public String title;

    @ColumnInfo(name = "Date")
    public long date;

    @ColumnInfo(name = "Status")
    public String status;

    @ColumnInfo(name = "Txt")
    public String txt;

    @ColumnInfo(name = "Done")
    public boolean done;

    protected TaskModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        date = in.readLong();
        status = in.readString();
        txt = in.readString();
        done = in.readByte() != 0;
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskModel taskModel = (TaskModel) o;
        return id == taskModel.id &&
                date == taskModel.date &&
                done == taskModel.done &&
                Objects.equals(title, taskModel.title) &&
                Objects.equals(status, taskModel.status) &&
                Objects.equals(txt, taskModel.txt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, status, txt, done);
    }

    public TaskModel() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeLong(date);
        dest.writeString(status);
        dest.writeString(txt);
        dest.writeByte((byte) (done ? 1 : 0));
    }
}
