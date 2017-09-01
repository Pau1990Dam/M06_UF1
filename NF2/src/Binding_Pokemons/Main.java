package Binding_Pokemons; /**
 * Created by 14270729b on 27/10/15.
 */

import Binding_Pokemons.generated.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Esta clase permite mostrar, añadir, borrar y modificar elementos pokemons de un xml cuyo elemento raiz sea un
 * Podkedex, por medio de los ojetos generados con xjc y grácias a los métodos proporcionados por las clases  básicas de
 * JAXB.
 */
public class Main {
    //para hacer las pruebas con más comodidad introduce aquí la ruta al xml pokemons.
    private static String ruta =System.getProperty("user.dir")+"/NF2/src/Binding_Pokemons/pokemons.xml";

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        String opcion;
        //Objeto main que utilizo para da la opción a ejecutar todos los métodos.
        Main m = new Main();
        //Llamo a la función cambiaRuta() para comprovar que la variable ruta lleva a un xml y cambiarla si no es así.
        m.cambiaRuta();

        //Bucle while que implementa un switch que da la opción de ejecutar los métodos deseados del objeto Main.
        do{
            //Llamo a la función menu() para mostrar de forma ordenada las opciones que da el programa y la forma de
            // acceder a estas.
            opcion=m.menu();
            switch(opcion){
                case "1":
                case "leer":
                    //Seleccionada esta opción llamo a la función mostrar(String ruta) para visionar todos los pokemons
                    //contenidos en el xml referido por ruta.
                    m.mostrar(ruta);
                    opcion="";
                    System.out.println("Presiona intro para continuar...");
                    entrada.nextLine();
                    break;
                case "2":
                case "guardar":
                    //Seleccionada esta opción llamo a la función escribir(String ruta) para añadir cuantos pokémons se
                    //deseen. Además esta funcion visualiza el contenido xml para comprobar los cambios.
                    m.escribir(ruta);
                    System.out.println("Presiona intro para continuar...");
                    entrada.nextLine();
                    break;
                case "3":
                case "modificar":
                    //Seleccionada esta opción llamo a la fución modificarOborrar(String ruta, int opcion) para
                    //modificar el contenido de los tags de los pokémons que se desee.
                    m.modificarOborrar(ruta, 3);
                    System.out.println();
                    break;
                case "4":
                case "borrar":
                    //Seleccionada esta opción llamo a la fución modificarOborrar(String ruta, int opcion) para borrar
                    //los elementos pokemons que se desee.
                    m.modificarOborrar(ruta, 4);
                    System.out.println();
                    break;
                case "5":
                case "cambiar":
                    //Seleccionada esta llamo a la función cambiaRuta() para modificar el archivo xml al que apunta la
                    //variable String ruta.
                    ruta="";
                    m.cambiaRuta();
                    System.out.println();
                    break;
                default:
                    //Seleccionada esta opción dejamos de ejecutar el programa y salimos.
                    opcion="salir";
                    System.out.println("ADEU!");
                    break;
            }
        }while(!opcion.equals("salir"));

    }

    /**
     *El método mostrar(String) muestra de forma ordenada el contenido de los tags de todos los elementos pokemons del
     * archivo xml referido.
     * @param ruta  Contiene el path al archivo xml cuyos elemntos pokémon queremos ver.
     */
    public void mostrar(String ruta){
        File arxiuXML = new File(ruta);
        try{
            JAXBContext context = JAXBContext.newInstance(PokedexType.class);
            Unmarshaller xmlAjava = context.createUnmarshaller();
            PokedexType pokemons = (PokedexType) xmlAjava.unmarshal(arxiuXML);
            System.out.println("------------POKEMONS------------");
            for(PokemonType i: pokemons.getPokemon() ){
                System.out.println("Nombre: "+i.getNombre().getValue());
                System.out.println("Clase: "+i.getNombre().getClasse());
                System.out.println("PV: "+i.getPV());
                System.out.println("Ataque 1: "+i.getAtaque1());
                System.out.println("Ataque 2: "+i.getAtaque2());
                System.out.println("Etapa: "+i.getEtapa());
                System.out.println("--------------------------------");
            }
            System.out.println("Total pokemons: " + pokemons.getPokemon().size() + "\n");
        }catch(JAXBException e){
            e.printStackTrace();
        }
    }

    /**
     *El método escribir(String) mediante un método privado auxiliar crea una objeto de la clase generada PokedexType,
     *gracias al Unmarshalling, con todos los elementos pokémons existentes más los añadidos. Luego utiliza este objeto
     *creado para reescribir mediante el Marshalling el archivo xml dado. Por último muestra el XML  tal cual ha quedado.
     * @param ruta Contiene el path al archivo xml sobre el que escribiremos.
     * @see #insertar(String)   Método auxiliar que mediante Unmarshall del xml dado y la inserción de nuevos objetos
     * pokémon en el objeto en el que se ha recogido el resultado del Unmarshall crear un PokedexType que luego devuelve.
     */
    public void escribir(String ruta){
        File arxiuXML = new File(ruta);
        try{
            JAXBContext context = JAXBContext.newInstance(PokedexType.class);
            Marshaller XML = context.createMarshaller();
            XML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            PokedexType pokemons = insertar(ruta);
            XML.marshal(pokemons, arxiuXML);
            XML.marshal(pokemons, System.out);
        }catch(JAXBException e){
        e.printStackTrace();
        }
    }

    /**
     *insertar(String) Recoge en un PokedexType todos el contenido del xml dado, mediante Unmarshalling, y luego crea
     *tantos objetos PokemonType(que representan los elentos del XML) como se desee, para añadirlos al PokedexType
     *que al final se devolverá.
     * @param ruta Contiene el path al archivo xml sobre el que se hace el Unmarshalling.
     * @return PokedexType con todos los objetos pokémon del xml dado más los pokémons añadidos.
     */
    private PokedexType insertar(String ruta){
        Scanner entrada = new Scanner(System.in);
        File arxiuXML = new File(ruta);
        int numPokemons;
        PokemonType pokemon;
        NombreType nombreYclase;
        PokedexType pokemons= new PokedexType();

        System.out.println();
        try{
            JAXBContext context = JAXBContext.newInstance(PokedexType.class);
            Unmarshaller xmlAjava = context.createUnmarshaller();
            pokemons = (PokedexType) xmlAjava.unmarshal(arxiuXML);

            System.out.println("Indica cuantos pokemons quieres añadir: ");
            numPokemons= entrada.nextInt();
            entrada.nextLine();

            System.out.println("Introduzca los datos del/los pokémon/s: ");
            for(int i=1;i<=numPokemons;i++){
                pokemon = new PokemonType();
                nombreYclase = new NombreType();

                System.out.println("Nombre: ");
                nombreYclase.setValue(entrada.nextLine());
                System.out.println("Clase: ");
                nombreYclase.setClasse(entrada.nextLine());
                pokemon.setNombre(nombreYclase);
                System.out.println("PV: ");
                pokemon.setPV(entrada.nextLine());
                System.out.println("Ataque 1: ");
                pokemon.setAtaque1(entrada.nextLine());
                System.out.println("Ataque 2: ");
                pokemon.setAtaque2(entrada.nextLine());
                System.out.println("Etapa: ");
                pokemon.setEtapa(entrada.nextLine());
                pokemons.getPokemon().add(pokemon);
            }
        }catch(JAXBException e){
            e.printStackTrace();
        }catch(InputMismatchException nfe)
        {
            System.out.println("Introducción de datos equivocados. No se insertará ningún pokémon. Puedes volver a " +
                    "intentarlo.");
            numPokemons=0;
        }
        return pokemons;
    }

    /**
     *modificarOborrar(String, int) Recoge la información del xml dado, mediante Unmarshalling, crea una List con los
     * elementos recogidos (objetos PokemonType) que los modifica o elimina según la opción escogida mediante una serie
     * de  métodos y para acabar hace un Marshallign que efectua los cambios realizados en la Lista.
     * @param ruta Contiene el path al archivo xml sobre el que se hace el Marshalling y el Unamrshalling.
     * @param opcio Indica la opción del menú desde la que se accede al método para activar los métodos borrardor o
     * modificador.
     */
    public void modificarOborrar(String ruta, int opcio){
        File arxiuXML = new File(ruta);
        try{
            JAXBContext context = JAXBContext.newInstance(PokedexType.class);
            Unmarshaller xmlAjava = context.createUnmarshaller();
            PokedexType pokemons = (PokedexType) xmlAjava.unmarshal(arxiuXML);
            List<PokemonType> llistaPokemons=pokemons.getPokemon();

            menuModificarBorrar(llistaPokemons, pokemons, opcio);

            Marshaller m = context.createMarshaller();
            m.marshal(pokemons, new File(ruta));
        }catch(JAXBException e){
            e.printStackTrace();
        }
    }

    /**
     *menuModificarBorrar(List<PokemonType>,PokedexType, int) Crea un ArrayList de enteros con las posiciones de los
     * objetos pokémon de la Lista dada que se quieren borrar o modificar. A continuación ejecuta el método borrador
     * o modificador, según el usuario haya elejido en el menú, que utilizará el ArrayList de entero para modificar o
     * borrar solo aquellos objetos que el usuario haya indicado. Este menú también cuenta con la opción de cancelar
     * la operación de borrar o modificar con el fin de evitar su ejecución accidental.
     *
     * @param llistaPokemons    Objeto List que contiene todos los elementos pokémons del xml del cual algunos elementos
     *                          se quieren borrar o modificar.
     * @param pokemons          Objeto PokedexType que se utiliza para mostrar de forma resumida el contenido del xml
     *                          a modificar o borrar.
     * @param opcio Indica la opción del menú desde la que se accede al método para activar los métodos borrardor o
     * modificador.
     * @see #isInteger(String)  Método auxiliar que se utiliza para saber si se ha indicado correctamente que se quiere
     *                          hacer. Es decir, un rudimentario control de errores.
     */
    private void menuModificarBorrar(List<PokemonType> llistaPokemons, PokedexType pokemons, int opcio){
        Scanner entrada = new Scanner(System.in);
        ArrayList<Integer> ordenPokemonsXML=new ArrayList<>();  //ordenPokemonsXML
        int cont=1;
        String eleccion;
        String palabraAajustar="borrar";

        if(opcio==3)palabraAajustar="modificar";

        System.out.println("Instrucciones: ");
        System.out.println("- Indica el o los pokemons que quieres "+palabraAajustar+" escribiendo su/s número/s seguido" +
                " de intro.");
        System.out.println("- Por último escribe 0 para señalar que ya lo/s has indicado.");
        System.out.println("- Escribe \"cancelar\" o \"-1\" para cancelar la operación.");
        for (PokemonType i : pokemons.getPokemon()) {
            System.out.print(cont + ". Nombre: " + i.getNombre().getValue());
            System.out.print("\tClase: " + i.getNombre().getClasse() + "\n");
            cont++;
        }
        do{
            System.out.print(palabraAajustar.toUpperCase()+" pokemon/s (Cancelar : -1 o \"cancelar\". Ejecutar " +
                    "eleccion/es: 0) :");
            if((eleccion=entrada.nextLine()).equals("-1")||eleccion.equals("cancelar")){ break;}
            if(isInteger(eleccion)) {
                if (Integer.parseInt(eleccion) > 0 && Integer.parseInt(eleccion) -1<llistaPokemons.size())
                    ordenPokemonsXML.add(Integer.parseInt(eleccion));
                else if(Integer.parseInt(eleccion)!=0)
                    System.out.println("Eleccion ignorada: el número \"" + eleccion + "\" no se corresponde con " +
                            "ninguno de los pokémons de la lista.");
            }else
                System.out.println("Error: \""+eleccion+"\" no es un número entero. Intentalo de nuevo. ");

        }while(!eleccion.equals("0"));

        if(ordenPokemonsXML.size()>0){
            if(opcio==3)
                modificador(llistaPokemons, ordenPokemonsXML);
            else
                borrador(llistaPokemons, ordenPokemonsXML);
        }
    }

    /**
     *modificador(List<PokemonType>, ArrayList<Integer>) Permite sobreescribir los datos en cada uno de los atributos
     * de los objetos pokemons extraidos del xml que se quieren modificar.
     * @param llistaPokemons    Objeto List con todos los objetos pokémons del xml.
     * @param pokemonsAmodificar    ArrayList de enteros cuyos valores se corresponden con la posición de los objetos
     *                              pokémons del List que se quieren modificar.
     */
    private void modificador(List<PokemonType> llistaPokemons, ArrayList<Integer>pokemonsAmodificar){
        String nuevosDatos;
        Scanner entrada = new Scanner(System.in);
        for(int i: pokemonsAmodificar){

            System.out.print("\nNombre: " + llistaPokemons.get(i - 1).getNombre().getValue());
            System.out.print("\t Nuevo nombre: ");
            nuevosDatos = entrada.nextLine();
            llistaPokemons.get(i - 1).getNombre().setValue(nuevosDatos);

            System.out.print("\nClase: " + llistaPokemons.get(i - 1).getNombre().getClasse());
            System.out.print("\t Nueva Clase: ");
            nuevosDatos = entrada.nextLine();
            llistaPokemons.get(i - 1).getNombre().setClasse(nuevosDatos);

            System.out.print("\nPV: " + llistaPokemons.get(i-1).getPV());
            System.out.print("\t Nuevos PV: ");
            nuevosDatos = entrada.nextLine();
            llistaPokemons.get(i - 1).setPV(nuevosDatos);

            System.out.print("\nAtaque 1: " + llistaPokemons.get(i - 1).getAtaque1());
            System.out.print("\t Nuevo Ataque 1: ");
            nuevosDatos = entrada.nextLine();
            llistaPokemons.get(i - 1).setAtaque1(nuevosDatos);

            System.out.print("\nAtaque 2: " + llistaPokemons.get(i - 1).getAtaque2());
            System.out.print("\t Nuevo Ataque2: ");
            nuevosDatos = entrada.nextLine();
            llistaPokemons.get(i - 1).setAtaque2(nuevosDatos);

            System.out.print("\nEtapa: " + llistaPokemons.get(i - 1).getEtapa());
            System.out.print("\t Nueva Etapa: ");
            nuevosDatos = entrada.nextLine();
            llistaPokemons.get(i - 1).setEtapa(nuevosDatos);
        }
    }

    /**
     *borrador(List<PokemonType>, ArrayList<Integer>) Permite borrar los objetos pokémons del xml que se han seleccionado.
     * @param llistaPokemons    Objeto List con todos los objetos pokémons del xml.
     * @param pokemonsAborrar   ArrayList de enteros cuyos valores se corresponden con la posición de los objetos
     *                          pokémons del List que se quieren borrar.
     */
    private void borrador(List<PokemonType> llistaPokemons, ArrayList<Integer>pokemonsAborrar){
        int borrados=0;
        for(int i: pokemonsAborrar){
            llistaPokemons.remove(i-1-borrados);
            borrados++;
        }
    }

    /**
     *menu() método que permit elegir y muestra de manera ordenada y descriptiva las opciones del programa.
     * @return  opcion  String con el valor del case elegido.
     */
    private String menu(){
        Scanner entrada = new Scanner(System.in);
        String opcion;
        System.out.println("-------------------------------------------MENÚ-------------------------------------------");
        System.out.println("Elija una de las siguientes opciones:");
        System.out.println("1. Leer e insertar los pokemons del XML en un array. Escriba el número de la opcion o la" +
                "palabra \"leer\".");
        System.out.println("2. Insertar nuevos objetos pokemon en el array y escribirlos en el XML. Escriba el número " +
                "de la opcion o la palabra \"guardar\".");
        System.out.println("3. Modificar datos de los p3okemons . Escriba el número de la opcion o la palabra " +
                "\"modificar\".");
        System.out.println("4. Borrar pokemons del archivo xml. Escriba el número de la opcion o la palabra " +
                "\"borrar\".");
        System.out.println("5. Cambiar ruta del archivo XML. Escriba el número de la opcion o la palabra \"cambiar\".");
        System.out.println("6. Salir del programa. Escriba cuaquier otra cosa.\n");
        opcion=entrada.nextLine().toLowerCase();
        return opcion;
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

    /**
     *isInteger(String) método auxiliar que indica, devolviendo un booleano, si el String pasado por parámetro se puede
     * convertir en un entero o no.
     * @param str   String cuyo contenido se quiere saber si puede corresponder a un número entero o no.
     * @return  boolean Booleano que indica si el estring era convertible a int (true) o no (false).
     */
    private boolean isInteger(String str){
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
