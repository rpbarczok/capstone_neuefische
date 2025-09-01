package org.example.backend.services;

import org.example.backend.models.Species;
import org.example.backend.repositories.SpeciesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public List<Species> getAllSpecies () {
       return (List<Species>) speciesRepository.findAll();
    }

    public Optional<Species> addOneSpecies(Species species) {
        Species newSpecies = speciesRepository.save(species);
        return speciesRepository.findById(newSpecies.getId());
    }
}
