package com.example.demo.controller;

import com.example.demo.common.Param;
import com.example.demo.dao.ScoreDao;
import com.example.demo.dao.TestDao;
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

@Controller
public class ChapterController {

    @Autowired
    public TestDao testDao;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 查询有哪些试题
     */
    @RequestMapping(value = "/chapter", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo login(HttpServletRequest request) {
        return new ResultVo(1,"查询成功",testDao.find());

    }

}
