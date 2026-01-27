# **Phase 1: Local-style tool in Codespaces (cloud-based)**

---

## **1️⃣ Tech Stack**

| Layer           | Technology                                     | Notes                                                       |
| --------------- | ---------------------------------------------- | ----------------------------------------------------------- |
| Language        | **Java 17+**                                   | Long-term support, modern features                          |
| Framework       | **Spring Boot (optional for scheduler/email)** | Can start pure Java, later migrate to Spring Boot if needed |
| Scheduler       | **Java `ScheduledExecutorService`**            | Run follow-up checks every hour/day                         |
| Storage         | **JSON files or H2 Database**                  | JSON simplest first; H2 for structured DB in cloud          |
| Email           | **SMTP via JavaMail (Gmail/Outlook)**          | Send reminder only when follow-up missed                    |
| Dev Environment | **GitHub Codespaces**                          | Full cloud IDE, Git integrated                              |
| Version Control | **Git + GitHub**                               | Auto backup, versioning, easy collaboration                 |
| Optional UI     | **Command-line first**                         | Can add Web UI later using Spring Boot + React              |

---

## **2️⃣ Project Architecture (Cloud-Local)**

```
+---------------------+
| User adds client    |
| + follow-up date    |
+---------------------+
            |
            v
+---------------------+
| Scheduler           | <-- runs every hour/day
| Checks pending      |
| follow-ups          |
+---------------------+
            |
            v
+---------------------+       +-----------------+
| Condition: missed?  | ----> | Send email      |
| status == PENDING   |       | reminder via    |
+---------------------+       | SMTP            |
            |                 +-----------------+
            |
            v
+---------------------+
| Mark follow-up DONE |
| or REMINDER_SENT    |
+---------------------+
```

---

## **3️⃣ Workflow in Codespaces**

1. **Create Codespace from GitHub repo**

   * Repository: `follow-up-reminder`
   * Branch: `main` (stable), `dev` (feature work)

2. **Create project structure**

```
follow-up-reminder/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/followup/
│   │   │       ├── App.java
│   │   │       ├── Scheduler.java
│   │   │       ├── EmailService.java
│   │   │       └── Storage.java
│   │   └── resources/
│   │       └── config.properties
├── data/
│   └── clients.json
└── pom.xml
```

3. **Scheduler logic**

   * Runs every hour/day
   * Reads `clients.json` (client email, follow-up date, status)
   * Sends email **if missed**
   * Updates status to `REMINDER_SENT`

4. **Email logic**

   * SMTP via Gmail/Outlook
   * Only sends if follow-up date passed and status PENDING
   * Subject example:

     ```
     ❗ FOLLOW UP MISSED – Client Name
     ```

5. **Storage**

   * Start JSON-based:

```json
[
  {
    "client": "client@email.com",
    "lastSent": "2026-01-27",
    "followUp": "2026-01-30",
    "status": "PENDING"
  }
]
```

* Later migrate to **H2** if you need queries, sorting, filtering

6. **Version control**

   * `git commit` after each feature
   * Push to GitHub → fully backed up in cloud

7. **Optional later**

   * Add Web UI: Spring Boot + React
   * Access via browser from anywhere
   * Same scheduler + email logic

---

## **4️⃣ Daily Usage**

* Open Codespace
* Add clients/follow-up in `clients.json`
* Scheduler runs in background (or start App)
* Email triggers only for missed follow-ups
* Mark DONE when follow-up done
* Push changes to GitHub (backup)

---

