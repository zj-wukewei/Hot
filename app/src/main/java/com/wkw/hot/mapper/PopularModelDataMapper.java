package com.wkw.hot.mapper;

import com.wkw.hot.entity.PopularEntity;
import com.wkw.hot.model.PopularModel;
import com.wkw.hot.reject.PerActivity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by wukewei on 16/12/2.
 * Email zjwkw1992@163.com
 * GitHub https://github.com/zj-wukewei
 */
public class PopularModelDataMapper {

    @Inject
    public PopularModelDataMapper() {

    }

    public PopularModel transform(PopularEntity popularEntity) {
        if (popularEntity == null) {
            throw  new IllegalArgumentException("Cannot transform a null value");
        }
        PopularModel popularModel = new PopularModel();
        popularModel.setCtime(popularEntity.getCtime());
        popularModel.setDescription(popularEntity.getDescription());
        popularModel.setPicUrl(popularEntity.getPicUrl());
        popularModel.setTitle(popularEntity.getTitle());
        popularModel.setUrl(popularEntity.getUrl());
        return popularModel;
    }


    public List<PopularModel> transform(List<PopularEntity> popularList) {
        List<PopularModel> popularModelsList = new ArrayList<>();
        if (popularList != null && !popularList.isEmpty()) {
            for (PopularEntity popularEntity : popularList) {
                popularModelsList.add(transform(popularEntity));
            }
        }
        return popularModelsList;
    }


}
