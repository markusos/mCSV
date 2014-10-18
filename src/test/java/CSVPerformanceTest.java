import java.io.File;

import static org.junit.Assert.assertEquals;

public class CSVPerformanceTest {

    CSVParser csvParser;

    @org.junit.Test
    public void testPerformance() throws Exception {
        File testFile = new File("output/testOutput.csv");
        csvParser = new CSVParser(testFile);

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

        long start = System.nanoTime();

        CSVField field = new CSVField();

        // Parse the generated file and save the last parsed field to validate result
        while (true) {
            CSVField tmpField = csvParser.parseNextField();
            if (tmpField != null) {
                field = tmpField;
            } else {
                break;
            }
        }

        long elapsedNanos = System.nanoTime() - start;

        System.out.println("Time elapsed: " + elapsedNanos + " ns");
        System.out.println("Data parsed");

        assertEquals(nrOfRecords + "abcdefghijklmnopqrstuvxyz" + nrOfFields, field.toString());
    }
}
