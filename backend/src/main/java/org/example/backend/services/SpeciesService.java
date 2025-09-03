package org.example.backend.services;

import org.example.backend.exceptions.CreationFailedException;
import org.example.backend.models.Species;
import org.example.backend.repositories.SpeciesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public List<Species> getAllSpecies () {
       return (List<Species>) speciesRepository.findAll();
    }

    public Species addOneSpecies(Species species) {
        try {
            Species newSpecies = speciesRepository.save(species);
            return speciesRepository
                    .findById(newSpecies.getId())
                    .orElseThrow(() -> new CreationFailedException("species", newSpecies.getGenus()));
        } catch (Exception e) {
            throw new CreationFailedException("species", species.getGenus());
        }

    }
}
