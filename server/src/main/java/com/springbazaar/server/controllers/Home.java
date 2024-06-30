package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class Home {
    private final HomeService homeService;

    @Autowired
    public Home(HomeService homeService) {
        this.homeService = homeService;
    }
    @GetMapping("/")
    List<InventoryEntity> getAllItems(){
        return homeService.getAllInventoryItems();
    }
}
