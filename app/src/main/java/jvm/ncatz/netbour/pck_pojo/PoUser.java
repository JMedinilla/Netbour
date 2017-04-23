package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoUser implements Parcelable {
    public static final int GROUP_NEIGHBOUR = 1;
    public static final int GROUP_PRESIDENT = 2;
    public static final int GROUP_ADMIN = 3;

    private long key;
    private String community;
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

    public PoUser(long key, String community, String floor, String door, String phone, String email, String name, int category, String photo, boolean deleted) {
        this.key = key;
        this.community = community;
        this.floor = floor;
        this.door = door;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.category = category;
        this.photo = photo;
        this.deleted = deleted;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
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

    protected PoUser(Parcel in) {
        key = in.readLong();
        community = in.readString();
        floor = in.readString();
        door = in.readString();
        phone = in.readString();
        email = in.readString();
        name = in.readString();
        category = in.readInt();
        photo = in.readString();
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
        dest.writeLong(key);
        dest.writeString(community);
        dest.writeString(floor);
        dest.writeString(door);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeInt(category);
        dest.writeString(photo);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }
}
