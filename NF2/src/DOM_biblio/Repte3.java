package DOM_biblio;/*
Acostumo a fer aquest tipus d'exercicis en un sol document per facilitar-ne 
l'elaboració i la seva correcció, ja que són codis relativament petits, però 
sóc conscient que en POO el codi s'ha de fragmentar en objectes i en fitxers 
diferents, no només en mètodes,  per tal facilitar la seva comprensió, 
correcció i actualització.
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * @author Pau Iglesias Casanova
 */
public class Repte3 {
/**
 * CarregarDocXML retorna un obj Document que carrega l'estructura i contingut 
 * del document referit al objete File passat com a paràmetre.
 * @param arxiuXML Ruta del arxiu XML que es vol parsejar.
 * @return doc. Objecte de la clase Document que carrega l'arxiu XML en memòria.
 * @throws ParserConfigurationException
 * @throws IOException
 * @throws SAXException 
 */
    public Document CarregarDocXML(File arxiuXML) throws 
            ParserConfigurationException, IOException, SAXException{
        
        DocumentBuilderFactory Factoria 
            = DocumentBuilderFactory.newInstance();
        DocumentBuilder carregarXML = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();
        Document doc = carregarXML.parse(arxiuXML);
        
        return doc;
    }
/**
 * deXMLaCadenes retorna el contingut d'un NodeList pasat per paràmete en forma
 * de un String i amb les frases extres que s'han d'afegir segons l'enunciat
 * de l'exercici.
 * @param fills Nodelist del qual es vol extreure el contingut entre tags.
 * @return extractorDeText.toString(), es a dir, un String amb tot e cotingut 
 * entre tags més els afegits que demana l'exercici.
 */
    public String deXMLaCadenes(NodeList fills){
        
        String [] arr ={"El llibre amb títol \'","\' té com autor \'",
            "\' i va ser publicat l'any "};    
        StringBuilder extractorDeText = new StringBuilder();
        int cont =0;
        //Recorrém cada node llibre 
        for(int i=0; i<fills.getLength();i++){
            Node llibre = fills.item(i);
            NodeList llibreInfo=llibre.getChildNodes();
            //Recorrém els tags de cada node llibre
            for(int j=0; j<llibreInfo.getLength();j++){
                Node tagInfo = llibreInfo.item(j).getFirstChild();
                //Guardem el contingut que hi ha entre cada tag en un 
                //StringBuilder amb la corresponent frase definida a l'exercici.
                if(tagInfo!=null){
                   extractorDeText.append(arr[cont]+" "+tagInfo.getNodeValue());
                   cont ++;
                } 
            }
            extractorDeText.append("\n");
            cont =0;
        }
        return extractorDeText.toString();
    }
/**
 * Escritor guarda el String passat per paràmetre en un fitxer txt anomenat
 * resum.txt
 * @param text String passat per paràmetre que representa el contingut de 
 * l'arxiu XML extret abans.
 * @throws IOException 
 */
    public static void Escritor(String text) throws IOException{
        
	Scanner entrada = new Scanner (System.in);
	String rutaWindowsOrutaLinux;
	System.out.println("Introdueix la ruta del directori on vols guardar el"
                + "contingut parsejat de l'arxiu biblio.xml: ");
	File resum = new File(entrada.nextLine());
        
	//entrada.close();
	//Si la ruta donada per l'usuari no condueix a cap directori es tornarà 
	//a demanar de nou.
	while(!resum.isDirectory()){
            System.out.println("Error: La ruta que has introduït no es "
                    + "vàlida." +" Escriu-la de nou:");
            resum = new File(entrada.nextLine());
	}
	//Detecta si se tracta d'una ruta de tipus Windows o Linux per
	//emmagatzemar en la variable String rutaWindowsOrutaLinux el nom de 
	//l'arxiu amb el separador correcte.
        if(resum.getAbsolutePath().contains("\\")) rutaWindowsOrutaLinux=
                "\\resum.txt";
        else rutaWindowsOrutaLinux="/resum.txt";
        
	BufferedWriter escritor = new BufferedWriter (new FileWriter
            (resum.getAbsolutePath()+rutaWindowsOrutaLinux));
	escritor.write(text);
	escritor.close();

        System.out.println("Arxiu resum.txt creat amb èxit.");
    }        
    
    public static void main(String[] args) {
        Scanner entrada = new Scanner (System.in);
        Repte3 XML = new Repte3();
        File rutaDocXML;
        
        try{
               String ruta = System.getProperty("user.dir")+"/NF2/src/DOM_biblio/biblio.xml";
                rutaDocXML=new File(ruta);
               //System.out.println("Introdueix la ruta del arxiu biblio.xml: ");
               //rutaDocXML=new File(entrada.nextLine());

               while(!rutaDocXML.getName().equals("biblio.xml")){
                    System.out.println("Falta la ruta correcta al arxiu biblio.xml.\nIndica-la : ");
                    rutaDocXML = new File(entrada.nextLine());
               }
               //Crear obj Document amb el contingut de l'arxiu bibilio.xml
               Document doc = XML.CarregarDocXML(rutaDocXML);

               //Si el document té Nodes fills, que en aquest cas es segur que sí,
               //entra en el if
               if(doc.hasChildNodes()){
               //Cridem al mètode deXMLaCadenes per extreure la informació del XML
               //en forma d'un String
                String textExtret=
                        XML.deXMLaCadenes(doc.getElementsByTagName("libro"));

                System.out.println("Resultat:\n"+textExtret);
                //Cridem a la funció Escritor per guardar el resultat en el un txt
                Escritor(textExtret);
               }
           
        }catch (ParserConfigurationException | IOException | SAXException e){
            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
