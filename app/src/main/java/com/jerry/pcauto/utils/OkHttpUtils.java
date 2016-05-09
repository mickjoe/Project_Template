package com.jerry.pcauto.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp的工具类
 */
public class OkHttpUtils
{

    private static OkHttpClient okHttpClient;
    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 初始化OkHttpUtils
     */
    public static void initOkHttp()
    {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }


    /**
     * 异步Get请求
     *
     * @param url
     */
    public static void get(final String url, final OnGetDataListener onGetDataListener)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (onGetDataListener != null)
                {
                    onGetDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String str = response.body().string();
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (onGetDataListener != null)
                        {
                            onGetDataListener.onResponse(url, str);
                        }
                    }
                });
            }
        });
    }


    /**
     * 同步get请求 -- 让子类调用
     *
     * @return
     */
    public static Response downResponse(String url)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        try
        {
            return call.execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Post提交键值对参数
     */
    public static void postSubmitForm(
            final String url, Map<String, Object> params, final OnGetDataListener onGetDataListener)
    {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0)
        {
            for (String key : params.keySet())
            {
                String value = String.valueOf(params.get(key));
                builder.add(key, value);
            }
        }

        FormBody formBody = builder.build();

        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (onGetDataListener != null)
                {
                    onGetDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String str = response.body().string();
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (onGetDataListener != null)
                        {
                            onGetDataListener.onResponse(url, str);
                        }
                    }
                });
            }
        });

    }

    /**
     * Post提交键值对参数
     */
    public static void postSubmitForm(final String url, Map<String, Object> params)
    {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0)
        {
            for (String key : params.keySet())
            {
                String value = String.valueOf(params.get(key));
                builder.add(key, value);
            }
        }

        FormBody formBody = builder.build();

        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String str = response.body().string();
                /*handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EventBus.getDefault().post(new EventMessage(str, url));
                    }
                });*/
                EventBus.getDefault().post(new EventMessage(str, url));
            }
        });

    }

    private static final MediaType MEDIA_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * Post提交Json参数，参数放置在Http Body中
     */
    public static void postJSONBody(final String url, Object obj,
                                    final OnGetDataListener onGetDataListener)
    {
        String jsonParam = "";
        if (obj != null)
        {
            Gson gson = new GsonBuilder().create();

            jsonParam = gson.toJson(obj);
        }

        RequestBody body = RequestBody.create(MEDIA_JSON, jsonParam);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (onGetDataListener != null)
                {
                    onGetDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                final String str = response.body().string();
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (onGetDataListener != null)
                        {
                            onGetDataListener.onResponse(url, str);
                        }
                    }
                });
            }
        });
    }

    public interface OnGetDataListener
    {
        void onResponse(String url, String data);

        void onFailure(String url, String error);
    }
}
