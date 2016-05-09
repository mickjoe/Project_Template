package com.jerry.pcauto.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class ReflectUtils
{
    /**
     * 检测实体属性是否已经被标注为 不被识别
     *
     * @param field 字段
     * @return
     */
    public static boolean isTransient(Field field)
    {
        //return field.getAnnotation(TATransparent.class) != null;

        return true;
    }

    /**
     * 是否为基本的数据类型
     *
     * @param field
     * @return
     */
    public static boolean isBaseDateType(Field field)
    {
        Class<?> clazz = field.getType();
        return clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(
                Byte.class) || clazz.equals(Long.class) || clazz.equals(
                Double.class) || clazz.equals(Float.class) || clazz.equals(
                Character.class) || clazz.equals(Short.class) || clazz.equals(
                Boolean.class) || clazz.equals(Date.class) || clazz.equals(
                Date.class) || clazz.equals(java.sql.Date.class) || clazz.isPrimitive();
    }

    /**
     * reflect to invoke object method
     *
     * @param methodName
     * @param object
     * @param params
     */
    public static void invokeMethed(String methodName, Object object, Object[] params)
    {
        if (object == null)
        {
            return;
        }

        Class<?> cls = object.getClass();
        Class<?>[] paramClses = new Class<?>[params.length];
        int index = 0;
        for (Object item : params)
        {
            if (item != null)
            {
                paramClses[index++] = item.getClass();
            }
        }

        try
        {
            Method method = cls.getDeclaredMethod(methodName, paramClses);

            method.invoke(object, params);
        }
        catch (NoSuchMethodException e)
        {
            //e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
