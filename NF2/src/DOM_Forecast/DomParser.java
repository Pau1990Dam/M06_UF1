package DOM_Forecast;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Created by pau on 3/11/15.
 */
public class DomParser {
    private static String ruta=System.getProperty("user.dir")+"/NF2/src/DOM_Forecast/forecast.xml";

    /**
     * CarregarDocXML retorna un obj Document que carrega l'estructura i contingut
     * del document referit al objete File passat com a paràmetre.
     *
     * @param arxiuXML Ruta del arxiu XML que es vol parsejar.
     * @return doc. Objecte de la clase Document que carrega l'arxiu XML en memòria.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public Document CarregarDocXML(File arxiuXML) throws
            ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory Factoria
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder carregarXML = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = carregarXML.parse(arxiuXML);
        doc.getDocumentElement().normalize();

        return doc;

    }

    /**
     *
     * @param doc
     * @param file
     * @throws TransformerException
     */
    public void DocToXML(Document doc, File file) throws TransformerException {
        ResumidorForecastDOM resumidor = new ResumidorForecastDOM();
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(file);
        DOMSource source = new DOMSource(resumidor.DocResum(doc));
        trans.transform(source, result);
    }

    /**
     *
     * @param arxiu
     */
    public void cambiaRuta(String arxiu) {
        Scanner entrada = new Scanner(System.in);
        while (!new File(ruta).exists()) {
            System.out.println("Por favor, introduzca la ruta del fichero xml "+arxiu+": ");
            if (!new File(ruta = entrada.nextLine()).exists() || !new File(ruta).getName().contains(".xml"))
                System.out.println("Ruta incorrecta.");
        }
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        LectorForecastDOM displayer = new LectorForecastDOM();
        DomParser parser =  new DomParser();
        parser.cambiaRuta("forecast");
        File arxiu = new File(ruta);
        System.out.println();
        try{
            Document doc= parser.CarregarDocXML(arxiu);
            if(doc.hasChildNodes()){
                displayer.LectorNodos(doc.getElementsByTagName("location"));
                displayer.LectorNodos(doc.getElementsByTagName("forecast"));

                ruta = System.getProperty("user.dir")+"/NF2/src/DOM_Forecast/resumForecast.xml";

                System.out.println("El resumen del archivo forecast.xml será guardado como -> "+ruta);

                parser.DocToXML(doc, new File(ruta));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}