/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*<--
 *  niezbędne importy
 */
public class Main {
  public static void main(String[] args) {
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */

    Function<String, List<String>> flines = path -> {

      List<String> list = new ArrayList<>();

      File file = new File(path);

      try(Scanner scan = new Scanner(file)){
        while(scan.hasNextLine()){
          list.add(scan.nextLine());
        }
      } catch (IOException e){
        e.printStackTrace();
      }

      return list;
    };

    Function<List<String>, String> join = list -> {
      String txt = "";

      for(String s : list){
        txt += s;
      }

      return txt;
    };

    Function<String, List<Integer>> collectInts = src -> {

      List<Integer> list = new ArrayList<>();

      Matcher m = Pattern.compile("[-]?(\\d+)").matcher(src);

      while(m.find()){
        list.add(Integer.parseInt(m.group()));
      }

      return list;
    };

    Function<List<Integer>, Integer> sum = list -> {
      int g = 0;

      for(int n : list){
        g += n;
      }

      return g;
    };


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
}
