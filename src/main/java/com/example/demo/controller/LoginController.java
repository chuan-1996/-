package com.example.demo.controller;


import com.example.demo.dao.UserDao;
import com.example.demo.entity.UserEntity;
import com.example.demo.vo.ResultVo;
import com.example.demo.common.Param;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.utils.isEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Password;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.HashMap;
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

    /**
     * 登录
     * 学号
     * 密码
     * 学校
     * 班级
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo login(@RequestParam("username") String id,
                          @RequestParam("password") String password,
                          @RequestParam("school") String school,
                          @RequestParam("class") String classN,
                          HttpServletRequest request) {
        HttpSession b = request.getSession();
        UserEntity a = userDao.login(id,password,school,classN);
        if(isEmpty.isObjectNotEmpty(a)) {
            b.setAttribute(Param.user,a);
            System.out.println(a.toString());
            return new ResultVo(1,"登录成功",a.getUser());
        }
        return new ResultVo(0,"密码或账号错误",null);
    }

    /**
     * 超级管理员注册
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo register(@RequestParam("username") String id,
                             @RequestParam("password") String password,
                             @RequestParam("school") String school,
                             HttpServletRequest request) {

        HttpSession b = request.getSession();
        String c = "管理员";
        UserEntity a = userDao.login(id,password,school,c);
        UserEntity user =(UserEntity) b.getAttribute(Param.user);
        if(user.getPermission()>2){
            if(isEmpty.isObjectNotEmpty(a)) {
                return new ResultVo(0,"重复注册",null);
            }
            else {
                userDao.save(new UserEntity("未设置",id,2, password,school,c));
                return new ResultVo(1,"注册成功",id);
            }
        }
        else {
            return new ResultVo(1,"权限不足",null);
        }

    }
    /**
     * 注册
     * 学号
     * 密码
     * 学校
     * 班级
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo register(@RequestParam("username") String id,
                          @RequestParam("password") String password,
                          @RequestParam("school") String school,
                          @RequestParam("class") String classN,
                          HttpServletRequest request) {
        System.out.println(classN);
        HttpSession b = request.getSession();
        UserEntity a = userDao.login(id,password,school,classN);
        if(isEmpty.isObjectNotEmpty(a)) {
            return new ResultVo(0,"重复注册",null);
        }
        else if("管理员".equals(classN)){
            UserEntity u = (UserEntity) b.getAttribute(Param.user);
            if("管理员".equals(u.getClazz())){
                userDao.save(new UserEntity("未设置",id,0, password,school,classN));
                return new ResultVo(1,"注册成功",id);
            }
            else {
                return new ResultVo(0,"注册该人员请联系管理员",null);
            }

        }
        else {
            userDao.save(new UserEntity("未设置",id,0, password,school,classN));
            return new ResultVo(1,"注册成功",id);
        }
    }
    /**
     * 改名
     */
    @RequestMapping(value = "/edit_name", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo editName(@RequestParam("name") String id,
                             HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            UserEntity a = (UserEntity) b.getAttribute(Param.user);
            a.setUser(id);
            userDao.save(a);
            b.removeAttribute(Param.user);
            b.setAttribute(Param.user,a);
            return new ResultVo(1,"修改成功",id);
        }
        return new ResultVo(0,"操作异常",null);
    }

    /**
     * 改权限
     */
    @RequestMapping(value = "/e_p_u", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo edia(@RequestParam("id") String id,
                         @RequestParam("permission") int p,
                         HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            UserEntity a = (UserEntity) b.getAttribute(Param.user);
            if(a.getPermission()>1){
                Optional t = userDao.findById(id);
                if(t.isPresent()){
                    UserEntity user = (UserEntity) t.get();
                    if(user.getSchool().equals(a.getSchool())){
                        user.setPermission(p);
                        userDao.save(user);
                        logger.error(a.getUser()+"修改了用户"+user.getUser()+"的权限****改为"+p);
                        return new ResultVo(1,"修改成功",id);
                    }
                    else{
                        return new ResultVo(0,"修改失败，该用户不是你所管辖的用户",id);
                    }

                }
                else {
                    return new ResultVo(1,"没有该用户",id);
                }
            }
            else{
                return new ResultVo(0,"权限不足",null);
            }
        }
        return new ResultVo(0,"操作异常",null);
    }
    /**
     * 删除自己
     */
    @RequestMapping(value = "/d", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo ds(HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            UserEntity a = (UserEntity) b.getAttribute(Param.user);
            b.removeAttribute(Param.user);
            userDao.delete(a);
            return new ResultVo(1,"操作成功",null);
        }
        return new ResultVo(0,"操作异常",null);
    }
    /**
     * 删除某班用户
     */
    @RequestMapping(value = "/c_d", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo ca(@RequestParam("id") String id,
                         HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            UserEntity a = (UserEntity) b.getAttribute(Param.user);
            if(a.getPermission()>0){
                Optional t = userDao.findById(id);
                if(t.isPresent()){
                    UserEntity user = (UserEntity) t.get();
                    if(user.getClazz().equals(a.getClazz())){
                        userDao.delete(user);
                        logger.error(a.getUser()+"删除了用户"+user.getUser());
                        return new ResultVo(1,"删除成功",id);
                    }
                    else{
                        return new ResultVo(0,"修改失败，该用户不是你所管辖的用户",id);
                    }

                }
                else {
                    return new ResultVo(0,"没有该用户",id);
                }
            }
            else{
                return new ResultVo(0,"权限不足",null);
            }
        }
        return new ResultVo(0,"操作异常",null);
    }

    /**
     * 改密码
     */
    @RequestMapping(value = "/edit_password", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo editPassword(@RequestParam("id") String id,
                                 @RequestParam("password") String p,
                                 @RequestParam("password1") String p1,
                                HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            UserEntity a = (UserEntity) b.getAttribute(Param.user);
            if(a.getPassword().equals(p)){
                a.setPassword(p1);
                userDao.save(a);
                b.removeAttribute(Param.user);
                b.setAttribute(Param.user,a);
                return new ResultVo(1,"修改成功",id);
            }
            else {
                return new ResultVo(0,"旧密码输入错误",null);
            }
        }
        return new ResultVo(0,"操作异常",null);
    }
    /**
     * 注销
     */
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

    /**
     * 查询session是谁
     */
    @RequestMapping(value = "/who", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo who2(HttpServletRequest request) {
        HttpSession b = request.getSession();
        if(isEmpty.isObjectNotEmpty(b.getAttribute(Param.user))){
            return new ResultVo(1,null, b.getAttribute(Param.user));
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
