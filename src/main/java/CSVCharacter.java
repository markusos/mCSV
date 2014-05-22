class CSVCharacter {
    private enum State {CHAR, EOF}

    private final State state;

    private static final int EOF = -1;
    private static final char NEW_LINE = '\n';
    private static final char COMMA = ',';
    private static final char DQUOTE = '\"';

    private final char c;

    CSVCharacter(int c) {
        if (c == EOF) {
            state = State.EOF;
        }
        else {
            state = State.CHAR;
        }

        this.c = (char) c;
    }

    public boolean isEOF() {
        return state.equals(State.EOF);
    }

    public boolean isNewLine() {
        return c == NEW_LINE;
    }

    public boolean isComma() {
        return c == COMMA;
    }

    public boolean isQuote() {
        return c == DQUOTE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVCharacter character = (CSVCharacter) o;

        if (c != character.c) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return c;
    }

    @Override
    public String toString() {
        return String.valueOf(c);
    }
}