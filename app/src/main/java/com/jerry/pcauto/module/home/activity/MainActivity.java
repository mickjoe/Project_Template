package com.jerry.pcauto.module.home.activity;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jerry.pcauto.R;
import com.jerry.pcauto.base.BaseActivity;
import com.jerry.pcauto.base.BaseFragment;
import com.jerry.pcauto.constant.Urls;
import com.jerry.pcauto.module.home.fragment.MyFragment;
import com.jerry.pcauto.utils.EventMessage;
import com.jerry.pcauto.utils.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import greendao.LoreDetailTable;
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

        menu = new SlidingMenu(this);

        // 设置左边侧边栏
        menu.setMode(SlidingMenu.LEFT);

        // 设置什么位置可以拉动侧边栏
        //menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        // 中间阴影部分宽度
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.sliding_shadow);

        // 侧边栏后面的布局的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //menu.setBehindOffset(DensityUtil.dip2px(120));

        menu.setFadeDegree(0.35f);

        // 把侧边栏附加到当前的Activity
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

        // 设置侧边栏的布局
        menu.setMenu(R.layout.main_left_menu);

        manager = getSupportFragmentManager();

        //
        Map<String,String> params = new HashMap<>();
        params.put("tag","1");

        //
        MyFragment myFragment =
                BaseFragment.newInstance(MyFragment.class,params);

        // 默认先显示首页
        manager.beginTransaction().replace(R.id.fl_main,myFragment).commit();


        dao = getDaoSession().getLoreDetailTableDao();

        // 添加
        LoreDetailTable loreDetail = new LoreDetailTable();
        loreDetail.setTitle("标题内容");
        loreDetail.setImageUrl("111111111");
        dao.insert(loreDetail);

        // 查询
        Query<LoreDetailTable> query = dao.queryRawCreate("_id = ?",1);
        List<LoreDetailTable> list = query.list();


        // 使用QueryBuilder查询
        QueryBuilder<LoreDetailTable> qb = dao.queryBuilder();
        qb.where(LoreDetailTableDao.Properties.Id.eq(1));
        long count = qb.buildCount().count();

        // 使用链式法
        list = dao.queryBuilder()
                .where(LoreDetailTableDao.Properties.Title.eq("标题内容"))
                .orderAsc(LoreDetailTableDao.Properties.Id)
                .list();

        // 查找某一个对象
        LoreDetailTable loreDetailTable =
                dao.queryBuilder()
                        .where(LoreDetailTableDao.Properties.Id.eq(1))
                        .unique();

        // 删除
        //dao.deleteByKey(1l);
        dao.delete(loreDetailTable);

        // 修改
        loreDetailTable.setTitle("修改标题");
        dao.update(loreDetailTable);

    }

    /**
     * 加载数据
     */
    @Override
    protected void loadData()
    {
        Map<String, Object> param = new HashMap<>();
        param.put("pageindex", 3);
        OkHttpUtils.postSubmitForm(Urls.HOTEL_LIST, param);
    }

    /**
     * 获取具体的结果
     *
     * @param message
     */
    public void postEventResult(EventMessage message)
    {
        String type = message.type;

        if (Urls.HOTEL_LIST.equals(type))
        {
            Log.d("getResult", "onGetResult() returned: " + message.result);
        }
//        else if (Urls.HOTEL_LIST2.equals(type))
//        {
//
//        }
    }
}
