
package volley;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("thingId")
    @Expose
    private String thingId;
    @SerializedName("thingKey")
    @Expose
    private String thingKey;
    @SerializedName("thingName")
    @Expose
    private String thingName;
    @SerializedName("connected")
    @Expose
    private Boolean connected;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("locUpdated")
    @Expose
    private String locUpdated;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("lastRecordTimeStamp")
    @Expose
    private String lastRecordTimeStamp;
    @SerializedName("macId")
    @Expose
    private String macId;
    @SerializedName("timeZone")
    @Expose
    private String timeZone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("thingKey")
    @Expose
    private String thingKey2;

    public String getThingId() {
        return thingId;
    }

    public void setThingId(String thingId) {
        this.thingId = thingId;
    }

    public String getThingKey() {
        return thingKey;
    }

    public void setThingKey(String thingKey) {
        this.thingKey = thingKey;
    }

    public String getThingKey2() {
        return thingKey2;
    }

    public void setThingKey2(String thingKey2) {
        this.thingKey2 = thingKey;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocUpdated() {
        return locUpdated;
    }

    public void setLocUpdated(String locUpdated) {
        this.locUpdated = locUpdated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastRecordTimeStamp() {
        return lastRecordTimeStamp;
    }

    public void setLastRecordTimeStamp(String lastRecordTimeStamp) {
        this.lastRecordTimeStamp = lastRecordTimeStamp;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
