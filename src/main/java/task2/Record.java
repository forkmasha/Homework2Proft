package task2;


// read xml file as objects
// "date_time: "2020-05-05 15:39:03", //
//"first_name": "Ivan",
//"last_name": "Ivanov"
//"type": "SPEEDING", //
//"fine_amount": 340.00 // cyMa urpady

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// create object with parameter:
// date_time, first_name, last_name, type, fine_amount

public class Record {
    private String date_time;
    private String first_name;
    private String last_name;
    private String type;
    private double fine_amount;

    public Record(String date_time, String first_name, String last_name, String type, double fine_amount) {
        this.date_time = date_time;
        this.first_name = first_name;
        this.last_name = last_name;
        this.type = type;
        this.fine_amount = fine_amount;
    }

//	public String getDate_time() {
//		return date_time;
//	}

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

//	public String getFirst_name() {
//		return first_name;
//	}

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

//	public String getLast_name() {
//		return last_name;
//	}

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFine_amount() {
        return fine_amount;
    }

    public void setFine_amount(double fine_amount) {
        this.fine_amount = fine_amount;
    }

    @Override
    public String toString() {
        return "Record [date_time=" + date_time + ", first_name=" + first_name + ", last_name=" + last_name + ", type="
                + type + ", fine_amount=" + fine_amount + "]";
    }


    // get all types of records
    public static List<String> getTypes(List<Record> records) {
        List<String> types = new ArrayList<>();
        for (Record record : records) {
            if (!types.contains(record.getType())) {
                types.add(record.getType());
            }
        }
        return types;
    }

    // divide records by types

    public static List<Record> getRecordsByType(List<Record> records, String type) {
        List<Record> recordsByType = new ArrayList<>();
        for (Record record : records) {
            if (Objects.equals(record.getType(), type)) {
                recordsByType.add(record);
            }
        }
        return recordsByType;
    }


    public static Comparator<Record> fine_amountComparator = (o1, o2) -> (int) (o2.getFine_amount() - o1.getFine_amount());

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
                            case "date_time":
                                record.setDate_time(value);
                                break;
                            case "first_name":
                                record.setFirst_name(value);
                                break;
                            case "last_name":
                                record.setLast_name(value);
                                break;
                            case "type":
                                record.setType(value);
                                break;
                            case "fine_amount":
                                record.setFine_amount(Double.parseDouble(value));
                                break;
                        }
                    }
                }
                // add object to list
                list.add(record);
            }
        }
        return list;
    }
}

