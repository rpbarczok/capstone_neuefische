package org.example.backend.repositories;

import org.example.backend.models.Terrarium;

public interface TerrariumRepository extends CrudRepository<Terrarium, Integer>{

    Terrarium getTerrariumByName(String name);
}
