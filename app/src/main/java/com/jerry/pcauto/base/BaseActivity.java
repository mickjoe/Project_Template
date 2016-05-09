package com.jerry.pcauto.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jerry.pcauto.utils.EventMessage;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import greendao.DaoSession;

/**
 * Created by mac on 27/4/16.
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity
{
    private DaoSession daoSession;
    protected DaoSession getDaoSession()
    {
        return daoSession;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // 获取DaoSession
        daoSession =
                BaseApplication.getApplication().getDaoSession();

        // 绑定视图和事件
        ButterKnife.bind(this);

        // 注册当前Activity为订阅者
        EventBus.getDefault().register(this);

        // 初始化视图，子类实现
        this.initViews();

        // 加载数据，子类实现
        //
        this.loadData();
    }

    /**
     * 销毁Activity
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // 取消注册
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onGetResult(EventMessage message)
    {
        if (message == null)
        {
            return;
        }

        // 是否为Event消息
        if (message instanceof EventMessage)
        {
            BaseActivity.this.postEventResult((EventMessage) message);
        }
        // 其他类型消息
        else
        {
            BaseActivity.this.postOtherResult(message);
        }
    }

    // 获取布局，子类实现逻辑
    protected abstract int getLayoutId();

    // 初始化视图，此方法调用前，视图已经初始化
    protected void initViews()
    {

    }

    // 加载所需数据，子类实现逻辑
    protected void loadData()
    {

    }

    // 传递EventBus事件类型结果，子类实现逻辑
    protected void postEventResult(@NonNull EventMessage message)
    {

    }

    // 传送其他结果，子类实现逻辑
    protected void postOtherResult(@NonNull Object message)
    {

    }
}
