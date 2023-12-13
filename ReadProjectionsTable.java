package Parser;
import java.io.File;
import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

import dao.EmissionDAO;
import entities.Emission;


public class ReadProjectionsTable {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GHGEmission");

    public static void main(String[] argv) throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        File xmlFile = new File("MMR_IRArticle23T1_IE_2016v2.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = builder.parse(xmlFile);

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        XPathExpression expr = xPath.compile("//Row[Value > 0 and Scenario='WEM' and Year='2023']");
        org.w3c.dom.NodeList nodeList = (org.w3c.dom.NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        int entryCount = nodeList.getLength();

                EmissionDAO emissionDAO = new EmissionDAO();

        for (int i = 0; i < entryCount; i++) {
            org.w3c.dom.Node row = nodeList.item(i);

            System.out.println("\nCurrent Element: " + row.getNodeName());

            if (row.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element elem = (org.w3c.dom.Element) row;

                String catValue = elem.getElementsByTagName("Category__1_3").item(0).getTextContent();
                String yearValue = elem.getElementsByTagName("Year").item(0).getTextContent();
                String scenarioValue = elem.getElementsByTagName("Scenario").item(0).getTextContent();
                String gasUnitsValue = elem.getElementsByTagName("Gas___Units").item(0).getTextContent();
                String valueValue = elem.getElementsByTagName("Value").item(0).getTextContent();

                Emission emission = new Emission();
                emission.setCategory(catValue);
                emission.setGasUnit(gasUnitsValue);
                emission.setValue(Double.parseDouble(valueValue));
                emission.setYear(Integer.parseInt(yearValue));
                emission.setScenario(scenarioValue);

                                emissionDAO.saveEmission(emission);

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
