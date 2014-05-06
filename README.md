mCSV
=======

mCSV is a simple CSV parser implemented in Java. mCSV parses the CSV format described in [RFC4180](http://tools.ietf.org/html/rfc4180)

Example
----

```

CSVParser csvParser = new CSVParser();
csvParser.parse(new File("input.csv"));
List<List<String>> data = csvParser.getData();

```
