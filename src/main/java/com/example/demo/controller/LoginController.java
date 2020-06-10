package com.example.demo.controller;


import com.example.demo.dao.UserDao;
import com.example.demo.entity.UserEntity;
import com.example.demo.vo.ResultVo;
import com.example.demo.common.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.utils.isEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author qq491
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    public UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo login(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        Optional<UserEntity> a = userDao.findById(password.trim());
        HttpSession b = request.getSession();
        if(a.isPresent()&&a.get().getUser().equals(username)) {
            b.setAttribute(Param.user,password);
            System.out.println(b.getAttribute(Param.user));
            return new ResultVo(1,"登录成功",username);
        }
        return new ResultVo(0,"查无此人",null);

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo logout(HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            Object a = b.getAttribute(Param.user);
            b.removeAttribute(Param.user);
            return new ResultVo(1,"注销成功",a);
        }
        return new ResultVo(0,"操作异常",null);
    }

    @RequestMapping(value = "/who", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo who(HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            Object a = b.getAttribute(Param.user);
            UserEntity user = userDao.findById((String)a).get();
            return new ResultVo(1,"你是"+user.getUser(),user);
        }
        return new ResultVo(0,"操作异常",null);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test2(@RequestParam("key") String key,
                          @RequestParam("value") String value) {

        return new ResultVo(1,"success",
                userDao.findAll()
                        .stream().
                        filter(s->s.getId().isEmpty()).
                        limit(5).
                        skip(0).
                        sorted(Comparator.comparing(UserEntity::getId)).
                        collect(Collectors.toList())
        );
    }
}
