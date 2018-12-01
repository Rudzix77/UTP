/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad2;


import static zad2.Futil.processDir;

public class Main {
  public static void main(String[] args) {
    String dirName = System.getProperty("user.home")+"/UTP6dir";
    String resultFileName = "UTP6res.txt";
    processDir(dirName, resultFileName);
  }
}
