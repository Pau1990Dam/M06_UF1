package SAX_Pokemons;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Pau Iglesias Casanova
 */

/**
 *XMLparser extiende DefaultHandler que a su vez implementa  ContentHandler. ContentHandler tiene la capacidad de recibir
 * todas las notificaciones de contenido de un XMLReader, además de otras classes que en este ejercicio no he utilizado.
 */
public class XMLparser extends DefaultHandler {
    private final XMLReader Lectorxml;
    private boolean Nom =false;
    private boolean PV=false;
    private boolean Ataque1=false;
    private boolean Ataque2=false;
    private boolean Etapa=false;
    String clase;

    /**
     * Este constructor crea el XMLReader mediante XMLReaderFactory.createXMLReader(), este devuelve un XMLReader por
     * defecto para el sistema. Asocio el XMLReader con el ContentHandler mediante setContentHandler para que
     * pueda recibir todas las notificaciones, es decir, para que pueda implementar los métodos que se ejecutarán cada vez
     * que se produce un evento tipo, principio de archivo, final de archivo, incio de elemento...
     * @throws SAXException
     */
    public XMLparser() throws SAXException {
        Lectorxml = XMLReaderFactory.createXMLReader();
        Lectorxml.setContentHandler(this);
    }

    /**
     *Método que recoge el archivo xml que se ha de parsear y que desencadenará todas las acciones llamando a todos los
     * métodos sobreescritos derivados de ContentHandler.
     * @param xml
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SAXException
     */
    public void leer(final String xml)
            throws FileNotFoundException, IOException,
            SAXException {
        FileReader fr = new FileReader(xml);
        Lectorxml.parse(new InputSource(fr));
    }

    /**
     *Cuando se detecta el principio del xml nuestra clase ejecuta este método.
     */
    @Override
    public void startDocument() {
        System.out.println("\n---------POKÉMONS---------");
    }

    /**
     *Cuando se detecta el final del xml nuestra clase ejecuta este método.
     */
    @Override
    public void endDocument() {
        System.out.println("\n----------FIN----------");
    }

    /**
     *Cada vez que se detecta una etiqueta xml se ejecuta este método. startElements() está sobreescrito de manera
     * que cuando se detecte una etiqueta cuya información quiero conocer se active el booleano  (se ponga true)
     * al cual la he asociado. De esta manera indico que se lea el contenido entre los tags cuando se ejecute el
     * método characters().
     * También desde este método almacenamos el atributo del elemento Nombre para mostrarlo cuando se ejecute el método
     * characters().
     * @param uri   String con el valor de la uri del namespace. Si no hay namespace es un string vacio.
     * @param name  String con el valor del namespace (si lo hay) + el nombre del elemento.
     * @param qName String con el nombre del elemento.
     * @param atts  Objeto Attributes que almacena la información de los atributos del elemento.
     */
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {

        if (qName.equalsIgnoreCase("nombre")) {
            Nom = true;
            clase=atts.getValue("Classe");
            System.out.println();
        } else if (qName.equalsIgnoreCase("pv")) {
            PV = true;
        } else if (qName.equalsIgnoreCase("ataque1")) {
            Ataque1 = true;
        }else if (qName.equalsIgnoreCase("ataque2")) {
            Ataque2 = true;
        }
        else if(qName.equalsIgnoreCase("etapa")){
            Etapa = true;
        }
    }



    /**
     *Este método se ejecuta cada vez que se detecta contenido entre los tags de un elemento. characters() esta
     * sobreescrito de manera que si el contendio pertenece al del elemento que interesa, es decir, si el booleano
     * que he asociado al elemento es verdadero, se muestra por pantalla el contenido.
     * @param ch        Array de chars que contiene toda la información del xml.
     * @param start     int que funciona como indice dentro del array de chars que indica donde comienza el contenido que
     *              hay entre los tags del elemento.
     * @param length    int que indica los chars que ocupa la información entre tags del elemento.
     * @throws SAXException
     */
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if(Nom){
            System.out.println("Nombre: "+ new String(ch, start, length)+"  Clase: "+clase);
            Nom=false;
        }else if(PV){
            System.out.println("PV: "+ new String(ch, start, length));
            PV=false;
        }else if(Ataque1){
            System.out.println("Ataque 1: "+ new String(ch, start, length));
            Ataque1=false;
        }else if(Ataque2){
            System.out.println("Ataque 2: "+ new String(ch, start, length));
            Ataque2=false;
        }else if(Etapa){
            System.out.println("Etapa: "+ new String(ch, start, length));
            Etapa=false;
        }

    }

}


class MainSAX {
    public static String ruta=System.getProperty("user.dir")+"/NF2/src/SAX_Pokemons/Pokemons.xml";
    public static void main(String[] args)
            throws IOException, SAXException {

        MainSAX m = new MainSAX();
        XMLparser lector = new XMLparser();
        try{
            lector.leer(ruta);
        }catch (FileNotFoundException e){
            m.cambiaRuta();
            main(args);
        }
    }
    /**
     *cambiaRuta() método que comprueba que la variable ruta conduzca a un archivo xml y obliga a cambiarla hasta que
     * la ruta lleve a un xml.
     */
    public void cambiaRuta(){
        Scanner entrada= new Scanner(System.in);
        while(!new File(ruta).exists()){
            System.out.println("Por favor, introduzca la ruta del fichero xml pokemons: ");
            if(! new File(ruta=entrada.nextLine()).exists() ||!new File(ruta).getName().contains(".xml") )
                System.out.println("Ruta incorrecta.");
        }
    }
}