package com.jerry.pcauto.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelephonyUtils
{
    private static TelephonyManager tm = null;

    private static TelephonyUtils util = new TelephonyUtils();

    private static Context mContext;

    private TelephonyUtils()
    {
    }

    public static TelephonyUtils init(Context context)
    {
        if (mContext == null)
        {
            mContext = context;

            tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return util;
    }

    public static String getDeviceId()
    {
        return tm.getDeviceId();
    }

    /**
     * 获取Telephony ID
     *
     * @param context
     * @return
     */
    public static String getTelephonyId(Context context)
    {
        String telephonyId = null;

        TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String tmDevice, tmSerial, androidId;

        // 获取设备ID
        tmDevice = tm.getDeviceId();
        if (tmDevice == null)
        {
            tmDevice = "";
        }

        // 获取SIM序列号
        tmSerial = tm.getSimSerialNumber();
        if (tmSerial == null || "".equals(tmSerial))
        {
            tmSerial = System.currentTimeMillis() + "";
        }
        // 获取AndroidID
        androidId = android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        telephonyId = tmSerial + androidId + tmDevice.hashCode();

        if (StringUtils.isBlank(telephonyId))
        {
            telephonyId = System.currentTimeMillis() + "";
        }
        else if (telephonyId.length() > 24)
        {
            telephonyId = telephonyId.substring(0, 24);
        }

        return telephonyId;
    }

}
