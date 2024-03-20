package Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sensdata {

    @SerializedName("value")
    @Expose
    private String temperature;

    @SerializedName("value")
    @Expose
    private String humidity;
    @SerializedName("value")
    @Expose
    private String pm2p5;

    @SerializedName("value")
    @Expose
    private String pm10;
    @SerializedName("value")
    @Expose
    private String co;

    @SerializedName("value")
    @Expose
    private String so2;
    @SerializedName("value")
    @Expose
    private String no2;

    @SerializedName("value")
    @Expose
    private String ozone;

    @SerializedName("value")
    @Expose
    private String sound;


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String value) {
        this.temperature = value;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String value) {
        this.humidity = value;
    }

    public String getPm2p5() {
        return pm2p5;
    }

    public void setPm2p5(String value) {
        this.pm2p5 = value;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String value) {
        this.pm10 = value;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String value) {
        this.co = value;
    }
    public String getSo2() {
        return so2;
    }

    public void setSo2(String value) {
        this.so2 = value;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String value) {
        this.no2 = value;
    }

    public String getOzone() {
        return ozone;
    }

    public void setOzone(String value) {
        this.ozone = value;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String value) {
        this.sound = value;
    }
}

