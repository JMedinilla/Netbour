package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoIncidence implements Parcelable {

    private boolean deleted;
    private long date;
    private long key;
    private String authorEmail;
    private String authorName;
    private String description;
    private String photo;
    private String title;

    public PoIncidence() {
        //
    }

    public PoIncidence(boolean deleted, long date, long key, String authorEmail, String authorName, String description, String photo, String title) {
        this.deleted = deleted;
        this.date = date;
        this.key = key;
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.description = description;
        this.photo = photo;
        this.title = title;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoIncidence)) return false;

        PoIncidence incidence = (PoIncidence) o;

        return isDeleted() == incidence.isDeleted()
                && getDate() == incidence.getDate()
                && getKey() == incidence.getKey()
                && getAuthorEmail().equals(incidence.getAuthorEmail())
                && getAuthorName().equals(incidence.getAuthorName())
                && getDescription().equals(incidence.getDescription())
                && getPhoto().equals(incidence.getPhoto())
                && getTitle().equals(incidence.getTitle());

    }

    @Override
    public int hashCode() {
        int result = (isDeleted() ? 1 : 0);
        result = 31 * result + (int) (getDate() ^ (getDate() >>> 32));
        result = 31 * result + (int) (getKey() ^ (getKey() >>> 32));
        result = 31 * result + getAuthorEmail().hashCode();
        result = 31 * result + getAuthorName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getPhoto().hashCode();
        result = 31 * result + getTitle().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PoIncidence{" +
                "deleted=" + deleted +
                ", date=" + date +
                ", key=" + key +
                ", authorEmail='" + authorEmail + '\'' +
                ", authorName='" + authorName + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    protected PoIncidence(Parcel in) {
        deleted = in.readByte() != 0;
        date = in.readLong();
        key = in.readLong();
        authorEmail = in.readString();
        authorName = in.readString();
        description = in.readString();
        photo = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeLong(date);
        dest.writeLong(key);
        dest.writeString(authorEmail);
        dest.writeString(authorName);
        dest.writeString(description);
        dest.writeString(photo);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
