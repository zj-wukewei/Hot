package com.wkw.hot.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wukewei on 16/6/25.
 */
public class ListPopularEntity implements Serializable {
    public List<PopularEntity> data;

    public ListPopularEntity(List<PopularEntity> data) {
        this.data = data;
    }
}
