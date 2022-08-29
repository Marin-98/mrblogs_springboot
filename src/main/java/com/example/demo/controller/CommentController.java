package com.example.demo.controller;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Article;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.ArticleMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController()
public class CommentController {


    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ArticleMapper articleMapper;

   //查询文章评论数据
    @GetMapping(value = "comment/ArticleComment")
    @CrossOrigin       //后端跨域
    public Result ArticleComment(@RequestParam String art_id, @RequestParam String commentid){
        QueryWrapper wp=new QueryWrapper();
        wp.eq("articleid",art_id);
        wp.gt("pid",0);

        List<Comment> lst=commentMapper.selectList(wp);
        QueryWrapper wp1=new QueryWrapper();
        wp1.eq("articleid",art_id);
        wp1.eq("pid",0);
        List<Comment> lst1=commentMapper.selectList(wp1);
        for (Comment c:lst1) {
            List<Comment> ad=new LinkedList<>();
            for (Comment p:lst) {
                if (p.getPid()==c.getCommentid()){
                    ad.add(p);
                }
            }
            c.setChildsSon(ad);
        }
        return new Result(1001,"Query Success",lst1);
    }
//

      // {"comment_id":960,"user_id":0,"leave_id":1,"content":"asdw","leave_pid":0,"pid":0,"time":"2021-05-12 09:25:07","state":0,"username":"\u6e38\u5ba2","avatar":"http:\/\/www.mangoya.cn\/static\/img\/demo.jpg","label":"\u840c\u840c\u54d2","ChildsSon":[]}],"msg":"Query Success"}
//    //查询其他评论数据  分类类型ID（1：赞赏 2：友情链接 3：留言板 4：关于我）
    @GetMapping(value = "comment/OtherComment")
    @CrossOrigin       //后端跨域
    public Result OtherComment(@RequestParam int leaveid, @RequestParam int commentid){
        QueryWrapper wp=new QueryWrapper();
        wp.eq("leaveid",leaveid);
        wp.gt("pid",0);

        List<Comment> lst=commentMapper.selectList(wp);
        QueryWrapper wp1=new QueryWrapper();
        wp1.eq("leaveid",leaveid);
        wp1.eq("pid",0);
        List<Comment> lst1=commentMapper.selectList(wp1);
        for (Comment c:lst1) {
            List<Comment> ad=new LinkedList<>();
            for (Comment p:lst) {
                if (p.getPid()==c.getCommentid()){
                    ad.add(p);
                }
            }
            c.setChildsSon(ad);
        }
        return new Result(1001,"Query Success",lst1);
    }
//

   //文章评论
    @GetMapping(value = "comment/setArticleComment")
    @CrossOrigin       //后端跨域
    public Result SetArticleComment(@RequestParam String content, @RequestParam int userid, @RequestParam int article_id, @RequestParam int leavepid, @RequestParam int pid){
        Comment cmt=new Comment();
        if(userid==0) {
            cmt.setUserid(0);
            cmt.setUsername("游客");
        }else {
            User usr=userMapper.selectById(userid);
            cmt.setAvatar(usr.getAvatar());
            cmt.setLabel(usr.getLabel());
            cmt.setState(usr.getState());
            cmt.setUserid(userid);
            cmt.setUsername(usr.getUsername());
        }
        cmt.setArticleid(article_id);
        cmt.setContent(content);
        cmt.setPid(pid);
        cmt.setLeavepid(leavepid);
        cmt.setTime(DateTime.now().toString());

        Comment pcmt=commentMapper.selectById(leavepid);
        if(pcmt!=null){
            cmt.setReplyname(pcmt.getUsername());
        }
        if(commentMapper.insert(cmt)==1){
            QueryWrapper<Article> awp=new QueryWrapper<>();
            awp.eq("id",article_id);
            Article art= articleMapper.selectOne(awp);
            art.setCommentcount(art.getCommentcount()+1);
            articleMapper.updateById(art);
            return new Result(1001,"Comment Success",cmt.getCommentid());
        }
        return new Result(400,"Comment error","");
    }

   //其他评论
    @GetMapping(value = "comment/setOuthComment")
    @CrossOrigin       //后端跨域
    public Result SetOuthComment(@RequestParam String content, @RequestParam int userid, @RequestParam int article_id, @RequestParam int leaveid,@RequestParam int leavepid, @RequestParam int pid){
        Comment cmt=new Comment();
        if(userid==0) {
            cmt.setUserid(0);
            cmt.setUsername("游客");
        }else {
            User usr=userMapper.selectById(userid);
            cmt.setAvatar(usr.getAvatar());
            cmt.setLabel(usr.getLabel());
            cmt.setState(usr.getState());
            cmt.setUserid(userid);
            cmt.setUsername(usr.getUsername());
        }
        cmt.setArticleid(0);
        cmt.setContent(content);
        cmt.setLeaveid(leaveid);
        cmt.setPid(pid);
        cmt.setLeavepid(leavepid);
        cmt.setTime(DateTime.now().toString());

        Comment pcmt=commentMapper.selectById(leavepid);
        if(pcmt!=null){
            cmt.setReplyname(pcmt.getUsername());
        }
        if(commentMapper.insert(cmt)==1){
            return new Result(1001,"Comment Success",cmt.getCommentid());
        }
        return new Result(400,"Comment error","");
    }


}
