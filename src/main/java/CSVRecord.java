import java.util.*;

public class CSVRecord implements Iterable<String>{

    private Map<String,Integer> headers;
    private final List<String> values;

    public CSVRecord() {
        values = new ArrayList<String>();
    }

    public CSVRecord(List<String> data) {
        values = data;
    }

    public String get(String key) {
        if (headers != null && headers.containsKey(key)) {
            return values.get(headers.get(key));
        }
        else {
            return null;
        }
    }

    public String get(int i) {
        return values.get(i);
    }

    public boolean add(String value) {
        return values.add(value);
    }

    void setHeaders(Map<String, Integer> headers) {
        this.headers = headers;
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVRecord strings = (CSVRecord) o;

        if (values != null ? !values.equals(strings.values) : strings.values != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = headers != null ? headers.hashCode() : 0;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CSVRecord{" +
                "values=" + values +
                '}';
    }
}
