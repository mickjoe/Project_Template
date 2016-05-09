package com.jerry.pcauto.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.pcauto.utils.EventMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by mac on 27/4/16.
 */
public abstract class BaseFragment extends Fragment
{
    /**
     * 构造BaseFragment子类实例对象,子类务必实现空参构造方法
     * @param subFragmentCls 子类Fragment类型
     * @param params 参数，以Map的形式传递，可空
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newInstance(Class<?> subFragmentCls,Map<String,String> params)
    {
        try
        {
            Constructor constructor =
                    subFragmentCls.getConstructor(new Class[]{});

            T fragment = (T)constructor.newInstance(new Object[]{});

            Bundle bundle = new Bundle();

            if (params != null && params.size() > 0)
            {
                for (String key : params.keySet())
                {
                    String value = params.get(key);
                    bundle.putString(key,value);
                }
            }
            fragment.setArguments(bundle);

            return fragment;

        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (java.lang.InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        // 注册当前Fragment为订阅者
        EventBus.getDefault().register(this);

        // 获取参数，子类实现
        getReqParams(getArguments());
    }

    /**
     * 获取参数，在子类中定义逻辑
     * @param bundle
     */
    protected void getReqParams(Bundle bundle)
    {

    }

    /**
     * 创建视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // 加载视图
        rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    private View rootView;

    protected View getRootView()
    {
        return this.rootView;
    }

    /**
     * 视图加载完毕
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // 绑定视图和事件
        ButterKnife.bind(this,view);

        // 初始化视图，子类实现
        initViews();

        // 初始化视图
        loadData();
    }

    /**
     * 销毁视图
     */
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

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
            BaseFragment.this.postEventResult((EventMessage) message);
        }
        // 其他类型消息
        else
        {
            BaseFragment.this.postOtherResult(message);
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
