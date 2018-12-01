package zad1;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
	public static void processDir(String dirName, String resultFileName) {

		Path startDir = Paths.get(dirName);
		Path result = Paths.get(resultFileName);


		try{
			File file = new File(resultFileName);
			file.createNewFile();

			BufferedWriter bW = Files.newBufferedWriter(result, Charset.forName("UTF-8"));

			Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

					if (file.toString().endsWith(".txt")) {
						BufferedReader bR = Files.newBufferedReader(file, Charset.forName("Cp1250"));

						String line = bR.readLine();

						while(line != null){
							bW.write(line + "\n");

							line = bR.readLine();
						}
					}

					return FileVisitResult.CONTINUE;
				}
			});

			bW.close();

		}catch (IOException e){
			e.printStackTrace();
		}


	}
}
