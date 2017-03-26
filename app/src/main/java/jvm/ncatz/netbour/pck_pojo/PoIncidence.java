package jvm.ncatz.netbour.pck_pojo;

public class PoIncidence {
    private String key;
    private String title;
    private String description;
    private long date;
    private String photo;
    private String authorName;

    public PoIncidence() {

    }

    public PoIncidence(String key, String title, String description, long date, String photo, String authorName) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.date = date;
        this.photo = photo;
        this.authorName = authorName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
