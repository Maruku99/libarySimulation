package com.marcwendt.libarySim.service;

import com.marcwendt.libarySim.exception.BookAlreadyBorrowedException;
import com.marcwendt.libarySim.exception.BookNotFoundException;
import com.marcwendt.libarySim.model.Book;
import com.marcwendt.libarySim.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // aktiviert Mockito – kein Spring, keine DB, sehr schnell
class BookServiceTest {

    @Mock
    private BookRepository bookRepository; // simuliertes Repository – macht keine echten DB-Abfragen

    @InjectMocks
    private BookService bookService; // echter Service, bekommt das simulierte Repository injiziert

    // getAllBooks()
    // ────────────────────────────────────────────
    @Test
    void getAllBooks_gibtAlleZurueck() {
        // Vorbereitung
        List<Book> bücher = List.of(
                new Book("Clean Code", "Robert C. Martin"),
                new Book("Der Hobbit", "J.R.R. Tolkien"));
        when(bookRepository.findAll()).thenReturn(bücher);

        // Ausführen
        List<Book> ergebnis = bookService.getAllBooks();

        // Prüfen
        assertEquals(2, ergebnis.size());
        assertEquals("Clean Code", ergebnis.get(0).getTitle());
    }

    @Test
    void getAllBooks_gibtLeereListeZurueck_wennKeineExistieren() {
        when(bookRepository.findAll()).thenReturn(List.of());

        List<Book> ergebnis = bookService.getAllBooks();

        assertEquals(0, ergebnis.size());
    }
    // ────────────────────────────────────────────

    // getBookById()
    // ────────────────────────────────────────────
    @Test
    void getBookById_gibtBuchZurueck_wennVorhanden() {
        Book buch = new Book("Clean Code", "Robert C. Martin");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(buch));

        Book ergebnis = bookService.getBookById(1L);

        assertEquals("Clean Code", ergebnis.getTitle());
    }

    @Test
    void getBookById_wirftException_wennNichtVorhanden() {
        // Repository gibt leeres Optional zurück → Buch nicht gefunden
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        // Prüft: wird BookNotFoundException geworfen?
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(999L));
    }
    // ────────────────────────────────────────────

    // addBook()
    // ────────────────────────────────────────────
    @Test
    void addBook_speichertUndGibtBuchZurueck() {
        Book buch = new Book("Clean Code", "Robert C. Martin");
        when(bookRepository.save(buch)).thenReturn(buch);

        Book ergebnis = bookService.addBook(buch);

        assertEquals("Clean Code", ergebnis.getTitle());
        verify(bookRepository, times(1)).save(buch); // wurde save() genau 1x aufgerufen?
    }
    // ────────────────────────────────────────────

    // borrowBookById()
    // ────────────────────────────────────────────
    @Test
    void borrowBook_setztVerfuegbarkeitAufFalse_wennVerfuegbar() {
        Book buch = new Book("Clean Code", "Robert C. Martin");
        buch.setAvailable(true);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(buch));

        bookService.borrowBookById(1L);

        assertFalse(buch.isAvailable()); // available muss jetzt false sein
        verify(bookRepository, times(1)).save(buch); // wurde gespeichert?
    }

    @Test
    void borrowBook_wirftException_wennBereitsAusgeliehen() {
        Book buch = new Book("Clean Code", "Robert C. Martin");
        buch.setAvailable(false); // bereits ausgeliehen
        when(bookRepository.findById(1L)).thenReturn(Optional.of(buch));

        // Prüft: wird eine Exception geworfen?
        assertThrows(BookAlreadyBorrowedException.class, () -> bookService.borrowBookById(1L));

        // Prüft: wurde save() NICHT aufgerufen? (kein Update wenn Fehler)
        verify(bookRepository, never()).save(any());
    }
    // ────────────────────────────────────────────

    // returnBookById()
    // ────────────────────────────────────────────
    @Test
    void returnBook_setztVerfuegbarkeitAufTrue() {
        Book buch = new Book("Clean Code", "Robert C. Martin");
        buch.setAvailable(false); // ist ausgeliehen
        when(bookRepository.findById(1L)).thenReturn(Optional.of(buch));

        bookService.returnBookById(1L);

        assertTrue(buch.isAvailable()); // available muss wieder true sein
        verify(bookRepository, times(1)).save(buch); // wurde gespeichert?
    }

    @Test
    void returnBook_wirftException_wennIDNichtExistiert() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.returnBookById(999L));
    }
    // ────────────────────────────────────────────
}
