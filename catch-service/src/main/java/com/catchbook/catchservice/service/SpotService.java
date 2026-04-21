package com.catchbook.catchservice.service;

import com.catchbook.catchservice.model.Spot;
import com.catchbook.catchservice.repository.SpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {

    private final SpotRepository spotRepository;

    public SpotService(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public Spot getSpotById(Long id) {
        return spotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Spot not found"));
    }

    public Spot saveSpot(Spot spot) {
        return spotRepository.save(spot);
    }

    public void deleteSpot(Long id) {
        spotRepository.deleteById(id);
    }



}
