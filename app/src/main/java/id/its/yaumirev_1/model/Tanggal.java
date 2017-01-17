package id.its.yaumirev_1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Zachary on 5/1/2016.
 */
public class Tanggal {

    @SerializedName("idtgl")
    private String idtgl;

    public List<Amal> getAmal() {
        return amal;
    }

    public void setAmal(List<Amal> amal) {
        this.amal = amal;
    }

    @SerializedName("amal")
    private List<Amal> amal;

    public String getIdtgl() {
        return idtgl;
    }

    public void setIdtgl(String idtgl) {
        this.idtgl = idtgl;
    }

}
