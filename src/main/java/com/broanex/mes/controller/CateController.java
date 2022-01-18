package com.broanex.mes.controller;

import com.broanex.mes.service.CateService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CateController {

    private final CateService cateService;

    public CateController(CateService cateService) {
        this.cateService = cateService;
    }
}
