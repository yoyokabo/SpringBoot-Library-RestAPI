package com.maids.Library.Management.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.Library.Management.entites.Book;
import com.maids.Library.Management.entites.Borrowing;
import com.maids.Library.Management.entites.Patron;
import com.maids.Library.Management.services.BorrowingService;
import com.maids.Library.Management.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BorrowingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BorrowingService borrowingServiceTest;

    @Test
    public void testThatBorrowingRecordIsNotCreatedWhenBookAndPatronDontExist() throws Exception {
        final Borrowing borrowing = TestData.testBorrowing(TestData.testBookEntity(),TestData.testPatronEntity());
        final ObjectMapper objectMapper = new ObjectMapper();
        final String borrowingJson = objectMapper.writeValueAsString(borrowing);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/1/patron/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(borrowingJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatBorrowingRecordIsCreated() throws Exception {
        final Borrowing borrowing = TestData.testBorrowing(TestData.testBookEntity(),TestData.testPatronEntity());
        final ObjectMapper objectMapper = new ObjectMapper();
        final String borrowingJson = objectMapper.writeValueAsString(borrowing);

        final Book book = TestData.testBook();
        final String bookJson = objectMapper.writeValueAsString(book);

        final Patron patron = TestData.testPatron();
        final String patronJson = objectMapper.writeValueAsString(patron);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/").
                contentType(MediaType.APPLICATION_JSON).
                content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons/").
                contentType(MediaType.APPLICATION_JSON).
                content(patronJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowingJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatBorrowingRecordIsCreatedAndReturned() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();

        final Borrowing borrowing = TestData.testBorrowing(TestData.testBookEntity(),TestData.testPatronEntity());
        final String borrowingJson = objectMapper.writeValueAsString(borrowing);

        final Book book = TestData.testBook();
        final String bookJson = objectMapper.writeValueAsString(book);

        final Patron patron = TestData.testPatron();
        final String patronJson = objectMapper.writeValueAsString(patron);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons/").
                        contentType(MediaType.APPLICATION_JSON).
                        content(patronJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowingJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/borrow/1/patron/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

