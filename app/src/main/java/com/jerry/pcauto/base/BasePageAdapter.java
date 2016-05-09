package com.jerry.pcauto.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by mac on 27/4/16.
 */
public class BasePageAdapter extends PagerAdapter
{
    public BasePageAdapter()
    {
    }

    @Override
    public int getCount()
    {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return false;
    }
}
