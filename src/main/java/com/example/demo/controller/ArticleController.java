package com.example.demo.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.mapper.ArticleMapper;
import com.example.demo.mapper.CollectionlistMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.LikelistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@RestController()
public class ArticleController {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CollectionlistMapper collectionlistMapper;

    @Autowired
    LikelistMapper likelistMapper;
//    //文章分类查询
//    @GetMapping(value = "article/ArtClassData")
//    @CrossOrigin       //后端跨域
//    public Result GetArtClassData(){
//        return new Result(1001,"Query Success");
//    }
//

    //发布文章
    @GetMapping(value = "article/Publish")
    @CrossOrigin
    public Result Publish(@RequestParam String title, @RequestParam String image, @RequestParam String description, @RequestParam String content, @RequestParam String classId,@RequestParam String catename) {
//        //将%2B转化为+
//        String decode1 = URLDecoder.decode(content, StandardCharsets.UTF_8);
//        byte[] decode = Base64.getMimeDecoder().decode(decode1);
//        String content1 = new String(decode, StandardCharsets.UTF_8);
        Article art = new Article();
        art.setTitle(title);
        art.setBrowsecount(0);
        art.setImage(image);
        art.setDescription(description);
        art.setContent(content);
        art.setCreateTime(DateTime.now().toString());
        art.setCommentcount(0);
        art.setLikecount(0);
        art.setCollectcount(0);
        art.setIsrecommend(0);
        art.setIshot(0);
        art.setClassid(Integer.valueOf(classId));
        art.setCatename(catename);
        art.setWechatimage("");
        art.setAlipayimage("");
        if (articleMapper.insert(art) == 1) {
            return new Result(1001, "insert Success", art);
        }
        return new Result(400, "insert error");
    }


    //查询文章列表
    @GetMapping(value = "article/ShowArticleAll")
    @CrossOrigin       //后端跨域
    public Result GetShowArticleAll(@RequestParam String art_id, @RequestParam String cate_id, @RequestParam String article_name) {
        QueryWrapper<Article> wp = new QueryWrapper<Article>();
        wp.orderByDesc("Id");

        wp.like("title", article_name);
        List<Article> art = articleMapper.selectList(wp);
        return new Result(1001, "Query Success", art);
    }
//


    //查询文章详情
    @GetMapping(value = "article/getArticleInfo")
    @CrossOrigin       //后端跨域
    public Result GetArticleInfo(@RequestParam String art_id, @RequestParam String userid) {
        Article art = articleMapper.selectById(art_id);
        art.setBrowsecount(art.getBrowsecount() + 1);
        articleMapper.updateById(art);
        QueryWrapper<Likelist> lwp = new QueryWrapper<>();
        lwp.eq("userid", userid);
        lwp.eq("articleid", art_id);
        Likelist lkt = likelistMapper.selectOne(lwp);
        if (lkt == null) {
            art.setUserlikestart("0");
        } else {
            art.setUserlikestart("1");
        }
        QueryWrapper<Collectionlist> cwp = new QueryWrapper<>();
        cwp.eq("userid", userid);
        cwp.eq("articleid", art_id);
        Collectionlist cot = collectionlistMapper.selectOne(cwp);
        if (cot == null) {
            art.setUsercollectstart("0");
        } else {
            art.setUsercollectstart("1");
        }

        String content = art.getContent().replaceAll(" ", "+");
        final Base64.Decoder decoder = Base64.getDecoder();
        content = new String(decoder.decode(content), StandardCharsets.UTF_8);
        art.setContent(content);

        return new Result(1001, "Query Success", art);
    }
//


    //    //查询浏览量最多的10篇文章数据
    @GetMapping(value = "article/ShowBrowseCount")
    @CrossOrigin       //后端跨域
    public Result ShowBrowseCount() {
        QueryWrapper<Article> wp = new QueryWrapper<Article>();
        wp.orderByDesc("browsecount");
        List<Article> art = articleMapper.selectList(wp);
        return new Result(1001, "Query Success", art);
    }

    //    //查询文章评论量最大的10篇文章
    @GetMapping(value = "article/ShowArtCommentCount")
    @CrossOrigin       //后端跨域
    public Result ShowArtCommentCount() {
        QueryWrapper<Article> wp = new QueryWrapper<Article>();
        wp.gt("commentcount", 0);
        wp.orderByDesc("commentcount");
        List<Article> art = articleMapper.selectList(wp);

        for (Article a : art) {
            QueryWrapper<Comment> cmt = new QueryWrapper<Comment>();
            cmt.eq("articleid", a.getId());
            cmt.last("limit 1");
            Comment ct = commentMapper.selectOne(cmt);
            if (ct != null) {
                a.setUsername(ct.getUsername());
                a.setAvatar(ct.getAvatar());
                a.setComment(ct.getContent());
            }
        }
        return new Result(1001, "Query Success", art);
    }


