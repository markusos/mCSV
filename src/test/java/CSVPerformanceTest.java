import java.io.File;

import static org.junit.Assert.assertEquals;

public class CSVPerformanceTest {

    CSVParser csvParser;
    CSVData data;

    @org.junit.Test
    public void testPerformance() throws Exception {
        csvParser = new CSVParser();
        File testFile = new File("output/testOutput.csv");

        // Create test data
        CSVData testData = new CSVData();
        int nrOfRecords = 10000;
        int nrOfFields = 100;

        for (int i = 0; i <= nrOfRecords; i++) {
            CSVRecord record = new CSVRecord();
            for (int j = 0; j <= nrOfFields; j++) {
                record.add(i + "abcdefghijklmnopqrstuvxyz" + j);
            }
            testData.add(record);
        }

        System.out.println("Data constructed");

        CSVExporter exporter = new CSVExporter();
        exporter.export(testFile, testData);

        System.out.println("Data exported");

        data = csvParser.parse(testFile);
        System.out.println("Data parsed");
        //assertEquals(testData, data);
        System.out.println();
        assertEquals(data.get(nrOfRecords).get(nrOfFields), nrOfRecords + "abcdefghijklmnopqrstuvxyz" + nrOfFields);
    }
}
