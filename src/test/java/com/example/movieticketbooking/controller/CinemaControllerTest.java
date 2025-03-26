package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.cinema.request.CinemaCreateRequest;
import com.example.movieticketbooking.service.CinemaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CinemaController.class)
public class CinemaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CinemaService cinemaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createCinema_ShouldReturn201_WhenValidRequest() throws Exception {
        // Fake Cinema JSON data
        MockMultipartFile cinemaJson = new MockMultipartFile("cinema", "",
                "application/json",
                ("{ \"name\": \"Galaxy Cinema\", \"address\": \"123 Main St\", \"street\": \"123 Main St\", " +
                        "\"ward\": \"123 Main St\", \"district\": \"123 Main St\", \"city\": \"123 Main St\", " +
                        "\"phoneNumber\": \"0123456789\" }").getBytes());

        // Fake image file
        MockMultipartFile imageFile = new MockMultipartFile("images", "image.jpg",
                "image/jpeg", new byte[]{1, 2, 3});

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/cinemas")
                        .file(cinemaJson) // JSON data
                        .file(imageFile) // Image file
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated()) // Kiểm tra status 201
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Galaxy Cinema"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value("123 Main St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value("0123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.images").isArray());
    }




    @Test
    void createCinema_WhenMissingRequiredFields_ShouldReturn400() throws Exception {
        // Given
        CinemaCreateRequest request = new CinemaCreateRequest(null, "Address", "Street", "Ward", "District", "City", "0123456789");

        // When & Then
        mockMvc.perform(post("/api/v1/cinemas")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
