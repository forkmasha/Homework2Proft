package task1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class Person {

    public static String path = "src/main/java/task1/";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path + "input.xml"));
        NodeList persons = document.getElementsByTagName("person");
        for (int i = 0; i < persons.getLength(); i++) {
            Element person = (Element) persons.item(i);
            String name = person.getAttribute("name");
            String surname = person.getAttribute("surname");
            person.setAttribute("name", name + " " + surname);
            person.removeAttribute("surname");
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(path + "output.xml"));
        transformer.transform(source, result);

    }

}
