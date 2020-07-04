package com.example.demo.controller;

import com.example.demo.common.Param;
import com.example.demo.dao.*;
import com.example.demo.entity.ClasssEntity;
import com.example.demo.entity.SchoolEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.utils.isEmpty;
import com.example.demo.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
import java.util.stream.Collectors;


/**
 * @author qq491528074
 */
@Controller
public class SchoolController {

    @Autowired
    public SchoolDao schoolDao;

    @Autowired
    public ClassDao classDao;

    @Autowired
    public UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    /**
     * 查询有哪些学校
     */
    @RequestMapping(value = "/school", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo sch(HttpServletRequest request) {
        return new ResultVo(1,"查询成功",schoolDao.findAll());

    }

    /**
     * 查询学校有哪些班级
     */
    @RequestMapping(value = "/class", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo cla(@RequestParam("schoolName") String school,
                          HttpServletRequest request) {

        return new ResultVo(1,"查询成功",classDao.find1(school));

    }

    /**
     * 查询学校有哪些学生
     */
    @RequestMapping(value = "/who_school", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo whoSchool(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(isEmpty.isObjectNotEmpty(session.getAttribute(Param.user))) {
            UserEntity user = (UserEntity)session.getAttribute(Param.user);
            List<UserEntity> a = new ArrayList<>(userDao.findSchoolStudentsDetial(user.getSchool()));
            a= a.stream().peek( x-> x.setPassword("0")).collect(Collectors.toList());
            return new ResultVo(1,"查询成功",a);
        }
        return new ResultVo(0,"操作异常或登录超时",null);

    }

    /**
     * 查询班级有哪些学生
     */
    @RequestMapping(value = "/who_class", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo whoClass(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(isEmpty.isObjectNotEmpty(session.getAttribute(Param.user))) {
            UserEntity user = (UserEntity)session.getAttribute(Param.user);
            List<UserEntity> a = new ArrayList<>(userDao.findStudentsDetial(user.getSchool(),user.getClazz()));
            a= a.stream().peek( x-> x.setPassword("0")).collect(Collectors.toList());
            return new ResultVo(1,"查询成功",a);
        }
        return new ResultVo(0,"操作异常或登录超时",null);


    }


    /**
     * 查添加学校
     */
    @RequestMapping(value = "/a_s", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo schAdd(@RequestParam("id") String id,
                            HttpServletRequest request) {
        UserEntity a = (UserEntity) request.getSession().getAttribute(Param.user);
        if(a.getPermission()>2){
            Optional<SchoolEntity> b = schoolDao.findById(id);
                if(b.isPresent()){
                    return new ResultVo(0,"该学校已存在",null);
                }
                else {
                    schoolDao.save(new SchoolEntity(id));
                    logger.error(a.toString()+"IP地址："+request.getRemoteAddr()+"********创建了学校******"+id );
                    classDao.save(new ClasssEntity("管理员",id));
                    return new ResultVo(1,"创建成功，删除为危险操作请联系管理员",null);
                }
        }
        else{
            return new ResultVo(0,"权限不足",schoolDao.findAll());
        }


    }

    /**
     * 添加班级
     */
    @RequestMapping(value = "/a_c", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo clzAdd(@RequestParam("id") String id,
                           HttpServletRequest request) {
        UserEntity a = (UserEntity) request.getSession().getAttribute(Param.user);
        if(a.getPermission()>1){
            Example<ClasssEntity> c = Example.of(new ClasssEntity(id,a.getSchool()));
            Optional<ClasssEntity> b = classDao.findOne(c);
            if(b.isPresent()){
                return new ResultVo(0,"该班级已存在",null);
            }
            else {
                classDao.save(new ClasssEntity(id,a.getSchool()));
                logger.error(a.toString()+"IP地址："+request.getRemoteAddr()+"********创建了班级******"+id );
                return new ResultVo(1,"创建成功",null);
            }
        }
        else{
            return new ResultVo(0,"权限不足",schoolDao.findAll());
        }


    }
    /**
     * 删除班级
     */
    @RequestMapping(value = "/d_c", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo clzDelete(@RequestParam("id") String id,
                           HttpServletRequest request) {
        UserEntity a = (UserEntity) request.getSession().getAttribute(Param.user);
        if(a.getPermission()>1){
            Example<ClasssEntity> c = Example.of(new ClasssEntity(id,a.getSchool()));
            ClasssEntity b = classDao.findOn(id,a.getSchool());
            if(isEmpty.isObjectNotEmpty(b)){
                classDao.delete(b);
                logger.error(a.toString()+"IP地址："+request.getRemoteAddr()+"********删除了班级******"+id );
                return new ResultVo(1,"操作成功",null);
            }
            else {
                return new ResultVo(0,"该班级不存在",null);
            }
        }
        else{
            return new ResultVo(0,"权限不足",schoolDao.findAll());
        }


    }

}
