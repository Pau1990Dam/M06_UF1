package Serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by pau on 10/10/15.
 *
 */

/**
 * Clase Animal basada en la descripció de l'enunciat. Implementa la iterfície "Serializable" per indicar que els objectes
 * d'aquesta clase es poden guarda en fitxers.
 *
 * Nota: No he utilitzat cap mètode "set" de cap objecte de cap clase perquè en el main() cada cop que insereixo o
 * canvio un objecte ho faig de nou. D'aquesta manera guanyo en comoditat i m'estalvio la càrrega de més d'un objecte
 * quan en vull inserir més d'un.
 */
public class Animal implements Serializable {
    private Codi CNIJ;
    private String nom;

    public Animal(Codi CNIJ, String nom){
        this.CNIJ=CNIJ;
        this.nom=nom;
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public void setCodi(String codi){
        CNIJ=new Codi(codi);
    }
    public String getNom(){
        return nom;
    }
    public String getCodi(){
        return CNIJ.getCodi();
    }
}

/**
 * La clase Codi implementa la interficie "Serializable" amb el mateix propósit que la clase Animal i per permetre
 * la Serialització d'aquesta última.
 */
class Codi implements Serializable{
    //La variable Scanner porta l'atribut transient per obviar explicitament la seva Serialització, ja que sino donaría
    //error, doncs aquests objectes no son Srialitzables.
    private transient Scanner entrada = new Scanner(System.in);
    private static final String [] arr ={"EX","EW","CR","EN","VU","LT","NC"};
    private String codi="Codi no establert";


    public Codi(String codi){
        //Sí el codi enviat per paràmete no coincideix amb cap dels codis vàids llavors boliga ha reintroduir-lo
        //per mitja de la invocació del mètode "setCodi" desde el constructor.
        for(String c:  arr){
            if(c.equals(codi)){
                this.codi=codi;
                return;
            }
        }
        System.out.println("Codi no vàlid. Reedirigint al menú d'elecció de codis vàlids...");
        setCodi();
    }

    public void setCodi(){
        int eleccio=0;
        do{
            System.out.println("Selecciona el codi, que indica l'estat de conservació de l'espécie, introduïnt el " +
                    "número corresponent: ");
            System.out.println("1. EX   2. EW   3. CR   4. EN   5. VU   6. LT   7. NC");
            try {
                //Utilitzo un nextLine() en comptes d'un nextInt() x agafar el caràcter "\n" i no haver de fer més línies
                //per netejar el buffer de l'Scanner, acció necessaria si n'hi ha moltes entrades per teclat i pocs
                // nextLines();
                eleccio = Integer.parseInt(entrada.nextLine());
            }catch(Exception e){
                System.out.println(e.getCause());
            }
        }while(eleccio<=0||eleccio>7);
        codi=arr[eleccio-1];
    }

    public String getCodi(){
            return codi;
    }
}

class Main {

