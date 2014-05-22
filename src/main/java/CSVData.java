import java.util.*;

public class CSVData implements Iterable<CSVRecord> {

    private final List<CSVRecord> data;

    public CSVData() {
        data = new LinkedList<CSVRecord>();
    }

    @Override
    public Iterator<CSVRecord> iterator() {
        return data.iterator();
    }

    public boolean add(CSVRecord r) {
        return data.add(r);
    }

    public CSVRecord get(int i) {
        return data.get(i);
    }

    void setHeaders() {
        Map<String, Integer> headers = new HashMap<String, Integer>();

        int i = 0;
        for(String value : data.get(0)) {
            headers.put(value, i);
            i++;
        }

        for(CSVRecord record : data) {
            record.setHeaders(headers);
        }
    }

    @Override
    public String toString() {
        return "CSVData{" +
                "data=" + data +
                '}';
    }
}
