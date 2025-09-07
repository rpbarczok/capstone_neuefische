package org.example.backend.services;

import org.example.backend.exceptions.CreationFailedException;
import org.example.backend.exceptions.DeletionFailedException;
import org.example.backend.exceptions.NotFoundException;
import org.example.backend.exceptions.UpdateFailedException;
import org.example.backend.models.Species;
import org.example.backend.repositories.SpeciesRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    void updateSpecies_shouldReturnUpdatedSpecies_whenCalledWithValidData() {
        // given
         Species speciesWithId = new Species(1, "Phidippus ardens");
        Species speciesWithIdNew = new Species(1, "Phidippus regius");

        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        when(repo.findById(speciesWithId.getId())).thenReturn(Optional.of(speciesWithId));
        when(repo.save(speciesWithIdNew)).thenReturn(speciesWithIdNew);
        // when & then
        assertEquals(speciesWithIdNew,service.updateSpecies(speciesWithIdNew));
    }

    @Test
    void updateSpecies_shouldThrowNotFoundException_whenAttemptToUpdateNotExistingSpecies() {
        // given
        Species speciesWithIdNew = new Species(1, "Phidippus regius");
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);
        when(repo.save(speciesWithIdNew)).thenReturn(speciesWithIdNew);
        when(repo.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> service.updateSpecies(speciesWithIdNew));
    }

    @Test
    void updateSpecies_shouldThrowUpdateFailedException_whenAttemptToUpdateNotExistingSpecies() {
        // given
        Species speciesWithIdOld = new Species(1, "Phidippus ardens");
        Species speciesWithIdNew = new Species(1, "Phidippus regius");
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);
        when(repo.findById(1)).thenReturn(Optional.of(speciesWithIdOld));
        when(repo.save(speciesWithIdNew)).thenReturn(null);
        // when & then
        assertThrows(UpdateFailedException.class, () -> service.updateSpecies(speciesWithIdNew));

    }

    @Test
    void deleteSpecies_shouldReturnVoid_whenCalledWithExistingSpecies() {
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        Species species = new Species(1, "Phidippus regius");

        when(repo.findById(1)).thenReturn(Optional.of(species), Optional.empty());

        // When
        service.deleteSpecies(1);

        // Then
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void deleteSpecies_shouldThrowNotFound_whenSpeciesDoesNotExist() {
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        when(repo.findById(1)).thenReturn(Optional.empty());
        // when & then
        assertThrows(NotFoundException.class, () -> service.deleteSpecies(1));
    }

    @Test
    void deleteSpecies_shouldThrowDeletionFailed_whenSpeciesDeletionFails() {
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        Species species = new Species(1, "Phidippus regius");

        when(repo.findById(1)).thenReturn(Optional.of(species));
        doThrow(new RuntimeException("something went wrong"))
                .when(repo)
                .deleteById(1);
        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteSpecies(1));
    }

    @Test
    void deleteSpecies_shouldThrowDeletionFailed_WhenDeletedSpeciesStillExists() {
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        Species species = new Species(1, "Phidippus regius");

        when(repo.findById(1)).thenReturn(Optional.of(species));

        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteSpecies(1));
    }

    @Test
    void getSpeciesById_shouldReturnSpecies_whenSpeciesExists() {
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        Species species = new Species(1, "Phidippus regius");

        when(repo.findById(1)).thenReturn(Optional.of(species));

        assertEquals(species,service.getSpeciesById(1));
    }

    @Test
    void getSpeciesById_shouldThrowNotFoundError_whenSpeciesDoesntExist() {
        SpeciesRepository repo = mock(SpeciesRepository.class);
        SpeciesService service = new SpeciesService(repo);

        when(repo.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getSpeciesById(1));
    }
}