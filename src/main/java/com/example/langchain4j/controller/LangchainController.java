package com.example.langchain4j.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LangchainController {

    @RequestMapping(value = "/ai/generateMessage", method = RequestMethod.GET)
    @ResponseBody
    public Object generateMessage(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<>();
        result.put("result","test");
        System.out.println("test");
        return result;
    }
}