package com.yupi.yuso.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.yuso.model.entity.Post;
import com.yupi.yuso.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全量同步帖子到 es
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
// 取消注释后，每次启动 springboot 项目时会执行一次 run 方法开启任务
//@Component
@Slf4j
public class FullSyncPostToEs implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
        //1. 获取数据
        String json = "{\"pageSize\":12,\"sortOrder\":\"descend\",\"sortField\":\"_score\",\"tags\":[],\"searchText\":\"\",\"current\":1,\"reviewStatus\":1,\"hiddenContent\":true,\"type\":\"passage\"}";
        String url = "https://api.codefather.cn/api/search/";

        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();
        System.out.println(result);
        //2. json转对象
        Map<String, Object> map = JSONUtil.toBean(result,Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONObject value = (JSONObject) data.get("searchPage");
        JSONArray records = (JSONArray) value.get("records") ;
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempObject = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempObject.getStr("title"));
            post.setContent(tempObject.getStr("description"));
            JSONArray tags = (JSONArray)tempObject.get("tags");
            List<String> tagsList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagsList));
            post.setThumbNum(Integer.parseInt(tempObject.getStr("thumbNum")));
            post.setFavourNum(Integer.parseInt(tempObject.getStr("favourNum")));
            post.setUserId(1L);
            postList.add(post);
        }
//        System.out.println(postList);
        //3.数据入库
        boolean b = postService.saveBatch(postList);
        if(b){
            log.info("获取帖子列表成功，条数为 {}",postList.size());
        }else{
            log.error("获取帖子列表失败");
        }

    }
}
