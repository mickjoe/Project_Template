package com.jerry.pcauto.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.jerry.pcauto.base.BaseApplication;

import java.util.Map;

public class SharePreUtils
{
    private static SharedPreferences sp;
    private static String defaultFileName;
    private static Editor mEditor;
    private static SharePreUtils utils;

    private SharePreUtils()
    {
    }

    @SuppressLint("CommitPrefEdits")
    public static SharePreUtils getInstance()
    {
        if (sp == null)
        {
            sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getApplication());
            mEditor = sp.edit();
        }
        if (utils == null)
        {
            utils = new SharePreUtils();
        }
        return utils;
    }

    @SuppressLint("CommitPrefEdits")
    public static SharePreUtils getInstance(String fileName)
    {
        if (defaultFileName == null || !defaultFileName.trim().equals(fileName))
        {
            defaultFileName = fileName;
            sp = BaseApplication.getApplication().getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            mEditor = sp.edit();
        }
        if (utils == null)
        {
            utils = new SharePreUtils();
        }
        return utils;
    }

    /**
     * 获取保存着的boolean对象。
     *
     * @param key      键名
     * @param defValue 当不存在时返回的默认值。
     * @return 返回获取到的值，当不存在时返回默认值。
     */
    public boolean getBoolean(String key, boolean defValue)
    {
        return sp.getBoolean(key, defValue);
    }

    /**
     * 获取保存着的int对象。
     *
     * @param key      键名
     * @param defValue 当不存在时返回的默认值。
     * @return 返回获取到的值，当不存在时返回默认值。
     */
    public int getInt(String key, int defValue)
    {
        return sp.getInt(key, defValue);
    }

    /**
     * 获取保存着的long对象。
     *
     * @param key      键名
     * @param defValue 当不存在时返回的默认值。
     * @return 返回获取到的值，当不存在时返回默认值。
     */
    public long getLong(String key, long defValue)
    {
        return sp.getLong(key, defValue);
    }

    /**
     * 获取保存着的float对象。
     *
     * @param key      键名
     * @param defValue 当不存在时返回的默认值。
     * @return 返回获取到的值，当不存在时返回默认值。
     */
    public float getFloat(String key, float defValue)
    {
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取保存着的String对象。
     *
     * @param key      键名
     * @param defValue 当不存在时返回的默认值。
     * @return 返回获取到的值，当不存在时返回默认值。
     */
    public String getString(String key, String defValue)
    {
        return sp.getString(key, defValue);
    }

    /**
     * 获取所有键值对。
     *
     * @return 获取到的所胡键值对。
     */
    public Map<String, ?> getAll()
    {
        return sp.getAll();
    }

    /**
     * 设置一个键值对，它将在{@linkplain #commit()}被调用时保存。<br/>
     * 注意：当保存的value不是boolean, byte(会被转换成int保存),int, long, float,
     * String等类型时将调用它的toString()方法进行值的保存。
     *
     * @param key   键名称。
     * @param value 值。
     * @return 引用的KV对象。
     */
    public SharePreUtils put(String key, Object value)
    {
        if (value instanceof Boolean)
        {
            mEditor.putBoolean(key, (Boolean) value);
        }
        else if (value instanceof Integer || value instanceof Byte)
        {
            mEditor.putInt(key, (Integer) value);
        }
        else if (value instanceof Long)
        {
            mEditor.putLong(key, (Long) value);
        }
        else if (value instanceof Float)
        {
            mEditor.putFloat(key, (Float) value);
        }
        else if (value instanceof String)
        {
            mEditor.putString(key, (String) value);
        }
        else
        {
            mEditor.putString(key, value.toString());
        }
        commit();
        return this;
    }

    /**
     * 移除键值对。
     *
     * @param key 要移除的键名称。
     * @return 引用的KV对象。
     */
    public SharePreUtils remove(String key)
    {
        mEditor.remove(key);
        return this;
    }

    /**
     * 清除所有键值对。
     *
     * @return 引用的KV对象。
     */
    public SharePreUtils clear()
    {
        mEditor.clear();
        return this;
    }

    /**
     * 是否包含某个键。
     *
     * @param key 查询的键名称。
     * @return 当且仅当包含该键时返回true, 否则返回false.
     */
    public boolean contains(String key)
    {
        return sp.contains(key);
    }

    /**
     * 返回是否提交成功。
     *
     * @return 当且仅当提交成功时返回true, 否则返回false.
     */
    public boolean commit()
    {
        return mEditor.commit();
    }
}
