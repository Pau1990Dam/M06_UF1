package Serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Created by pau on 10/10/15.
 */

/**
 * Recull de la clase Zoo la informació dels fitxers escrits. Si n'hi han fitxers per llegir dóna l'opció
 * d'escollir quin fitxer (gabia) volem llegir (obrir). Una vegada s'eleccionat el fitxer a llegir, s'envia aquest
 * a un objecte Engabiar, i es recull l'objecte deserialitzat del fitxer per llegir els seus atributs. En llegir
 * els atributs de l'objecte borra l'arxiu.
 */
public class GreenPeace{
    private Animal animalAlliberat;
    private Zoo zoo;
    private Engabiar obrirGabia;
    private File arxiu;
    private String ruta;

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     */
    public void Lliberar() throws IOException, ClassNotFoundException {
        obrirGabia = new Engabiar();
        zoo = new Zoo();
        ruta = zoo.getGabia();
        if(ruta.equals("")){
            System.out.println("Per llegir (soltar) un objecte animal primer l'has de guardar.");
            return;
        }
        arxiu = new File(ruta);
        animalAlliberat=obrirGabia.Soltar(arxiu);
        System.out.println("Característiques de l'animal alliberat: ");
        System.out.println("Espècie: "+animalAlliberat.getNom());
        System.out.println("Codi d'estat de conservació: "+animalAlliberat.getCodi());
        arxiu.delete();
    }
}
