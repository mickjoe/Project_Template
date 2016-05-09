package com.jerry.pcauto.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jerry.pcauto.constant.Constants;

import greendao.DaoMaster;

/**
 * Created by mac on 9/5/16.
 */
public class MyDBHelper extends DaoMaster.OpenHelper
{
    public MyDBHelper(Context context)
    {
        super(context, Constants.DATABASE_NAME, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
