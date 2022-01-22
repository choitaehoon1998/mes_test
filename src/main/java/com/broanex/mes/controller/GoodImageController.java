package com.broanex.mes.controller;

import com.broanex.mes.service.GoodImageService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodImageController {
    private final GoodImageService goodImageService;

    public GoodImageController(GoodImageService goodImageService) {
        this.goodImageService = goodImageService;
    }
}
