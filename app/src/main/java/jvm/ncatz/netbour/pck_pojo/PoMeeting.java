package jvm.ncatz.netbour.pck_pojo;

public class PoMeeting {
    private String key;
    private long date;
    private String description;

    public PoMeeting() {

    }

    public PoMeeting(String key, long date, String description) {
        this.key = key;
        this.date = date;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
