package DOM_Forecast;

import org.w3c.dom.Node;

/**
 * Created by pau on 4/11/15.
 */
public class MiniTraductors {
    /**
     *
     * @param n
     * @return
     */
    public String nuvols(Node n){
        switch(n.getNodeValue()){
            case "scattered clouds":
                return "nubes dispersas";
            case "broken clouds":
                return "nubes rotas";
            case "overcast clouds":
                return "nublado";
            case "few clouds":
                return "pocas nubes";
            case "clear sky":
                return "despejado";
            default:
                return "";
        }
    }

    /**
     *
     * @param n
     * @return
     */
    public String vent(Node n){
        switch(n.getNodeValue()){
            case "East-northeast":
                return "Este-nordeste";
            case "NorthEast":
                return "Nordeste";
            case "West":
                return "Oeste";
            case "Northwest":
                return "NordOeste";
            case "South":
                return "Sud";
            case "West-southwest":
                return "Oeste-sudoeste";
            case "West-northwest":
                return "Oeste-nordoeste";
            case "Southwest":
                return "Sudoeste";
            case "South-southwest":
                return "Sud-sudoeste";
            case "East-southeast":
                return "Este-sudeste";
            case "North-northeast":
                return "Norte-nordeste";
            case "East":
                return "Este";
            default:
                return "";
        }
    }
}
