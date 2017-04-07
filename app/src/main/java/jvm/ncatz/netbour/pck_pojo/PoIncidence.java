package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoIncidence implements Parcelable {
    private long createdAt;
    private String title;
    private String description;
    private long date;
    private String photo;
    private String authorName;
    private boolean deleted;

    public PoIncidence() {
        //
    }

    public PoIncidence(long createdAt, String title, String description, long date, String photo, String authorName, boolean deleted) {
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.date = date;
        this.photo = photo;
        this.authorName = authorName;
        this.deleted = deleted;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    protected PoIncidence(Parcel in) {
        createdAt = in.readLong();
        title = in.readString();
        description = in.readString();
        date = in.readLong();
        photo = in.readString();
        authorName = in.readString();
        deleted = in.readByte() != 0;
    }

    public static final Creator<PoIncidence> CREATOR = new Creator<PoIncidence>() {
        @Override
        public PoIncidence createFromParcel(Parcel in) {
            return new PoIncidence(in);
        }

        @Override
        public PoIncidence[] newArray(int size) {
            return new PoIncidence[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createdAt);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(date);
        dest.writeString(photo);
        dest.writeString(authorName);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }
}
