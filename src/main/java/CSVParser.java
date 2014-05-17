import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private static final boolean debug = false;

    private enum State {PRE, TOKEN, STRING, POST}

    private State currentState;
    private StringBuilder token;
    private List<List<String>> data;
    private List<String> row;


    public CSVParser() throws IllegalFormatException, IOException {
        this.data = new ArrayList<List<String>>();
    }

    public List<List<String>> getData() {
        return data;
    }

    public void parse(File file) throws IOException, IllegalFormatException {

        this.currentState = State.PRE;
        row = new ArrayList<String>();
        token = new StringBuilder();

        InputStream in = new FileInputStream(file);
        Reader reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
        while (parseNextCharacter(reader.read())) ;
    }

    private boolean parseNextCharacter(int d) throws IllegalFormatException {
        Character nextChar = new Character(d);

        if (nextChar.isEOF()) {
            addTokenToRow();
            addRowToData();
            debug("---EOF---");
            debug(data.toString());
            return false;
        } else {
            nextState(nextChar);
            return true;
        }
    }

    private void nextState(Character nextChar) throws IllegalFormatException {
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

    private void statePre(Character nextChar) {
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

    private void statePost(Character nextChar) throws IllegalFormatException {
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

    private void stateString(Character nextChar) {
        if (nextChar.isQuote()) {
            currentState = State.POST;
        } else {
            token.append(nextChar);
        }
    }

    private void stateToken(Character nextChar) throws IllegalFormatException {
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
        row.add(token.toString());
        token = new StringBuilder();
    }

    private void addRowToData() {
        debug(String.format("Row added: '%s'", row.toString()));
        data.add(row);
        row = new ArrayList<String>();
    }

    private void debug(String s) {
        if (debug) {
            System.out.println(s);
        }
    }
}
