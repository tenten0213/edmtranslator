package edmtranslate;


import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author tenten0213
 */
public class StringUtillTest {

    @Test
    public void camelToSnakeはスネークケースを返すCamelToSnakeの場合() {
        assertThat(StringUtill.camelToSnake("CamelToSnake"), is("CAMEL_TO_SNAKE"));
    }

    @Test
    public void camelToSnakeはスネークケースを返すcameltosnakeの場合() {
        assertThat(StringUtill.camelToSnake("cameltosnake"), is("CAMELTOSNAKE"));
    }

    @Test
    public void camelToSnakeはスネークケースを返すcamelTo蛇の場合() {
        assertThat(StringUtill.camelToSnake("camelTo蛇"), is("CAMEL_TO蛇"));
    }
}
