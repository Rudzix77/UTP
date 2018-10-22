# Uniwersalne Techniki Programowania ⛈

### Zadania z SDKP stworzone przez Lana


## Spis Treści
+ [Zadanie 1](#zadanie-1---tabela-państw)
+ [Zadanie 2](#zadanie-2---generics)
+ [Zadanie 3](#zadanie-3---ceny-przelotów---lambda-1)
+ [Zadanie 4](#zadanie-4---ceny-przelotów---lambda-2)
+ [Zadanie 5](#zadanie-4---ceny-przelotów---lambda-2)
+ [Zadanie 6](#zadanie-4---ceny-przelotów---lambda-2)

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

## Zadanie 3 - Ceny przelotów - lambda 1

Lista dest zawiera informacje o cenach przelotów w postaci napisów:

```port_wylotu port_przylotu cena_w_EUR```

Należy utworzyć listę wynikową, której elementy będą opisywać ceny przelotów do poszczególnych miejsc (tylko) z Warszawy w PLN i wypisać na konsoli jej kolejne elementy.

Aby rozwiązać to zadanie, należy utworzyć sparametryzowaną klasę ListCreator, zawierającą:
statyczną metodę collectFrom (lista)
metodę when
metodę mapEvery
które działają w taki sposób, że symboliczny zapis:

```java
collectFrom(list).when(lambda-1).mapEvery(lambda-2)
```

spowoduje utworzenie listy wynikowej, której elementy stanowią wybrane przez lambda-1 elementy listy list, przekształcone za pomocą podanego lambda-2.

Uwagi: 
w zadaniu nie wolno korzystać z własnych interfejsów,
klasa ListCreator i jej metody powinny działać dla list (źródłowej i docelowej) elementów dowolnego typu.

Następujący (niemodyfikowalny poza miejsami oznaczonymi /*<--*/) program:
```java
    import java.util.*;

    public class Main {

      static List<String> getPricesInPLN(List<String> destinations, double xrate) {
        return ListCreator.collectFrom(destinations)
                           .when(  /*<-- lambda wyrażenie
                                    *  selekcja wylotów z Warszawy (zaczynających się od WAW)
                                    */
                            )
                           .mapEvery( /*<-- lambda wyrażenie
                                       *  wyliczenie ceny przelotu w PLN
                                       *  i stworzenie wynikowego napisu
                                       */
                            );
      }

      public static void main(String[] args) {
        // Lista destynacji: port_wylotu port_przylotu cena_EUR 
        List<String> dest = Arrays.asList(
          "bleble bleble 2000",
          "WAW HAV 1200",
          "xxx yyy 789",
          "WAW DPS 2000",
          "WAW HKT 1000"
        );
        double ratePLNvsEUR = 4.30;
        List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
        for (String r : result) System.out.println(r);
      }
    }
```
ma wyprowadzić na konsolę napisy:

```
to HAV - price in PLN:	5160
to DPS - price in PLN:	8600
to HKT - price in PLN:	4300
```
Postać wydruku jest obowiązkowa.


## Zadanie 4 - Ceny przelotów - lambda 2

Lista dest zawiera informacje o cenach przelotów w postaci napisów:
```port_wylotu port_przylotu cena_w_EUR```

Należy utworzyć listę wynikową, której elementy będą opisywać ceny przelotów do poszczególnych miejsc (tylko) z Warszawy w PLN i wypisać na konsoli jej kolejne elementy, używając następującego programu:

```java
/*<-- niezbędne importy */

public class Main {

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR 
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result = 
    /*<-- tu należy dopisać fragment
     * przy czym nie wolno używać żadnych własnych klas, jak np. ListCreator
     * ani też żadnych własnych interfejsów
     */

    for (String r : result) System.out.println(r);
  }
}
```

Plik Main.java wolno modyfikować tylko w miejscach oznaczonych /*<--  */, a program ma wyprowadzić na konsolę:

```
to HAV - price in PLN:	5160
to DPS - price in PLN:	8600
to HKT - price in PLN:	4300
```

## Zadanie 5 - Klasa Maybe

Zdefiniować klasę Maybe o następujących właściwościach.

Obiekty Maybe reprezentują kontenery, które mogą zawierać lub nie pojedynczą wartość. Motywacją do wprowadzenia takiej konstrukcji jest ułatwienie programowania w sytuacji, gdy zmienna może mieć wartość null, szczególnie kiedy wymagane jest jej dalsze bezpieczne przetwarzanie (na przykład za pomocą lambda-wyrażeń, oznaczających jakieś funkcje). Bezpieczne - to znaczy takie, które nie powoduje wyjątku NullPointerException.

Obiekty typu Maybe zawierają jakąś wartość lub są puste (nigdy nie powinny mieć wartości null). 
W klasie Maybe zdefiniować następujące metody:

* Maybe.of(x) - ta metoda statyczna zwraca obiekt Maybe, „opakowujący” wartość x, dowolnego typu referencyjnego.

* void ifPresent(Consumer cons)  - jeżeli w obiekcie Maybe znajduje się wartość, wykonywana jest operacja cons z tą wartością jako argumentem, w przeciwnym razie - gdy obiekt Maybe jest pusty - nic się nie dzieje.

* Maybe map(Function func) -  jeżeli w obiekcie  jest wartość, wykonywana jest funkcja func z tą wartością jako argumentem i zwracany jest jej wynik „zapakowany” w nowy obiekt klasy Maybe (to opakowanie jest niezbędne, bo wynik mógłby być null, a tego chcemy uniknąć w ewentualnym dalszym przetwarzaniu; jeśli wynikiem funkcji jest null, zwracany jest pusty obiekt klasy Maybe).

* T get() zwraca zawartość obiektu Maybe, ale jeśli jest on pusty, powinna zgłosić wyjątek NoSuchElementException.
boolean isPresent() - zwraca true jeśli w obiekcie Maybe zawarta jest wartośc, a false - gdy jest on pusty

* T orElse(T defVal) - zwraca zawartość obiektu Maybe lub domyślną wartosć defVal, jeśli obiekt Maybe jest pusty.

* Maybe filter(Predicate pred)  - zwraca  to Maybe, jeśli spełniony jest warunek pred lub to Maybe jest puste; zwraca puste Maybe, jeśli warunek pred jest niespełniony.

Klasę Maybe przetestować na przykładzie następującej klasy Main:

```java
        public class Main {

          public static void test() {
            // Metoda of(...)
            String s = "aaa";    
            Maybe<String> m1 = Maybe.of(s);
            System.out.println(m1);
            s = null;
            Maybe<String> m2 = Maybe.of(s);
            System.out.println(m2);

            // Metoda ifPresent(...)
            Integer num = null;
            Maybe<Integer> m4 = Maybe.of(num);
            // ZAMIAST
            if (num != null) System.out.println(num);
            // PISZEMY
            m4.ifPresent(n -> System.out.println(n));
            // A NAWET
            m4.ifPresent(System.out::println);

            Maybe<Integer> m5 = Maybe.of(10);
            m5.ifPresent(System.out::println);

            // Metoda map()
            Maybe<Integer> m6 = m5.map( n -> n +10 ); 
            System.out.println(m6);

            // Metoda get()
            System.out.println(m6.get());
            try {
              System.out.println(m4.get());
            } catch(Exception exc) {
              System.out.println(exc);
            }

            // Metoda orElse()
            // ZAMIAST
            String snum = null;
            if (num != null) snum = "Wartość wynosi: " + num;
            if (snum != null) System.out.println(snum);
            else System.out.println("Wartość niedostępna");

            //MOŻNA NAPISAĆ
            String res = Maybe.of(num).map(n -> "Wartość wynosi: "+n)
                              .orElse("Wartość niedostępna");
            System.out.println(res);

            // I filter(...)

            String txt = "Pies";
            String msg = "";

            //ZAMIAST
            if (txt != null && txt.length() > 0) {
              msg = txt;
            } else {
              msg = "Txt is null or empty";
            }

            //MOŻNA NAPISAĆ
            msg = Maybe.of(txt)
                       .filter(t -> t.length() > 0)
                       .orElse("Txt is null or empty"); 
            System.out.println(msg);
          }

          public static void main(String[] args) {
            test();
          }
        }
        // Wynik na konsoli:
        /*    
          Maybe has value aaa
          Maybe is empty
          10
          Maybe has value 20
          20
          java.util.NoSuchElementException:  maybe is empty
          Wartość niedostępna
          Wartość niedostępna
          Pies
        */
```

## Zadanie 6 - Lambda-wyrażenia dla niefunkcyjnych interfejsów

Spowodować, by w następującycm dalej programie,  po naciśnięciu klawisza myszki na przycisku b
na konsoli zostało wypisane "ok". Pliku Main.java nie wolno modyfikować.

```java
  public static void main(String[] args) {
    SwingUtilities.invokeLater( ()-> {
        JFrame f = new JFrame();
        JButton b = new JButton("Myszą ciśnij");
        b.addMouseListener ( (MousePressListener) e -> System.out.println("ok") );
        f.add(b);
        f.pack();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
      }
    );
  }
```

## Zadanie 7 - Lambda-wyrażenia - kompozycja funkcji


Zbudować klasę InputConverter, która pozwala przekształcić dane wejściowe (ustalane w konstruktorze klasy) za pomocą funkcji, podanych jako argumenty metody convertBy.

Np. jeśli mamy zdefiniowane operacje (funkcje):
* flines - zwraca listę wierszy z pliku tekstowego

* join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)

* collectInts - zwraca listę liczb całkowitych zawartych w napisie

* sum - zwraca sumę elementów listy liczb całkowitych

to sumę liczb całkowitych, występujących w pliku o nazwie fname możemy uzyskać poprzez: 

```java
Integer s = new InputConverter<String>(fname).convertBy(flines, join, collectInts, sum); 
```

a listę liczb całkowitych z napisu txt tak:

```java
List<Integer> n = new InputConverter<String>(txt).convertBy(collectInts);
```

Istotą metody convertBy jest to, że pozwala ona w różny sposób kombinować różne operacje na różnych danych i łatwo uzyskiwać wyniki.

Zapewnić prawidłowe działanie następującego fragmentu kodu (dostarczonego w szablonie projektu, pliku Main.java nie wolno modyfikować):

```java
  public static void main(String[] args) {
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
```

Dla następujących danych z pliku:
Cars:
```
- Fiat: 15, Ford: 20
- Opel: 8, Mitsubishi: 10
```

oraz  nastepujących argumentów wywołania metody main:

```Warszawa 100 Kielce 200 Szczecin 300```

program powinien wyprowadzić na konsolę:

```
[Cars:, - Fiat: 15, Ford: 20, - Opel: 8, Mitsubishi: 10]
Cars:- Fiat: 15, Ford: 20- Opel: 8, Mitsubishi: 10
[15, 20, 8, 10]
53
600
```

Uwaga: w klasie InputConverter musi występować tylko jedna metoda o nazwie convertBy (czyli w tym zadaniu nie dopuszczamy przeciążania metody convertBy).

## Zadanie 8 - Przekazywanie wyjątków kontrolowanych z lambda-wyrażeń do obsługi w bloku otaczającym lambda.

W kontekście:

```java
  public static void main(String[] args) throws IOException {
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń :
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
   // ....
}
```

Uwaga: w programie nie wolno definiować żadnych własnych interfejsów (za wyjątkiem być może rozszerzeń interfejsów z pakietu java.util.function), a więc operacje flines, join, itp. muszą opierać się na gotowych interfejsach funkcyjnych z pakietu java.util.function lub ich rozszerzeniach.

Operacja flines zawiera odczyt pliku, zatem może powstać wyjątek IOException.
Wymagane jest, aby tę operację zdefiniowac jako lambda-wyrażenie.
Ale z lambda wyrażeń, opierających się na interfejsach funkcyjnych z pakietu java.util.function, nie możemy przekazać obsługi wyjatków do otaczającego bloku.
I wobec tego musimy pisać w definicji flines try { } catch { }
Jak spowodować, aby nie było to konieczne i w przypadku powstania wyjątku IOException
zadziałała klauzula throws metody main ?

## Zadanie 9 - Dodatkowe operacje na listach

Stworzyć klasę XList, dostarczającą dodatkowych możliwości tworzenia list i operowania na nich.
W klasie powinny znaleźć się odpowiednie konstruktory oraz statyczne metody of, umożliwiające tworzenie obiektów XList z innych kolekcji, tablic oraz argumentów podawanych przez przecinki.
Dodatkowo pomocnicze metody do tworzenia xlist z napisów: 
* ofChars(napis) - zwraca x-listę znaków napisu,

* ofTokens(napis, [ sep ]) - zwraca x-listę symboli napisu, rozdzielonych separatorami z sep (jesśi brak - to białymi znakami).

Oprócz tego dostarczyć metod: 

* union(dowolna_kolekcja)  -  zwraca  nową x-list z dołączaną do tej x-list  zawartością kolekcji,

* diff(dowolna_kolekcja) - zwraca x-list zawierającą te 
elementy tej x-list, które nie występują w kolekcji,

* unique() - zwraca nową x-list, która zawiera wszystkie niepowtarzające się elementy tej x-list

* combine() - zwraca x-listę list-kombinacji elementów z poszczególnych kolekcji, będących elementami tej x-listy

* collect(Function) - zwraca nową x-listę, której elemenatmi są wyniki funkcji stosowanej wobec elementów tej x-listy,

* join([sep]) - zwraca napis, będący połączeniem elementów tej x-listy, z ewentualnie wstawionym pomiędzy nie separatorem,

* forEachWithIndex(konsumer_z_dwoma argumentami: element, index) - do iterowania po liście z dostępem i do elementów i do ich indeksów.

Za realizację każdej z w/w właściwosci  uzyskuje się odrębne punkty, tak że można wykonac tylko część zadania.
Przy tym należy jednak uważać, aby przekazany w rozwiązaniu plik Main.java nie miał błędów w kompilacji.

Klasa Main zawarta w projekcie powinna dobrze wyjaśniać sposób realizacji zadania:

```java
import java.util.*;

// Plik Main.java może być dowolnie modyfikowany, 
// ale punkty uzyskuje się za właściwe dzialanie poszczególnych pokazanych tu metod klasy XList.

// Jeżeli nie oprogramujemy wszystkich metod, to z klasy Main nalezy usunąć te fragmenty,
// które powodują błędy w kompilacji - w przeciwnym razie nie uzyskamy punktów.

public class Main {
  public static void main(String[] args) {
    // Pewne dodatkowe zestawy danych
    Integer[] ints = { 100, 200, 300 };
    Set<Integer> set = new HashSet<>(Arrays.asList(3, 4, 5));
    
    // Sposoby tworzenia
    XList<Integer> list1 = new XList<>(1, 3, 9, 11);
    XList<Integer> list2 = XList.of(5, 6, 9);
    XList<Integer> list3 = new XList(ints);
    XList<Integer> list4 = XList.of(ints);
    XList<Integer> list5 = new XList(set);
    XList<Integer> list6 = XList.of(set);

    System.out.println(list1);
    System.out.println(list2);
    System.out.println(list3);
    System.out.println(list4);
    System.out.println(list5);
    System.out.println(list6);
    
    // --- i pomocnicze metody do tworzenia z napisów
    XList<String> slist1 = XList.charsOf("ala ma kota");
    XList<String> slist2 = XList.tokensOf("ala ma kota");
    XList<String> slist3 = XList.tokensOf("A-B-C", "-");

    System.out.println(slist1);
    System.out.println(slist2);
    System.out.println(slist3);

    // Metoda union - suma elementów 
    List<Integer> m1 = list1.union(list2);  // oczywiście, można podstawiać na List
    System.out.println(m1);
    // można wykonywać wszystkie operacje z interfejsu List, np:
    m1.add(11);
    System.out.println(m1);
    XList<Integer> m2 = (XList<Integer>) m1;
    XList<Integer> m3 = m2.union(ints).union(XList.of(4, 4));
    System.out.println(m2); // m2 się nie zmienia
    System.out.println(m3); // wynik jest w m3
    m3 = m3.union(set);
    System.out.println(m3);
    
    // Widzieliśmy metode union
    // Teraz metoda diff(dowolna kolekcja)
    System.out.println(m3.diff(set));  // wszystko z m3, co nie jest w set
    System.out.println(XList.of(set).diff(m3)); // co jest w set, czego nie ma w m3
    
    // Metoda unique -zwraca nową Xlist bez duplikatow   
    XList<Integer> uniq = m3.unique(); // lista, nie Set
    System.out.println(uniq);    
   
    // kombinacje (kolejność jest istotna)
    List<String> sa = Arrays.asList( "a", "b");
    List<String> sb = Arrays.asList( "X", "Y", "Z" );
    XList<String> sc = XList.charsOf( "12" );
    XList toCombine = XList.of(sa, sb, sc);  // czy można tu uniknąć użycia typu surowego?
    System.out.println(toCombine);
    XList<XList<String>> cres = toCombine.combine();
    System.out.println(cres);

    // collect i join
    XList<String> j1 = cres.collect( list -> list.join());
    System.out.println(j1.join(" "));
    XList<String> j2 =cres.collect( list -> list.join("-"));
    System.out.println(j2.join(" "));
    
    // forEachWithIndex
    XList<Integer> lmod = XList.of(1,2,8, 10, 11, 30, 3, 4);  
    lmod.forEachWithIndex( (e, i) -> lmod.set(i, e*2));
    System.out.println(lmod);
    lmod.forEachWithIndex( (e, i) -> { if (i % 2 == 0) lmod.remove(e); } );
    System.out.println(lmod);
    lmod.forEachWithIndex( (e, i) -> { if (i % 2 == 0) lmod.remove(i); } );
    System.out.println(lmod); // Pytanie: dlaczego mamy taki efekt? 

  }
}
```

Ten program wypisuje na konsoli:

```
[1, 3, 9, 11]
[5, 6, 9]
[100, 200, 300]
[100, 200, 300]
[3, 4, 5]
[3, 4, 5]
[a, l, a,  , m, a,  , k, o, t, a]
[ala, ma, kota]
[A, B, C]
[1, 3, 9, 11, 5, 6, 9]
[1, 3, 9, 11, 5, 6, 9, 11]
[1, 3, 9, 11, 5, 6, 9, 11]
[1, 3, 9, 11, 5, 6, 9, 11, 100, 200, 300, 4, 4]
[1, 3, 9, 11, 5, 6, 9, 11, 100, 200, 300, 4, 4, 3, 4, 5]
[1, 9, 11, 6, 9, 11, 100, 200, 300]
[]
[1, 3, 9, 11, 5, 6, 100, 200, 300, 4]
[[a, b], [X, Y, Z], [1, 2]]
[[a, X, 1], [b, X, 1], [a, Y, 1], [b, Y, 1], [a, Z, 1], [b, Z, 1], [a, X, 2], [b, X, 2], [a, Y, 2], [b, Y, 2], [a, Z, 2], [b, Z, 2]]
aX1 bX1 aY1 bY1 aZ1 bZ1 aX2 bX2 aY2 bY2 aZ2 bZ2
a-X-1 b-X-1 a-Y-1 b-Y-1 a-Z-1 b-Z-1 a-X-2 b-X-2 a-Y-2 b-Y-2 a-Z-2 b-Z-2
[2, 4, 16, 20, 22, 60, 6, 8]
[4, 16, 22, 60, 8]
[16, 22, 60, 8]
```