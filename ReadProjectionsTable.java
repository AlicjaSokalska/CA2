package Parser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entities.Prediction;
import service.PredictionService;

import java.io.File;
import java.io.IOException;

public class ReadProjectionsTable {

    public static void main(String[] argv) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        File xmlFile = new File("MMR_IRArticle23T1_IE_2016v2.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        XPathExpression expr = xPath.compile("//Row[Value > 0 and Scenario='WEM' and Year='2023']");
        NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        int entryCount = nodeList.getLength();

        // Create an instance of the PredictionService to interact with the database
        PredictionService predictionService = new PredictionService();

        for (int i = 0; i < entryCount; i++) {
            Node row = nodeList.item(i);

            System.out.println("\nCurrent Element: " + row.getNodeName());

            if (row.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) row;

                String catValue = elem.getElementsByTagName("Category__1_3").item(0).getTextContent();
                String yearValue = elem.getElementsByTagName("Year").item(0).getTextContent();
                String scenarioValue = elem.getElementsByTagName("Scenario").item(0).getTextContent();
                String gasUnitsValue = elem.getElementsByTagName("Gas___Units").item(0).getTextContent();
                String valueValue = elem.getElementsByTagName("Value").item(0).getTextContent();

                // Create a Prediction entity
                Prediction prediction = new Prediction();
                prediction.setCategory(catValue);
                prediction.setGasUnit(gasUnitsValue);
                prediction.setValue(Double.parseDouble(valueValue));
                prediction.setYear(Integer.parseInt(yearValue));
                prediction.setScenario(scenarioValue);

                // Save the Prediction entity to the database
                predictionService.createPrediction(prediction);

                System.out.println("Category: " + catValue);
                System.out.println("Year: " + yearValue);
                System.out.println("Scenario: " + scenarioValue);
                System.out.println("Gas Units: " + gasUnitsValue);
                System.out.println("Value: " + valueValue);
            }
        }

        System.out.println("\nTotal Entries: " + entryCount);
    }
}
