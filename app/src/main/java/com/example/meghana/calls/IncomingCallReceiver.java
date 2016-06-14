package com.example.meghana.calls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by meghana on 14/6/16.
 */
public class IncomingCallReceiver extends BroadcastReceiver {

    private Interface.ITelephony telephonyService;
    private String blacklistednumber = "+458664455";
    @Override
    public void onReceive(Context context, Intent intent) {


        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Interface.ITelephony telephonyService = (Interface.ITelephony) m.invoke(tm);
            Bundle bundle = intent.getExtras();
            String phoneNumber = bundle.getString("incoming_number");
            Log.e("INCOMING", phoneNumber);
            if ((phoneNumber != null) && phoneNumber.equals(blacklistednumber)) {
                telephonyService.silenceRinger();
                telephonyService.endCall();
                Log.e("HANG UP", phoneNumber);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
