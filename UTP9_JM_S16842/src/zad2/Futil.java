package zad2;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Futil {
		public static void processDir(String dirName, String resultFileName) {

			Path startDir = Paths.get(dirName);
			Path result = Paths.get(resultFileName);

			try {

				if(!Files.exists(result)){
					Files.createFile(result);
				}

				BufferedWriter bW = Files.newBufferedWriter(result, Charset.forName("UTF-8"));

				getFiles(startDir).filter(p -> p.toString().endsWith(".txt")).forEach((Throwing<Path>) f -> Files.lines(f, Charset.forName("Cp1250")).forEach((Throwing<String>) l -> bW.write(l + "\n")));

				bW.close();

			} catch (IOException e) {
				e.printStackTrace();
			}


		}

	static Stream<Path> getFiles(Path path) {
		if (Files.isDirectory(path)) {
			try {
				return Files.list(path).flatMap(e -> getFiles(e));
			} catch (Exception e) {
				return Stream.empty();
			}
		} else {
			return Stream.of(path);
		}
	}

	@FunctionalInterface
	public interface Throwing<T> extends Consumer<T> {

		@Override
		default void accept(final T elem) {
			try {
				acceptThrows(elem);
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}

		void acceptThrows(T elem) throws Exception;

	}
}