package com.example.demo.controller;


import com.example.demo.dao.TestDao;
import com.example.demo.entity.TestEntity;
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

    /*返回index章节的所有问题*/
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo login(@RequestParam("index") int index,
                          HttpServletRequest request) {
        Optional<TestEntity> a = testDao.findById(index);
        //将查询questionEntity里的answer置0
        return a.map(testEntity ->{
            testEntity.setQuestions(testEntity.getQuestions().stream().peek(x -> x.setAnswer(0)).collect(Collectors.toList()));
            return new ResultVo(1, "题目获取成功",testEntity);
        }).orElseGet(() -> new ResultVo(0, "题目获取失败", null));

    }

}
