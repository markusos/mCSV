import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CSVParserTest {

    CSVParser csvParser;

    @org.junit.Test
    public void testBasicParse() throws Exception {
        assertNotNull("Test file missing", getClass().getResource("/testBasic.csv"));
        csvParser = new CSVParser();
        csvParser.parse(new File(getClass().getResource("/testBasic.csv").getFile()));

        CSVRecord expected;
        CSVRecord actual;

        expected = new CSVRecord(Arrays.asList("", "", "", "", ""));
        actual = csvParser.getData().get(0);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a", "b", "c", "d", "e"));
        actual = csvParser.getData().get(1);
        assertEquals(expected, actual);

        actual = csvParser.getData().get(2);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a", " b", " c", " d", " e"));
        actual = csvParser.getData().get(3);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a ", " b ", " c ", " d ", " e"));
        actual = csvParser.getData().get(4);
        assertEquals(expected, actual);

        actual = csvParser.getData().get(5);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a,a", "b,b", "c,c", "d,d", "e,e"));
        actual = csvParser.getData().get(6);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a,\na", "b,\nb", "c,\nc", "d,\nd", "e,\ne"));
        actual = csvParser.getData().get(7);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a\"a", "b\"b", "c\"c", "d\"d", "e\"e"));
        actual = csvParser.getData().get(8);
        assertEquals(expected, actual);

        assertNotNull("Test file missing", getClass().getResource("/testWikipediaExample.csv"));
        csvParser.parse(new File(getClass().getResource("/testWikipediaExample.csv").getFile()));

        expected = new CSVRecord(Arrays.asList("Year", "Make", "Model", "Description", "Price"));
        actual = csvParser.getData().get(0);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1997", "Ford", "E350", "ac, abs, moon", "3000.00"));
        actual = csvParser.getData().get(1);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1999", "Chevy", "Venture \"Extended Edition\"", "", "4900.00"));
        actual = csvParser.getData().get(2);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1999", "Chevy", "Venture \"Extended Edition, Very Large\"", "", "5000.00"));
        actual = csvParser.getData().get(3);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1996", "Jeep", "Grand Cherokee", "MUST SELL!\nair, moon roof, loaded", "4799.00"));
        actual = csvParser.getData().get(4);
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testGet() throws Exception {
        assertNotNull("Test file missing", getClass().getResource("/testWikipediaExample.csv"));
        csvParser = new CSVParser();
        csvParser.parse(new File(getClass().getResource("/testWikipediaExample.csv").getFile()));

        CSVRecord expected;
        CSVRecord actual;

        expected = new CSVRecord(Arrays.asList("1997", "Ford", "E350", "ac, abs, moon", "3000.00"));
        actual = csvParser.getData().get(1);
        assertEquals(expected.get(0), actual.get("Year"));
        assertEquals(expected.get(1), actual.get("Make"));
        assertEquals(expected.get(2), actual.get("Model"));
        assertEquals(expected.get(3), actual.get("Description"));
        assertEquals(expected.get(4), actual.get("Price"));
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
        assertEquals(expected.get(4), actual.get(4));

        expected = new CSVRecord(Arrays.asList("1999", "Chevy", "Venture \"Extended Edition\"", "", "4900.00"));
        actual = csvParser.getData().get(2);
        assertEquals(expected.get(0), actual.get("Year"));
        assertEquals(expected.get(1), actual.get("Make"));
        assertEquals(expected.get(2), actual.get("Model"));
        assertEquals(expected.get(3), actual.get("Description"));
        assertEquals(expected.get(4), actual.get("Price"));
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
        assertEquals(expected.get(4), actual.get(4));

        expected = new CSVRecord(Arrays.asList("1999", "Chevy", "Venture \"Extended Edition\"", "", "4900.00"));
        actual = csvParser.getData().get(2);
        assertEquals(expected.get(0), actual.get("Year"));
        assertEquals(expected.get(1), actual.get("Make"));
        assertEquals(expected.get(2), actual.get("Model"));
        assertEquals(expected.get(3), actual.get("Description"));
        assertEquals(expected.get(4), actual.get("Price"));
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
        assertEquals(expected.get(4), actual.get(4));

        expected = new CSVRecord(Arrays.asList("1999", "Chevy", "Venture \"Extended Edition, Very Large\"", "", "5000.00"));
        actual = csvParser.getData().get(3);
        assertEquals(expected.get(0), actual.get("Year"));
        assertEquals(expected.get(1), actual.get("Make"));
        assertEquals(expected.get(2), actual.get("Model"));
        assertEquals(expected.get(3), actual.get("Description"));
        assertEquals(expected.get(4), actual.get("Price"));
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
        assertEquals(expected.get(4), actual.get(4));

        expected = new CSVRecord(Arrays.asList("1996", "Jeep", "Grand Cherokee", "MUST SELL!\nair, moon roof, loaded", "4799.00"));
        actual = csvParser.getData().get(4);
        assertEquals(expected.get(0), actual.get("Year"));
        assertEquals(expected.get(1), actual.get("Make"));
        assertEquals(expected.get(2), actual.get("Model"));
        assertEquals(expected.get(3), actual.get("Description"));
        assertEquals(expected.get(4), actual.get("Price"));
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
        assertEquals(expected.get(4), actual.get(4));
    }
}