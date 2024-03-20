
package Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SEnsrResult {

    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("unit")
    @Expose
    private String unit;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
