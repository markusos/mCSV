
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class CSVParser {

    private enum State {PRE_FIELD, PRE_RECORD, FIELD, ESCAPED_FIELD, POST, EOF}

    private State currentState;
    private CSVField field;
    private CSVData data;
    private CSVRecord record;

    private Reader reader;

    public CSVParser(File file) throws IOException, IllegalFormatException {
        InputStream in = new FileInputStream(file);

        this.reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
        this.currentState = State.PRE_RECORD;
        this.data = new CSVData();
        this.record = new CSVRecord();
        this.field = new CSVField();
    }

    public CSVData parse() throws IOException, IllegalFormatException {
        if (currentState.equals(State.EOF)) {
            return null;
        }

        this.data = new CSVData();
        while (true) {
            parseNextRecord();
            addRecordToData();
            if (currentState.equals(State.EOF)) {
                data.setHeaders();
                return data;
            }
        }
    }

    public CSVRecord parseNextRecord() throws IOException {
        if (currentState.equals(State.EOF)) {
            return null;
        }

        record = new CSVRecord();
        while (true) {
            parseNextField();
            addFieldToRecord();
            if (currentState.equals(State.PRE_RECORD) ||
                    currentState.equals(State.EOF)) {
                return record;
            }
        }
    }

    public CSVField parseNextField() throws IOException {
        if (currentState.equals(State.EOF)) {
            return null;
        }

        field = new CSVField();
        while (true) {
            parseNextCharacter();
            if (currentState.equals(State.PRE_RECORD) ||
                    currentState.equals(State.PRE_FIELD) ||
                    currentState.equals(State.EOF)) {
                return field;
            }
        }
    }

    private void parseNextCharacter() throws IOException, IllegalFormatException {
        int next = reader.read();
        CSVCharacter nextChar = new CSVCharacter(next);
        nextState(nextChar);
    }

    private void nextState(CSVCharacter nextChar) {
        if (nextChar.isEOF()) {
            currentState = State.EOF;
            return;
        }

        switch (currentState) {
            case PRE_FIELD:
            case PRE_RECORD:
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
            currentState = State.PRE_RECORD;
        } else if (nextChar.isComma()) {
            currentState = State.PRE_FIELD;
        } else if (nextChar.isQuote()) {
            currentState = State.ESCAPED_FIELD;
        } else {
            field.append(nextChar);
            currentState = State.FIELD;
        }
    }

    private void statePost(CSVCharacter nextChar) throws IllegalFormatException {
        if (nextChar.isNewLine()) {
            currentState = State.PRE_RECORD;
        } else if (nextChar.isComma()) {
            currentState = State.PRE_FIELD;
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
            currentState = State.PRE_RECORD;
        } else if (nextChar.isComma()) {
            currentState = State.PRE_FIELD;
        } else if (nextChar.isQuote()) {
            throw new IllegalFormatException(String.format("Expected char but found '%s'.", nextChar));
        } else {
            field.append(nextChar);
        }
    }

    private void addFieldToRecord() {
        record.add(field.toString());
        field = new CSVField();
    }

    private void addRecordToData() {
        data.add(record);
        record = new CSVRecord();
    }
}
