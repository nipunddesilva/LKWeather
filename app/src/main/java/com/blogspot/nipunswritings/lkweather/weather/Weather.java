package com.blogspot.nipunswritings.lkweather.weather;

/**
 * Created by Nipun on 7/2/2016.
 */
public class Weather {
    private float temp;
    private String description;
    private float humidity;
    private float pressure;
    private float windSpeed;
    private float windDirec;
    private long time;

    public Weather() {

    }

    public Weather(float temp, String description, float humidity, float pressure
            , float windSpeed, float windDirec, long time){

        this.setTemp(temp);
        this.setDescription(description);
        this.setHumidity(humidity);
        this.setPressure(pressure);
        this.setWindSpeed(windSpeed);
        this.setWindDirec(windDirec);
        this.setTime(time);

    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDirec() {
        return windDirec;
    }

    public void setWindDirec(float windDirec) {
        this.windDirec = windDirec;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
