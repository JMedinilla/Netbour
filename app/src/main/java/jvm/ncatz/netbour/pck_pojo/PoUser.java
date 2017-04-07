package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoUser implements Parcelable {
    private long createdAt;
    private String floor;
    private String door;
    private String phone;
    private String email;
    private String name;
    private int category;
    private boolean deleted;

    private PoUser() {
        //
    }

    public PoUser(long createdAt, String floor, String door, String phone, String email, String name, int category, boolean deleted) {
        this.createdAt = createdAt;
        this.floor = floor;
        this.door = door;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.category = category;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    protected PoUser(Parcel in) {
        createdAt = in.readLong();
        floor = in.readString();
        door = in.readString();
        phone = in.readString();
        email = in.readString();
        name = in.readString();
        category = in.readInt();
        deleted = in.readByte() != 0;
    }

    public static final Creator<PoUser> CREATOR = new Creator<PoUser>() {
        @Override
        public PoUser createFromParcel(Parcel in) {
            return new PoUser(in);
        }

        @Override
        public PoUser[] newArray(int size) {
            return new PoUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(createdAt);
        dest.writeString(floor);
        dest.writeString(door);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeInt(category);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }
}
