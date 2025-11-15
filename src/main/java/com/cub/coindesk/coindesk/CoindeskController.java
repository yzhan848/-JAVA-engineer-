package com.cub.coindesk.coindesk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {

    private final CoindeskService service;

    public CoindeskController(CoindeskService service) { this.service = service; }

    @GetMapping("/raw")
    public CoindeskDTO raw() { return service.getRaw(); }

    @GetMapping("/transform")
    public Map<String,Object> transform() { return service.getTransformed(); }
}
