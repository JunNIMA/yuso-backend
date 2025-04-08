package com.yupi.yuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuso.model.entity.Picture;
import org.apache.poi.ss.formula.functions.T;

/**
 * 数据源接口（新接入的数据源必须实现）
 * @param <T>
 */
public interface DataSource<T> {

    /**
     * 搜素
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, int pageNum, int pageSize);

}
