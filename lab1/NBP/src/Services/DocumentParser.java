package Services;

import InterfacesServices.IDocumentParser;
import data.ExchangeRate;
import data.ExchangeTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DocumentParser implements IDocumentParser {

    @Override
    public ExchangeTable getTable(String rawData) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        InputStreamReader reader = new InputStreamReader(
                new ByteArrayInputStream(rawData.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8
        );

        InputSource is = new InputSource(reader);
        Document doc = builder.parse(is);
        doc.getDocumentElement().normalize();

        String dataPublikacji = doc.getElementsByTagName("data_publikacji")
                .item(0).getTextContent().trim();

        Map<String, ExchangeRate> ratesMap = new HashMap<>();

        NodeList pozycjaList = doc.getElementsByTagName("pozycja");

        for (int i = 0; i < pozycjaList.getLength(); i++) {
            Node pozycjaNode = pozycjaList.item(i);
            if (pozycjaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element pozycjaElement = (Element) pozycjaNode;

                String nazwa = pozycjaElement
                        .getElementsByTagName("nazwa_waluty")
                        .item(0).getTextContent().trim();

                String kod = pozycjaElement
                        .getElementsByTagName("kod_waluty")
                        .item(0).getTextContent().trim();

                String przelicznikStr = pozycjaElement
                        .getElementsByTagName("przelicznik")
                        .item(0).getTextContent().trim();

                int przelicznik = safeParseInt(przelicznikStr);

                String kursStr = pozycjaElement
                        .getElementsByTagName("kurs_sredni")
                        .item(0).getTextContent().trim()
                        .replace(',', '.');

                double kursSredni = safeParseDouble(kursStr);

                ExchangeRate rate = new ExchangeRate(kursSredni, przelicznik, nazwa, kod);
                ratesMap.put(kod, rate);
            }
        }

        return new ExchangeTable(dataPublikacji, ratesMap);
    }

    private int safeParseInt(String s) {
        try {
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            System.err.println("Nie udało się sparsować przelicznika: " + s + " — przyjmuję domyślnie 1");
            return 1;
        }
    }

    private double safeParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.err.println("Nie udało się sparsować kursu: " + s + " — przyjmuję 0.0");
            return 0.0;
        }
    }
}
