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
