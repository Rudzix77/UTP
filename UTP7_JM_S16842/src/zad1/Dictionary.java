package zad1;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Dictionary {

	Map<String, TreeSet<String>> definitions = new LinkedHashMap();
	String path;

	public Dictionary(String path){

		this.path = path;

		try(Scanner scan = new Scanner(new File(path))){
			while (scan.hasNextLine()){

				String line = scan.nextLine();

				if(line.contains("=")){

					String[] p = line.split("=");
						String key = p[0].trim();
						String value = p[1].trim();

					if(p.length == 2){
						add(key, value);
					}
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private boolean validate(String key){
		if(!definitions.containsKey(key)){
			return false;
		}

		return true;
	}

	public List<String> lookup(String key) throws NotFoundException{

		if(validate(key)){
			List list = new ArrayList();

			definitions.get(key).iterator().forEachRemaining(e -> list.add((list.size()+1) + ". " + e));

			return list;
		}else{
			throw new NotFoundException();
		}
	}

	public void add(String key, String value){
		definitions.computeIfAbsent(key, e -> new TreeSet()).add(value);
	}

	public void delete(String key, int n) throws NotFoundException {

		if(validate(key)){
			Set set = definitions.get(key);
			Iterator it = set.iterator();
			int i = 1;

			while(it.hasNext()){

				it.next();

				if(i++ == n){
					it.remove();
				}
			}

			if(set.isEmpty()){
				definitions.remove(key);
			}

		}else{
			throw new NotFoundException();
		}
	}

	public void update(String key, String value, String value2) throws NotFoundException {

		if(validate(key)){
			Set set = definitions.get(key);

			set.removeIf(e -> value.equals(e));

			if(set.size() == 0){
				definitions.remove(set);
			}

			add(key, value2);
		}else{
			throw new NotFoundException();
		}

	}

	public boolean save() {
		String out = "";

		for(Map.Entry<String, TreeSet<String>> e : definitions.entrySet()){

			String key = e.getKey();
			Set<String> values = e.getValue();

			for(String v : values){
				out += key + " = " + v + "\n";
			}
		}

		try{
			Files.write(Paths.get(path), out.getBytes());
		}catch (IOException e){
			return false;
		}

		return true;

	}

}

class NotFoundException extends Exception{}