package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoDocument implements Parcelable {

    private boolean deleted;
    private long key;
    private String authorEmail;
    private String description;
    private String link;
    private String title;

    public PoDocument() {
        //
    }

    public PoDocument(boolean deleted, long key, String authorEmail, String description, String link, String title) {
        this.deleted = deleted;
        this.key = key;
        this.authorEmail = authorEmail;
        this.description = description;
        this.link = link;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoDocument)) return false;

        PoDocument document = (PoDocument) o;

        return isDeleted() == document.isDeleted()
                && getKey() == document.getKey()
                && getAuthorEmail().equals(document.getAuthorEmail())
                && getDescription().equals(document.getDescription())
                && getLink().equals(document.getLink())
                && getTitle().equals(document.getTitle());

    }

    @Override
    public int hashCode() {
        int result = (isDeleted() ? 1 : 0);
        result = 31 * result + (int) (getKey() ^ (getKey() >>> 32));
        result = 31 * result + getAuthorEmail().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getLink().hashCode();
        result = 31 * result + getTitle().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PoDocument{" +
                "deleted=" + deleted +
                ", key=" + key +
                ", authorEmail='" + authorEmail + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    protected PoDocument(Parcel in) {
        deleted = in.readByte() != 0;
        key = in.readLong();
        authorEmail = in.readString();
        description = in.readString();
        link = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeLong(key);
        dest.writeString(authorEmail);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
