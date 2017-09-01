package DOM_Forecast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Created by pau on 4/11/15.
 */
public class TraductorForecastDOM {
    /**
     *
     * @param doc
     */
    public void DocTraduccioElements(Document doc){
        NodeList nodes = doc.getElementsByTagName("weatherdata");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "datosDelTiempo");
        }
        nodes = doc.getElementsByTagName("forecast");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "prediccion");
        }
        nodes = doc.getElementsByTagName("location");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "localizacion");
        }
        nodes = doc.getElementsByTagName("name");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "nombre");
        }
        nodes = doc.getElementsByTagName("country");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "pais");
        }
        nodes=doc.getElementsByTagName("time");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "tiempo");
        }
        nodes=doc.getElementsByTagName("precipitation");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "precipitaciones");
        }
        nodes=doc.getElementsByTagName("windDirection");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "direccionViento");
        }
        nodes=doc.getElementsByTagName("windSpeed");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "velocidadViento");
        }
        nodes=doc.getElementsByTagName("pressure");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "presion");
        }
        nodes=doc.getElementsByTagName("humidity");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "humedad");
        }
        nodes=doc.getElementsByTagName("temperature");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "temperatura");
        }
        nodes=doc.getElementsByTagName("clouds");
        for (int i = 0; i < nodes.getLength(); i++) {
            doc.renameNode(nodes.item(i), null, "nubes");
        }

    }

}
