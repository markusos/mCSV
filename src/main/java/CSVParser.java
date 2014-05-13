import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private boolean debug = false;

    private enum State {PRE, TOKEN, STRING, POST}

    private State currentState;
    private StringBuilder token;
    private List<List<String>> data;
    private List<String> row;

    private static final int EOF = -1;
    private static final char NEW_LINE = '\n';
    private static final char COMMA = ',';
    private static final char DQUOTE = '\"';

    public CSVParser() throws IllegalFormatException, IOException {
        this.data = new ArrayList<List<String>>();
        ;
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
        while (parse(reader.read())) ;
    }

    private boolean parse(int d) throws IllegalFormatException {
        if (d == EOF) {
            addTokenToRow();
            addRowToData();
            debug("---EOF---");
            debug(data.toString());
            return false;
        }

        char nextChar = (char) d;
        if (nextChar == NEW_LINE) {
            if (currentState.equals(State.STRING)) {
                token.append('\n');
            }
            else {
                addTokenToRow();
                addRowToData();
                currentState = State.PRE;
            }
            debug("---EOL---");
        } else {
            nextState(nextChar);
        }
        return true;
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

    private void statePre(char nextChar) {
        if (nextChar == COMMA) {
            addTokenToRow();
        } else if (nextChar == DQUOTE) {
            currentState = State.STRING;
        } else {
            token.append(nextChar);
            currentState = State.TOKEN;
        }
    }

    private void statePost(char nextChar) throws IllegalFormatException {
        if (nextChar == DQUOTE) {
            token.append(DQUOTE);
            currentState = State.STRING;
        } else if (nextChar == COMMA) {
            addTokenToRow();
            currentState = State.PRE;
        } else {
            throw new IllegalFormatException(String.format("Expected char ',' or '\"', but found '%s'.", nextChar));
        }
    }

    private void stateString(char nextChar) {
        if (nextChar == DQUOTE) {
            currentState = State.POST;
        } else {
            token.append(nextChar);
        }
    }

    private void stateToken(char nextChar) throws IllegalFormatException {
        if (nextChar == DQUOTE) {
            throw new IllegalFormatException(String.format("Expected char but found '%s'.", nextChar));
        } else if (nextChar == COMMA) {
            addTokenToRow();
            currentState = State.PRE;
        } else {
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
