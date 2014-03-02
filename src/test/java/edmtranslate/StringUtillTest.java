package edmtranslate;


import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author tenten0213
 */
public class StringUtillTest {

    @Test
    public void camelToSnakeは大文字スネークケースを返すCamelToSnakeの場合() {
        assertThat(StringUtill.camelToSnake("CamelToSnake"), is("CAMEL_TO_SNAKE"));
    }

    @Test
    public void camelToSnakeは大文字スネークケースを返すcameltosnakeの場合() {
        assertThat(StringUtill.camelToSnake("cameltosnake"), is("CAMELTOSNAKE"));
    }

    @Test
    public void camelToSnakeは大文字スネークケースを返すcamelTo蛇の場合() {
        assertThat(StringUtill.camelToSnake("camelTo蛇"), is("CAMEL_TO蛇"));
    }
}
