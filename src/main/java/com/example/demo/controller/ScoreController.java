package com.example.demo.controller;

import com.example.demo.common.Answer;
import com.example.demo.common.Param;
import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.ScoreDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.ScoreEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author qq491528074
 */
@Controller
public class ScoreController {

    @Autowired
    public UserDao userDao;

    @Autowired
    public ScoreDao scoreDao;

    private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

    /**
     * 查询班级成绩
     */
    @RequestMapping(value = "/score_class", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo scoreClass(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(isEmpty.isObjectNotEmpty(session.getAttribute(Param.user))) {
            UserEntity user = (UserEntity)session.getAttribute(Param.user);
            List<String> a = new ArrayList<>(userDao.findStudents(user.getSchool(), user.getClazz()));
            List<ScoreEntity> s = new ArrayList<>();
            for(String t : a){
                s.addAll(scoreDao.findAllScore(t));
            }
            return new ResultVo(1,"查询成功",s);
        }
        return new ResultVo(0,"操作异常或登录超时",null);
    }

    /**
     * 查询学校成绩
     */
    @RequestMapping(value = "/score_school", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo scoreSchool(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(isEmpty.isObjectNotEmpty(session.getAttribute(Param.user))) {
            UserEntity user = (UserEntity)session.getAttribute(Param.user);
            List<String> a = new ArrayList<>(userDao.findSchoolStudents(user.getSchool()));
            List<ScoreEntity> s = new ArrayList<>();
            for(String t : a){
                s.addAll(scoreDao.findAllScore(t));
            }
            return new ResultVo(1,"查询成功",s);
        }
        return new ResultVo(0,"操作异常或登录超时",null);
    }

    /**
     * 查询该用户所有成绩
     */
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo submit(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(isEmpty.isObjectNotEmpty(session.getAttribute(Param.user))) {
            UserEntity user = (UserEntity)session.getAttribute(Param.user);
            return new ResultVo(1,"查询成功",scoreDao.findAllScore(user.getId()));
        }
        return new ResultVo(0,"操作异常或登录超时",null);
    }

}
