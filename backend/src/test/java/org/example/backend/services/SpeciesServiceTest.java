package org.example.backend.services;

import org.example.backend.exceptions.CreationFailedException;
import org.example.backend.models.Species;
import org.example.backend.repositories.SpeciesRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SpeciesServiceTest {

    @Test
    void getAllSpecies_returnsEmptyList_WhenDBIsEmpty() {
        //given
        List<Species> speciesList = new ArrayList<>();
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);
        when(repo.findAll()).thenReturn(speciesList);

        // when
        List<Species> result = service.getAllSpecies();

        assertEquals(0,result.size());
    }

    @Test
    void getAllSpecies_returnsSpeciesList_WhenSpeciesListIsNotEmpty() {
        //given

        List<Species> speciesList = new ArrayList<>();
        speciesList.add(new Species(1, "Phidippus regius"));
        speciesList.add(new Species(2, "Harpactira pulchripes"));
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);
        when(repo.findAll()).thenReturn(speciesList);
        // when
        List<Species> result = service.getAllSpecies();

        assertEquals(2,result.size());
    }

    @Test
    void addOneSpecies_shouldReturnSpecies_WhenCalledWithValidData() {
        // given
        Species species = new Species("Phidippus regius");
        Species speciesWithId = new Species(1, "Phidippus regius");
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        when(repo.save(species)).thenReturn(speciesWithId);
        when(repo.findById(speciesWithId.getId())).thenReturn(Optional.of(speciesWithId));


        // when
        Species actual = service.addOneSpecies(species);

        // then
        assertEquals(speciesWithId,actual);
    }

    @Test
    void addOneSpecies_shouldThrowException_saveSpeciesFailed () {
        // given
        Species species = new Species("Phidippus regius");
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);
        when(repo.save(species)).thenReturn(null);

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneSpecies(species));
    }

    @Test
    void addOneSpecies_shouldThrowException_savedSpeciesWasNotFound () {
        // given
        Species species = new Species("Phidippus regius");
        Species speciesWithId = new Species(1, "Phidippus regius");
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);
        when(repo.save(species)).thenReturn(speciesWithId);
        when(repo.findById(speciesWithId.getId())).thenReturn(Optional.empty());

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneSpecies(species));
    }
}