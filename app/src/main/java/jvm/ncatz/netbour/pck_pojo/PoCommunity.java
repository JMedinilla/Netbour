package jvm.ncatz.netbour.pck_pojo;

public class PoCommunity {
    private String key;
    private String province;
    private String municipality;
    private String street;
    private String number;
    private String postal;
    private int flats;

    public PoCommunity() {

    }

    public PoCommunity(String key, String province, String municipality, String street, String number, String postal, int flats) {
        this.key = key;
        this.province = province;
        this.municipality = municipality;
        this.street = street;
        this.number = number;
        this.postal = postal;
        this.flats = flats;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
