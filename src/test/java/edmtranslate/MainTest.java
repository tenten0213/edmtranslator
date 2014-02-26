package edmtranslate;

import org.junit.Test;

public class MainTest {

	@Test
	public void 引数なし() {
		Main.main(new String[] {});
	}
/*
	@Test
	public void 引数ひとつ() {
		Main.main(new String[] { "examples/data/dictionary.csv" });
	}

	@Test
	public void 引数ふたつ() {
		Main.main(new String[] { "examples/data/dictionary.csv",
				"examples/data/input.edm" });
	}

    @Test
    public void 引数みっつ() {
        Main.main(new String[] { "examples/data/dictionary.csv",
                "examples/data/input.edm",
                "examples/data/output.edm"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void 引数よっつ() {
        Main.main(new String[]{"examples/data/dictionary.csv",
                "examples/data/input.edm",
                "examples/data/output.edm",
                "examples/data/hoge.edm"});
    }
    */
}
