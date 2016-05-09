package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table "LORE_DETAIL_TABLE".
 */
public class LoreDetailTable
{

    private Long id;
    /**
     * Not-null value.
     */
    private String title;
    /**
     * Not-null value.
     */
    private String imageUrl;
    private Long time;

    public LoreDetailTable()
    {
    }

    public LoreDetailTable(Long id)
    {
        this.id = id;
    }

    public LoreDetailTable(Long id, String title, String imageUrl, Long time)
    {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.time = time;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Not-null value.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Not-null value.
     */
    public String getImageUrl()
    {
        return imageUrl;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

}
