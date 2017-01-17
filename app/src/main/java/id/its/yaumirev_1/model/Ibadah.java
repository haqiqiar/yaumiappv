package id.its.yaumirev_1.model;

import java.util.List;

/**
 * Created by Zachary on 4/19/2016.
 */
public class Ibadah {
    public void setAmal(List<Amal> amal) {
        this.amal = amal;
    }

    public List<Amal> amal;

    public List<Amal> getAmals() {
        return amal;
    }



}
