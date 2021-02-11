package iitg.vedinkaksa;
//Created by: Bishwaroop Bhattacharjee

import android.app.Application;

import net.gotev.uploadservice.UploadService;

public class GlobalClass extends Application {

    private Integer kar;

    private Integer interval;

    private Float threshold;

    public Integer getKar() {
        return kar;
    }

    public void setKar(Integer kar) {
        this.kar = kar;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Float getThreshold() {
        return threshold;
    }

    public void setThreshold(Float threshold) {
        this.threshold = threshold;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Or, you can define it manually.
        UploadService.NAMESPACE = "com.yourcompany.yourapp";
    }
}

