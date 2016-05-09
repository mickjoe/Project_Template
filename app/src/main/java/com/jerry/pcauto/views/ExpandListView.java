package com.jerry.pcauto.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by jerry
 */
public class ExpandListView extends ListView
{

    // ListView加入到ScrollView
    // ListView内容是网络获取，延迟，listView不能马上显示
    // LitView显示不全

    public ExpandListView(Context context)
    {
        super(context);
    }

    public ExpandListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExpandListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // 创建计量规格
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return super.onInterceptTouchEvent(ev);
    }
}
