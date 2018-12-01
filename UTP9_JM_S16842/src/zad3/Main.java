/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad3;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {

    try(InputStream is = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt").openStream()){

      Map<String, List<String>> grouped = new BufferedReader(new InputStreamReader(is)).lines().collect((Collectors.groupingBy(w -> {
        char[] arr = w.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
      })));

      int max = grouped.values().stream().max(Comparator.comparingInt(List::size)).get().size();

      grouped.values().stream().filter(e -> e.size() == max).sorted(Comparator.comparing(a -> a.get(0))).forEach(e -> {
        e.forEach(w -> System.out.print(w + " "));
        System.out.println();
      });


    }catch (Exception e) {
      e.printStackTrace();
    }
  }
}
