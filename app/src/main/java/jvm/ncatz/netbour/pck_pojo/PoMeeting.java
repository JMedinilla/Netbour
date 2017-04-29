package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoMeeting implements Parcelable {
    private long key;
    private String date;
    private String description;
    private String authorEmail;
    private boolean deleted;

    public PoMeeting() {
        //
    }

    public PoMeeting(long key, String date, String description, String authorEmail, boolean deleted) {
        this.key = key;
        this.date = date;
        this.description = description;
        this.authorEmail = authorEmail;
        this.deleted = deleted;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    protected PoMeeting(Parcel in) {
        key = in.readLong();
        date = in.readString();
        description = in.readString();
        authorEmail = in.readString();
        deleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(key);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(authorEmail);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PoMeeting> CREATOR = new Creator<PoMeeting>() {
        @Override
        public PoMeeting createFromParcel(Parcel in) {
            return new PoMeeting(in);
        }

        @Override
        public PoMeeting[] newArray(int size) {
            return new PoMeeting[size];
        }
    };
}
