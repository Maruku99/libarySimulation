# 📚 Bibliotheksverwaltung API

A RESTful backend service for managing a library system — built with Java, Spring Boot and JPA.
Users can add books, manage availability and borrow or return them via a clean REST API.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Database | H2 (In-Memory) |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Gradle |
| Testing | Postman |

---

## 🚀 Getting Started

```bash
# Projekt klonen
git clone https://github.com/dein-username/bibliotheksverwaltung.git
cd bibliotheksverwaltung

# App starten
./gradlew bootRun
```

Die API ist dann erreichbar unter: `http://localhost:8080`

---

## 📡 Endpoints

| Method | Endpoint | Beschreibung |
|---|---|---|
| `GET` | `/books` | Alle Bücher abrufen |
| `GET` | `/books/{id}` | Einzelnes Buch abrufen |
| `POST` | `/books` | Neues Buch hinzufügen |
| `DELETE` | `/books/{id}` | Buch löschen |
| `POST` | `/books/{id}/borrow` | Buch ausleihen |
| `POST` | `/books/{id}/return` | Buch zurückgeben |

---

## 💡 Beispiel Request

**Buch hinzufügen:**
```json
POST /books
{
  "title": "Clean Code",
  "author": "Robert C. Martin"
}
```

**Antwort:**
```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "available": true
}
```

---

## 📋 Lernfortschritt

> Persönliche Checkliste während der Entwicklung

### Phase 1 – Setup & erstes Lebenszeichen
- [x] Projekt auf [start.spring.io](https://start.spring.io) erstellt (Gradle, Java 21, Spring Web, JPA, H2)
- [x] Projekt in IDE geöffnet und erfolgreich gebaut
- [x] `BookController` mit `GET /books` → gibt "Hello Books" zurück
- [x] App gestartet, Antwort im Browser unter `localhost:8080/books` sichtbar

### Phase 2 – Die Book Klasse
- [x] Klasse `Book` mit Feldern `id`, `title`, `author`, `available` erstellt
- [x] Konstruktor, Getter & Setter geschrieben
- [x] Im Controller eine feste Liste von Büchern manuell erstellt (noch keine DB)
- [x] `GET /books` gibt Buch-Objekte als JSON zurück

### Phase 3 – Datenbank anbinden
- [x] `@Entity` Annotation zur `Book` Klasse hinzugefügt
- [x] `BookRepository` Interface erstellt (Spring Data JPA)
- [x] `BookService` erstellt – enthält die Business-Logik
- [x] Controller nutzt jetzt den Service statt einer festen Liste

### Phase 4 – CRUD Endpoints
- [x] `POST /books` → Buch hinzufügen
- [x] `GET /books` → alle Bücher abrufen
- [x] `GET /books/{id}` → einzelnes Buch
- [x] `DELETE /books/{id}` → Buch löschen
- [x] Alle Endpoints mit Postman getestet

### Phase 5 – Business Logik
- [ ] `available` Feld (true/false) korrekt gesetzt
- [ ] `POST /books/{id}/borrow` → Buch ausleihen (mit Verfügbarkeitsprüfung)
- [ ] `POST /books/{id}/return` → Buch zurückgeben
- [ ] Fehlerfall getestet: bereits ausgeliehenes Buch kann nicht nochmal ausgeliehen werden

### Phase 6 – Fehlerbehandlung
- [ ] Eigene Exception `BookNotFoundException` erstellt
- [ ] Sinnvolle HTTP-Statuscodes zurückgegeben (200, 400, 404)
- [ ] Unbekannte IDs geben klare Fehlermeldungen zurück

### Phase 7 – Bonus (optional)
- [ ] Bücher nach Autor filtern: `GET /books?author=Kafka`
- [ ] PostgreSQL statt H2 eingebunden
- [ ] GitHub Repository mit diesem README veröffentlicht

---

## 📁 Projektstruktur

```
src/
└── main/
    └── java/
        └── com/example/bibliothek/
            ├── controller/
            │   └── BookController.java
            ├── service/
            │   └── BookService.java
            ├── repository/
            │   └── BookRepository.java
            ├── model/
            │   └── Book.java
            └── exception/
                └── BookNotFoundException.java
```

---

## 🎯 Was ich dabei gelernt habe

- REST API Design mit Spring Boot
- Objektorientierung praktisch angewendet (Klassen, Services, Repositories)
- Datenbankanbindung mit Spring Data JPA
- Exception Handling und HTTP-Statuscodes
- Projektstruktur in einem echten Backend-Projekt

---

*Dieses Projekt wurde als erstes Portfolio-Projekt zur Vertiefung von Java Backend-Kenntnissen entwickelt.*
