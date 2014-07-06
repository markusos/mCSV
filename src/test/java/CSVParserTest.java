import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CSVParserTest {

    CSVParser csvParser;
    CSVData data;

    @org.junit.Test
    public void testBasicParse() throws Exception {
        assertNotNull("Test file missing", getClass().getResource("/testBasic.csv"));
        csvParser = new CSVParser();
        data = csvParser.parse(new File(getClass().getResource("/testBasic.csv").getFile()));

        CSVRecord expected;
        CSVRecord actual;

        expected = new CSVRecord(Arrays.asList("", "", "", "", ""));
        actual = data.get(0);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a", "b", "c", "d", "e"));
        actual = data.get(1);
        assertEquals(expected, actual);

        actual = data.get(2);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a", " b", " c", " d", " e"));
        actual = data.get(3);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a ", " b ", " c ", " d ", " e"));
        actual = data.get(4);
        assertEquals(expected, actual);

        actual = data.get(5);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a,a", "b,b", "c,c", "d,d", "e,e"));
        actual = data.get(6);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a,\na", "b,\nb", "c,\nc", "d,\nd", "e,\ne"));
        actual = data.get(7);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("a\"a", "b\"b", "c\"c", "d\"d", "e\"e"));
        actual = data.get(8);
        assertEquals(expected, actual);

        assertNotNull("Test file missing", getClass().getResource("/testWikipediaExample.csv"));
        data = csvParser.parse(new File(getClass().getResource("/testWikipediaExample.csv").getFile()));

        expected = new CSVRecord(Arrays.asList("Year", "Make", "Model", "Description", "Price"));
        actual = data.get(0);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1997", "Ford", "E350", "ac, abs, moon", "3000.00"));
        actual = data.get(1);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1999", "Chevy", "Venture \"Extended Edition\"", "", "4900.00"));
        actual = data.get(2);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1999", "Chevy", "Venture \"Extended Edition, Very Large\"", "", "5000.00"));
        actual = data.get(3);
        assertEquals(expected, actual);

        expected = new CSVRecord(Arrays.asList("1996", "Jeep", "Grand Cherokee", "MUST SELL!\nair, moon roof, loaded", "4799.00"));
        actual = data.get(4);
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testGet() throws Exception {
        assertNotNull("Test file missing", getClass().getResource("/testWikipediaExample.csv"));
        csvParser = new CSVParser();
        data = csvParser.parse(new File(getClass().getResource("/testWikipediaExample.csv").getFile()));

        CSVRecord expected;
        CSVRecord actual;

        expected = new CSVRecord(Arrays.asList("1997", "Ford", "E350", "ac, abs, moon", "3000.00"));
        actual = data.get(1);
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
        actual = data.get(2);
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
        actual = data.get(2);
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
        actual = data.get(3);
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
        actual = data.get(4);
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