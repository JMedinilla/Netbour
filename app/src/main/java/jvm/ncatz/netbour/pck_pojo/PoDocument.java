package jvm.ncatz.netbour.pck_pojo;

public class PoDocument {
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
}