    //{"code":1001,"msg":"Data processing success"}
//    //文章点击收藏 点击喜欢
    @GetMapping(value = "article/getArtLike")
    @CrossOrigin       //后端跨域
    public Result GetArtLike(@RequestParam int userid, @RequestParam int art_id) {
        QueryWrapper<Likelist> lwp = new QueryWrapper<>();
        lwp.eq("userid", userid);
        lwp.eq("articleid", art_id);
        Likelist lkt = likelistMapper.selectOne(lwp);
        if (lkt == null) {
            Likelist llt = new Likelist();
            llt.setUserid(userid);
            llt.setArticleid(art_id);
            if (likelistMapper.insert(llt) == 1) {
                Article art = articleMapper.selectById(art_id);
                art.setLikecount(art.getLikecount() + 1);
                if (articleMapper.updateById(art) == 1) {
                    return new Result(1001, "Data processing success");
                }

            }
            return new Result(400, "error");
        } else {
            if (likelistMapper.delete(lwp) == 1) {
                Article art = articleMapper.selectById(art_id);
                art.setLikecount(art.getLikecount() - 1);
                if (articleMapper.updateById(art) == 1) {
                    return new Result(1001, "Data processing success");
                }
            }
            return new Result(400, "error");
        }
    }
//

    //    {"code":1001,"msg":"Data processing success"}
//    //文章点击收藏 点击喜欢
    @GetMapping(value = "article/getArtCollect")
    @CrossOrigin       //后端跨域
    public Result GetArtCollect(@RequestParam int userid, @RequestParam int art_id) {
        QueryWrapper<Collectionlist> cwp = new QueryWrapper<>();
        cwp.eq("userid", userid);
        cwp.eq("articleid", art_id);
        Collectionlist cot = collectionlistMapper.selectOne(cwp);
        if (cot == null) {
            Collectionlist clt = new Collectionlist();
            clt.setUserid(userid);
            clt.setArticleid(art_id);
            if (collectionlistMapper.insert(clt) == 1) {
                Article art = articleMapper.selectById(art_id);
                art.setCollectcount(art.getCollectcount() + 1);
                if (articleMapper.updateById(art) == 1) {
                    return new Result(1001, "Data processing success");
                }
            }
            return new Result(400, "error");
        } else {
            if (collectionlistMapper.delete(cwp) == 1) {
                Article art = articleMapper.selectById(art_id);
                art.setLikecount(art.getLikecount() - 1);
                if (articleMapper.updateById(art) == 1) {
                    return new Result(1001, "Data processing success");
                }
            }
            return new Result(400, "error");
        }
    }


    //查询用户喜欢列表,查询用户收藏列表
    @GetMapping(value = "article/getLikeList")
    @CrossOrigin       //后端跨域
    public Result GetLikeList(@RequestParam String userid, @RequestParam String art_id, @RequestParam String article_name) {
        QueryWrapper<Likelist> wp = new QueryWrapper<Likelist>();
        wp.eq("userid", userid);
        List<Likelist> llt = likelistMapper.selectList(wp);
        List<Article> alt = new LinkedList<Article>();
        for (Likelist lt : llt) {
            QueryWrapper<Article> awp = new QueryWrapper<Article>();
            awp.eq("id", lt.getArticleid());
            alt.add(articleMapper.selectOne(awp));
        }
        return new Result(1001, "Query Success", alt);
    }
//

    //查询用户喜欢列表,查询用户收藏列表
    @GetMapping(value = "article/getCollectList")
    @CrossOrigin       //后端跨域
    public Result GetCollectList(@RequestParam String userid, @RequestParam String art_id, @RequestParam String article_name) {
        QueryWrapper<Collectionlist> wp = new QueryWrapper<Collectionlist>();
        wp.eq("userid", userid);
        List<Collectionlist> llt = collectionlistMapper.selectList(wp);
        List<Article> alt = new LinkedList<Article>();
        for (Collectionlist lt : llt) {
            QueryWrapper<Article> awp = new QueryWrapper<Article>();
            awp.eq("id", lt.getArticleid());
            alt.add(articleMapper.selectOne(awp));
        }
        return new Result(1001, "Query Success", alt);
    }


}
