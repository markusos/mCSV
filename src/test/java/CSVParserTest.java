import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CSVParserTest {

    CSVParser parser;

    @org.junit.Test
    public void testBasicGetData() throws Exception {
        assertNotNull("Test file missing", getClass().getResource("/testBasic.csv"));
        parser = new CSVParser();
        parser.parse(new File(getClass().getResource("/testBasic.csv").getFile()));

        List<String> expected;
        List<String> actual;

        expected = Arrays.asList("","","","","");
        actual = parser.getData().get(0);
        assertEquals(expected, actual);

        expected = Arrays.asList("a","b","c","d","e");
        actual = parser.getData().get(1);
        assertEquals(expected, actual);

        actual = parser.getData().get(2);
        assertEquals(expected, actual);

        expected = Arrays.asList("a"," b"," c"," d"," e");
        actual = parser.getData().get(3);
        assertEquals(expected, actual);

        expected = Arrays.asList("a "," b "," c "," d "," e");
        actual = parser.getData().get(4);
        assertEquals(expected, actual);

        actual = parser.getData().get(5);
        assertEquals(expected, actual);

        expected = Arrays.asList("a,a","b,b","c,c","d,d","e,e");
        actual = parser.getData().get(6);
        assertEquals(expected, actual);

        actual = parser.getData().get(7);
        assertEquals(expected, actual);

        expected = Arrays.asList("a\"a","b\"b","c\"c","d\"d","e\"e");
        actual = parser.getData().get(8);
        assertEquals(expected, actual);
    }
}