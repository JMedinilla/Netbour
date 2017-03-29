package jvm.ncatz.netbour.pck_pojo;

public class PoUser {
    private long createdAt;
    private String floor;
    private String door;
    private String phone;
    private String email;
    private String name;
    private int category;
    private String photo;
    private boolean deleted;

    private PoUser() {
        //
    }

    public PoUser(long createdAt, String floor, String door, String phone, String email, String name, int category, String photo, boolean deleted) {
        this.createdAt = createdAt;
        this.floor = floor;
        this.door = door;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.category = category;
        this.photo = photo;
        this.deleted = deleted;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
