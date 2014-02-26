package edmtranslate;

import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.CsvEntityListHandler;
import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
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

	/**
	 * main
     * @param args dictionary file, input edm file, output edm file
	 */
	public static void main(final String args[]) {

		System.out.println("start");
        String[] files = getFileNames(args);

        CsvManager csvManager = CsvManagerFactory.newCsvManager();
        try {
            // Load csv and convert to POJO.
            List<Dictionary> dictionaries = Csv.load(new File(files[0]), new CsvConfig(), new CsvEntityListHandler<Dictionary>(Dictionary.class));
            Comparator<Dictionary> comparator = (x, y) -> y.japanese.length() - x.japanese.length();
            // sort dictionaries by japanese desc.
            Collections.sort(dictionaries, comparator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println ("end");
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
