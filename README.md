# JamSession Backend

Welcome to the JamSession Backend! This guide will walk you through setting up and running the backend of the JamSession application.

## Prerequisites

- **Java 21** (Make sure you have Java installed on your machine)
- **MariaDB** (Installed and running)

## Setup Instructions

1. **Create the Database**  
   - Open your MariaDB client (e.g., via terminal or a GUI tool).
   - Create a new database named `jamsessiondb`:


2. **Configure the Application**  
   - Locate the file `application.properties` in the `src/main/resources` directory.
   - Update the `spring.datasource.username` and `spring.datasource.password` properties to match your local MariaDB credentials, for example:
     ```properties
     spring.datasource.url=jdbc:mariadb://localhost:3306/jamsessiondb
     spring.datasource.username=YOUR_USERNAME
     spring.datasource.password=YOUR_PASSWORD
     ```
3. **Import the Database Dump**
   - Locate the database dump file `dump-jamsessiondb.sql`.
   - Import it into your MariaDB database using the follow command in terminal:
     ``` mysql -u YOUR_USERNAME -p jamsessiondb < path/to/dump-jamsessiondb.sql```
   - If using a GUI tool like Dbeaver or Workbench open the database and execute the `dump-jamsessiondb.sql`.

4. **Sample User Credentials**
   Below are the credentials for example users that can be used for testing:
   - **Admin User**
      - Email: `admin@admin.com`
      - Password: `administrator`
   - **Regular User**
      - Email: `kamil@test.com`
      - Password: `password`
   - **Regular User**
      - Email: `tobiasz@test.com`
      - Password: `password`
# JamSessionBackend
