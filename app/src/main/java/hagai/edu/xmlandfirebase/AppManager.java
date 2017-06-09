package hagai.edu.xmlandfirebase;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by Hagai Zamir on 09-Jun-17.
 */

//Register in the manifest tag in the name attribute
    //Init stuff once for an app
public class AppManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Font Awesome fa_android
        TypefaceProvider.registerDefaultIconSets();
    }
}
