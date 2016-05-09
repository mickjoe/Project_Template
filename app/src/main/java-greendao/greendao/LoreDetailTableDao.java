package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "LORE_DETAIL_TABLE".
 */
public class LoreDetailTableDao extends AbstractDao<LoreDetailTable, Long>
{

    public static final String TABLENAME = "LORE_DETAIL_TABLE";

    /**
     * Properties of entity LoreDetailTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties
    {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property ImageUrl = new Property(2, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property Time = new Property(3, Long.class, "time", false, "TIME");
    }

    ;


    public LoreDetailTableDao(DaoConfig config)
    {
        super(config);
    }

    public LoreDetailTableDao(DaoConfig config, DaoSession daoSession)
    {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists)
    {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"LORE_DETAIL_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TITLE\" TEXT NOT NULL UNIQUE ," + // 1: title
                "\"IMAGE_URL\" TEXT NOT NULL ," + // 2: imageUrl
                "\"TIME\" INTEGER);"); // 3: time
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(SQLiteDatabase db, boolean ifExists)
    {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LORE_DETAIL_TABLE\"";
        db.execSQL(sql);
    }

    /**
     * @inheritdoc
     */
    @Override
    protected void bindValues(SQLiteStatement stmt, LoreDetailTable entity)
    {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null)
        {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindString(3, entity.getImageUrl());

        Long time = entity.getTime();
        if (time != null)
        {
            stmt.bindLong(4, time);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long readKey(Cursor cursor, int offset)
    {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /**
     * @inheritdoc
     */
    @Override
    public LoreDetailTable readEntity(Cursor cursor, int offset)
    {
        LoreDetailTable entity = new LoreDetailTable( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // title
                cursor.getString(offset + 2), // imageUrl
                cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // time
        );
        return entity;
    }

    /**
     * @inheritdoc
     */
    @Override
    public void readEntity(Cursor cursor, LoreDetailTable entity, int offset)
    {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setImageUrl(cursor.getString(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
    }

    /**
     * @inheritdoc
     */
    @Override
    protected Long updateKeyAfterInsert(LoreDetailTable entity, long rowId)
    {
        entity.setId(rowId);
        return rowId;
    }

    /**
     * @inheritdoc
     */
    @Override
    public Long getKey(LoreDetailTable entity)
    {
        if (entity != null)
        {
            return entity.getId();
        }
        else
        {
            return null;
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    protected boolean isEntityUpdateable()
    {
        return true;
    }

}
