package org.example.backend.services;

import org.example.backend.exceptions.CreationFailedException;
import org.example.backend.exceptions.DeletionFailedException;
import org.example.backend.exceptions.NotFoundException;
import org.example.backend.exceptions.UpdateFailedException;
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

    public Species getSpeciesById (int id) {
        Optional<Species> foundSpecies = speciesRepository.findById(id);
        if  (foundSpecies.isPresent()) {
            return foundSpecies.get();
        } else {
            throw new NotFoundException("species", id);
        }
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

    public Species updateSpecies(Species species) {
        Optional<Species> oldSpecies = speciesRepository.findById(species.getId());
        if  (oldSpecies.isPresent()) {

            Species updatedSpecies = speciesRepository.save(species);

            if (updatedSpecies == null) {
                throw new UpdateFailedException("species", species.getId());
            } else {
                return updatedSpecies;
            }
        } else {
            throw new NotFoundException("species", species.getId());
        }
    }

    public void deleteSpecies(int id) {
        Optional<Species> oldSpecies = speciesRepository.findById(id);
        if  (oldSpecies.isPresent()) {
            try {
                speciesRepository.deleteById(id);
                Optional<Species> newSpecies = speciesRepository.findById(id);
                if (newSpecies.isPresent()) {
                    throw new DeletionFailedException("", "species", id);
                }
            } catch (Exception e) {
                throw new DeletionFailedException(e.getMessage(), "species", id);
            }

        } else {
            throw new NotFoundException("species", id);
        }

    }
}
