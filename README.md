# Bibliotheksverwaltung API

REST API zur Verwaltung einer Bibliothek. Buecher koennen angelegt, ausgeliehen und zurueckgegeben werden. Die API ist bewusst klein gehalten und zeigt saubere Schichten (Controller, Service, Repository) mit Tests.

## Features

- CRUD fuer Buecher
- Ausleihen und Zurueckgeben mit Verfuegbarkeitslogik
- In-Memory Datenbank (H2) fuer schnelle Demos
- Tests fuer Controller und Service

## Tech Stack

| Bereich | Technologie |
|---|---|
| Sprache | Java 21 |
| Framework | Spring Boot 3.x |
| Datenbank | H2 (In-Memory) |
| ORM | Spring Data JPA / Hibernate |
| Build | Gradle |
| Tests | JUnit 5, Mockito, Spring Boot Test |

## Quickstart

Voraussetzungen: JDK 21

```bash
# Projekt klonen
git clone https://github.com/maruku99/bibliotheksverwaltung.git
cd bibliotheksverwaltung

# App starten
./gradlew bootRun
```

Die API laeuft unter: http://localhost:8080

Tests ausfuehren:

```bash
./gradlew test
```

## API Uebersicht

| Methode | Pfad | Beschreibung | Statuscodes |
|---|---|---|---|
| GET | /books | Alle Buecher abrufen | 200 |
| GET | /books/{id} | Buch nach ID | 200, 404 |
| GET | /books/available | Verfuegbare Buecher | 200 |
| POST | /books | Buch anlegen | 200 |
| DELETE | /books/{id} | Buch loeschen | 200 |
| POST | /books/{id}/borrow | Buch ausleihen | 200, 400, 404 |
| POST | /books/{id}/return | Buch zurueckgeben | 200, 404 |

## Beispiele

Buch anlegen:

```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Clean Code","author":"Robert C. Martin"}'
```

Antwort:

```json
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "available": true
}
```

Buch ausleihen:

```bash
curl -X POST http://localhost:8080/books/1/borrow
```

## Architektur

Controller -> Service -> Repository -> DB

## Projektstruktur

```
src/
  main/
    java/
      com/marcwendt/libarySim/
        controller/
        exception/
        model/
        repository/
        service/
```

## Roadmap (optional)

- DTOs + Validation fuer Eingaben
- Einheitliches Error-Response-Format
- OpenAPI/Swagger Doku
- Persistente Datenbank-Konfiguration

## Lizenz

MIT
