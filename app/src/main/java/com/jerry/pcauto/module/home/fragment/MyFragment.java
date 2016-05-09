package com.jerry.pcauto.module.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jerry.pcauto.R;
import com.jerry.pcauto.base.BaseFragment;
import com.jerry.pcauto.constant.Urls;
import com.jerry.pcauto.utils.EventMessage;
import com.jerry.pcauto.utils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by mac on 27/4/16.
 */
public class MyFragment extends BaseFragment
{
    @BindView(R.id.textView)
    protected TextView tv1;

    // 务必实现空参数构造方法
    public MyFragment()
    {
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_main;
    }

    // 获取参数
    @Override
    protected void getReqParams(Bundle bundle)
    {
        String tag = bundle.getString("tag");
    }

    @Override
    protected void initViews()
    {
        tv1.setText("加载首页");
    }

    @Override
    protected void loadData()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageindex", 3);
        OkHttpUtils.postSubmitForm(Urls.HOTEL_LIST, param);
    }

    @Override
    protected void postEventResult(@NonNull EventMessage message)
    {
        super.postEventResult(message);
    }
}
