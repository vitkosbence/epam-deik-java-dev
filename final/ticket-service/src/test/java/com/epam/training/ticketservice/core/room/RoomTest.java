package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoomTest {
    private RoomService underTest;
    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRoomShouldSaveObjectToRepository(){
        underTest = new RoomServiceImpl(roomRepository);
        underTest.createRoom("Test",5,5);
        Mockito.verify(roomRepository).save(new Room("Test",5,5));
    }

    @Test
    public void testUpdateRoomShouldNotSaveIfRoomIsNotFound(){
        underTest = new RoomServiceImpl(roomRepository);
        underTest.updateRoom("Test",10,10);
        Mockito.verify(roomRepository,Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testUpdateRoomShouldSaveUpdatedRoomIfItIsFound(){
        underTest = new RoomServiceImpl(roomRepository);
        BDDMockito.given(roomRepository.findRoomByName("Test")).willReturn(Optional.of(new Room("Test",20,20)));
        underTest.updateRoom("Test",10,10);
        Mockito.verify(roomRepository).save(new Room("Test",10,10));
    }

    @Test
    public void testDeleteRoomShouldCallDeleteOnRepository(){
        underTest = new RoomServiceImpl(roomRepository);
        underTest.deleteRoom("Test");
        Mockito.verify(roomRepository).deleteRoomByName("Test");
    }

    @Test
    public void testGetRoomByNameShouldReturnEmptyOptionalIfRoomIsNotFound(){
        Optional<RoomDto> expectedResult = Optional.empty();
        underTest = new RoomServiceImpl(roomRepository);
        Optional<RoomDto> actualResult = underTest.getRoomByName("Test");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetRoomByNameShouldReturnTheRoomIfItIsFound(){
        Optional<RoomDto> expectedResult = Optional.of(new RoomDto("Test",10,10));
        BDDMockito.given(roomRepository.findRoomByName("Test")).willReturn(Optional.of(new Room("Test",10,10)));
        underTest = new RoomServiceImpl(roomRepository);
        Optional<RoomDto> actualResult = underTest.getRoomByName("Test");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetRoomByIdShouldReturnEmptyOptionalIfRoomIsNotFound(){
        Optional<RoomDto> expectedResult = Optional.empty();
        underTest = new RoomServiceImpl(roomRepository);
        Optional<RoomDto> actualResult = underTest.getRoomById(1);
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetRoomByIdShouldReturnTheRoomIfItIsFound(){
        Optional<RoomDto> expectedResult = Optional.of(new RoomDto("Test",10,10));
        BDDMockito.given(roomRepository.findById(1)).willReturn(Optional.of(new Room("Test",10,10)));
        underTest = new RoomServiceImpl(roomRepository);
        Optional<RoomDto> actualResult = underTest.getRoomById(1);
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetRoomListShouldReturnWithEmptyListIfThereAreNoRooms(){
        List<RoomDto> expectedResult = Collections.emptyList();
        underTest = new RoomServiceImpl(roomRepository);
        List<RoomDto> actualResult = underTest.getRoomList();
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void testGetRoomListShouldReturnWithListOfRoomsIfThereAreAny(){
        List<RoomDto> expectedResult = Collections.singletonList(new RoomDto("Test",10,10));
        BDDMockito.given(roomRepository.findAll()).willReturn(Collections.singletonList(new Room("Test",10,10)));
        underTest = new RoomServiceImpl(roomRepository);
        List<RoomDto> actualResult = underTest.getRoomList();
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
