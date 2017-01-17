package id.its.yaumirev_1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by Zachary on 4/25/2016.
 */
public class Amal implements Serializable {
    @SerializedName("idamal")
    private String idamal;

    @SerializedName("namaamal")
    private String namaamal;

    @SerializedName("value")
    private String value;

    @SerializedName("satuan")
    private String satuan;

    public String getIdamal() {
        return idamal;
    }

    public void setIdamal(String idamal) {
        this.idamal = idamal;
    }

    public String getNamaamal() {
        return namaamal;
    }

    public void setNamaamal(String namaamal) {
        this.namaamal = namaamal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

}
