package com.jerry.pcauto.module.home.entity;


/**
 * Created by mac on 22/4/16.
 */
public class LoreDetailEntity
{
    private int count;
    private String description;
    private int fcount;
    private int id;
    private String img;
    private String keywords;
    private int loreclass;
    private String message;
    private int rcount;
    private boolean status;
    private long time;
    private String title;
    private String url;
    // 类型 0 - 收藏 1 - 浏览记录
    private int type;

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getFcount()
    {
        return fcount;
    }

    public void setFcount(int fcount)
    {
        this.fcount = fcount;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public int getLoreclass()
    {
        return loreclass;
    }

    public void setLoreclass(int loreclass)
    {
        this.loreclass = loreclass;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getRcount()
    {
        return rcount;
    }

    public void setRcount(int rcount)
    {
        this.rcount = rcount;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
