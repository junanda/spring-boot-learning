package com.patihullah.junanda.pelatihan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HaloController {

    @RequestMapping("/hallo")
    public Map<String, Object> hallo(@RequestParam(value = "nama", required = false) String nama){
        Map<String, Object> hasil = new HashMap<>();
        hasil.put("nama", nama);
        hasil.put("waktu", new Date());
        return hasil;
    }
}
