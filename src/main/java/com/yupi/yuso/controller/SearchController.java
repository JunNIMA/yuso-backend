package com.yupi.yuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuso.common.BaseResponse;
import com.yupi.yuso.common.ErrorCode;
import com.yupi.yuso.common.ResultUtils;
import com.yupi.yuso.exception.BusinessException;
import com.yupi.yuso.exception.ThrowUtils;
import com.yupi.yuso.manager.SearchFacade;
import com.yupi.yuso.model.dto.picture.PictureQueryRequest;
import com.yupi.yuso.model.dto.post.PostQueryRequest;
import com.yupi.yuso.model.dto.search.SearchRequest;
import com.yupi.yuso.model.dto.user.UserQueryRequest;
import com.yupi.yuso.model.entity.Picture;
import com.yupi.yuso.model.entity.Post;
import com.yupi.yuso.model.enums.SearchTypeEnum;
import com.yupi.yuso.model.vo.PostVO;
import com.yupi.yuso.model.vo.SearchVO;
import com.yupi.yuso.model.vo.UserVO;
import com.yupi.yuso.service.PictureService;
import com.yupi.yuso.service.PostService;
import com.yupi.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 图片接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest,HttpServletRequest request){
        return ResultUtils.success(searchFacade.searchAll(searchRequest,request));
    }

}
