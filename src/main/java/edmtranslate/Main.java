package edmtranslate;

import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.CsvEntityListHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.io.XMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * EDMファイルの日本語→英語変換ツールのメインクラス。
 * edmtranslatorは本クラスから起動します。
 *
 * @author tenten0213
 *
 */
public class Main {

    /** 入力ファイル名デフォルト値 */
    private static final String DICTIONARY_FILE = "src/test/resources/data/dictionary.csv";

	/** 入力ファイル名デフォルト値 */
	private static final String INPUT_FILE = "src/test/resources/data/input.edm";

	/** 出力ファイル名デフォルト値 */
	private static final String OUTPUT_FILE = "src/test/resources/data/output.edm";

    private static final String PNAME = "p-name";

	/**
	 * main
     * @param args dictionary file, input edm file, output edm file
	 */
	public static void main(final String args[]) {

		System.out.println("start");
        String[] files = getFileNames(args);
        List<Dictionary> dictionaries = new ArrayList<>();

        try {
            // Load csv and convert to POJO.
            dictionaries =
                    Csv.load(new File(files[0]),
                            new CsvConfig(),
                            new CsvEntityListHandler<>(Dictionary.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Comparator<Dictionary> comparator = (x, y) -> y.japanese.length() - x.japanese.length();

        // sort dictionaries by japanese desc.
        Collections.sort(dictionaries, comparator);

        // load input.edm
        File input = new File(files[1]);
        Document xmlDoc = null;
        try {
            xmlDoc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = xmlDoc.getElementsByTag("ENTITY");
        xmlDoc.getElementsByTag("ATTR").forEach(s -> elements.add(s));
        xmlDoc.getElementsByTag("INDEX").forEach(s -> elements.add(s));
        xmlDoc.getElementsByTag("RELATION").forEach(s -> elements.add(s));
        dictionaries.forEach((dictionary) -> elements.stream()
                .filter(s -> s.attr(PNAME).indexOf(dictionary.japanese) > -1)
                .map(s -> s.attr(PNAME, StringUtill.camelToSnake(s.attr(PNAME).replace(dictionary.japanese, dictionary.english))))
                .forEach(System.out::println));

        try {
            File file = new File(files[2]);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        System.out.println("end");
	}

    /**
     * getFileNames
     * @param args dictionary file, input edm file, output edm file. if you don't set args, return default fileNames.
     */
    private static String[] getFileNames(String[] args) {
        if( !ArrayUtils.isEmpty(args) ) {
            switch (args.length) {
                case 1: return new String[]{args[0], INPUT_FILE, OUTPUT_FILE};
                case 2: return new String[]{args[0], args[1], OUTPUT_FILE};
                case 3: return new String[]{args[0], args[1], args[2]};
                default: throw new IllegalArgumentException("too many arguments");
            }
        }
        return new String[]{DICTIONARY_FILE, INPUT_FILE, OUTPUT_FILE};
    }
}
