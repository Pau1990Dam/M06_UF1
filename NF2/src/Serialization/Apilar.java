package Serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

/**
 * Created by pau on 11/10/15.
 */

/**
 * Serialitza i deserialitza objectes en un mateix fitxer.
 */
public class Apilar {
    private File arxiu;
    private String rutaPerDefecte = new File("").getAbsolutePath();

    public Apilar(){
        arxiu = new File(rutaPerDefecte,"arxiuAnimalsXDefecte.ser");
        System.out.println("Arxiu per defecte de l'objecte Apilar: "+arxiu.getAbsolutePath());
    }

    /**
     * Aquests constructor només
     * @param arxiu
     */
    public Apilar(File arxiu){

        if(arxiu.isDirectory()&&arxiu.canWrite())
            this.arxiu=new File(arxiu,"arxiuAnimalsXDefecte.ser");
        else if(arxiu.isDirectory()&&!arxiu.canWrite())
            arxiu =new File(rutaPerDefecte,"arxiuAnimalsXDefecte.ser");
        else if(!arxiu.canWrite())new File(rutaPerDefecte,"arxiuAnimalsXDefecte.ser");
        else this.arxiu = arxiu;
        System.out.println("Arxiu establit de l'objecte Apilar: "+arxiu.getAbsolutePath());
    }

    //public void setFile(File canviArxiu){arxiu=canviArxiu;} Tal i com esta fet el programa no fa falta.
    public void empilar(Animal animal) throws IOException {
        int tamany;
        //Abrimos el archivo con un objeto RandomAccessFile(source, modo)
        RandomAccessFile raf = new RandomAccessFile(arxiu, "rw");
        //Creamos un fujo  ByteArrayOutputStream q permite escribir una secuencia de datos en un bloque de memoria
        //que se expande según se necesite. Es como una imitación del FileOutputStream pero pillando bytes.
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        //ObjectOutputStream es un filtro que escribe los bytes formateandolos como objetos
        ObjectOutput out = new ObjectOutputStream(bos);
        //Estos bytes que siguen la estrucutura del objeto dado se descargan en el flujo ByteArrayOutputStream
        out.writeObject(animal);
        out.close();
        // Metemos los bytes, que se corresponden con el objeto, en un array.
        byte[] buf = bos.toByteArray();
        //Colocamos el puntero de RAF al final del archivo para no sobreescribir contenido. El archivo se irá ampliando según se escriba
        raf.seek(raf.length());
        //Escribimos los bytes correspondientes al objeto, que como ya he dicho estan almacenados en el array de bytes, en el fichero abierto por RAF
        raf.write(buf);
        //Guyardamos el tamaño del objeto, es decir los bytes que este  ocupa, en un int.
        tamany=buf.length;
        //Escribimos el int al final del fichero.
        //Este int nos servirà para saber cuantos bytes ocupa el objeto. Con ello sabremos cuantos
        //bytes hemos de desplazar el puntero (raf.seek(valor a desplazar)) para leer el objeto.
        raf.writeInt(tamany);
        raf.close();
    }
    //Lee los objetos contenidos en el archivo dado desde el final al principio, es decir, desde el último objeto
    //insertado hasta el primero
    public void desEmpilar () throws IOException, ClassNotFoundException {
        int tamanyObjecte;
        //Abrimos el archivo con un objeto RandomAccessFile(source, modo)
        RandomAccessFile raf = new RandomAccessFile(arxiu, "rw");//rW significa lectura escritura
        //Colocamos el puntero al final del archivo
        raf.seek(raf.length());
        do{
            //Leemos el último int del archivo que almacena el tamaño del objeto escrito en las posiciones anteriores a
            //este. Nota: retrocedemos -4 porque los int siempre ocupan 4 bytes.
            raf.seek(raf.getFilePointer()-4);
            //Leemos el int. Al leer el  puntero de RAF se desplaza justo después de lo leído.
            tamanyObjecte=raf.readInt();
            //Nos colocamos antes del objeto a leer raf.seek(posición actual-tamaño del objeto a leer-tamaño del último int leido)
            raf.seek(raf.getFilePointer() - tamanyObjecte-4);
            //Llamamos a la función llegirObjecte
            llegirObjecte(raf, tamanyObjecte);
            //Nos volvemos a colocar antes del objeto leído
            raf.seek(raf.getFilePointer()-tamanyObjecte);
            //Mientras no llegue al principio del archivo avanzamos. Recuerda que leemos de final a principio
        }while(raf.getFilePointer()!=0);
        System.out.println("_____________________________________________");
        raf.close();
    }
    //función auxiliar que dado un objeto RandomAccessFile inicializado y el tamaño del objeto Animal a leer,
    //vuelca los bytes correspondientes al tamaño del objeto en un ObjectInputStream que filtra los bytes leeidos para darles
    //formato de objeto. Una vez desparseado el objeto lee sus atributos y los muestra por pantalla.
    private void llegirObjecte(RandomAccessFile raf, int tamanyObjecte) throws IOException, ClassNotFoundException {
        Animal animalDesempilat;
        byte [] objecteEnArrDeBytes=new byte[tamanyObjecte];
        raf.readFully(objecteEnArrDeBytes);
        ByteArrayInputStream bais =new ByteArrayInputStream(objecteEnArrDeBytes);
        ObjectInputStream lector = new ObjectInputStream(bais);
        animalDesempilat=(Animal)lector.readObject();
        lector.close();
        System.out.println("_____________________________________________");
        System.out.println("Nom del animal: " + animalDesempilat.getNom());
        System.out.println("Codi d'estat de conservació: "+animalDesempilat.getCodi());
    }

}
