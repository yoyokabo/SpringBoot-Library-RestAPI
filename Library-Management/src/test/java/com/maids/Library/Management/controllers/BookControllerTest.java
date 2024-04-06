package com.maids.Library.Management.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.Library.Management.entites.Book;
import com.maids.Library.Management.services.BookService;
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

import static com.maids.Library.Management.utils.TestData.testBook;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Reset Data after each test method
public class BookControllerTest {  // Integration testing

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreated() throws Exception {
        final Book book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/").
                        contentType(MediaType.APPLICATION_JSON).
                        content(bookJson))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.pubyear").value(book.getPubyear()));



    }
    @Test
    public void testThatRetrieveBookReturn404() throws Exception {  // Integration test case 404
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/12"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveBookReturnBookWhenExists() throws Exception { // Integration test case exists Better to split into 2 tests but combined for brevity
        final Book book = testBook();
        bookService.create(book);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/" + book.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pubyear").value(book.getPubyear()));

    }

    @Test
    public void testThatListBooksReturnEmptyListWhenNoBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    public void testThatListBooksReturnsBooksWhenBooks() throws Exception {
        final Book book = TestData.testBook();
        bookService.create(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].pubyear").value(book.getPubyear()));

    }

    @Test
    public void testThatBookIsUpdatedCorrectlyIfExists() throws Exception {
        final Book book = TestData.testBook();
        bookService.create(book);

        book.setAuthor("Test Change");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Test Change"));


    }

    @Test
    public void testThatBookIsDeletedAndReturnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/5231"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
