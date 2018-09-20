package com.skip.flickrlite.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = PersistedSearchResult.class, version = 1, exportSchema = false)
public abstract class PersistedSearchResultDatabase extends RoomDatabase{
    public abstract PersistedSearchResultDao getDao();
}
