package com.yupi.yuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 帖子服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class VideoDataSource implements DataSource<Object> {

    @Override
    public Page<Object> doSearch(String searchText, int pageNum, int pageSize) {
        return null;
    }
}




