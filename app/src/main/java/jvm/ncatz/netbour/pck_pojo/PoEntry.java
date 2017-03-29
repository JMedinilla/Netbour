package jvm.ncatz.netbour.pck_pojo;

public class PoEntry {
    private long createdAt;
    private String title;
    private String content;
    private long date;
    private int category;
    private String authorName;

    public PoEntry() {
        //
    }

    public PoEntry(long createdAt, String title, String content, long date, int category, String authorName) {
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.date = date;
        this.category = category;
        this.authorName = authorName;
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
}
