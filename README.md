mCSV
=======

mCSV is a simple CSV parser implemented in Java. mCSV parses the CSV format described in [RFC4180](http://tools.ietf.org/html/rfc4180)

Example
----

```
CSVParser csvParser = new CSVParser();
csvParser.parse(new File("input.csv"));
CSVData data = csvParser.getData();

// Get the value of the column with the header 'name' from row 5 in the file input.csv'
String value = data.get(5).get("name");

```
