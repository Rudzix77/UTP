package zad1;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dictionary {

	Map<String, List<String>> definitions = new LinkedHashMap();
	String path;

	public Dictionary(String path){

		this.path = path;

		try(Scanner scan = new Scanner(new File(path))){
			while (scan.hasNextLine()){

				String line = scan.nextLine();

				if(line.contains("=")){

					String[] p = line.split("=");

					if(p.length == 2){
						if(!definitions.containsKey(p[0])){
							definitions.put(p[0], new ArrayList<>());
						}

						definitions.get(p[0]).add(p[1]);
					}
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public List<String> lookup(String key){
		List<String> list = getSorted(key).stream().collect(Collectors.toList());

		IntStream.range(0, list.size()).boxed().forEach(n -> {
			list.set(n, (n+1) + ". " + list.get(n));
		});

		return list;
	}

	public boolean add(String key, String value){
		if(!definitions.containsKey(key)){
			definitions.put(value, new ArrayList<>());
		}

		if(definitions.get(key).contains(value)){
			return false;
		}

		definitions.get(key).add(value);

		return true;
	}

	public boolean delete(String key, int num){

		if(definitions.containsKey(key)){
			if(num-1 < definitions.get(key).size()){
				getSorted(key).remove(num-1);

				return true;
			}
		}

		return false;
	}

	public boolean update(String key, String value, String value2){
		if(definitions.containsKey(key)){

			List<String> list = definitions.get(key);

			if(list.contains(value)){
				list.set(list.indexOf(value), value2);

				return true;
			}
		}

		return false;
	}

	public void save() {
		String out = "";

		for(Map.Entry<String, List<String>> e : definitions.entrySet()){

			String key = e.getKey();
			List<String> values = e.getValue();

			for(String v : values){
				out += key + "=" + v + "\n";
			}
		}


		try{
			Files.write(Paths.get(path), out.getBytes());
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	public List<String> getSorted(String key){
		definitions.get(key).sort(Comparator.naturalOrder());

		return definitions.get(key);
	}


}
