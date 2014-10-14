class CSVField  {
    private StringBuilder field = new StringBuilder(32);

    public void append(CSVCharacter c) {
        field.append(c.getChar());
    }

    @Override
    public String toString() {
        return field.toString();
    }
}
