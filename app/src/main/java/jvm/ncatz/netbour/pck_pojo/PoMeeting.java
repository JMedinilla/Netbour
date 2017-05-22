package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoMeeting implements Parcelable {

    private boolean deleted;
    private long key;
    private String authorEmail;
    private String date;
    private String description;

    public PoMeeting() {
        //
    }

    public PoMeeting(boolean deleted, long key, String authorEmail, String date, String description) {
        this.deleted = deleted;
        this.key = key;
        this.authorEmail = authorEmail;
        this.date = date;
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoMeeting)) return false;

        PoMeeting meeting = (PoMeeting) o;

        return isDeleted() == meeting.isDeleted()
                && getKey() == meeting.getKey()
                && getAuthorEmail().equals(meeting.getAuthorEmail())
                && getDate().equals(meeting.getDate())
                && getDescription().equals(meeting.getDescription());

    }

    @Override
    public int hashCode() {
        int result = (isDeleted() ? 1 : 0);
        result = 31 * result + (int) (getKey() ^ (getKey() >>> 32));
        result = 31 * result + getAuthorEmail().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PoMeeting{" +
                "deleted=" + deleted +
                ", key=" + key +
                ", authorEmail='" + authorEmail + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    protected PoMeeting(Parcel in) {
        deleted = in.readByte() != 0;
        key = in.readLong();
        authorEmail = in.readString();
        date = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeLong(key);
        dest.writeString(authorEmail);
        dest.writeString(date);
        dest.writeString(description);
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
