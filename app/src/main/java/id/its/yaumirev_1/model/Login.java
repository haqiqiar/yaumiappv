package id.its.yaumirev_1.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zachary on 5/12/2016.
 */
public class Login {

    @SerializedName("notif")
    private String notif;

    @SerializedName("nrp")
    private String nrp;

    @SerializedName("nama")
    private String nama;

    @SerializedName("key")
    private String key;


    public String getNotif() {
        return notif;
    }

    public String getNrp() {
        return nrp;
    }

    public String getNama() {
        return nama;
    }

    public String getKey() {
        return key;
    }



}
