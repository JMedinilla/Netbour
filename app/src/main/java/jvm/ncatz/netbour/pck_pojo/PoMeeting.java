package jvm.ncatz.netbour.pck_pojo;

public class PoMeeting {
    private long createdAt;
    private String date;
    private String description;
    private boolean deleted;

    public PoMeeting() {
        //
    }

    public PoMeeting(long createdAt, String date, String description, boolean deleted) {
        this.createdAt = createdAt;
        this.date = date;
        this.description = description;
        this.deleted = deleted;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
