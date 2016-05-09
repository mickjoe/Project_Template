package com.jerry.pcauto.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by mac on 27/4/16.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter
{
    private Context context;
    private List<T> list = new ArrayList<T>();

    public MyBaseAdapter(Context context,List<T> list)
    {
        this.context = context;
        this.list = list;
    }

    protected void addItem(T t)
    {
        this.list.add(t);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public T getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public abstract int getLayoutId();

    public abstract BaseViewHolder getViewHolder();

    public abstract void bindViews(View view,BaseViewHolder viewHolder);

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        BaseViewHolder viewHolder = null;

        if (convertView  == null)
        {
            convertView = LayoutInflater.from(context).inflate(getLayoutId(),null);

            viewHolder = getViewHolder();

            ButterKnife.bind(viewHolder,convertView);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (BaseViewHolder) convertView.getTag();
        }

        viewHolder.position = position;

        bindViews(convertView,viewHolder);

        return convertView;
    }

}
