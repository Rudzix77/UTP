package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ProgLang{
	Map<String, Set<String>> map = new LinkedHashMap<>();
	Set<String> hset = new LinkedHashSet<>();

	public ProgLang(String fname) throws FileNotFoundException {
		try {
			Scanner reader = new Scanner(new File(fname));
			while(reader.hasNextLine()){
				String line = reader.nextLine();
				String key = line.split("\t")[0];
				Set<String> tsPom = new LinkedHashSet<>();
				for (int i = 1; i < line.split("\t").length; i++) {
					tsPom.add(line.split("\t")[i]);
					hset.add(line.split("\t")[i]);
				}
				map.put(key, tsPom);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Set<String>> getLangsMap() {
		return map;
	}

	public Map<String, Set<String>> getProgsMap() {
		Map<String, Set<String>> progsMap = new LinkedHashMap<>();
		Iterator<String> iter = hset.iterator();
		while(iter.hasNext()) {
			String lit = iter.next();
			Set<String> setPom = new LinkedHashSet<>();
			for(Map.Entry<String, Set<String>> entry : map.entrySet()){
				Iterator<String> iter2 = entry.getValue().iterator();
				boolean bool = true;
				while(iter2.hasNext() && bool){
					if(iter2.next().equals(lit)){
						setPom.add(entry.getKey());
					}
				}
			}
			progsMap.put(lit, setPom);
		}
		return progsMap;
	}

	public Map<String, Set<String>> getLangsMapSortedByNumOfProgs() {
		return  sorted(map, (o1, o2) -> {
			if (o1.getValue().size() > o2.getValue().size())
				return -1;
			else if (o1.getValue().size() < o2.getValue().size())
				return 1;
			else
				return 0;
		});

	}

	public <T, S> Map<T, Set<S>> sorted(Map<T, Set<S>> map, Comparator<Map.Entry<T, Set<S>>> comp){


		return map.entrySet().stream().sorted(comp).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue() ));
	}
}

/*
public class ProgLang {

	Map<String, List<String>> map;

	public ProgLang(String path) throws IOException {
		map = Files.lines(Paths.get(path)).collect(
				Collectors.toMap(e -> e.substring(0, e.indexOf("\t")),
								 e -> Pattern.compile("\t").splitAsStream(e).skip(1).distinct().collect(Collectors.toList()),
								 (p, c) -> c, LinkedHashMap::new));
	}

	public Map<String, List<String>> getLangsMap() {
		return map;
	}

	public Map<String, List<String>> getProgsMap() {

		Map<String, List<String>> res = new LinkedHashMap();

		map.entrySet().stream().forEach(e -> {
			e.getValue().forEach(v -> res.computeIfAbsent(v, x -> new ArrayList()).add(e.getKey()));
		});

		return res;
	}

	public Map<String, List<String>> getLangsMapSortedByNumOfProgs() {
		return sorted(map, (a, b) -> b.getValue().size() - a.getValue().size());
	}

	public Map<String, List<String>> getProgsMapSortedByNumOfLangs() {
		return sorted(getProgsMap(), (a, b) -> {
			int r = b.getValue().size() - a.getValue().size();
			return (r != 0) ? r :  a.getKey().compareTo(b.getKey());
		});
	}

	public Map getProgsMapForNumOfLangsGreaterThan(int n) {
		return filtered(getProgsMap(), e -> e.getValue().size() > n);
	}

	private <K, V> Map<K, V> sorted(Map<K, V> m, Comparator<Map.Entry<K, V>> f){
		return m.entrySet().stream().sorted(f).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (p, c) -> p, LinkedHashMap::new));
	}

	private <K, V> Map<K, V> filtered(Map<K, V> m, Predicate<Map.Entry<K,V>> f){
		return m.entrySet().stream().filter(f).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue(), (p, c) -> p, LinkedHashMap::new));
	}


}
*/