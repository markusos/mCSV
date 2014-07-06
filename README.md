mCSV
=======

mCSV is a simple CSV parser implemented in Java. mCSV parses the CSV format described in [RFC4180](http://tools.ietf.org/html/rfc4180)

Example
----

Get the value of the column with the header 'name' from row 5 in the file input.csv'

```
CSVParser csvParser = new CSVParser();
CSVData data = csvParser.parse(new File("input.csv"));
String value = data.get(5).get("name");

```
