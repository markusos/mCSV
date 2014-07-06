import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CSVRecord implements Iterable<String>{

    private Map<String,Integer> headers;
    private final List<String> fields;

    public CSVRecord() {
        fields = new ArrayList<String>();
    }

    public CSVRecord(List<String> data) {
        fields = data;
    }

    public String get(String key) {
        if (headers != null && headers.containsKey(key)) {
            return fields.get(headers.get(key));
        }
        else {
            return null;
        }
    }

    public String get(int i) {
        return fields.get(i);
    }

    public boolean add(String value) {
        return fields.add(value);
    }

    void setHeaders(Map<String, Integer> headers) {
        this.headers = headers;
    }

    @Override
    public Iterator<String> iterator() {
        return fields.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVRecord strings = (CSVRecord) o;

        if (fields != null ? !fields.equals(strings.fields) : strings.fields != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = headers != null ? headers.hashCode() : 0;
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).contains("\"") || fields.get(i).contains("\n") || fields.get(i).contains(",")) {
                stringBuilder.append("\"").append(fields.get(i).replaceAll("\"","\"\"" )).append("\"");
            }
            else {
                stringBuilder.append(fields.get(i));
            }

            if (i != fields.size() -1) {
                stringBuilder.append(",");
            }
        }

        return stringBuilder.toString();
    }

    public String toDebugString() {
        return "CSVRecord{" +
                "fields=" + fields +
                '}';
    }
}
