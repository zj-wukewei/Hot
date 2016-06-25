package com.wkw.hot.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wukewei on 16/6/25.
 */
public class ListPopular implements Serializable {
    public List<Popular> data;

    public ListPopular(List<Popular> data) {
        this.data = data;
    }
}
