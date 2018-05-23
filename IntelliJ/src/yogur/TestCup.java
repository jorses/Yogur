package yogur;

import java_cup.runtime.Symbol;
import yogur.cup.YogurParser;
import yogur.jflex.YogurLex;
import yogur.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestCup {

	private static String TESTDIR = "./../tests/";

	public static void main(String args[]) {
		File file = new File("./../tests/02_Expressions.yogur");

		testAll();
		//testFile(file);
	}

	public static void testAll(){
		File dir = new File(TESTDIR);

		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File file : directoryListing) {
				testFile(file);
			}
		} else{
			System.err.println("Empty testing directory");
		}

	}

	public static void testFile(File file) {
		YogurParser p = null;

		try (FileInputStream is = new FileInputStream(file)) {
			YogurLex jlex = new YogurLex(new InputStreamReader(is));
			p = new YogurParser(jlex);

			Symbol s = p.parse();

			if (!jlex.getExceptions().isEmpty()) {
				for (Exception e: jlex.getExceptions()) {
					Log.error(e);
				}
				return;
			}

			if (!p.getExceptions().isEmpty()) {
				for (Exception e: jlex.getExceptions()) {
					Log.error(e);
				}
				return;
			}

			System.out.println(s);
		} catch (Exception e) {
			System.err.println("Parsing error " + p.getExceptions() + " on file " + file.getName());
			e.printStackTrace();
		}

	}
}
