package DOM_Forecast;

import org.w3c.dom.*;

import java.text.DecimalFormat;

/**
 * Created by pau on 4/11/15.
 */
public class ResumidorForecastDOM {

    MiniTraductors minitr = new MiniTraductors();

    /**
     *
     * @param doc
     * @return
     */
    public Document DocResum(Document doc){
        TraductorForecastDOM traductor= new TraductorForecastDOM();
        DocResumCapçalera(doc);
        DocResumForecast(doc);
        traductor.DocTraduccioElements(doc);
        return doc;
    }

    /**
     *
     * @param doc
     */
    public void DocResumCapçalera(Document doc) {
        String annoyingItems[] = {"meta", "credit", "sun"};
        NodeList localitzacioItems = doc.getElementsByTagName("location");

        for (int i = 0; i < localitzacioItems.getLength(); i++) {
            Node localitzacio = localitzacioItems.item(i);
            if (localitzacio.hasChildNodes()) {
                NodeList eraseItems = localitzacio.getChildNodes();
                for (int j = 0; j < eraseItems.getLength(); j++) {
                    Node delete = eraseItems.item(j);
                    if (delete.getNodeName().equals("type") || delete.getNodeName().equals("timezone")) {
                        localitzacio.removeChild(delete);
                    }
                }
            }
            if (localitzacio.hasAttributes()) {
                NamedNodeMap attrEraser = localitzacio.getAttributes();
                Node attrModificator = attrEraser.getNamedItem("altitude");
                ((Element) localitzacio).setAttribute("altitud", attrModificator.getNodeValue());
                attrModificator = attrEraser.getNamedItem("latitude");
                ((Element) localitzacio).setAttribute("latitud", attrModificator.getNodeValue());
                attrModificator = attrEraser.getNamedItem("longitude");
                ((Element) localitzacio).setAttribute("longitud", attrModificator.getNodeValue());
                attrEraser.removeNamedItem("altitude");
                attrEraser.removeNamedItem("latitude");
                attrEraser.removeNamedItem("longitude");
                attrEraser.removeNamedItem("geobase");
                attrEraser.removeNamedItem("geobaseid");
            }
        }
        for (int i = 0; i < annoyingItems.length; i++) {
            Node delete = doc.getElementsByTagName(annoyingItems[i]).item(0);
            delete.getParentNode().removeChild(delete);
        }
    }

    /**
     *
     * @param doc
     */
    public void DocResumForecast(Document doc) {
        Node modificator;
        NamedNodeMap eraser;
        NodeList items = doc.getElementsByTagName("time");

        for (int i = 0; i < items.getLength(); i++) {
            Node temps = items.item(i);
            //NodeList pre=temps.getChildNodes();
            //for(int a=0;a<pre.getLength();a++){if(pre.item(a).getNodeName().equals("precipitation"))cont++;}
            eraser = temps.getAttributes();
            modificator = eraser.getNamedItem("from");
            ((Element) temps).setAttribute("desde", modificator.getNodeValue().substring(11));
            modificator = eraser.getNamedItem("to");
            ((Element) temps).setAttribute("hasta", modificator.getNodeValue().substring(11));
            eraser.removeNamedItem("from");
            eraser.removeNamedItem("to");
            NodeList tempsItems = temps.getChildNodes();
            for (int j = 0; j < tempsItems.getLength(); j++) {
                Node data = tempsItems.item(j);
                forecastModificator(data);
            }
        }
    }

    /**
     *
     * @param item
     */
    private void forecastModificator(Node item) {
        String translation;
        NamedNodeMap attrMod;
        Node atributeMod;
        switch (item.getNodeName()) {
            case "symbol":
                item.getParentNode().removeChild(item);
                break;
            case "precipitation":
                if (item.hasAttributes()) {
                    attrMod = item.getAttributes();
                    attrMod.removeNamedItem("unit");
                    attrMod.removeNamedItem("value");
                    attrMod.removeNamedItem("type");
                    ((Element) item).setAttribute("tipo", "LLuvia suave");
                } else item.getParentNode().removeChild(item);
                break;
            case "windDirection":

                attrMod = item.getAttributes();
                translation = minitr.vent(attrMod.getNamedItem("name"));
                attrMod.removeNamedItem("name");
                attrMod.removeNamedItem("code");
                attrMod.removeNamedItem("deg");
                ((Element) item).setAttribute("direccion", translation);
                break;
            case "windSpeed":
                DecimalFormat formato = new DecimalFormat("#00");
                attrMod = item.getAttributes();
                double kmh = Double.parseDouble(attrMod.getNamedItem("mps").getNodeValue()) * 1.60934 * 3.6;
                attrMod.removeNamedItem("name");
                attrMod.removeNamedItem("mps");
                ((Element) item).setAttribute("kmh", formato.format(kmh));
                break;
            case "temperature":
                attrMod = item.getAttributes();
                atributeMod = attrMod.getNamedItem("unit");
                ((Element) item).setAttribute("unidad", atributeMod.getNodeValue());
                atributeMod = attrMod.getNamedItem("value");
                ((Element) item).setAttribute("media", atributeMod.getNodeValue());
                attrMod.removeNamedItem("unit");
                attrMod.removeNamedItem("value");
                break;
            case "pressure":
                attrMod = item.getAttributes();
                atributeMod = attrMod.getNamedItem("unit");
                ((Element) item).setAttribute("unidad", atributeMod.getNodeValue());
                atributeMod = attrMod.getNamedItem("value");
                ((Element) item).setAttribute("valor", atributeMod.getNodeValue());
                attrMod.removeNamedItem("unit");
                attrMod.removeNamedItem("value");
                break;
            case "humidity":
                attrMod = item.getAttributes();
                atributeMod = attrMod.getNamedItem("unit");
                ((Element) item).setAttribute("unidad", atributeMod.getNodeValue());
                atributeMod = attrMod.getNamedItem("value");
                ((Element) item).setAttribute("valor", atributeMod.getNodeValue());
                attrMod.removeNamedItem("unit");
                attrMod.removeNamedItem("value");
                break;
            case "clouds":
                attrMod = item.getAttributes();
                translation = minitr.nuvols(attrMod.getNamedItem("value"));
                attrMod.removeNamedItem("all");
                attrMod.removeNamedItem("unit");
                attrMod.removeNamedItem("value");
                ((Element) item).setAttribute("valor", translation);
                break;
        }
    }
}
