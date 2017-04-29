package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoEntry implements Parcelable {
    public static final int CATEGORY_FIRST = 1;
    public static final int CATEGORY_SECOND = 2;

    private long key;
    private String title;
    private String content;
    private long date;
    private int category;
    private String authorName;
    private String authorEmail;
    private boolean deleted;

    public PoEntry() {
        //
    }

    public PoEntry(long key, String title, String content, long date, int category, String authorName, String authorEmail, boolean deleted) {
        this.key = key;
        this.title = title;
        this.content = content;
        this.date = date;
        this.category = category;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.deleted = deleted;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    protected PoEntry(Parcel in) {
        key = in.readLong();
        title = in.readString();
        content = in.readString();
        date = in.readLong();
        category = in.readInt();
        authorName = in.readString();
        authorEmail = in.readString();
        deleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(key);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(date);
        dest.writeInt(category);
        dest.writeString(authorName);
        dest.writeString(authorEmail);
        dest.writeByte((byte) (deleted ? 1 : 0));
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
