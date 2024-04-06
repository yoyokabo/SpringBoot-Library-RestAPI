package com.maids.Library.Management.services.impl;

import com.maids.Library.Management.entites.Patron;
import com.maids.Library.Management.entites.PatronEntity;
import com.maids.Library.Management.repositories.PatronRepo;
import com.maids.Library.Management.services.implementation.PatronServiceImpl;
import com.maids.Library.Management.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatronServiceImplTest {

    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private PatronServiceImpl patronServiceTest;


    @Test
    public void testPatronSaved(){
        final Patron patron = TestData.testPatron();

        final PatronEntity patronEntity = TestData.testPatronEntity();

        when(patronRepo.save(eq(patronEntity))).thenReturn(patronEntity);

        final Patron result = patronServiceTest.create(patron);

        assertEquals(patron,result);
    }

    @Test
    public void testThatFindByIdReturnEmptyWhenNoPatron(){
        final Long id = 12L;

        when(patronRepo.findById(eq(id))).thenReturn(Optional.empty());

        final Optional<Patron> result = patronServiceTest.findById(id);
        assertEquals(Optional.empty(),result);
    }

    @Test
    public void testThatFindByIdReturnsPatronWhenPatron(){

        final Patron patron = TestData.testPatron();
        final PatronEntity patronEntity = TestData.testPatronEntity();


        when(patronRepo.findById(eq(patron.getId()))).thenReturn(Optional.of(patronEntity));


        final Optional<Patron> result = patronServiceTest.findById(patron.getId());
        assertEquals(Optional.of(patron),result);

    }

    @Test
    public void testListPatronReturnsEmptyListWhenNoPatrons(){
        final List<Patron> result = patronServiceTest.getPatrons();
        assertEquals(0,result.size());

    }

    @Test
    public void testListPatronReturnsPatronListWhenPatrons(){
        final PatronEntity patronEntity = TestData.testPatronEntity();

        when(patronRepo.findAll()).thenReturn(List.of(patronEntity));

        final List<Patron> result = patronServiceTest.getPatrons();

        assertEquals(1,result.size());

    }

    @Test
    public void testDeleteBookById(){
        final Long id = 1L;
        patronRepo.deleteById(id);
        verify(patronRepo,times(1)).deleteById(id);
    }






}
