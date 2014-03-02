package edmtranslate;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MainTest {

    /** 出力ファイル名デフォルト値 */
    private static final String OUTPUT_FILE = "src/test/resources/data/output.edm";

    /** 出力ファイル名デフォルト値 */
    private static final String EXPECTED_FILE = "src/test/resources/data/expected.edm";

	@Test
	public void 引数なし() throws DocumentException {
		Main.main(new String[] {});
        final SAXReader reader = new SAXReader();
        Document actual = reader.read(OUTPUT_FILE);
        Document expected = reader.read(EXPECTED_FILE);
        assertThat(actual.asXML(), is(expected.asXML()));
	}
	@Test
	public void 引数ひとつ() throws DocumentException {
		Main.main(new String[]{"src/test/resources/data/dictionary.csv"});
        final SAXReader reader = new SAXReader();
        Document actual = reader.read(OUTPUT_FILE);
        Document expected = reader.read(EXPECTED_FILE);
        assertThat(actual.asXML(), is(expected.asXML()));
	}

	@Test
	public void 引数ふたつ() throws DocumentException {
		Main.main(new String[] { "src/test/resources/data/dictionary.csv",
				"src/test/resources/data/input.edm" });
        Main.main(new String[] {});
        final SAXReader reader = new SAXReader();
        Document actual = reader.read(OUTPUT_FILE);
        Document expected = reader.read(EXPECTED_FILE);
        assertThat(actual.asXML(), is(expected.asXML()));
	}

    @Test
    public void 引数みっつ() throws DocumentException {
        Main.main(new String[] { "src/test/resources/data/dictionary.csv",
                "src/test/resources/data/input.edm",
                "src/test/resources/data/output.edm"});
        Main.main(new String[] {});
        final SAXReader reader = new SAXReader();
        Document actual = reader.read(OUTPUT_FILE);
        Document expected = reader.read(EXPECTED_FILE);
        assertThat(actual.asXML(), is(expected.asXML()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void 引数よっつ() throws DocumentException {
        Main.main(new String[] { "src/test/resources/data/dictionary.csv",
                "src/test/resources/data/input.edm",
                "src/test/resources/data/output.edm",
                "src/test/resources/data/output.edm"});
        Main.main(new String[] {});
        final SAXReader reader = new SAXReader();
        Document actual = reader.read(OUTPUT_FILE);
        Document expected = reader.read(EXPECTED_FILE);
        assertThat(actual.asXML(), is(expected.asXML()));
    }
}
