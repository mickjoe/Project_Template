package com.jerry.pcauto.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jerry.Zou
 */
public class JSONUtils
{
    public static boolean isPrintException = true;

    private static final String LOG_TAG ="JSON";

    /**
     * 空的 {@code JSON} 数据 - <code>"{}"</code>。
     */
    public static final String EMPTY_JSON = "{}";

    /**
     * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。
     */
    public static final String EMPTY_JSON_ARRAY = "[]";

    /**
     * 默认的 {@code JSON} 日期/时间字段的格式化模式。
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";// "yyyy-MM-dd HH:mm:ss SSS"
    // 精确到毫秒

    /**
     * 默认的 {@code JSON} 是否排除有 {@literal @Expose} 注解的字段。
     */
    public static final boolean EXCLUDE_FIELDS_WITHOUT_EXPOSE = false;

    /**
     * {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.0}。
     */
    public static final Double SINCE_VERSION_10 = 1.0d;

    /**
     * {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.1}。
     */
    public static final Double SINCE_VERSION_11 = 1.1d;

    /**
     * {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.2}。
     */
    public static final Double SINCE_VERSION_12 = 1.2d;

    /**
     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
     * <p/>
     * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，普通对象返回 <code>"{}"</code>； 集合或数组对象返回
     * <code>"[]"</code> </strong>
     *
     * @param target                      目标对象。
     * @param targetType                  目标对象的类型。
     * @param isSerializeNulls            是否序列化 {@code null} 值字段。
     * @param version                     字段的版本号注解。
     * @param datePattern                 日期字段的格式化模式。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType, boolean isSerializeNulls,
                                Double version, String datePattern,
                                boolean excludesFieldsWithoutExpose)
    {
        if (target == null)
        {
            return EMPTY_JSON;
        }

        GsonBuilder builder = new GsonBuilder();
        if (isSerializeNulls)
        {
            builder.serializeNulls();
        }

        if (version != null)
        {
            builder.setVersion(version.doubleValue());
        }

        if (isEmpty(datePattern))
        {
            datePattern = DEFAULT_DATE_PATTERN;
        }

        builder.setDateFormat(datePattern);
        if (excludesFieldsWithoutExpose)
        {
            builder.excludeFieldsWithoutExposeAnnotation();
        }

        String result = "";

        // 添加排除策略
        addExclusionStrategy(builder);

        Gson gson = builder.create();

        try
        {
            if (targetType != null)
            {
                result = gson.toJson(target, targetType);
            }
            else
            {
                result = gson.toJson(target);
            }
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, "目标对象 " + target.getClass().getName() + " 转换 JSON 字符串时，发生异常!", ex);
            if (target instanceof Collection || target instanceof Iterator || target instanceof Enumeration || target.getClass().isArray())
            {
                result = EMPTY_JSON_ARRAY;
            }
            else
            {
                result = EMPTY_JSON;
            }

        }

        return result;
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
     * </ul>
     *
     * @param target 要转换成 {@code JSON} 的目标对象。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target)
    {
        return toJson(target, null, false, null, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * </ul>
     *
     * @param target      要转换成 {@code JSON} 的目标对象。
     * @param datePattern 日期字段的格式化模式。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, String datePattern)
    {
        return toJson(target, null, false, null, datePattern, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
     * </ul>
     *
     * @param target  要转换成 {@code JSON} 的目标对象。
     * @param version 字段的版本号注解({@literal @Since})。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Double version)
    {
        return toJson(target, null, false, version, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
     * </ul>
     *
     * @param target                      要转换成 {@code JSON} 的目标对象。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, boolean excludesFieldsWithoutExpose)
    {
        return toJson(target, null, false, null, null, excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     * <ul>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
     * </ul>
     *
     * @param target                      要转换成 {@code JSON} 的目标对象。
     * @param version                     字段的版本号注解({@literal @Since})。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Double version, boolean excludesFieldsWithoutExpose)
    {
        return toJson(target, null, false, version, null, excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
     * </ul>
     *
     * @param target     要转换成 {@code JSON} 的目标对象。
     * @param targetType 目标对象的类型。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType)
    {
        return toJson(target, targetType, false, null, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
     * </ul>
     *
     * @param target     要转换成 {@code JSON} 的目标对象。
     * @param targetType 目标对象的类型。
     * @param version    字段的版本号注解({@literal @Since})。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType, Double version)
    {
        return toJson(target, targetType, false, version, null, EXCLUDE_FIELDS_WITHOUT_EXPOSE);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
     * <ul>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
     * </ul>
     *
     * @param target                      要转换成 {@code JSON} 的目标对象。
     * @param targetType                  目标对象的类型。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType, boolean excludesFieldsWithoutExpose)
    {
        return toJson(target, targetType, false, null, null, excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
     * <ul>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss}</li>
     * </ul>
     *
     * @param target                      要转换成 {@code JSON} 的目标对象。
     * @param targetType                  目标对象的类型。
     * @param version                     字段的版本号注解({@literal @Since})。
     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     */
    public static String toJson(Object target, Type targetType, Double version,
                                boolean excludesFieldsWithoutExpose)
    {
        return toJson(target, targetType, false, version, null, excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>         要转换的目标类型。
     * @param json        给定的 {@code JSON} 字符串。
     * @param token       {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @param datePattern 日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern)
    {
        if (isEmpty(json))
        {
            return null;
        }

        GsonBuilder builder = new GsonBuilder();
        if (isEmpty(datePattern))
        {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);

        // 添加排除策略
        addExclusionStrategy(builder);

        Gson gson = builder.create();

        try
        {
            return gson.fromJson(json, token.getType());
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, json + " 无法转换为 " + token.getRawType().getName() + " 对象!", ex);
            return null;
        }
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>  要转换的目标类型。
     * @param json 给定的 {@code JSON} 字符串。
     * @param type {@code java.lang.reflect.Type} 的类型指示类的类型。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, Type type)
    {
        if (isEmpty(json))
        {
            return null;
        }

        GsonBuilder builder = new GsonBuilder();

        // 添加排除策略
        addExclusionStrategy(builder);

        Gson gson = builder.create();

        try
        {
            T r = gson.fromJson(json, type);
            return r;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e(LOG_TAG, json + " 无法转换对象!", ex);
        }
        return null;
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>   要转换的目标类型。
     * @param json  给定的 {@code JSON} 字符串。
     * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, TypeToken<T> token)
    {
        return fromJson(json, token, null);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     *
     * @param <T>         要转换的目标类型。
     * @param json        给定的 {@code JSON} 字符串。
     * @param clazz       要转换的目标类。
     * @param datePattern 日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, Class<T> clazz, String datePattern)
    {
        if (isEmpty(json))
        {
            return null;
        }

        GsonBuilder builder = new GsonBuilder();
        if (isEmpty(datePattern))
        {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);

        // 添加排除策略
        addExclusionStrategy(builder);

        Gson gson = builder.create();

        try
        {
            return gson.fromJson(json, clazz);
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, json + " 无法转换为 " + clazz.getName() + " 对象!", ex);
            return null;
        }
    }

