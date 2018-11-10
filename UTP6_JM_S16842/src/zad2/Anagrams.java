/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {

	List<String> words;

	public Anagrams(String path){
		this.words = getWords(path);
	}

	List<String> getWords(String path){

		List<String> list = new ArrayList();

		try(Scanner in = new Scanner(new File(path))){

			while(in.hasNextLine()){
				Collections.addAll(list, in.nextLine().split(" "));
			}

		}catch (FileNotFoundException e){
			e.printStackTrace();
		}

		return list;
	}

	public List<List<String>> getSortedByAnQty(){

		Map<String, List<String>> grouped =  words.stream().collect((Collectors.groupingBy(w -> {
			char[] arr = w.toCharArray();
			Arrays.sort(arr);
			return new String(arr);
		})));

		return grouped.values().stream().sorted((f, g) -> g.size() - f.size()).collect(Collectors.toList());

	}

	public String getAnagramsFor(String a){
		List<String> list = getSortedByAnQty().stream().filter(e -> e.contains(a)).collect(Collectors.toList()).get(0);
			list.remove(a);

			return a + ": " + list;
	}
}  
