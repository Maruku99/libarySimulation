package com.marcwendt.libarySim.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // startet die komplette App (Controller + Service + DB)
@AutoConfigureMockMvc // MockMvc zum Testen von HTTP Requests
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // setzt DB vor jedem Test zurück
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc; // simulieren der HTTP Requests, kein Postman nötig

    // GET /books
    // ────────────────────────────────────────────
    @Test
    void getAllBooks_sollte200Zurueckgeben() throws Exception {
        // Prüft: Endpoint ist erreichbar und gibt 200 zurück
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllBooks_sollteLeereListeZurueckgeben_wennKeineExistieren() throws Exception {
        // Prüft: Frische DB = leere Liste, kein Fehler
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllBooks_sollteAngelegteBuecherZurueckgeben() throws Exception {
        // Erst Buch anlegen, dann prüfen ob es in der Liste ist
        buchAnlegen("Clean Code", "Robert C. Martin");

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"));
    }
    // ────────────────────────────────────────────

    // GET /books/{id}
    // ────────────────────────────────────────────
    @Test
    void getBookById_sollte200Zurueckgeben_wennVorhanden() throws Exception {
        // Erst Buch anlegen, dann per ID abrufen
        buchAnlegen("Der Hobbit", "J.R.R. Tolkien");

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Der Hobbit"));
    }

    @Test
    void getBookById_sollte404Zurueckgeben_wennNichtVorhanden() throws Exception {
        // Prüft: BookNotFoundException → GlobalExceptionHandler → 404
        // Keine Bücher angelegt, ID 999 existiert nicht
        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }
    // ────────────────────────────────────────────

    // POST /books
    // ────────────────────────────────────────────
    @Test
    void addBook_sollte200Zurueckgeben_undBuchSpeichern() throws Exception {
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Clean Code\", \"author\": \"Robert C. Martin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    void addBook_sollteIdAutomatischVergeben() throws Exception {
        // Prüft: keine ID mitgegeben – DB vergibt sie automatisch
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"1984\", \"author\": \"George Orwell\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()); // ID muss vorhanden sein
    }
    // ────────────────────────────────────────────

    // DELETE /books/{id}
    // ────────────────────────────────────────────
    @Test
    void deleteBook_sollte200Zurueckgeben_wennVorhanden() throws Exception {
        buchAnlegen("Zu löschendes Buch", "Autor");

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBook_sollteNichtMehrAbrufbarSein_nachLoeschen() throws Exception {
        buchAnlegen("Zu löschendes Buch", "Autor");

        // Löschen
        mockMvc.perform(delete("/books/1"));

        // Danach sollte 404 kommen
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }
    // ────────────────────────────────────────────

    // POST /books/{id}/borrow
    // ────────────────────────────────────────────
    @Test
    void borrowBook_sollte200Zurueckgeben_wennVerfuegbar() throws Exception {
        buchAnlegen("Clean Code", "Robert C. Martin");

        mockMvc.perform(post("/books/1/borrow"))
                .andExpect(status().isOk());
    }

    @Test
    void borrowBook_sollteVerfuegbarkeitAufFalseSetzen() throws Exception {
        buchAnlegen("Clean Code", "Robert C. Martin");

        // Ausleihen
        mockMvc.perform(post("/books/1/borrow"));

        // Prüfen ob available jetzt false ist
        mockMvc.perform(get("/books/1"))
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void borrowBook_sollte400Zurueckgeben_wennBereitsAusgeliehen() throws Exception {
        buchAnlegen("Clean Code", "Robert C. Martin");

        // Erstes Mal ausleihen
        mockMvc.perform(post("/books/1/borrow"));

        // Zweites Mal ausleihen → soll Fehler geben
        mockMvc.perform(post("/books/1/borrow"))
                .andExpect(status().isBadRequest());
    }
    // ────────────────────────────────────────────

    // POST /books/{id}/return
    // ────────────────────────────────────────────
    @Test
    void returnBook_sollteVerfuegbarkeitAufTrueSetzen() throws Exception {
        buchAnlegen("Clean Code", "Robert C. Martin");

        // Ausleihen
        mockMvc.perform(post("/books/1/borrow"));

        // Zurückgeben
        mockMvc.perform(post("/books/1/return"))
                .andExpect(status().isOk());

        // Prüfen ob available wieder true ist
        mockMvc.perform(get("/books/1"))
                .andExpect(jsonPath("$.available").value(true));
    }
    // ────────────────────────────────────────────

    // GET /books/available
    // ────────────────────────────────────────────
    @Test
    void getAvailableBooks_sollteNurVerfuegbareZurueckgeben() throws Exception {
        buchAnlegen("Verfügbares Buch", "Autor A");
        buchAnlegen("Ausgeliehenes Buch", "Autor B");

        // Zweites Buch ausleihen
        mockMvc.perform(post("/books/2/borrow"));

        // Nur verfügbare abrufen → sollte nur 1 Buch sein
        mockMvc.perform(get("/books/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Verfügbares Buch"));
    }
    // ────────────────────────────────────────────

    // Hilfsmethode – Buch anlegen ohne Code-Wiederholung
    // ────────────────────────────────────────────
    private void buchAnlegen(String title, String author) throws Exception {
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author)));
    }
    // ────────────────────────────────────────────
}
