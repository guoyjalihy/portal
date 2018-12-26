package com.common.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 错误处理
 * @author: guoyanjun
 * @date: 2018/12/26 18:52
 */
@Controller
@RequestMapping(value = "error")
public class ErrorPageController {
    /**
     * 404
     * @return
     */
    @RequestMapping(produces = "text/html",value = "404")
    public String errorHtml404() {
        return "error/404";
    }
    /**
     * 500
     * @return
     */
    @RequestMapping(produces = "text/html",value = "500")
    public String errorHtml500() {
        return "error/500";
    }

}
