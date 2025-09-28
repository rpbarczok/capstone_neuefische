package org.example.backend.services;

import org.example.backend.exceptions.CreationFailedException;
import org.example.backend.exceptions.DeletionFailedException;
import org.example.backend.exceptions.NotFoundException;
import org.example.backend.exceptions.UpdateFailedException;
import org.example.backend.models.Terrarium;
import org.example.backend.repositories.TerrariumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TerrariumServiceTest {

    TerrariumRepository repo = mock(TerrariumRepository.class);
    TerrariumService service = new TerrariumService(repo);

    Terrarium terrarium1Id = new Terrarium(
            1,
            "Leonies Castle",
            30,
            20,
            20
    );
    Terrarium terrarium1IdNew = new Terrarium(
            1,
            "Leonies Traumhaus",
            30,
            20,
            20
    );
    Terrarium terrarium2Id = new Terrarium(
            2,
            "Benes Castle",
            30,
            40,
            30
    );
    Terrarium terrarium1 = new Terrarium(
            "Leonies Castle",
            30,
            20,
            20
    );

    @Test
    void getAllTerraria_returnsEmptyList_WhenDBIsEmpty() {
        //given
        List<Terrarium> terrariumList = new ArrayList<>();
        when(repo.findAll()).thenReturn(terrariumList);

        //when
        List<Terrarium> result = service.getAllTerraria();

        // then
        assertEquals(terrariumList.size(), result.size());
    }

    @Test
    void getAllTerraria_returnsTerrariumList_WhenListIsNotEmpty() {
        //given
        List<Terrarium> terrariumList = new ArrayList<>();
        terrariumList.add(terrarium1Id);
        terrariumList.add(terrarium2Id);

        when(repo.findAll()).thenReturn(terrariumList);

        //whe
        List<Terrarium> result = service.getAllTerraria();

        // then
        assertEquals(terrariumList.size(), result.size());
    }

    @Test
    void addOneTerrarium_shouldReturnTerrarium_whenCalledWithValidData() {
        // given
        when(repo.save(terrarium1)).thenReturn(terrarium1Id);
        when(repo.findById(terrarium1Id.getId())).thenReturn(Optional.of(terrarium1Id));


        // when
        Terrarium actual = service.addOneTerrarium(terrarium1);

        // then
        assertEquals(terrarium1Id,actual);
    }

    @Test
    void addOneTerrarium_shouldThrowException_SaveTerrariumFailed () {
        // given
        when(repo.save(terrarium1)).thenReturn(null);

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneTerrarium(terrarium1));
    }

    @Test
    void addOneTerrarium_shouldThrowException_savedTerrariumWasNotFound () {
        // given
        when(repo.save(terrarium1)).thenReturn(terrarium1Id);
        when(repo.findById(terrarium1Id.getId())).thenReturn(Optional.empty());

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneTerrarium(terrarium1));
    }
    
    @Test
    void updateTerrarium_shouldReturnUpdatedTerrarium_whenCalledWithValidData() {
        when(repo.findById(terrarium1Id.getId())).thenReturn(Optional.of(terrarium1Id));
        when(repo.save(terrarium1IdNew)).thenReturn(terrarium1IdNew);
        // when & then
        assertEquals(terrarium1IdNew, service.updateTerrarium(terrarium1IdNew));
    }

    @Test
    void updateTerrarium_shouldThrowNotFoundException_whenAttemptToUpdateNotExistingTerrarium() {
        // given
        when(repo.save(terrarium1IdNew)).thenReturn(terrarium1IdNew);
        when(repo.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> service.updateTerrarium(terrarium1));
    }

    @Test
    void updateTerrarium_shouldThrowUpdateFailedException_whenAttemptToUpdateNotExistingTerrarium() {
        // given
        when(repo.findById(1)).thenReturn(Optional.of(terrarium1Id));
        when(repo.save(terrarium1IdNew)).thenReturn(null);
        // when & then
        assertThrows(UpdateFailedException.class, () -> service.updateTerrarium(terrarium1IdNew));

    }

    @Test
    void deleteTerrarium_shouldReturnVoid_whenCalledWithExistingTerrarium() {


        when(repo.findById(1)).thenReturn(Optional.of(terrarium1Id), Optional.empty());

        // When
        service.deleteTerrarium(1);

        // Then
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void deleteTerrarium_shouldThrowNotFound_whenTerrariumDoesNotExist() {

        when(repo.findById(1)).thenReturn(Optional.empty());
        // when & then
        assertThrows(NotFoundException.class, () -> service.deleteTerrarium(1));
    }

    @Test
    void deleteTerrarium_shouldThrowDeletionFailed_whenTerrariumDeletionFails() {
        when(repo.findById(1)).thenReturn(Optional.of(terrarium1Id));
        doThrow(new RuntimeException("something went wrong"))
                .when(repo)
                .deleteById(1);
        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteTerrarium(1));
    }

    @Test
    void deleteTerrarium_shouldThrowDeletionFailed_WhenDeletedTerrariumStillExists() {
        when(repo.findById(1)).thenReturn(Optional.of(terrarium1Id));

        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteTerrarium(1));
    }

    @Test
    void getTerrariumById_shouldReturnTerrarium_whenTerrariumExists() {
        when(repo.findById(1)).thenReturn(Optional.of(terrarium1Id));

        assertEquals(terrarium1Id,service.getTerrariumById(1));
    }

    @Test
    void getTerrariumById_shouldThrowNotFoundError_whenTerrariumDoesntExist() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getTerrariumById(1));
    }
}