package com.example.planner_.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class ListModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "list_text")
    public String[] text;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @ColumnInfo(name = "done")
    public boolean done;

    public ListModel() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListModel listModel = (ListModel) o;
        return id == listModel.id &&
                timestamp == listModel.timestamp &&
                done == listModel.done &&
                Arrays.equals(text, listModel.text);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, timestamp, done);
        result = 31 * result + Arrays.hashCode(text);
        return result;
    }

    protected ListModel(Parcel in) {
        id = in.readInt();
        text = in.createStringArray();
        timestamp = in.readLong();
        done = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeStringArray(text);
        dest.writeLong(timestamp);
        dest.writeByte((byte) (done ? 1 : 0));
    }

    public static final Creator<ListModel> CREATOR = new Creator<ListModel>() {
        @Override
        public ListModel createFromParcel(Parcel in) {
            return new ListModel(in);
        }

        @Override
        public ListModel[] newArray(int size) {
            return new ListModel[size];
        }
    };
}
