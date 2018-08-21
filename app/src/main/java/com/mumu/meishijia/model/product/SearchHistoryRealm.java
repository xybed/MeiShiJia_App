package com.mumu.meishijia.model.product;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 77 on 2018/8/20 0020.
 */

public class SearchHistoryRealm extends RealmObject{
    @PrimaryKey
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
