package com.jerry.pcauto.module.home.activity;

import android.support.v4.app.FragmentManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jerry.pcauto.R;
import com.jerry.pcauto.base.BaseActivity;
import com.jerry.pcauto.utils.EventMessage;

import greendao.LoreDetailTableDao;

public class MainActivity extends BaseActivity
{
    SlidingMenu menu = null;
    private FragmentManager manager;
    private LoreDetailTableDao dao;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initViews()
    {


    }

    /**
     * 加载数据
     */
    @Override
    protected void loadData()
    {

    }

    /**
     * 获取具体的结果
     *
     * @param message
     */
    public void postEventResult(EventMessage message)
    {

    }
}
