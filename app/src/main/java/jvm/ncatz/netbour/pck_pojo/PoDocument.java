package jvm.ncatz.netbour.pck_pojo;

public class PoDocument {
    private long createdAt;
    private String title;
    private String description;
    private String link;

    public PoDocument() {
        //
    }

    public PoDocument(long createdAt, String title, String description, String link) {
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.link = link;
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
}
