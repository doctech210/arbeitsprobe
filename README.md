Cold Stone Ice Creator

Studiengang: Informatik - Technische Informatik

Autoren:   Stefan Bierenriede, 852142
            Marvin Gels, 868603

## Das Programm im Entwickler Modus starten

Um das Programm im Entwickler Modus zu starten folgendes in das Terminal eingeben:
```shell script
./mvnw quarkus:dev
```
## Das Programm kompilieren

Um das Programm nur zu compilen einfach folgenden Befehl eingeben:
```shell script
./mvnw compile
```
## Die Tests der Anwendung starten

Um alle Tests der Anwedung auszuführen einfach folgendes eingeben:
```shell script
./mvnw test
```

## Nach dem Start der Anwendung

In der import.sql Datei sind bereits genung Datensätze vorhanden um ein Lauffähiges Programm bereit zu stellen.
Genauso sind bereits zwei Nutzer standardmäßig in dem System eingefügt.

1. Nutzer
Name:       admin
Passwort:   admin
Rolle:      Admin

2. Nutzer
Name:       user
Passwort:   user
Rolle:      Kunde

Mit diesen Zugangsdaten ist es möglich alle Aspekte des Systems zu nutzen.

## REST API und Webfrontend

Wenn die Anwedung korrekt gestartet wurde, ist es möglich die REST Endpunkte unter localhost:8080/q/swagger-ui zu erreichen.

Das Webfrontend ist unter localhost:8080/ erreichbar.

Die Benutzerkennungen sind für beide Systeme identisch.