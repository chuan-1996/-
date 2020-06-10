package com.example.demo.controller;


import com.example.demo.common.Param;
import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.TestDao;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.TestEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.vo.ResultVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author qq491
 */
@Controller
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    public TestDao testDao;

    @Autowired
    public QuestionDao questionDao;


    @Autowired
    private ObjectMapper mapper;

    private boolean permission(HttpServletRequest r){
        UserEntity u = (UserEntity) r.getSession().getAttribute(Param.user);
        if(u.getPermission()>0){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     *return index章节的所有问题不带答案
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getQuestion(@RequestParam("index") int index,
                          HttpServletRequest request) {
        Optional<TestEntity> a = testDao.findById(index);
        //将查询questionEntity里的answer置0  千万不要改动
        return a.map(testEntity ->{
            testEntity.setQuestions(testEntity.getQuestions().stream().peek(x -> x.setAnswer("0")).collect(Collectors.toList()));
            return new ResultVo(1, "题目获取成功",testEntity);
        }).orElseGet(() -> new ResultVo(0, "题目获取失败", null));

    }

    /**
     *  返回带答案的试题
     */
    @RequestMapping(value = "/question_withA", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo getQuestionWithAnswer(@RequestParam("index") int index,
                                HttpServletRequest request) {
        if(permission(request)){
            Optional<TestEntity> a = testDao.findById(index);
            return a.map(testEntity ->{
                return new ResultVo(1, "题目获取成功",testEntity);
            }).orElseGet(() -> new ResultVo(0, "题目获取失败", null));
        }
        else {
            return new ResultVo(0, "权限不足",null);
        }
    }
    /**
     *  删除一套试题
     */
    @RequestMapping(value = "/delete_test", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo del(@RequestParam("index") int index,
                        HttpServletRequest request) {
        if(permission(request)){
            Optional<TestEntity> a = testDao.findById(index);
            if(a.isPresent()){
                this.deleteTest(a.get());
                logger.error((request.getSession().getAttribute(Param.user)).toString()+"IP地址："+request.getRemoteAddr()+"*********删除了试题******"+ index);
                return new ResultVo(1, "题目删除成功",null);
            }
            else{
                return new ResultVo(0, "操作异常", null);
            }
        }
            else {
            return new ResultVo(0, "权限不足",null);
        }

    }
    /**
     * 改动一套试题
     */
    @RequestMapping(value = "/question_edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo edit(@RequestParam String utf8,
                         HttpServletRequest request) throws IOException {
        if(permission(request)){
            String b = utf8;
            System.out.println(b);
            TestEntity a = mapper.readValue(b,TestEntity.class);
            editTest(a);
            return new ResultVo(1, "题目保存成功",null);
        }
        else {
            return new ResultVo(0, "权限不足",null);
        }
    }

    /**
     * 新增一套试题
     */
    @RequestMapping(value = "/question_add", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo saveNew(@RequestParam String utf8,
                          HttpServletRequest request) throws IOException {
        if(permission(request)){
            String b = utf8;
            System.out.println(b);
            TestEntity a = mapper.readValue(b,TestEntity.class);
            int i = saveTest(a);
            return new ResultVo(1, "题目保存成功",i);
        }
        else {
            return new ResultVo(0, "权限不足",null);
        }
    }

    /**
     * 无视外键修改一个试题
     */
    private void editTest(TestEntity a){
        System.out.println("修改试题");
        a.show();
        List<QuestionEntity> questionEntices = a.getQuestions();
        a.setQuestions(null);
        testDao.save(a);
        //先保存试题名字
        //在保存题目
        questionDao.saveAll(questionEntices);
    }

    /**
     * 无视外键删除一个TEST
     */
    private void deleteTest(TestEntity a){
        System.out.println("删除试题");
        a.show();
        List<QuestionEntity> questionEntices = a.getQuestions();
        questionDao.deleteAll(questionEntices);
        a.setQuestions(null);
        testDao.delete(a);
    }

    /**
     * 无视外键保存一个TEST
     */
    private int saveTest(TestEntity a){
        System.out.println("添加试题，此方法会在最后生成一套试题");
        a.show();
        int i = testDao.findMax();
        i++;
        List<QuestionEntity> questionEntices = a.getQuestions();
        a.setQuestions(null);
        a.setTestIndex(i);
        testDao.save(a);
        //先保存试题名字
        //在保存题目
        for (QuestionEntity questionEntity : questionEntices) {
            questionEntity.setQuestionId(0);
            questionEntity.setIndex(i);
        }
        questionDao.saveAll(questionEntices);


        return i;
    }

    /**
     * 用流获取JSON 无法保证中文编码正确
     * @deprecated
     */
    @Deprecated
    @RequestMapping(value = "/question_add__", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo saveTest123(//@RequestParam String utf8,
                          HttpServletRequest request) throws IOException {

        BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
        BufferedReader brr = new BufferedReader(new InputStreamReader(bis));
        String b = brr.readLine();
        TestEntity a = mapper.readValue(b,TestEntity.class);
        a.show();
        for (QuestionEntity questionEntity : a.getQuestions()) {
            questionEntity.setQuestionId(0);
        }

        List<QuestionEntity> questionEntices = a.getQuestions();
        int i = testDao.findMax();
        a.setQuestions(null);
        a.setTestIndex(i+1);
        testDao.save(a);
        return new ResultVo(1, "题目保存成功",b);
    }


}
