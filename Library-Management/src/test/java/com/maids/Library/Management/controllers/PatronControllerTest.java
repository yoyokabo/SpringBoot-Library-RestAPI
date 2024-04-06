package com.maids.Library.Management.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.Library.Management.entites.Patron;
import com.maids.Library.Management.services.PatronService;
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

import static com.maids.Library.Management.utils.TestData.testPatron;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Reset Data after each test method
public class PatronControllerTest {  // Integration testing

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatronService patronService;

    @Test
    public void testThatPatronIsCreated() throws Exception {
        final Patron patron = TestData.testPatron();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String patronJson = objectMapper.writeValueAsString(patron);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patrons/").
                        contentType(MediaType.APPLICATION_JSON).
                        content(patronJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(patron.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(patron.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value(patron.getPhonenumber()));



    }
    @Test
    public void testThatRetrievePatronReturn404() throws Exception {  // Integration test case 404
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/12"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrievePatronReturnPatronWhenExists() throws Exception { // Integration test case exists Better to split into 2 tests but combined for brevity
        final Patron patron = testPatron();
        patronService.create(patron);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/" + patron.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(patron.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(patron.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value(patron.getPhonenumber()));

    }

    @Test
    public void testThatListPatronsReturnEmptyListWhenNoPatrons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    @Test
    public void testThatListPatronsReturnsPatronsWhenPatrons() throws Exception {
        final Patron patron = TestData.testPatron();
        patronService.create(patron);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("[0].name").value(patron.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].address").value(patron.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].phonenumber").value(patron.getPhonenumber()));

    }

    @Test
    public void testThatPatronIsUpdatedCorrectlyIfExists() throws Exception {
        final Patron patron = TestData.testPatron();
        patronService.create(patron);

        patron.setPhonenumber("Test Change");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String patronJson = objectMapper.writeValueAsString(patron);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/patrons/" + patron.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patronJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value("Test Change"));


    }

    @Test
    public void testThatPatronIsDeletedAndReturnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patrons/5231"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
