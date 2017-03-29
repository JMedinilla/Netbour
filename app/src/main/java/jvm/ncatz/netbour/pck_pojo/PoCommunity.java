package jvm.ncatz.netbour.pck_pojo;

public class PoCommunity {
    private long createdAt;
    private String province;
    private String municipality;
    private String street;
    private String number;
    private String postal;
    private int flats;
    private boolean deleted;

    public PoCommunity() {
        //
    }

    public PoCommunity(long createdAt, String province, String municipality, String street, String number, String postal, int flats, boolean deleted) {
        this.createdAt = createdAt;
        this.province = province;
        this.municipality = municipality;
        this.street = street;
        this.number = number;
        this.postal = postal;
        this.flats = flats;
        this.deleted = deleted;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public int getFlats() {
        return flats;
    }

    public void setFlats(int flats) {
        this.flats = flats;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
