package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoUser implements Parcelable {

    public static final int GROUP_NEIGHBOUR = 1;
    public static final int GROUP_PRESIDENT = 2;
    public static final int GROUP_ADMIN = 3;

    private boolean deleted;
    private int category;
    private long key;
    private String community;
    private String door;
    private String email;
    private String floor;
    private String name;
    private String phone;
    private String photo;

    public PoUser() {
        //
    }

    public PoUser(boolean deleted, int category, long key, String community, String door, String email, String floor, String name, String phone, String photo) {
        this.deleted = deleted;
        this.category = category;
        this.key = key;
        this.community = community;
        this.door = door;
        this.email = email;
        this.floor = floor;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoUser)) return false;

        PoUser user = (PoUser) o;

        return isDeleted() == user.isDeleted()
                && getCategory() == user.getCategory()
                && getKey() == user.getKey()
                && getCommunity().equals(user.getCommunity())
                && getDoor().equals(user.getDoor())
                && getEmail().equals(user.getEmail())
                && getFloor().equals(user.getFloor())
                && getName().equals(user.getName())
                && getPhone().equals(user.getPhone())
                && getPhoto().equals(user.getPhoto());

    }

    @Override
    public int hashCode() {
        int result = (isDeleted() ? 1 : 0);
        result = 31 * result + getCategory();
        result = 31 * result + (int) (getKey() ^ (getKey() >>> 32));
        result = 31 * result + getCommunity().hashCode();
        result = 31 * result + getDoor().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getFloor().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getPhoto().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PoUser{" +
                "deleted=" + deleted +
                ", category=" + category +
                ", key=" + key +
                ", community='" + community + '\'' +
                ", door='" + door + '\'' +
                ", email='" + email + '\'' +
                ", floor='" + floor + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    protected PoUser(Parcel in) {
        deleted = in.readByte() != 0;
        category = in.readInt();
        key = in.readLong();
        community = in.readString();
        door = in.readString();
        email = in.readString();
        floor = in.readString();
        name = in.readString();
        phone = in.readString();
        photo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeInt(category);
        dest.writeLong(key);
        dest.writeString(community);
        dest.writeString(door);
        dest.writeString(email);
        dest.writeString(floor);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(photo);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