    public static void main(String[] args) {
        //Objecte Codi necessari per instanciar un objecte Animal, ja que aquest ho exigeix en el constructor.
        Codi codiConservacio;
        //Guarda un string que serà enviat com a paràmetre al constructor d' Animal
        String nomAnimal;
        //Creo un ojecte animal buit per commoditat, ja que només utilitzo un a la vegada.
        Animal animal=null;
        Zoo zoo;
        GreenPeace greenpeace;
        Apilar ap;
        //Ruta a establir de l'objecte Apilar
        String rutaApilar="";
        //Indica si s'ha establit la ruta per la clase Apilar per no tornar-la a demanar una vegada establida.
        boolean rutaApilarestablida= false;
        Scanner entrada = new Scanner(System.in);
        //Enmagatzema la opció del menú switch
        String opcio;

            do {
                System.out.println("Escull una de les següents opcions:");
                System.out.println("--------------------------------------PART1----------------------------------------");
                System.out.println("1. Crear animal. Escriu \"crear\" o el número de la opció .");
                System.out.println("2. Guardar (Engabiar) l'animal creat. Escriu \"guardar\" o el número de la opció .");
                System.out.println("3. Llegir (Soltar) un animal guardat. Escriu \"llegir\" o el número de la opció .");
                System.out.println("4. Crear, guardar (Engabiar) i llegir (Soltar) animal d'una vegada.Escriu. " +
                        "\"part1\" o el número de la opció  ");
                System.out.println("--------------------------------------PART2----------------------------------------");
                System.out.println("6. Guardar (Empilar) l'animal creat. Escriu \"empilar\" o el número de la opció .\"");
                System.out.println("7. Llegir (Desempilar) els animals creats des de l'últim fins al primer. Escriu " +
                        "\"desempilar\" o el número de la opció.");
                System.out.println("8. Crear, guardar (Empilar) i llegir (Desempilar)els animals d'una vegada. Escriu " +
                        "\"part2\" o el número de la opció.");
                System.out.println("9. Establir o canviar la ruta on apilar animals. Escriu \"canviar\" o el número de " +
                        "la opció.");
                System.out.print("10. Sortir. Escriu qualsevol cosa.");
                System.out.print("\n\topcio: ");

                opcio = entrada.nextLine().toLowerCase();

                switch (opcio) {
                    //Crear objecte animal a apliar
                    case"4":
                    case"part1":
                    case "1":
                    case "crear":
                        afegirSeparador();
                        System.out.println("Nom del animal: ");
                        nomAnimal = entrada.nextLine();
                        System.out.println("Codi del animal: (Només pot ser:\"EX\",\"EW\",\"CR\",\"EN\",\"VU\",\"LT\"," +
                                "\"NC\")");
                        codiConservacio = new Codi(entrada.nextLine().toUpperCase());
                        System.out.println(codiConservacio.getCodi());//Control
                        animal = new Animal(codiConservacio, nomAnimal);
                        System.out.println("Animal creat");
                        afegirSeparador();
                        //Si només s'ha escolllit l'opció crear es para i torna al menú
                        if(opcio.equals("1")||opcio.equals("crear"))break;
                    case "2":
                    case "guardar":
                        afegirSeparador();
                        //Comprova que hi ha un objecte disponible per emmagatzemar per iniciar el procés o no de guardar-ho.
                        if (animal == null) System.out.println("Primer has de crear un objecte animal.");
                        else {
                            zoo = new Zoo();
                            try{
                                zoo.Engabiar(animal);
                            }catch (IOException e){
                                e.getCause();
                                e.printStackTrace();
                            }
                        }
                        afegirSeparador();
                        //Si només s'ha escolllit l'opció guarda es para i torna al menú
                        if(opcio.equals("2")||opcio.equals("guardar"))break;
                    case "3":
                    case "llegir":
                        //Llegeix un objecte animal guardat
                        afegirSeparador();
                            greenpeace= new GreenPeace();
                            try {
                                greenpeace.Lliberar();
                            }catch (IOException  | ClassNotFoundException e){
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                            }
                        System.out.println("Presiona una tecla per seguir "+entrada.nextLine());
                        afegirSeparador();
                        break;
                    //-------------------------------------------PART2
                    case "8":
                    case "part2":
                        //Es el mateix que el case 1 xo amb la particularitat que esta invocat desde la opció que engloba
                        //tots els case de la part2. He preferit fer aixó a un goto.
                        afegirSeparador();
                        System.out.println("Nom del animal: ");
                        nomAnimal = entrada.next();
                        System.out.println("Codi del animal: (Només pot ser:\"EX\",\"EW\",\"CR\",\"EN\",\"VU\",\"LT\"," +
                                "\"NC\")");
                        codiConservacio = new Codi(entrada.nextLine().toUpperCase());
                        animal = new Animal(codiConservacio, nomAnimal);
                        System.out.println("Animal creat");
                        afegirSeparador();
                    case "6":
                    case "empilar":
                        afegirSeparador();
                        if (animal == null){System.out.println("Primer has de crear un objecte animal."); break;}
                        //Si la ruta de la clase empilar no esta establida, es fa des d'aquí
                        if(!rutaApilarestablida) {
                            //Estableix la ruta del objecte Apilar
                            rutaApilar = establirRutaApilar();
                            //Indica que la ruta esta establida
                            rutaApilarestablida = true;
                        }
                        try {
                            //ruta per defecte
                            if (rutaApilar.equals("")) ap = new Apilar();
                            else{
                                //ruta explícitament indicada
                                ap = new Apilar(new File(rutaApilar));}

                            ap.empilar(animal);
                        }catch(FileNotFoundException x){
                            new File(rutaApilar);
                        }catch(IOException e){
                            System.out.println("Error: "+e.getMessage());
                            e.printStackTrace();
                        }
                        afegirSeparador();
                        if(opcio.equals("6")||opcio.equals("empilar"))break;
                    case "7":
                    case "desempilar":
                        afegirSeparador();
                        //si abans no s'ha guardat cap objecte retornem al menú
                        if(!rutaApilarestablida&&animal==null){System.out.println("Primer has de guardar un objecte"); break;}
                        //llegeix tots els objectes del fitxer utilitzat per la clase.
                        Apilar ep;
                        if(rutaApilar.equals(""))ep = new Apilar ();
                        else  ep = new Apilar (new File(rutaApilar));
                        try{
                            ep.desEmpilar();
                        }catch(IOException | ClassNotFoundException e){
                            System.out.println("Error: "+e.getMessage());
                            e.printStackTrace();
                        }
                        afegirSeparador();
                        break;
                    case "canviar":
                    case "9":
                        rutaApilarestablida=true;
                        rutaApilar = establirRutaApilar();
                        break;
                    default:
                        System.out.println("Adeu!!!");
                        opcio="sortir";
                        break;
                }
            } while (!opcio.equals("sortir"));

    }
    public static void afegirSeparador(){
        System.out.println("-------------------------------------------------------------------------");
    }
    public static String establirRutaApilar(){
        Scanner entrada = new Scanner(System.in);
        String ruta;
        int opcio=0;
        do {
            System.out.println("Quin arxiu vols utilitar per empilar o desempilar? (Escull indicant el número de la opció).");
            System.out.println("1. Fitxer per defecte (el programa es qui ho decideix).");
            System.out.println("2. Crear o decidir en quin fitxer empilar l'animal (has d'indicar la ruta).");
            System.out.print("\topcio: ");
            try{
                opcio = Integer.parseInt(entrada.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Has d'escollir amb un 1 o un 2. Torna-ho a intentar.");
                continue;
            }
        }while(opcio!=1&&opcio!=2);

        if(opcio==1)return "";
        else{
            System.out.println("Escriu la ruta que vols establir per l'objecte \"Apilar\": ");
            ruta=entrada.nextLine();
        }
        return ruta;
    }
}
