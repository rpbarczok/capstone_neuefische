package org.example.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
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
    public List<Species> getSpecies(HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        return speciesService.getAllSpecies();
    }

    @PostMapping
    public Species addSpecies(@RequestBody Species species, HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        return speciesService.addOneSpecies(species);
    }

    @GetMapping("/{id}")
    public Species getSpeciesById(@PathVariable int id, HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        return speciesService.getSpeciesById(id);}

    @PutMapping("/{id}")
    public Species updateSpeciesById(@PathVariable int id, @RequestBody Species species, HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        if (id == species.getId()) {
            return speciesService.updateSpecies(species);
        } else {
            throw new BadRequestException("Id of species does not match with id of URI.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpeciesById(@PathVariable int id, HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        speciesService.deleteSpecies(id);}
}
