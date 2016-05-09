package com.jerry.pcauto.utils;

/**
 * Created by mac on 28/4/16.
 */
public class EventMessage
{
    public String type;
    public String result;

    public EventMessage(String result, String type)
    {
        this.result = result;
        this.type = type;
    }
}
