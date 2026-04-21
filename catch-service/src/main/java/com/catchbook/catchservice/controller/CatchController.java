package com.catchbook.catchservice.controller;

import com.catchbook.catchservice.model.Catch;
import com.catchbook.catchservice.service.CatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catch")
public class CatchController {

    private final CatchService catchService;

    public CatchController(CatchService catchService) {
        this.catchService = catchService;
    }

    @GetMapping
    public List<Catch> getAllCatches() {
        return catchService.getAllCatches();
    }

    @GetMapping("/{id}")
    public Catch getCatchById(@PathVariable Long id) {
        return catchService.getCatchById(id);
    }

    @PostMapping
    public Catch saveCatch(@RequestBody Catch fishCatch) {
        return catchService.saveCatch(fishCatch);
    }

    @DeleteMapping("/{id}")
    public void deleteCatchById(@PathVariable Long id) {
        catchService.deleteCatch(id);
    }



}
