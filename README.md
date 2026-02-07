## Wstęp
Repozytorium zawiera zestaw laboratoriów z przedmiotu **Technologie Obiektowe**, realizowanego na **5 semestrze studiów**.  
Każde laboratorium prezentuje inne zagadnienia związane z programowaniem obiektowym, wzorcami projektowymi oraz algorytmami przetwarzania danych.

## Zawartość laboratoriów

### Laboratorium 1 – Aplikacja do przewalutowywania
Aplikacja do przeliczania walut, pobierająca aktualne kursy z zewnętrznego API.  
Zastosowano wzorzec projektowy **Singleton** do zarządzania dostępem do konfiguracji i danych kursów walut.

### Laboratorium 2 – Wzorce projektowe na wektorach
Zadanie przedstawiające działanie wzorców projektowych:
- **Strategy (Strategia)**
- **Observer (Obserwator)**
- **Iterator**

Prosta aplikacja ilustrująca działanie tych wzorców na przykładzie operacji na wektorach 2- oraz 3-wymiarowych.

### Laboratorium 3 – Symulacja działania jednostek straży pożarnej w Krakowie
Projekt przedstawia symulację działania jednostek straży pożarnej na terenie miasta Krakowa.  
Celem laboratorium jest modelowanie procesu reagowania na zdarzenia (pożary, wypadki) oraz zarządzanie ruchem i dostępnością jednostek ratowniczych.
<img width="678" height="495" alt="image" src="https://github.com/user-attachments/assets/97ca0ebb-3d5c-4832-8787-695c90291793" />


### Laboratorium 4 – Symulacja rozprzestrzeniania się chorób
Implementacja symulacji rozprzestrzeniania się chorób w populacji.  
W projekcie wykorzystano wzorce:
- **Observer (Obserwator)**
- **Strategy (Strategia)**
- **Memento**

Celem laboratorium jest demonstracja ich praktycznego zastosowania w systemie symulacyjnym.
<img width="572" height="428" alt="image" src="https://github.com/user-attachments/assets/0dac1fc0-e182-4aea-87f9-5975a2c7a4ca" />

### Laboratorium 5 – Wykrywanie obiektów w szumie
Implementacja algorytmów wykrywania obiektów w danych zaszumionych:
- **CCL (Connected Component Labeling)**
- **MHT (Multiple Hypothesis Tracking)**
- **Otsu (automatyczne progowanie obrazu)**

Projekt skupia się na analizie danych oraz filtracji i segmentacji obiektów.
<img width="996" height="802" alt="image" src="https://github.com/user-attachments/assets/06ffc241-fc19-4b41-b464-f092eb082f60" />



# Symulator Ruchu Miejskiego (Traffic Simulation System)

## Opis projektu
Aplikacja jest agentowym symulatorem ruchu drogowego, w którym globalne zjawiska (takie jak korki czy zatory) wynikają z autonomicznych decyzji poszczególnych pojazdów. System pozwala na badanie przepustowości dróg w zależności od zastosowanych strategii sterowania ruchem (sygnalizacja świetlna, ronda).

## Kluczowe funkcjonalności
- **Autonomiczni agenci**  
  Każdy pojazd posiada własną logikę percepcji (wykrywanie kolizji, dopasowanie prędkości).

- **Dynamiczne blokady**  
  Możliwość ręcznego zamykania odcinków dróg w czasie rzeczywistym.

- **Różnorodność pojazdów**  
  Wsparcie dla aut osobowych, ciężarówek, autobusów (przystanki) oraz pojazdów uprzywilejowanych.

- **System analityczny**  
  Automatyczne wykrywanie zatorów i logowanie zdarzeń systemowych.

## Architektura i wzorce projektowe
Projekt został stworzony zgodnie z zasadami SOLID i wykorzystuje następujące wzorce:

- **Strategy (Strategia)**  
  Zarządzanie logiką skrzyżowań. Umożliwia łatwą zamianę sygnalizacji świetlnej na rondo bez modyfikacji klasy `Intersection`.

- **Observer (Obserwator)**  
  Odseparowanie silnika symulacji od interfejsu użytkownika. Silnik powiadamia zarejestrowanych słuchaczy (np. `LogPanel`) o zdarzeniach.

- **Factory (Fabryka)**  
  Klasa `VehicleFactory` odpowiada za polimorficzne tworzenie różnych typów pojazdów.

- **Builder (Budowniczy)**  
  Użyty do czytelnego konstruowania złożonej topologii mapy miasta (grafu dróg).

- **Singleton**  
  Zapewnienie globalnego dostępu do ustawień symulacji (`TrafficSettings`).

## Technologie
- **Język:** Java 17+
- **Interfejs graficzny:** JavaFX (akceleracja sprzętowa przez Canvas API)
- **Taktowanie:** `AnimationTimer` (pętla gry 60 FPS)

  <img width="1616" height="946" alt="image" src="https://github.com/user-attachments/assets/57caf7e3-b6b2-4d07-a8c6-8cba62d2f487" />


<img width="1622" height="948" alt="image" src="https://github.com/user-attachments/assets/2255aad4-13f4-4a6e-82d3-74b21a91f7e0" />

