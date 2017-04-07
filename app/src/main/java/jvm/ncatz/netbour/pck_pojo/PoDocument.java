package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoDocument implements Parcelable {
    private long createdAt;
    private String title;
    private String description;
    private String link;
    private boolean deleted;

    public PoDocument() {
        //
    }

    public PoDocument(long createdAt, String title, String description, String link, boolean deleted) {
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    protected PoDocument(Parcel in) {
        createdAt = in.readLong();
        title = in.readString();
        description = in.readString();
        link = in.readString();
        deleted = in.readByte() != 0;
    }

    public static final Creator<PoDocument> CREATOR = new Creator<PoDocument>() {
        @Override
        public PoDocument createFromParcel(Parcel in) {
            return new PoDocument(in);
        }

        @Override
        public PoDocument[] newArray(int size) {
            return new PoDocument[size];
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
        dest.writeString(link);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }
}
