import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CSVData implements Iterable<CSVRecord> {

    private final List<CSVRecord> data;

    public CSVData() {
        data = new ArrayList<CSVRecord>();
    }

    @Override
    public Iterator<CSVRecord> iterator() {
        return data.iterator();
    }

    public int size() {
        return data.size();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVData that = (CSVData) o;

        if (!data.equals(that.data)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return toDebugString();
    }

    public String toDebugString() {
        return "CSVData{" +
                "data=" + data +
                '}';
    }
}
