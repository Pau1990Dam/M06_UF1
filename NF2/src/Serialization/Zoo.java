package Serialization;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by pau on 10/10/15.
 */

/**
 * Guarda l'objecte  per paràmetre en un fitxer, i la ruta d'aquest fitxer en una variable de clase (llistaAnimalsEngabiats).
 * També conté un métode per enviar una de les rutes guardades (getGabia) que invoca, en la seva execució, i un altre
 * mètode pque elimina ruta continguda en la variaable de clase una vegada seleccionada, doncs aquest arxiu serà borrat
 * per GreenPeace.
 */
public class Zoo{
    private static TreeMap<Integer, String> llistaAnimalsEngabiats = new TreeMap<Integer, String>();
    private int i;
    private Scanner entrada = new Scanner(System.in);
    private File arxiu;
    private Engabiar gabia;
    private String ruta;

    public void Engabiar(Animal capturat) throws IOException {
        i = llistaAnimalsEngabiats.size();
        gabia= new Engabiar();
        System.out.println("Indica o crea l'arxiu on vols guardar l'animal: ");
        try{
            ruta=entrada.nextLine();
            arxiu= new File(ruta);
            gabia.GuardarAnimal(arxiu, capturat);
            //Per evitar guardar rutas duplicades o invàlides només es guarden si la ruta no esta emmagatzemada i l'arxiu existeix.
            if(!llistaAnimalsEngabiats.containsValue(arxiu.getAbsolutePath()) && arxiu.exists())
                llistaAnimalsEngabiats.put(i,arxiu.getAbsolutePath());
        }catch(Exception e){
            System.out.println("Error: Ruta incorrecta. Torna-ho a intentar");
            Engabiar(capturat);
        }
    }

    /**
     *
     * @return retorna la ruta escollida.
     */
    public String getGabia(){
        if(llistaAnimalsEngabiats.size()==0)return "";
        int x=0;
        String ruta;
        System.out.println("Obre una de les següents gabies (indica-ho amb el número de gabia): ");
        Iterator it = llistaAnimalsEngabiats.keySet().iterator();
        while(it.hasNext()){
            Integer key = (Integer) it.next();
            System.out.println("Gabia " + key + " -> ruta: " + llistaAnimalsEngabiats.get(key));
        }
        try {
            x = Integer.parseInt(entrada.nextLine());
            if(!llistaAnimalsEngabiats.containsKey(i)){
                System.out.println("Error: gabia no trobada. Torna-ho a intentar: ");
                getGabia();
            }
        }catch(java.util.InputMismatchException e){
            System.out.println("L'elecció s'ha de fer amb un enter. Torna-ho a intentar: ");
            getGabia();
        }
        ruta=llistaAnimalsEngabiats.get(x);
        removeGabia(x);
        return ruta;
    }

    /**
     * Elimina el fitxer llegit i la ruta emmagatzemada corresponent
     * @param key
     */
    private void removeGabia(int key){
        if(llistaAnimalsEngabiats.size()==0)return;
        if(llistaAnimalsEngabiats.containsKey(key)){
            llistaAnimalsEngabiats.remove(key);
        }
    }
}
