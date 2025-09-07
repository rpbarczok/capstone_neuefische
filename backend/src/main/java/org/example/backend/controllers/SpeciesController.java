package org.example.backend.controllers;

import org.example.backend.exceptions.BadRequestException;
import org.example.backend.models.Species;
import org.example.backend.services.SpeciesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/species")
public class SpeciesController {

    private final SpeciesService speciesService;

    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @GetMapping
    public List<Species> getSpecies() {
        return speciesService.getAllSpecies();
    }

    @PostMapping
    public Species addSpecies(@RequestBody Species species) {
        return speciesService.addOneSpecies(species);
    }

    @GetMapping("/{id}")
    public Species getSpeciesById(@PathVariable int id) {return speciesService.getSpeciesById(id);}

    @PutMapping("/{id}")
    public Species updateSpeciesById(@PathVariable int id, @RequestBody Species species) {
        if (id == species.getId()) {
            return speciesService.updateSpecies(species);
        } else {
            throw new BadRequestException("Id of species does not match with id of URI.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpeciesById(@PathVariable int id) {speciesService.deleteSpecies(id);}
}
