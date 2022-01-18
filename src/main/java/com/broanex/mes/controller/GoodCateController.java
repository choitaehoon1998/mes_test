package com.broanex.mes.controller;

import com.broanex.mes.service.GoodCateService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodCateController {
    private final GoodCateService goodCateService;

    public GoodCateController(GoodCateService goodCateService) {
        this.goodCateService = goodCateService;
    }
}
