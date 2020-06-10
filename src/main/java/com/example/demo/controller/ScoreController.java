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

    /*查询当前用户所有分数*/
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo submit(HttpServletRequest request){

        HttpSession session = request.getSession();
        if(isEmpty.isObjectNotEmpty(session.getAttribute(Param.user))) {

            String id = (String) session.getAttribute(Param.user);
            Optional<UserEntity> al = userDao.findById(id);
            if (al.isPresent()) {

                UserEntity user = al.get();
                return new ResultVo(1,"查询成功",scoreDao.findAllScore(user.getId()));

            }
            else{
                return new ResultVo(0,"操作异常或登录超时",null);
            }
        }

        return new ResultVo(0,"操作异常或登录超时",null);
    }

}
