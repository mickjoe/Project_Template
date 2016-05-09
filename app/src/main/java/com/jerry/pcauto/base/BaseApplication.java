package com.jerry.pcauto.base;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.jerry.pcauto.utils.OkHttpUtils;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LimitedAgeMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by mac on 27/4/16.
 */
public class BaseApplication extends Application
{
    private ImageLoader imageLoader;
    private static BaseApplication application;

    @Override
    public void onCreate()
    {
        super.onCreate();

        application = this;

        // 初始化GreenDao - DaoSession
        initDaoSession(this);

        // 百度地图初始化
        SDKInitializer.initialize(this);

        // 初始化Universal-Image-Loader
        initImageLoader();

        // 初始化OkHttpUtils
        OkHttpUtils.initOkHttp();

    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    /**
     * 初始化Universal-Image-Loader
     */
    private void initImageLoader()
    {
        // 获取单例的ImageLoader对象
        imageLoader = ImageLoader.getInstance();

        // 定义内存缓存大小
        int cacheSize = (int) Runtime.getRuntime().maxMemory() / 8;

        // 定义ImageLoaderConfiguration
        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration.Builder(this)
                        .diskCache(
                                new LimitedAgeDiskCache(
                                        new File("/mnt/sdcard/imagecache"), 60 * 60 * 1000))
                        .diskCacheSize(50 * 1024 * 1024)
                        .diskCacheFileCount(1000)
                        .memoryCache(
                                new LimitedAgeMemoryCache(
                                        new LruMemoryCache(cacheSize), 60 * 60 * 1000))
                        .build();

        // 初始化配置
        ImageLoader.getInstance().init(configuration);
    }

    /**
     * 获取ImageLoader对象
     *
     * @return
     */
    public ImageLoader getImageLoader()
    {
        return this.imageLoader;
    }

    /**
     * 获取应用程序对象
     *
     * @return
     */
    public static BaseApplication getApplication()
    {
        return application;
    }

    private DaoSession daoSession;
    public DaoSession getDaoSession()
    {
        return this.daoSession;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    private DaoSession initDaoSession(Context context)
    {
        if (daoSession == null)
        {
            // 开发模式
            //DaoMaster.OpenHelper helper =
            //        new DaoMaster.DevOpenHelper(context, Constants.DATABASE_NAME, null);

            DaoMaster.OpenHelper helper = new MyDBHelper(this);

            // 创建DaoMaster
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());

            // 创建会话
            daoSession = daoMaster.newSession();
        }
        return daoSession;

    }
}
