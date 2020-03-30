package com.project.tapnenjoy;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;

import java.io.IOException;
import java.util.List;

public class GeoLocationHelper {

    public static Pair<Float, Float> getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        Pair<Float, Float> position = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null || (address != null && (address.isEmpty() || address.size() == 0))) {
                return null;
            }

            Address location = address.get(0);
            position = new Pair((float)location.getLatitude(), (float)location.getLongitude());

        } catch (IOException ex){
            ex.printStackTrace();
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }

        return position;
    }
}
