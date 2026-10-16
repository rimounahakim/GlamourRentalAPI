GlamourRentalAPI

Om projektet

GlamourRentalAPI är ett REST API för uthyrning av festklänningar och brudklänningar.

Jag valde att skapa ett system för klänningsuthyrning eftersom det är ett ämne som intresserar mig. Jag ville bygga ett projekt där jag kan hantera både klänningar och kunder och koppla ihop dem.

I systemet går det att lägga till, visa, uppdatera och ta bort både klänningar och personer. Jag har också gjort en enkel rosa startsida som passar projektets tema.

Tekniker

I projektet använder jag:

* Java 25
* Spring Boot 4.0.8
* Maven
* Spring Web MVC
* Spring Data JPA och Hibernate
* H2 Database
* Jakarta Validation
* Swagger / OpenAPI
* JSON

Projektets struktur

Jag har delat upp koden i olika paket för att göra projektet tydligare och enklare att arbeta med.

Model

Här har jag klasserna Person och Item. De representerar personer och klänningar som sparas i databasen.

Controller

Här tar jag emot HTTP-anrop som GET, POST, PUT och DELETE.

Service

Här har jag lagt logiken för att hantera personer, klänningar och deras kopplingar.

Repository

Jag använder Spring Data JPA för att läsa och spara information i databasen.

DTO

Här bestämmer jag vilken information som ska skickas in och tillbaka genom API:et.

Exception

Här hanterar jag fel och skickar tillbaka tydliga felmeddelanden.

Config

Här har jag lagt in exempeldata som skapas när programmet startar.

Databas och relationer

Jag använder en H2-databas för att spara information.

Projektet har två entiteter.

Person

* ID
* Namn
* E-postadress

Item (klänning)

* ID
* Namn
* Kategori
* Storlek
* Pris per dag

Jag använder en Many-to-Many-relation mellan Person och Item.

Det betyder att en person kan vara kopplad till flera klänningar och att en klänning kan vara kopplad till flera personer.

Kopplingarna sparas i tabellen person_items.

API och funktioner

Jag använder GET, POST, PUT och DELETE för att hantera informationen.

Klänningar

Metod	Endpoint	Funktion
GET	/api/items	Visa alla klänningar
GET	/api/items/{id}	Visa en klänning
POST	/api/items	Lägg till en klänning
PUT	/api/items/{id}	Uppdatera en klänning
DELETE	/api/items/{id}	Ta bort en klänning

Personer

Metod	Endpoint	Funktion
GET	/api/persons	Visa alla personer
GET	/api/persons/{id}	Visa en person
POST	/api/persons	Lägg till en person
PUT	/api/persons/{id}	Uppdatera en person
DELETE	/api/persons/{id}	Ta bort en person

Koppling mellan personer och klänningar

Metod	Endpoint	Funktion
POST	/api/persons/{personId}/items/{itemId}	Koppla en klänning till en person
DELETE	/api/persons/{personId}/items/{itemId}	Ta bort kopplingen

Validering och felhantering

Jag använder validering för att kontrollera att informationen är korrekt innan den sparas.

Till exempel får namn och kategori inte vara tomma. E-postadressen måste ha rätt format och priset måste vara större än noll.

Jag har också lagt till felhantering med olika HTTP-statuskoder.

* 400 Bad Request: När informationen är felaktig.
* 404 Not Found: När en person eller klänning inte finns.
* 409 Conflict: När en åtgärd inte kan genomföras på grund av en konflikt.

Starta projektet

1. Öppna GlamourRentalAPI i IntelliJ IDEA.
2. Kontrollera att Java 25 är installerat.
3. Vänta tills Maven har laddat klart.
4. Öppna GlamourRentalApplication.java.
5. Klicka på Run för att starta programmet.

Programmet körs på port 8081.

Testa API:et

Jag använder Swagger för att testa mina endpoints och se hur API:et fungerar.

När programmet körs kan jag använda följande adresser:

Sida	Adress
Startsida	http://localhost:8081
Swagger	http://localhost:8081/swagger-ui.html
API-dokumentation	http://localhost:8081/v3/api-docs
Klänningar	http://localhost:8081/api/items
Personer	http://localhost:8081/api/persons
H2 Console	http://localhost:8081/h2-console

H2 Console

För att öppna databasen använder jag följande uppgifter:

* JDBC URL: jdbc:h2:mem:glamourdb
* User Name: sa
* Password: lämnas tomt

Databasen sparas i minnet. Det betyder att informationen försvinner när programmet stängs av.

Jag har därför lagt in några exempel på personer och klänningar som skapas när programmet startar.

Sammanfattning

I det här projektet har jag byggt ett REST API för att hantera uthyrning av klänningar.

Jag har arbetat med Spring Boot, databaser, relationer, validering och felhantering.

Genom att dela upp projektet i olika paket har jag försökt hålla koden tydlig och lättare att förstå. Jag använder Swagger för att testa funktionerna och kontrollera att API:et fungerar som jag tänkt mig.