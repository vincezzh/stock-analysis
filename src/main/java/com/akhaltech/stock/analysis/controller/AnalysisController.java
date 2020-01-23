package com.akhaltech.stock.analysis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AnalysisController {

    @RequestMapping("/")
    public String ping() {
        return "Good to work @" + new Date();
    }

}
