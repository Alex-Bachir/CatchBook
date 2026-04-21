package com.catchbook.catchservice.service;

import com.catchbook.catchservice.model.Catch;
import com.catchbook.catchservice.repository.CatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatchService {

    private final CatchRepository catchRepository;
    public CatchService(CatchRepository catchRepository) {
        this.catchRepository = catchRepository;
    }

    // récupere toutes les prises
    public List<Catch> getAllCatches() {
        return catchRepository.findAll();
    }

    public Catch getCatchById(Long id) {
        return catchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("catch not found"));
    }

    public Catch saveCatch(Catch fishCatch) {
        return catchRepository.save(fishCatch);
    }

    public void deleteCatch(Long id) {
        catchRepository.deleteById(id);
    }

}
