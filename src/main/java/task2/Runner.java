package task2;

import org.xml.sax.SAXException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static String path = "src/main/java/task2/";


	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		// List of objects
		List<Record> list = new ArrayList<>();
		List<Record> newlist = new ArrayList<>();

		// find all *.xml files and add to list
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		// if no files found print message
		assert listOfFiles != null;
		if (listOfFiles.length == 0) {
			System.out.println("No files found");
			System.exit(-1);
		}

		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (file.getName().endsWith(".xml")) {
					list.addAll(Record.file2list(file.getAbsolutePath()));
				}
			}
		}

		// print sorted array
		list.stream().sorted(Record.fine_amountComparator).forEach(System.out::println);

		// sum all fines form same type and sort form max to min then write as json file
		List<String> types = Record.getTypes(list);
		List<Record> recordsByType;

		for (String type : types) {
			recordsByType = Record.getRecordsByType(list, type);
			double sum = 0;
			for (Record record : recordsByType) {
				sum += record.getFine_amount();
			}
			Record record = new Record(null, null, null, type, sum);
			newlist.add(record);
		}

		// write as json file using JsonWriter
		File jsonFile = new File(path+"report.json");
		FileWriter writer = new FileWriter(jsonFile);
		JsonWriter jsonWriter = Json.createWriter(writer);
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (Record record : newlist) {
			JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
			objectBuilder.add("type", record.getType());
			objectBuilder.add("fine_amount", record.getFine_amount());
			arrayBuilder.add(objectBuilder);
		}

		jsonWriter.writeArray(arrayBuilder.build());
		jsonWriter.close();
		writer.close();
	}
}