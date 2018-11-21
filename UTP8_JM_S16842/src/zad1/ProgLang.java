package zad1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProgLang {

	Map<String, List<String>> map;

	public ProgLang(String path) throws IOException {
		map = Files.lines(Paths.get(path)).collect(
				Collectors.toMap(e -> e.substring(0, e.indexOf("\t")),
								 e -> Pattern.compile("\t").splitAsStream(e).skip(1).distinct().collect(Collectors.toList()),
								 (p, c) -> c, LinkedHashMap::new));
	}

	public Map getLangsMap() {
		return map;
	}

	public Map<String, List<String>> getProgsMap() {

		Map<String, List<String>> res = new LinkedHashMap();

		map.entrySet().stream().forEach(e -> {
			e.getValue().forEach(v -> {
				res.computeIfAbsent(v, x -> new ArrayList()).add(e.getKey());
			});
		});


		return res;
	}

	public Map getLangsMapSortedByNumOfProgs() {
		return sorted(map, (a, b) -> b.getValue().size() - a.getValue().size());
	}

	public Map getProgsMapSortedByNumOfLangs() {
		return sorted(getProgsMap(), (a, b) -> {
			int r = b.getValue().size() - a.getValue().size();
			return (r != 0) ? r :  a.getKey().compareTo(b.getKey());
		});
	}

	public Map getProgsMapForNumOfLangsGreaterThan(int n) {
		return sorted((Map<String, List<String>>)filtered(getProgsMap(), e -> e.getValue().size() > n), (a,b) -> {

			List<String> aL = a.getValue();
			List<String> bL = b.getValue();

			for(int z = 0; z < aL.size(); z++){
				int res = aL.get(z).compareTo(bL.get(z));

				if(res != 0){
					return res;
				}
			}

			return aL.size() - bL.size();
		});
	}

	private <K, V> Map sorted(Map<K, V> m, Comparator<Map.Entry<K, V>> f){
		return m.entrySet().stream().sorted(f).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (previous, current) -> previous, LinkedHashMap::new));
	}

	private <K, V> Map filtered(Map<K, V> m, Predicate<Map.Entry<K,V>> f){
		return m.entrySet().stream().filter(f).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
	}


}
