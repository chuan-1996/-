package com.example.demo.controller;

import com.example.demo.common.Answer;
import com.example.demo.common.Param;
import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.ScoreDao;
import com.example.demo.dao.TestDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.ScoreEntity;
import com.example.demo.entity.TestEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.utils.isEmpty;
import com.example.demo.vo.ResultVo;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author qq491528074
 */
@Controller
public class AnswerController {

    @Autowired
    public UserDao userDao;

    @Autowired
    public QuestionDao questionDao;

    @Autowired
    public ScoreDao scoreDao;

    @Autowired
    public TestDao testDao;

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    /*计算成绩*/
    private Answer compute(String form,int index){
        Answer answer = new Answer(0);
        answer.relocate();
        String[]  a = questionDao.find(index);
        String[] f = form.split(",");
        for(int i = 0 ; i < a.length; ++i){
            if(String.valueOf(a[i]).equals(f[i])){
                answer.addScore();
            }
            else{
                answer.pushW(i+1);
            }
        }
        return  answer;
    }

    /**
    * 提交答题结果结果
    */
    @RequestMapping(value = "/result_submit", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo submit(@RequestParam("form") String form,
                           @RequestParam("index") int index,
                           HttpServletRequest request){
        System.out.println(form);
        HttpSession session = request.getSession();
        if(isEmpty.isObjectNotEmpty(session.getAttribute(Param.user))) {
            UserEntity user = (UserEntity) session.getAttribute(Param.user);
            Object scoreExist = scoreDao.find(index,user.getId());
            if(isEmpty.isObjectNotEmpty(scoreExist)){
                return new ResultVo(0,"本章已有分数记录，无法继续提交",null);
            }
            else{
                Answer a = compute(form,index);
                TestEntity b = (testDao.findById(index)).get();
                ScoreEntity score1 = new ScoreEntity(index,user.getId(),a.getScore());
                scoreDao.save(score1);
                //返回code=2就不会纠错
                return new ResultVo(1, "提交成功！您的分数是" + a.getScore() + "分", a.getWhereWrong());
                //return new ResultVo(2, "提交成功！您的分数是" + a.getScore() + "分", null);
            }
        }

        return new ResultVo(0,"操作异常或登录超时",null);
    }

}
