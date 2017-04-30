package jvm.ncatz.netbour.pck_pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class PoCommunity implements Parcelable {

    private boolean deleted;
    private int flats;
    private String code;
    private String municipality;
    private String number;
    private String postal;
    private String province;
    private String street;

    public PoCommunity() {
        //
    }

    public PoCommunity(boolean deleted, int flats, String code, String municipality, String number, String postal, String province, String street) {
        this.deleted = deleted;
        this.flats = flats;
        this.code = code;
        this.municipality = municipality;
        this.number = number;
        this.postal = postal;
        this.province = province;
        this.street = street;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getFlats() {
        return flats;
    }

    public void setFlats(int flats) {
        this.flats = flats;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoCommunity)) return false;

        PoCommunity community = (PoCommunity) o;

        return isDeleted() == community.isDeleted()
                && getFlats() == community.getFlats()
                && getCode().equals(community.getCode())
                && getMunicipality().equals(community.getMunicipality())
                && getNumber().equals(community.getNumber())
                && getPostal().equals(community.getPostal())
                && getProvince().equals(community.getProvince())
                && getStreet().equals(community.getStreet());

    }

    @Override
    public int hashCode() {
        int result = (isDeleted() ? 1 : 0);
        result = 31 * result + getFlats();
        result = 31 * result + getCode().hashCode();
        result = 31 * result + getMunicipality().hashCode();
        result = 31 * result + getNumber().hashCode();
        result = 31 * result + getPostal().hashCode();
        result = 31 * result + getProvince().hashCode();
        result = 31 * result + getStreet().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PoCommunity{" +
                "deleted=" + deleted +
                ", flats=" + flats +
                ", code='" + code + '\'' +
                ", municipality='" + municipality + '\'' +
                ", number='" + number + '\'' +
                ", postal='" + postal + '\'' +
                ", province='" + province + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    protected PoCommunity(Parcel in) {
        deleted = in.readByte() != 0;
        flats = in.readInt();
        code = in.readString();
        municipality = in.readString();
        number = in.readString();
        postal = in.readString();
        province = in.readString();
        street = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (deleted ? 1 : 0));
        dest.writeInt(flats);
        dest.writeString(code);
        dest.writeString(municipality);
        dest.writeString(number);
        dest.writeString(postal);
        dest.writeString(province);
        dest.writeString(street);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PoCommunity> CREATOR = new Creator<PoCommunity>() {
        @Override
        public PoCommunity createFromParcel(Parcel in) {
            return new PoCommunity(in);
        }

        @Override
        public PoCommunity[] newArray(int size) {
            return new PoCommunity[size];
        }
    };
}
