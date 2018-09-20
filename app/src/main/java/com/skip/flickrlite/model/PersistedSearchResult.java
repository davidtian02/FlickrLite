package com.skip.flickrlite.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class PersistedSearchResult {
    @NonNull
    @PrimaryKey
    public String searchQuery;

    @ColumnInfo
    public String response;
}
