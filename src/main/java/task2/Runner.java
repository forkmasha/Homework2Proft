package task2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static String path = "src/main/java/task2/";

	public static List<Record> file2list(String filename) throws ParserConfigurationException, IOException, SAXException {
		// read xml file
		File file = new File(filename);

		// create document builder factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// create document builder
		DocumentBuilder builder = factory.newDocumentBuilder();

		// create document
		Document document = builder.parse(file);

		// get root element
		Element root = document.getDocumentElement();

		// get all child element
		NodeList nodes = root.getChildNodes();

		List<Record> list = new ArrayList<>();

		// get all child element from list
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			// check if node is element
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				// get all child element from element
				NodeList childNodes = element.getChildNodes();

				// create object
				Record record = new Record(null, null, null, null, 0);

				//insert data
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node childNode = childNodes.item(j);

					// check if child node is element
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;

						// get value from element
						String value = childElement.getTextContent();

						// insert data to object
						switch (childElement.getNodeName()) {
							case "date_time" -> record.setDateTime(value);
							case "first_name" -> record.setFirstName(value);
							case "last_name" -> record.setLastName(value);
							case "type" -> record.setType(value);
							case "fine_amount" -> record.setFineAmount(Double.parseDouble(value));
						}
					}
				}
				// add object to list
				list.add(record);
			}
		}
		return list;
	}


	public static void main(String[] args){

		// List of objects
		List<Record> List = new ArrayList<>();
		List<Record> NewList = new ArrayList<>();

		// try file exception
		try {
			// find all *.xml files and add to list
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();

			// if no files found print message
			assert listOfFiles != null;
			if (listOfFiles.length == 0) {
				System.out.println("No files found");
				throw new IOException();
				//System.exit(-1);
			}

			for (File file : listOfFiles) {
				if (file.isFile()) {
					if (file.getName().endsWith(".xml")) {
						List.addAll(file2list(file.getAbsolutePath()));
					}
				}
			}
		} catch (Exception e) {
			switch (e.getClass().getName()) {
				case "java.io.FileNotFoundException" -> System.out.println("File not found");
				case "java.io.IOException" -> System.out.println("IO error");
				case "org.xml.sax.SAXParseException" -> System.out.println("XML parse error");
				default -> System.out.println("Unknown error");
			}
			System.exit(-1);
		}

		// print sorted array
		//List.stream().sorted(Record.fine_amountComparator).forEach(System.out::println);

		// sum all fines form same type and sort form max to min then write as json file
		List<String> types = Record.getTypes(List);
		List<Record> recordsByType;

		for (String type : types) {
			recordsByType = Record.getRecordsByType(List, type);
			double sum = 0;
			for (Record record : recordsByType) {
				sum += record.getFineAmount();
			}
			Record record = new Record(null, null, null, type, sum);
			NewList.add(record);
		}

		try{
			// write as json file using JsonWriter
			File jsonFile = new File(path+"report.json");
			FileWriter writer = new FileWriter(jsonFile);
			JsonWriter jsonWriter = Json.createWriter(writer);
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

			for (Record record : NewList) {
				JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
				objectBuilder.add("type", record.getType());
				objectBuilder.add("fine_amount", record.getFineAmount());
				arrayBuilder.add(objectBuilder);
			}

			jsonWriter.writeArray(arrayBuilder.build());
			jsonWriter.close();
			writer.close();
		} catch (Exception e) {
			switch (e.getClass().getName()) {
				case "java.io.FileNotFoundException" -> System.out.println("File not found");
				case "java.io.IOException" -> System.out.println("IO error");
				default -> System.out.println("Unknown error");
			}
			System.exit(-1);
		}
	}
}