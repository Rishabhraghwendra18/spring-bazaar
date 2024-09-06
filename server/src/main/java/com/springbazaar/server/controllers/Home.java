package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.services.HomeService;
import com.springbazaar.server.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    List<InventoryEntity> getAllItems(@RequestParam(required = false) Integer limit){
        if(limit !=null){
            return homeService.getTopInventoryItems(limit);
        }
        return homeService.getAllInventoryItems();
    }
    @GetMapping("/{id}")
    public InventoryEntity getItemById(@PathVariable Integer id){return homeService.getItemById(id);}
    @GetMapping("/search")
    public List<InventoryEntity> searchProducts(@RequestParam("query") String query) {
        return homeService.searchProductsByTitle(query);
    }
}
