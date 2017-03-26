package jvm.ncatz.netbour.pck_pojo;

public class PoUser {
    private String key;
    private String floor;
    private String door;
    private String phone;
    private String email;
    private String name;
    private int category;
    private String photo;

    private PoUser() {

    }

    public PoUser(String key, String floor, String door, String phone, String email, String name, int category, String photo) {
        this.key = key;
        this.floor = floor;
        this.door = door;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.category = category;
        this.photo = photo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
