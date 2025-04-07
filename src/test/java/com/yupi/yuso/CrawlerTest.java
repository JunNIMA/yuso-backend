package com.yupi.yuso;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yupi.yuso.model.entity.Picture;
import com.yupi.yuso.model.entity.Post;
import com.yupi.yuso.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://cn.bing.com/images/search?q=小黑子&form=HDRSC2&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            //取图片地址（murl）
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
           // System.out.println(murl);
            //取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
           // System.out.println(title);
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
        }

    }


    @Test
    void testFetchPassage(){
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
        Assertions.assertTrue(b);

    }

}
