
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.*;
import java.util.Scanner;

/**
 * Created by 14270729b on 05/11/15.
 */
/*
(3 punts) Necessitareu el fitxer «planets.xml» que el professor us haurà facilitat. Usant el parser que el professor
heu de mostrar una frase com la que segueix:
El planeta «nom del planeta» té una massa «massa del planeta» cops la massa de la terra i té «satèl·lits» llunes.

Ampliacio:
Guardeu les frases en un fitxer anomenat “resum_planetes.txt”.
Detecteu quin planeta és el que té mes satèl·lits.
 */

/**
 * Guarda en un StringBuilder la informació la informació que es demana a l'exercici.
 */
public class ExamenEx2 extends DefaultHandler {
    private final XMLReader Lectorxml;
    private boolean nom = false;
    private boolean massa = false;
    private boolean satelits = false;
    private int numsatelits=0;
    private String current;
    private String planeta;
    private StringBuilder str= new StringBuilder();

    /**
     * Creem el Lector XML i li posem un handler per a que es puguin executar els events de lectura
     * @throws SAXException
     * @throws SAXException
     */
    public ExamenEx2() throws SAXException, SAXException {
        Lectorxml = XMLReaderFactory.createXMLReader();
        Lectorxml.setContentHandler(this);
    }

    /**
     * Iniciem el lector paant-li el xml a llegir.
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
     *
     * @param uri
     * @param name
     * @param qName
     * @param atts
     */
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {

        if (qName.equalsIgnoreCase("NAME")) {
            nom = true;
        } else if (qName.equalsIgnoreCase("MASS")) {
            massa = true;
        } else if (qName.equalsIgnoreCase("SATELLITES")) {
            satelits = true;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        String content=new String(ch, start, length);

        if (nom) {
            str.append("\nEl planeta " + content);
            current=content;
            nom = false;
        } else if (massa) {
            str.append(" té una massa " + content + " cops la massa de la terra");
            massa = false;
        } else if (satelits) {
            str.append(" i té " + content + " llunes");
            comparadorNumSatelis(Integer.parseInt(content),current);
            satelits = false;
        }
    }
    private void comparadorNumSatelis(int n, String p){
        if(numsatelits<n){
            numsatelits=n;
            planeta=p;
        }
    }
    @Override
    public void endDocument() {
        str.append("\n\nEl planeta amb més satel·lits té " + numsatelits + " satel·lits i es " + planeta);
    }
    public String getInfo(){
        return str.toString().trim();
    }

}
class MainSAX {
    public static String ruta = System.getProperty("user.dir")+"/EXAMEN/src/planets.xml";
    public static void main(String[] args)
            throws FileNotFoundException, IOException,
            SAXException {
        MainSAX m = new MainSAX();
        ExamenEx2 lector = new ExamenEx2();
        m.cambiaRuta();
        lector.leer(ruta);
        String a=lector.getInfo();
        System.out.println(a);
        Escritor(a);


    }
    /**
     *cambiaRuta() método que comprueba que la variable ruta conduzca a un archivo xml y obliga a cambiarla si asi no
     * sucede hasta que ruta lleve a un xml.
     */
    public void cambiaRuta(){
        Scanner entrada= new Scanner(System.in);
        while(!new File(ruta).exists()){
            System.out.println("Por favor, introduzca la ruta del fichero xml pokemons: ");
            if(! new File(ruta=entrada.nextLine()).exists() ||!new File(ruta).getName().contains(".xml") )
                System.out.println("Ruta incorrecta.");
        }
    }

    public static void Escritor(String xml){

        System.out.print("Resultado -> ");
        File resum = new File(System.getProperty("user.dir"));


        try{
            //
            BufferedWriter escritor = new BufferedWriter (new FileWriter(new File(resum, "/EXAMEN/resum_planetes.txt")));
            escritor.write(xml);
            escritor.close();
            System.out.print(resum.getAbsolutePath()+"/resum_planetes.txt");
        }catch(IOException e){
            System.out.println("Error4: "+e.getMessage());
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("Error5: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
