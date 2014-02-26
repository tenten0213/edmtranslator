package edmtranslate;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

/**
 * @author tenten0213
 */

@CsvEntity(header = true)
public class Dictionary {
    @CsvColumn(name = "日本語")
    public String japanese;

    @CsvColumn(name = "英語")
    public String english;
}
