package org.example.backend.controllers;

import org.example.backend.models.Species;
import org.example.backend.services.SpeciesService;
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



}
