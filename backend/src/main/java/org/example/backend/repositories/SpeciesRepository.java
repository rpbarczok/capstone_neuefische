package org.example.backend.repositories;

import org.example.backend.models.Species;

public interface SpeciesRepository extends CrudRepository<Species, Integer>{

    Species getSpeciesByGenus(String species);

}