    /**
     * 添加排除策略. <br/>
     * 日期: 2014-1-20 下午3:17:54 <br/>
     *
     * @param builder
     * @author wangyuyao
     * @since JDK 1.6
     */
    private static void addExclusionStrategy(GsonBuilder builder)
    {
        // add by wez 2014.01.20
        builder.addSerializationExclusionStrategy(new ExclusionStrategy()
        {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes)
            {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass)
            {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy()
        {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes)
            {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.deserialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass)
            {
                return false;
            }
        });
    }

    /**
     * get Long from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getLong(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getLong(String)}</li>
     * </ul>
     */
    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            return jsonObject.getLong(key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Long from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getLong(JSONObject, String, JSONObject)}</li>
     * </ul>
     */
    public static Long getLong(String jsonData, String key, Long defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getLong(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getLong(JSONObject, String, Long)
     */
    public static long getLong(JSONObject jsonObject, String key, long defaultValue)
    {
        return getLong(jsonObject, key, (Long) defaultValue);
    }

    /**
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getLong(String, String, Long)
     */
    public static long getLong(String jsonData, String key, long defaultValue)
    {
        return getLong(jsonData, key, (Long) defaultValue);
    }

    /**
     * get Int from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getInt(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getInt(String)}</li>
     * </ul>
     */
    public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            return jsonObject.getInt(key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Int from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getInt(JSONObject, String, JSONObject)}</li>
     * </ul>
     */
    public static Integer getInt(String jsonData, String key, Integer defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getInt(JSONObject, String, Integer)
     */
    public static int getInt(JSONObject jsonObject, String key, int defaultValue)
    {
        return getInt(jsonObject, key, (Integer) defaultValue);
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getInt(String, String, Integer)
     */
    public static int getInt(String jsonData, String key, int defaultValue)
    {
        return getInt(jsonData, key, (Integer) defaultValue);
    }

    /**
     * get Double from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getDouble(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getDouble(String)}</li>
     * </ul>
     */
    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            return jsonObject.getDouble(key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Double from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getDouble(JSONObject, String, JSONObject)}</li>
     * </ul>
     */
    public static Double getDouble(String jsonData, String key, Double defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getDouble(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getDouble(JSONObject, String, Double)
     */
    public static double getDouble(JSONObject jsonObject, String key, double defaultValue)
    {
        return getDouble(jsonObject, key, (Double) defaultValue);
    }

    /**
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getDouble(String, String, Double)
     */
    public static double getDouble(String jsonData, String key, double defaultValue)
    {
        return getDouble(jsonData, key, (Double) defaultValue);
    }

    /**
     * get String from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getString(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getString(String)}</li>
     * </ul>
     */
    public static String getString(JSONObject jsonObject, String key, String defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            return jsonObject.getString(key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getString(JSONObject, String, JSONObject)}</li>
     * </ul>
     */
    public static String getString(String jsonData, String key, String defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String from jsonObject
     *
     * @param jsonObject
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if keyArray is null or empty, return defaultValue</li>
     * <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     * null, return directly</li>
     * </ul>
     */
    public static String getStringCascade(JSONObject jsonObject, String defaultValue,
                                          String... keyArray)
    {
        if (jsonObject == null || ArrayUtils.isEmpty(keyArray))
        {
            return defaultValue;
        }

        String data = jsonObject.toString();
        for (String key : keyArray)
        {
            data = getStringCascade(data, key, defaultValue);
            if (data == null)
            {
                return defaultValue;
            }
        }
        return data;
    }

    /**
     * get String from jsonData
     *
     * @param jsonData
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     * <li>if jsonData is null, return defaultValue</li>
     * <li>if keyArray is null or empty, return defaultValue</li>
     * <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     * null, return directly</li>
     * </ul>
     */
    public static String getStringCascade(String jsonData, String defaultValue, String... keyArray)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        String data = jsonData;
        for (String key : keyArray)
        {
            data = getString(data, key, defaultValue);
            if (data == null)
            {
                return defaultValue;
            }
        }
        return data;
    }

    /**
     * get String array from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     * <li>if {@link JSONArray#getString(int)} exception, return defaultValue</li>
     * <li>return string array</li>
     * </ul>
     */
    public static String[] getStringArray(JSONObject jsonObject, String key, String[] defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null)
            {
                String[] value = new String[statusArray.length()];
                for (int i = 0;
                     i < statusArray.length();
                     i++)
                {
                    value[i] = statusArray.getString(i);
                }
                return value;
            }
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * get String array from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getStringArray(JSONObject, String, JSONObject)}</li>
     * </ul>
     */
    public static String[] getStringArray(String jsonData, String key, String[] defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringArray(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get String list from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     * <li>if {@link JSONArray#getString(int)} exception, return defaultValue</li>
     * <li>return string array</li>
     * </ul>
     */
    public static List<String> getStringList(JSONObject jsonObject, String key,
                                             List<String> defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null)
            {
                List<String> list = new ArrayList<String>();
                for (int i = 0;
                     i < statusArray.length();
                     i++)
                {
                    list.add(statusArray.getString(i));
                }
                return list;
            }
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * get String list from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getStringList(JSONObject, String, List)}</li>
     * </ul>
     */
    public static List<String> getStringList(String jsonData, String key, List<String> defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringList(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONObject from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getJSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getJSONObject(String)}</li>
     * </ul>
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key,
                                           JSONObject defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            return jsonObject.getJSONObject(key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONObject from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonData is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getJSONObject(JSONObject, String, JSONObject)}</li>
     * </ul>
     */
    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObject(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONObject from jsonObject
     *
     * @param jsonObject
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if keyArray is null or empty, return defaultValue</li>
     * <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     * null, return directly</li>
     * </ul>
     */
    public static JSONObject getJSONObjectCascade(JSONObject jsonObject, JSONObject defaultValue,
                                                  String... keyArray)
    {
        if (jsonObject == null || ArrayUtils.isEmpty(keyArray))
        {
            return defaultValue;
        }

        JSONObject js = jsonObject;
        for (String key : keyArray)
        {
            js = getJSONObject(js, key, defaultValue);
            if (js == null)
            {
                return defaultValue;
            }
        }
        return js;
    }

    /**
     * get JSONObject from jsonData
     *
     * @param jsonData
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     * <li>if jsonData is null, return defaultValue</li>
     * <li>if keyArray is null or empty, return defaultValue</li>
     * <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     * null, return directly</li>
     * </ul>
     */
    public static JSONObject getJSONObjectCascade(String jsonData, JSONObject defaultValue,
                                                  String... keyArray)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObjectCascade(jsonObject, defaultValue, keyArray);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONArray from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     * <li>return {@link JSONObject#getJSONArray(String)}</li>
     * </ul>
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            return jsonObject.getJSONArray(key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get JSONArray from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getJSONArray(JSONObject, String, JSONObject)}</li>
     * </ul>
     */
    public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONArray(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Boolean from jsonObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if key is null or empty, return defaultValue</li>
     * <li>return {@link JSONObject#getBoolean(String)}</li>
     * </ul>
     */
    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue)
    {
        if (jsonObject == null || StringUtils.isEmpty(key))
        {
            return defaultValue;
        }

        try
        {
            return jsonObject.getBoolean(key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get Boolean from jsonData
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     * <li>if jsonObject is null, return defaultValue</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     * <li>return {@link JSONUtils#getBoolean(JSONObject, String, Boolean)}</li>
     * </ul>
     */
    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue)
    {
        if (StringUtils.isEmpty(jsonData))
        {
            return defaultValue;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getBoolean(jsonObject, key, defaultValue);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * get map from jsonObject.
     *
     * @param jsonObject key-value pairs json
     * @param key
     * @return <ul>
     * <li>if jsonObject is null, return null</li>
     * <li>return {@link JSONUtils#parseKeyAndValueToMap(String)}</li>
     * </ul>
     */
    public static Map<String, String> getMap(JSONObject jsonObject, String key)
    {
        return JSONUtils.parseKeyAndValueToMap(JSONUtils.getString(jsonObject, key, null));
    }

    /**
     * get map from jsonData.
     *
     * @param jsonData key-value pairs string
     * @param key
     * @return <ul>
     * <li>if jsonData is null, return null</li>
     * <li>if jsonData length is 0, return empty map</li>
     * <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return null</li>
     * <li>return {@link JSONUtils#getMap(JSONObject, String)}</li>
     * </ul>
     */
    public static Map<String, String> getMap(String jsonData, String key)
    {

        if (jsonData == null)
        {
            return null;
        }
        if (jsonData.length() == 0)
        {
            return new HashMap<String, String>();
        }

        try
        {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getMap(jsonObject, key);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * parse key-value pairs to map. ignore empty key, if getValue exception, put empty value
     *
     * @param sourceObj key-value pairs json
     * @return <ul>
     * <li>if sourceObj is null, return null</li>
     * <li>else parse entry by {@link MapUtils#putMapNotEmptyKey(Map, String, String)} one by one</li>
     * </ul>
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> parseKeyAndValueToMap(JSONObject sourceObj)
    {
        if (sourceObj == null)
        {
            return null;
        }

        Map<String, String> keyAndValueMap = new HashMap<String, String>();
        for (Iterator iter = sourceObj.keys();
             iter.hasNext(); )
        {
            String key = (String) iter.next();
            MapUtils.putMapNotEmptyKey(keyAndValueMap, key, getString(sourceObj, key, ""));

        }
        return keyAndValueMap;
    }

    /**
     * parse key-value pairs to map. ignore empty key, if getValue exception, put empty value
     *
     * @param source key-value pairs json
     * @return <ul>
     * <li>if source is null or source's length is 0, return empty map</li>
     * <li>if source {@link JSONObject#JSONObject(String)} exception, return null</li>
     * <li>return {@link JSONUtils#parseKeyAndValueToMap(JSONObject)}</li>
     * </ul>
     */
    public static Map<String, String> parseKeyAndValueToMap(String source)
    {
        if (StringUtils.isEmpty(source))
        {
            return null;
        }

        try
        {
            JSONObject jsonObject = new JSONObject(source);
            return parseKeyAndValueToMap(jsonObject);
        }
        catch (JSONException e)
        {
            if (isPrintException)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     *
     * @param <T>   要转换的目标类型。
     * @param json  给定的 {@code JSON} 字符串。
     * @param clazz 要转换的目标类。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T fromJson(String json, Class<T> clazz)
    {
        return fromJson(json, clazz, null);
    }

    /**
     * 判断json字符串是否为空
     *
     * @param json
     * @return
     */
    private static boolean isEmpty(String json)
    {
        return json == null || json.trim().length() == 0;
    }
}
