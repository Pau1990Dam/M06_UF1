package Serialization;

import java.io.*;

/**
 * Created by pau on 10/10/15.
 */

/**
 * Escriu en el fitxer passat com a paràmetre l'objecte també passat i retorna els objectes animals de l'arxiu passat
 * com a paràmetre.
 */
public class Engabiar {
    /**
     *
     * @param arxiuAguardar
     * @param animal
     * @throws IOException
     */
    public void GuardarAnimal(File arxiuAguardar, Animal animal) throws IOException {
        //Obre el fitxer indicat en mode escritura
        FileOutputStream fs = new FileOutputStream(arxiuAguardar);
        //Indiquem a ObjectOuputStream que ha d'utilitzar el fitxer obert per FileOuputStream
        ObjectOutputStream ffs = new ObjectOutputStream(fs);
        //Donem a ObjectOuputStream  l'ordre d'escriure en el fitxer l'objecte amb el seu contingut
        ffs.writeObject(animal);
        //tanquem
        ffs.close();
    }

    /**
     *
     * @param arxiuAllegir
     * @return Animal
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Animal Soltar(File arxiuAllegir) throws IOException, ClassNotFoundException {
        Animal animalRecuperat;
        //Obre el fitxer indicat en mode lectura
        FileInputStream fe = new FileInputStream(arxiuAllegir);
        //Indiquem a ObjectInputStream d'on llegir.
        ObjectInputStream ffe = new ObjectInputStream(fe);
        //Donem l'ordre de llegir i fem el casting per convertir l'objecte genèric en un ojecte Animal que guardem
        //en una varible de la seva naturalessa
        animalRecuperat = (Animal)ffe.readObject();
        //retornem l'objecte llegit
        return animalRecuperat;
    }
}
