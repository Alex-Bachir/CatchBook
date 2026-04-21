package com.catchbook.catchservice.controller;

import com.catchbook.catchservice.model.Spot;
import com.catchbook.catchservice.service.SpotService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spots")
public class SpotController {

    private final SpotService spotService;

    public  SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/{id}")
    public Spot getSpotById(@PathVariable Long id) {
        return spotService.getSpotById(id);
    }

    @PostMapping
    public Spot saveSpot(@RequestBody Spot spot) {
        return spotService.saveSpot(spot);
    }

    @DeleteMapping("/{id}")
    public void deleteSpotById(@PathVariable Long id) {
        spotService.deleteSpot(id);
    }


}
