import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class CSVExporter {

    public void export(File file, CSVData data) throws IllegalFormatException, IOException {

        OutputStream out = new FileOutputStream(file);
        Writer writer = new BufferedWriter(new OutputStreamWriter(out, Charset.defaultCharset()));

        for (int i = 0; i < data.size(); i++) {
            writer.write(data.get(i).toString());

            if (i != data.size()-1) {
                writer.write("\n");
            }
        }
        writer.flush();
        writer.close();
    }
}
