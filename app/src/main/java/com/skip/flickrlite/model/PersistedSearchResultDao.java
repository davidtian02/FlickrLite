package com.skip.flickrlite.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface PersistedSearchResultDao {
    @Insert
    void insert(PersistedSearchResult entry);

    @Query("SELECT * FROM PersistedSearchResult WHERE searchQuery = :key ")
    PersistedSearchResult responseForKey(String key);

    // TODO have delete, because you may need to use some sort of LRU-cache and eviction policy... although more for Glide.
}
