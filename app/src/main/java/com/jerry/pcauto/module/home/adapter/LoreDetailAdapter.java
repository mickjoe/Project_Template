package com.jerry.pcauto.module.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.jerry.pcauto.R;
import com.jerry.pcauto.base.BaseViewHolder;
import com.jerry.pcauto.base.MyBaseAdapter;
import com.jerry.pcauto.module.home.entity.LoreDetailEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by mac on 27/4/16.
 */
public class LoreDetailAdapter extends MyBaseAdapter<LoreDetailEntity>
{
    public LoreDetailAdapter(Context context, List<LoreDetailEntity> list)
    {
        super(context,list);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_main;
    }

    @Override
    public BaseViewHolder getViewHolder()
    {
        return new ViewHodler();
    }

    @Override
    public void bindViews(View view,BaseViewHolder viewHolder)
    {
        // 绑定内容到控件
        LoreDetailEntity detail = getItem(viewHolder.position);

        ViewHodler myViewHolder = (ViewHodler) viewHolder;

        myViewHolder.btn_mainpage.setText(detail.getTitle());
    }

    public static class ViewHodler extends BaseViewHolder
    {
        @BindView(R.id.btn_mainpage)
        Button btn_mainpage;
    }


}
