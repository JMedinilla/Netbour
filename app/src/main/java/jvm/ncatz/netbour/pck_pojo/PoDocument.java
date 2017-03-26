package jvm.ncatz.netbour.pck_pojo;

public class PoDocument {
    private String key;
    private String title;
    private String description;
    private String link;

    public PoDocument() {

    }

    public PoDocument(String key, String title, String description, String link) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
