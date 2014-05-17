import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CSVParserTest {

    CSVParser csvParser;

    @org.junit.Test
    public void testBasicGetData() throws Exception {
        assertNotNull("Test file missing", getClass().getResource("/testBasic.csv"));
        csvParser = new CSVParser();
        csvParser.parse(new File(getClass().getResource("/testBasic.csv").getFile()));

        List<String> expected;
        List<String> actual;

        expected = Arrays.asList("", "", "", "", "");
        actual = csvParser.getData().get(0);
        assertEquals(expected, actual);

        expected = Arrays.asList("a", "b", "c", "d", "e");
        actual = csvParser.getData().get(1);
        assertEquals(expected, actual);

        actual = csvParser.getData().get(2);
        assertEquals(expected, actual);

        expected = Arrays.asList("a", " b", " c", " d", " e");
        actual = csvParser.getData().get(3);
        assertEquals(expected, actual);

        expected = Arrays.asList("a ", " b ", " c ", " d ", " e");
        actual = csvParser.getData().get(4);
        assertEquals(expected, actual);

        actual = csvParser.getData().get(5);
        assertEquals(expected, actual);

        expected = Arrays.asList("a,a", "b,b", "c,c", "d,d", "e,e");
        actual = csvParser.getData().get(6);
        assertEquals(expected, actual);

        expected = Arrays.asList("a,\na", "b,\nb", "c,\nc", "d,\nd", "e,\ne");
        actual = csvParser.getData().get(7);
        assertEquals(expected, actual);

        expected = Arrays.asList("a\"a", "b\"b", "c\"c", "d\"d", "e\"e");
        actual = csvParser.getData().get(8);
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testWikipediaExample() throws Exception {
        assertNotNull("Test file missing", getClass().getResource("/testWikipediaExample.csv"));
        csvParser = new CSVParser();
        csvParser.parse(new File(getClass().getResource("/testWikipediaExample.csv").getFile()));

        List<String> expected;
        List<String> actual;

        expected = Arrays.asList("Year", "Make", "Model", "Description", "Price");
        actual = csvParser.getData().get(0);
        assertEquals(expected, actual);

        expected = Arrays.asList("1997", "Ford", "E350", "ac, abs, moon", "3000.00");
        actual = csvParser.getData().get(1);
        assertEquals(expected, actual);

        expected = Arrays.asList("1999", "Chevy", "Venture \"Extended Edition\"", "", "4900.00");
        actual = csvParser.getData().get(2);
        assertEquals(expected, actual);

        expected = Arrays.asList("1999", "Chevy", "Venture \"Extended Edition, Very Large\"", "", "5000.00");
        actual = csvParser.getData().get(3);
        assertEquals(expected, actual);

        expected = Arrays.asList("1996", "Jeep", "Grand Cherokee", "MUST SELL!\nair, moon roof, loaded", "4799.00");
        actual = csvParser.getData().get(4);
        assertEquals(expected, actual);
    }
}