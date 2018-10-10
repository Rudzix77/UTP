# Uniwersalne Techniki Programowania ⛈

### Zadania z SDKP stworzone przez Lana


## Spis Treści
+ [Zadanie 1](#zadanie-1---tabela-państw)
+ [Zadanie 2](#zadanie-2---generics)

---

## Zadanie 1 - Tabela państw

Przedstawić w tabeli JTable państwa z pliku.

Plik powinien mieć następującą postać:
```
nazwa_kol1<TAB>nazwa_kol2<TAB> ....
nazwa_państwa1<TAB>stolica1<TAB> ludność1 ....
```

Na przykład:
```
Name    Capital    Population
Republic of Poland    Warsaw    38500
Czech Republic    Prague    10500 
Kingdom of Spain    Madrid    46599
```

Proszę zwrócić uwagę, że pola są rozdzielane przez znak tabulacji,  liczba ludności podawana jest w tysiącach. Kolumny zawierające nazwy i stolice państw są nieedytowalne, natomiast dane z kolumny zawierającej ludność można edytować.
Plik powinien  znajdować się w katalogu data projektu i mieć nazwę countries.txt

Wymaganiem podstawowym jest pokazanie państw w tabeli z użyciem następującej klasy Main, która uruchamia cały program:

```java
import javax.swing.*;

public class Main {
  
  private JTable ctab;
  
  public void createTable(String countriesFileName) throws Exception {
    ctab = new CountryTable(countriesFileName).create();
  }
  
  public void showGui() {
    SwingUtilities.invokeLater( new Runnable() {
      public void run() {
        JFrame f = new JFrame("Countries table");
        f.add( new JScrollPane(ctab) );
        f.pack();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
      }
    });
  }
  
  public static void main(String[] args)  {
    Main main = new Main();
    try {
      main.createTable("data/countries.txt");
      main.showGui();
    } catch(Exception exc) {
      JOptionPane.showMessageDialog(null, "Table creation failed, " + exc);
    }
  }
}
```

Struktura projektu oraz zawartośc pliku Main.java będzie wygenerowana,
Zawartości pliku Main.java nie wolno zmieniać. Nazwa i położenie pliku państw jest obowiązakowa.
Archiuwm uploadowanego projektu winno zawierać katalog data z plikiem countries.txt (sprawdzić, czy sa zaznaczenia przy eksporcie projektu)

Za wykonanie podstawowej części zadania mozna uzyskać 3 punkty.

Dodatkowe wymagania:

* kolumny tabeli mają mieć nazwy z  pierwszego wiersza pliku +2 p.
* dane o ludności mają być traktowane jako liczby +1 p.
* i pokazywane w tabeli jak liczby +1 p.
* pokazać w tabeli flagi państw (wymaga modyfikacji pliku państw i dodanie kolumny, pokazującej flagę jako grafikę) +2 p.
* zapewnić pokazywanie wszystkich państw świata +2 p.
* wyróżnić  komórki z liczbą ludności dla państw z ludnością > 20 mln czerwonym kolorem pisma +2 p.
* pokazać w tabeli dodatkową kolumnę wyświetlającą datę ostatniej modyfikacji kolumny ludności +2 p.


## Zadanie 2 - Generics

Stworzyć sparametryzowane interfejsy:
+ Selector - z metodą select, zwracającą true jesli argument spełnia warunek zapisany w metodzoe i false w przeciwnym razie
+ Mapper - z metodą map, będącą dowolną funkcją: argument -> wynik

oraz  sparametryzowaną klasę ListCreator, zawierającą:
statyczną metodę collectFrom (lista)
metodę when
metodę mapEvery
które działają w taki sposób, że symboliczny zapis:

```java
    collectFrom(list1).when(selektor).mapEvery(mapper)
```

spowoduje utworzenie listy wynikowej, której elementy stanowią wybrane przez selektor elementy listy list1, przekształacone za pomocą podanego mappera.

Działanie wyjasnia poniższy przykładowy program (którego plik może być modyfikowany tylko w meijscach oznaczonych przez /*<--    ....  */:

```java
      import java.util.*;
  
      public class Main {
        public Main() {
          List<Integer> src1 = Arrays.asList(1, 7, 9, 11, 12);
          System.out.println(test1(src1));

          List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv" );
          System.out.println(test2(src2));
        }
  
        public List<Integer> test1(List<Integer> src) {
          Selector /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
          Mapper   /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
          return   /*<-- zwrot wyniku
            uzyskanego przez wywołanie statycznej metody klasy ListCreator:
           */  collectFrom(src).when(sel).mapEvery(map);
        }
  
        public List<Integer> test2(List<String> src) {
          Selector /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
          Mapper   /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
          return   /*<-- zwrot wyniku
            uzyskanego przez wywołanie statycznej metody klasy ListCreator:
           */  collectFrom(src).when(sel).mapEvery(map);
        }
  
        public static void main(String[] args) {
          new Main();
        }
      }
```

Gdy w metodzie test1 selektor wybiera z listy liczby < 10, a mapper zwraca liczbę-argument powiększoną o 10, to na konsoli powinniśmy zobaczyć:
[11, 17, 19]

Gdy w metodzie test2  selektor wybiera z listy napisy, których długiość jest  większa od 3 znakow, a mapper dzwraca dlugość przekazanego napisu, powiększoną o 10, to na konsoli zobaczymy:
[14, 17]

Należy obowiązkowo zapewnić takie właśnie działanie programu..