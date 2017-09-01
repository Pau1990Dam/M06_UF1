package DOM_Forecast;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DecimalFormat;

/**
 * Created by pau on 4/11/15.
 */
public class LectorForecastDOM {
    private MiniTraductors minitr= new MiniTraductors();

    /**
     *
     * @param nl
     */
    public void LectorNodos(NodeList nl){
        for(int i=0; i<nl.getLength();i++){
            Node n= nl.item(i);
            formatejador2(n);
        }
    }

    /**
     *
     * @param n
     */
    public void formatejador2(Node n){
        switch(n.getNodeName()){
            case "location":
                if(n.hasChildNodes())location(n.getChildNodes());
                break;
            case "forecast":
                if(n.hasChildNodes())forecast(n.getChildNodes());
                break;
        }
    }

    /**
     *
     * @param nl
     */
    public void forecast(NodeList nl){
        for(int i=0; i<nl.getLength();i++){
            Node data=nl.item(i);
            switch(data.getNodeName()){
                case "time":
                    System.out.print("\nEl Tiempo de --> ");
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        String [] translations={"\t"," - "};
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            System.out.print(translations[j]+atr.getNodeValue().substring(11));
                        }
                    }
                    forecast(data.getChildNodes());
                    break;
                case "precipitation":
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(data.getNodeName().equals("type")) System.out.print("\n\tLLuvia suave");
                        }
                    }
                    break;
                case "windDirection":
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(data.getNodeName().equals("name")) System.out.print("\n\tViento\tdirección: "+minitr.vent(data));
                        }
                    }
                    break;
                case "windSpeed":
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(data.getNodeName().equals("mps")){
                                DecimalFormat formato = new DecimalFormat("#00");
                                double velocidad=( 1.60934*Double.parseDouble(data.getNodeValue()))*3.6;
                                System.out.print("\tvelocidad: "+formato.format(velocidad)+" Km\\h");
                            }
                        }
                    }
                    break;
                case "temperature":
                    System.out.print("\n\tTemperatura");
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        String translations[]={"\tmáxima: "," mínima: ",""," media: "};
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(!atr.getNodeName().equals("unit"))
                                System.out.print(translations[j]+atr.getNodeValue()+" ºC");
                        }
                    }
                    break;
                case "pressure":
                    System.out.print("\n\tPresion: ");
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(atr.getNodeName().equals("value")) System.out.print(atr.getNodeValue() + " hPa");
                        }
                    }
                    break;
                case "humidity":
                    System.out.print("\n\tHumedad: ");
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(atr.getNodeName().equals("value"))System.out.print(atr.getNodeValue()+" %");
                        }
                    }
                    break;
                case "clouds":
                    System.out.print("\n\tCielo: ");
                    if(data.hasAttributes()){
                        NamedNodeMap atributos = data.getAttributes();
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(atr.getNodeName().equals("value"))System.out.print(minitr.nuvols(atr)+"\n");
                        }
                    }
                    break;
            }

        }
    }

    /**
     *
     * @param nl
     */
    public void location(NodeList nl){
        for(int i=0;i<nl.getLength();i++){
            Node data = nl.item(i);
            switch(data.getNodeName()){
                case "location":
                    if(data.hasChildNodes()){
                        location(data.getChildNodes());
                    }
                    if(data.hasAttributes()){
                        System.out.print("\nCoordenadas ");
                        NamedNodeMap atributos = data.getAttributes();
                        String translations[]={"altitud = ","","","latitud = ","longitud = "};
                        for(int j=0;j<atributos.getLength();j++){
                            Node atr = atributos.item(j);
                            if(!atr.getNodeName().equals("geobase")&&!atr.getNodeName().equals("geobaseid"))
                                System.out.print(translations[j]+atr.getNodeValue()+" ");//translations[j]+
                        }
                        System.out.print("\n");
                    }
                    break;
                case "country":
                    System.out.print("("+data.getFirstChild().getNodeValue().toUpperCase()+")");
                    break;
                case "name":
                    System.out.print("\n\t\t\t\tCIUDAD "+data.getFirstChild().getNodeValue()+" ");
                    break;
            }
        }
    }
}
