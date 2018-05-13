package yogur;

import yogur.cup.YogurParser;
import yogur.ididentification.IdentifierAnalyzer;
import yogur.jlex.YogurLex;
import yogur.tree.Program;
import yogur.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestIdentifiers {
	private static String TESTDIR = "./../tests/";

	public static void main(String args[]) {
		File file = new File("./../tests/exampleOne.yogur");

		//testFile(file);
		testAll();
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
			Program prog = (Program)p.parse().value;

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

			IdentifierAnalyzer identifierAnalyzer = new IdentifierAnalyzer(prog);
			identifierAnalyzer.decorateTree();
			System.out.println("Success!" + prog);
		} catch (Exception e) {
			System.err.println("Parsing error " + p.getExceptions() + " on file " + file.getName());
			e.printStackTrace();
		}

	}
}
