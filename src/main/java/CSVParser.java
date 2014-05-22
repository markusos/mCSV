import java.io.*;
import java.nio.charset.Charset;

public class CSVParser {

    private static final boolean debug = false;

    private enum State {PRE, TOKEN, STRING, POST}

    private State currentState;
    private StringBuilder token;
    private CSVData data;
    private CSVRecord record;


    public CSVParser() throws IllegalFormatException, IOException {
        this.data = new CSVData();
    }

    public CSVData getData() {
        return data;
    }

    public void parse(File file) throws IOException, IllegalFormatException {

        this.data = new CSVData();

        this.currentState = State.PRE;
        record = new CSVRecord();
        token = new StringBuilder();

        InputStream in = new FileInputStream(file);
        Reader reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
        while (parseNextCharacter(reader.read())) ;
    }

    private boolean parseNextCharacter(int d) throws IllegalFormatException {
        CSVCharacter nextChar = new CSVCharacter(d);

        if (nextChar.isEOF()) {
            addTokenToRow();
            addRowToData();
            data.setHeaders();
            debug("---EOF---");
            debug(data.toString());
            return false;
        } else {
            nextState(nextChar);
            return true;
        }
    }

    private void nextState(CSVCharacter nextChar) throws IllegalFormatException {
        switch (currentState) {
            case PRE:
                debug("PRE: " + nextChar);
                statePre(nextChar);
                break;

            case POST:
                debug("POST: " + nextChar);
                statePost(nextChar);
                break;

            case STRING:
                debug("STRING: " + nextChar);
                stateString(nextChar);
                break;

            case TOKEN:
                debug("TOKEN: " + nextChar);
                stateToken(nextChar);
                break;

            default:
                throw new RuntimeException("Unknown internal state");
        }
    }

    private void statePre(CSVCharacter nextChar) {
        if (nextChar.isNewLine()) {
            addTokenToRow();
            addRowToData();
            currentState = State.PRE;
            debug("---EOL---");
        } else if (nextChar.isComma()) {
            addTokenToRow();
        } else if (nextChar.isQuote()) {
            currentState = State.STRING;
        } else {
            token.append(nextChar);
            currentState = State.TOKEN;
        }
    }

    private void statePost(CSVCharacter nextChar) throws IllegalFormatException {
        if (nextChar.isNewLine()) {
            addTokenToRow();
            addRowToData();
            currentState = State.PRE;
            debug("---EOL---");
        } else if (nextChar.isComma()) {
            addTokenToRow();
            currentState = State.PRE;
        } else if (nextChar.isQuote()) {
            token.append(nextChar);
            currentState = State.STRING;
        }  else {
            throw new IllegalFormatException(String.format("Expected char ',' or '\"', but found '%s'.", nextChar));
        }
    }

    private void stateString(CSVCharacter nextChar) {
        if (nextChar.isQuote()) {
            currentState = State.POST;
        } else {
            token.append(nextChar);
        }
    }

    private void stateToken(CSVCharacter nextChar) throws IllegalFormatException {
        if (nextChar.isNewLine()) {
            addTokenToRow();
            addRowToData();
            currentState = State.PRE;
            debug("---EOL---");
        } else if (nextChar.isComma()) {
            addTokenToRow();
            currentState = State.PRE;
        } else if (nextChar.isQuote()) {
            throw new IllegalFormatException(String.format("Expected char but found '%s'.", nextChar));
        }  else {
            token.append(nextChar);
        }
    }

    private void addTokenToRow() {
        debug(String.format("Token added: '%s'", token.toString()));
        record.add(token.toString());
        token = new StringBuilder();
    }

    private void addRowToData() {
        debug(String.format("Row added: '%s'", record.toString()));
        data.add(record);
        record = new CSVRecord();
    }

    private void debug(String s) {
        if (debug) {
            System.out.println(s);
        }
    }
}
