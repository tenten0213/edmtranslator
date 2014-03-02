package edmtranslate;

import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.CsvEntityListHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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

    private static final String PNAME = "P-NAME";

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
        Document xmlDoc = null;
        File input = new File(files[1]);
        final SAXReader reader = new SAXReader();
        try {
            xmlDoc = reader.read(input);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        List list = xmlDoc.getRootElement().selectNodes("//ENTITY");
        xmlDoc.getRootElement().selectNodes("//ATTR").forEach(s -> list.add((s)));
        xmlDoc.getRootElement().selectNodes("//INDEX").forEach(s -> list.add((s)));
        xmlDoc.getRootElement().selectNodes("//ATTR").forEach(s -> list.add((s)));

        dictionaries.forEach((dictionary) -> list.stream()
                .filter(s -> StringUtils.isNotEmpty(((Element) s).attributeValue(PNAME)))
                .filter(s -> ((Element) s).attributeValue(PNAME).indexOf(dictionary.japanese) > -1)
                .forEach(s -> ((Element) s).attribute(PNAME).setValue(((Element) s).attributeValue(PNAME).replace(dictionary.japanese, dictionary.english)))
        );
        list.stream()
            .filter(s -> StringUtils.isNotEmpty(((Element) s).attributeValue(PNAME)))
            .forEach(s -> ((Element) s).attribute(PNAME).setValue( StringUtill.camelToSnake(((Element) s).attributeValue(PNAME)))) ;

        StringWriter stringWriter = new StringWriter();
        OutputFormat outputFormat = new OutputFormat("", true, "UTF-8");
        outputFormat.setNewLineAfterDeclaration(false);
        outputFormat.setNewlines(false);
        outputFormat.setSuppressDeclaration(true);
        XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
        try {
            xmlWriter.write(xmlDoc);
            FileUtils.writeStringToFile(new File(files[2]), stringWriter.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                xmlWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
