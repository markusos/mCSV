import java.io.File;

import static org.junit.Assert.assertEquals;

public class CSVExporterTest {

    @org.junit.Test
    public void testBasicExport() throws Exception {

        File outFile = new File("output/testOutput.csv");
        CSVData expectedData = new CSVParser(new File(getClass().getResource("/testBasic.csv").getFile())).parse();

        CSVExporter exporter = new CSVExporter();
        exporter.export(outFile, expectedData);

        CSVData parsedData = new CSVParser(outFile).parse();

        assertEquals(expectedData.get(0), parsedData.get(0));
        assertEquals(expectedData.get(1), parsedData.get(1));
        assertEquals(expectedData.get(2), parsedData.get(2));
        assertEquals(expectedData.get(3), parsedData.get(3));
        assertEquals(expectedData.get(4), parsedData.get(4));
        assertEquals(expectedData.get(5), parsedData.get(5));
        assertEquals(expectedData.get(6), parsedData.get(6));
        assertEquals(expectedData.get(7), parsedData.get(7));
        assertEquals(expectedData.get(8), parsedData.get(8));

        assertEquals(expectedData, parsedData);
    }
}