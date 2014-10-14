
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class CSVParser {

    private enum State {PRE, FIELD, ESCAPED_FIELD, POST}

    private State currentState;
    private CSVField field;
    private CSVData data;
    private CSVRecord record;

    public CSVData parse(File file) throws IOException, IllegalFormatException {

        this.data = new CSVData();

        this.currentState = State.PRE;
        record = new CSVRecord();
        field = new CSVField();

        InputStream in = new FileInputStream(file);
        Reader reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));

        boolean notDone;
        do {
            int next = reader.read();
            notDone = parseNextCharacter(next);
        } while (notDone);
        reader.close();

        return data;
    }

    private boolean parseNextCharacter(int d) throws IllegalFormatException {
        CSVCharacter nextChar = new CSVCharacter(d);

        if (nextChar.isEOF()) {
            addFieldToRow();
            addRowToData();
            data.setHeaders();
            return false;
        } else {
            nextState(nextChar);
            return true;
        }
    }

    private void nextState(CSVCharacter nextChar) throws IllegalFormatException {
        switch (currentState) {
            case PRE:
                statePre(nextChar);
                break;

            case POST:
                statePost(nextChar);
                break;

            case ESCAPED_FIELD:
                stateString(nextChar);
                break;

            case FIELD:
                stateField(nextChar);
                break;

            default:
                throw new RuntimeException("Unknown internal state");
        }
    }

    private void statePre(CSVCharacter nextChar) {
        if (nextChar.isNewLine()) {
            addFieldToRow();
            addRowToData();
            currentState = State.PRE;
        } else if (nextChar.isComma()) {
            addFieldToRow();
        } else if (nextChar.isQuote()) {
            currentState = State.ESCAPED_FIELD;
        } else {
            field.append(nextChar);
            currentState = State.FIELD;
        }
    }

    private void statePost(CSVCharacter nextChar) throws IllegalFormatException {
        if (nextChar.isNewLine()) {
            addFieldToRow();
            addRowToData();
            currentState = State.PRE;
        } else if (nextChar.isComma()) {
            addFieldToRow();
            currentState = State.PRE;
        } else if (nextChar.isQuote()) {
            field.append(nextChar);
            currentState = State.ESCAPED_FIELD;
        } else {
            throw new IllegalFormatException(String.format("Expected char ',' or '\"', but found '%s'.", nextChar));
        }
    }

    private void stateString(CSVCharacter nextChar) {
        if (nextChar.isQuote()) {
            currentState = State.POST;
        } else {
            field.append(nextChar);
        }
    }

    private void stateField(CSVCharacter nextChar) throws IllegalFormatException {
        if (nextChar.isNewLine()) {
            addFieldToRow();
            addRowToData();
            currentState = State.PRE;
        } else if (nextChar.isComma()) {
            addFieldToRow();
            currentState = State.PRE;
        } else if (nextChar.isQuote()) {
            throw new IllegalFormatException(String.format("Expected char but found '%s'.", nextChar));
        } else {
            field.append(nextChar);
        }
    }

    private void addFieldToRow() {
        record.add(field.toString());
        field = new CSVField();
    }

    private void addRowToData() {
        data.add(record);
        record = new CSVRecord();
    }
}
