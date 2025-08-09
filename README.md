# Progetto-BD

**Progetto di Esame – Basi di Dati**  
Implementazione di un’applicazione Java con interfaccia grafica (GUI) per l’analisi dei dati relativi alla Formula 1. Il progetto include un database relazionale e varie query SQL previste nella *checkList*.

---

## Contenuto del repository
- **Database/**  
  Contiene script, file di dump o dati strutturati per la creazione e il popolamento del database.

- **GUI/**  
  Contiene il codice della parte grafica, sviluppata in Java (Swing, JavaFX o libreria simile).

- **Main.java**  
  Entry point dell’applicazione. Si occupa dell’inizializzazione della GUI e della connessione al database.

- **checkList.txt**  
  Elenco delle query SQL richieste per coprire i requisiti d’esame. In genere include:
  - Query di selezione base (SELECT)
  - Filtri (WHERE)
  - Join tra tabelle
  - Aggregazioni (GROUP BY, COUNT, SUM, AVG…)
  - Query più complesse (subquery, viste, ecc.)

---

## Requisiti
- **Java Development Kit (JDK)** versione 11 o superiore
- Ambiente per l’esecuzione di database relazionali (es. PostgreSQL, MySQL, SQLite)
- Tool per importare o eseguire gli script SQL (psql, mysql CLI, DB Browser per SQLite…)

---

## Installazione ed esecuzione

1. **Configurazione del database**  
   - Creare un nuovo database (es. `f1db`)
   - Eseguire gli script contenuti in `Database/` per creare tabelle e aggiungere dati.

2. **Configurazione dell’applicazione Java**  
   - Importare il progetto in un IDE (Eclipse, IntelliJ, VS Code…) o compilare da linea di comando:
     ```bash
     javac -d bin src/**/*.java
     ```
   - Se serve, impostare parametri di connessione al database (host, porta, nome utente, password).

3. **Avvio**  
   - Eseguire:
     ```bash
     java -cp bin Main
     ```
   - La GUI si avvierà e sarà possibile interagire con il database tramite interfaccia grafica.

---

## CheckList – Query SQL incluse
Nella cartella principale è presente `checkList.txt`, contenente le query richieste, per esempio:

- `SELECT * FROM pilotes WHERE nationality = 'Italia';`  
- `SELECT team, COUNT(*) FROM races GROUP BY team;`  
- `-- JOIN tra tabelle (es. piloti, squadre, gare)`  
- `-- Query con aggregazioni, ordinamenti, e subquery`

Ogni query è documentata e testata attraverso l’applicazione.

---

## Finalità didattiche
- Applicare le basi teoriche e pratiche di modellazione e interrogazione di database relazionali.
- Sviluppare una GUI Java per facilitare l’interazione e la visualizzazione dei risultati.
- Comprendere le strutture dati tipiche del contesto Formula 1 (piloti, squadre, gare, risultati).

---

## Autori
- **Roberto Cito & Aleksandre Chikviladze** -
