mCSV
=======

mCSV is a simple CSV parser implemented in Java. mCSV parses the CSV format described in [RFC4180](http://tools.ietf.org/html/rfc4180)

Example
----

Get the value of the column with the header 'name' from row 5 in the file input.csv'

```
CSVParser csvParser = new CSVParser(new File("input.csv"));
CSVData data = csvParser.parse();
String value = data.get(5).get("name");

```

The parse() method reads the whole input csv file to memory and stores it in a CSVData object

To stream the file, instead of reading the whole file, it is possible to read one CSV field or record (row) at a time.
To do this use the parseNextField() or parseNextRecord() methods. E.g To get the second field (column) in the first record (row) of a CSV file:

```
CSVParser csvParser = new CSVParser(new File("input.csv"));
CSVRecord record = csvParser.parseNextRecord();
String value = record.get(2);

```

