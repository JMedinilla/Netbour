package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoEntry implements Parcelable {

    public static final int CATEGORY_FIRST = 1;
    public static final int CATEGORY_SECOND = 2;

    private boolean deleted;
    private int category;
    private long date;
    private long key;
    private String authorEmail;
    private String authorName;
    private String content;
    private String title;

    public PoEntry() {
        //
    }

    public PoEntry(boolean deleted, int category, long date, long key, String authorEmail, String authorName, String content, String title) {
        this.deleted = deleted;
        this.category = category;
        this.date = date;
        this.key = key;
        this.authorEmail = authorEmail;
        this.authorName = authorName;
        this.content = content;
        this.title = title;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(o instanceof PoEntry)) return false;

        PoEntry entry = (PoEntry) o;

        return isDeleted() == entry.isDeleted()
                && getCategory() == entry.getCategory()
                && getDate() == entry.getDate()
                && getKey() == entry.getKey()
                && getAuthorEmail().equals(entry.getAuthorEmail())
                && getAuthorName().equals(entry.getAuthorName())
                && getContent().equals(entry.getContent())
                && getTitle().equals(entry.getTitle());

    }

    @Override
    public int hashCode() {
        int result = (isDeleted() ? 1 : 0);
        result = 31 * result + getCategory();
        result = 31 * result + (int) (getDate() ^ (getDate() >>> 32));
        result = 31 * result + (int) (getKey() ^ (getKey() >>> 32));
        result = 31 * result + getAuthorEmail().hashCode();
        result = 31 * result + getAuthorName().hashCode();
        result = 31 * result + getContent().hashCode();
        result = 31 * result + getTitle().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PoEntry{" +
                "deleted=" + deleted +
                ", category=" + category +
                ", date=" + date +
                ", key=" + key +
                ", authorEmail='" + authorEmail + '\'' +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    protected PoEntry(Parcel in) {
        deleted = in.readByte() != 0;
        category = in.readInt();
        date = in.readLong();
        key = in.readLong();
        authorEmail = in.readString();
        authorName = in.readString();
        content = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeInt(category);
        dest.writeLong(date);
        dest.writeLong(key);
        dest.writeString(authorEmail);
        dest.writeString(authorName);
        dest.writeString(content);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PoEntry> CREATOR = new Creator<PoEntry>() {
        @Override
        public PoEntry createFromParcel(Parcel in) {
            return new PoEntry(in);
        }

        @Override
        public PoEntry[] newArray(int size) {
            return new PoEntry[size];
        }
    };
}
