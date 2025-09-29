package org.example.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.exceptions.BadRequestException;
import org.example.backend.models.Terrarium;
import org.example.backend.services.TerrariumService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/terraria")
public class TerrariumController {

    private final TerrariumService terrariumService;

    public TerrariumController(TerrariumService terrariumService) {this.terrariumService = terrariumService;}

    @GetMapping
    public List<Terrarium> getAllTerrariumIds(HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        return terrariumService.getAllTerraria();
    }

    @PostMapping
    public Terrarium addTerrarium(@RequestBody Terrarium terrarium, HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        return terrariumService.addOneTerrarium(terrarium);
    }

    @GetMapping("/{id}")
    public Terrarium getTerrariumById(@PathVariable int id, HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        return terrariumService.getTerrariumById(id);
    }

    @PutMapping("/{id}")
    public Terrarium updateTerrariumById(@PathVariable int id,
                                         @RequestBody Terrarium terrarium,
                                         HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        if (id == terrarium.getId()) {
            Terrarium updatedTerrarium = terrariumService.updateTerrarium(terrarium);
            return new Terrarium(updatedTerrarium.getId(),
                    updatedTerrarium.getName(),
                    updatedTerrarium.getHeight(),
                    updatedTerrarium.getWidth(),
                    updatedTerrarium.getDepth()
            );
        } else {
            throw new BadRequestException("Id of Terrarium doesn't match with id of URI.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTerrariumById(@PathVariable int id, HttpServletResponse response) {
        response.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett");
        terrariumService.deleteTerrarium(id);
    }
}
