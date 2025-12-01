package com.example.library.controller;

import com.example.library.dto.BookRequest;
import com.example.library.dto.BookResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook_successfully() throws Exception {
        BookRequest request = new BookRequest("123-456", "Clean Architecture", "Robert C. Martin",
                "Engenharia", 2017, "Arquitetura limpa e sustentável.");

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("123-456"))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.title").value("Clean Architecture"));
    }

    @Test
    void createBook_duplicateIsbn_returnsConflict() throws Exception {
        BookRequest request = new BookRequest("111-222", "Livro A", "Autor A", "Tecnologia", 2020, null);
        createBook(request);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void searchBooks_withFilters() throws Exception {
        createBook(new BookRequest("301", "Java 17", "Ana Silva", "Tecnologia", 2023, null));
        createBook(new BookRequest("302", "Ficção", "Outro Autor", "Romance", 2019, null));

        MvcResult result = mockMvc.perform(get("/api/books")
                        .param("category", "Tecnologia")
                        .param("author", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andReturn();

        BookPage page = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertThat(page.content()).hasSize(1);
        Assertions.assertThat(page.content().get(0).author()).contains("Ana");
    }

    @Test
    void checkoutAndReturnFlow_changesAvailability() throws Exception {
        BookResponse created = createBook(new BookRequest("777", "Livro Emprestimo", "Fulano", "Aventura", 2010, null));

        MvcResult checkoutResult = mockMvc.perform(patch("/api/books/{id}/checkout", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false))
                .andReturn();

        BookResponse checkedOut = objectMapper.readValue(checkoutResult.getResponse().getContentAsString(), BookResponse.class);
        Assertions.assertThat(checkedOut.available()).isFalse();

        mockMvc.perform(patch("/api/books/{id}/return", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(true));
    }

    private BookResponse createBook(BookRequest request) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(), BookResponse.class);
    }

    private record BookPage(List<BookResponse> content) {
    }
}
