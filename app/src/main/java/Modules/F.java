package Modules;

public class F
{
    private String Latitude;

    private String Longitude;

    private String Place;

    public String getLatitude ()
    {
        return Latitude;
    }

    public void setLatitude (String Latitude)
    {
        this.Latitude = Latitude;
    }

    public String getLongitude ()
    {
        return Longitude;
    }

    public void setLongitude (String Longitude)
    {
        this.Longitude = Longitude;
    }

    public String getPlace ()
    {
        return Place;
    }

    public void setPlace (String Place)
    {
        this.Place = Place;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Latitude = "+Latitude+", Longitude = "+Longitude+", Place = "+Place+"]";
    }
}