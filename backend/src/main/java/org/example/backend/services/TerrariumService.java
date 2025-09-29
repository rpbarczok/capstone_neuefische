package org.example.backend.services;
import org.example.backend.exceptions.CreationFailedException;
import org.example.backend.exceptions.DeletionFailedException;
import org.example.backend.exceptions.NotFoundException;
import org.example.backend.exceptions.UpdateFailedException;
import org.example.backend.models.Terrarium;
import org.example.backend.repositories.TerrariumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TerrariumService {

    private final TerrariumRepository terrariumRepository;

    public TerrariumService(TerrariumRepository terrariumRepository) {
        this.terrariumRepository = terrariumRepository;
    }

    public List<Terrarium> getAllTerraria() {return (List<Terrarium>) terrariumRepository.findAll();}

    public Terrarium getTerrariumById(int id) {
        Optional<Terrarium> foundTerrarium = terrariumRepository.findById(id);
        if (foundTerrarium.isPresent()) {
            return foundTerrarium.get();
        } else {
            throw new NotFoundException("terrarium", id);
        }
    }

    public Terrarium addOneTerrarium(Terrarium terrarium) {
        Terrarium newTerrarium = new Terrarium(terrarium.getName(),
                terrarium.getHeight(),
                terrarium.getWidth(),
                terrarium.getDepth());
        try {
            Terrarium createdTerrarium = terrariumRepository.save(newTerrarium);
            return terrariumRepository.findById(createdTerrarium.getId()).orElseThrow(() -> new CreationFailedException("terrarium", terrarium.getName()));
        } catch (Exception e) {
            throw new CreationFailedException("terrarium", terrarium.getName());
        }
    }

    public Terrarium updateTerrarium (Terrarium terrarium) {
        Optional<Terrarium> oldTerrarium = terrariumRepository.findById(terrarium.getId());
        if (oldTerrarium.isPresent()) {
            Terrarium updatedTerrarium = new Terrarium(terrarium.getId(),
                    terrarium.getName(),
                    terrarium.getHeight(),
                    terrarium.getWidth(),
                    terrarium.getDepth());
            Terrarium savedTerrarium = terrariumRepository.save(updatedTerrarium);
            if (savedTerrarium == null) {
                throw new UpdateFailedException("terrarium", terrarium.getId());
            } else {
                return savedTerrarium;
            }
        } else {
                throw new NotFoundException("terrarium", terrarium.getId());
        }
    }

    public void deleteTerrarium(int id) {
        Optional<Terrarium> terrarium = terrariumRepository.findById(id);
        if (terrarium.isPresent()) {
            try {
                terrariumRepository.deleteById(id);
                Optional<Terrarium> deletedTerrarium = terrariumRepository.findById(id);
                if (deletedTerrarium.isPresent()) {
                    throw new DeletionFailedException("", "terrarium",  id);
                }
            } catch (Exception e) {
                throw new DeletionFailedException(e.getMessage(), "terrarium", id);
            }
        } else {
            throw new NotFoundException("terrarium", id);
        }
    }
}
