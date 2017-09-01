import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
/*Punt 1 - PseudoNavegador
Tenim una variable que serà ruta fitxer. El sistema ha de mostrar el nom, la ruta relativa, la
ruta absoluta i el directori pare. Ha de distingir si és un fitxer o si és un directori. Ha de
mostrar la data de creació i la data de l'última modificació. Si és un directori s'ha de
mostrar el llistat de fitxers. Si és un fitxer s'ha de mostrar si és ocult o no.
*/

/**
 * Aquesta classe extreu directament tota la informació de la carpeta o arxiu demanada
 * al enunciat amb excepció del temps de creació, que ho fa fent-se servir del mètode
 * getCreacion.
 *@author pau
 *@version 22/09/2015 PD: ho he comentat el día 24
 *Per cert he barrejat catlà i castellà n els comentaris.
 */
public class PseudoNavegador {

	private static String ruta = "/home/pau/Escritorio";

	public static void main (String[] args) {

		//Scanner entrada = new Scanner (System.in);
		//System.out.println("Introduce la ruta de la carpeta o fichero cuya información quieras conocer: ");
		//String ruta = entrada.nextLine();


	    String basePath = new File("").getAbsolutePath();
	    File archivo = new File (ruta);
	    System.out.println("Nombre del archivo: "+archivo.getName());
	    //Paths.get es un mètode static de la clase Paths (mira els imports).
	    //Paths.get(ruta d'orígen abstracta) Obté una ruta 
	    //.relativice(ruta destí abstracta) calcula y retorna la ruta relativa en compararla amb la obtinguda
	    System.out.println("Ruta relativa: "+Paths.get(new File(basePath).toURI()).relativize(Paths.get(archivo.toURI())));
	    System.out.println("Ruta absoluta: "+archivo.getAbsolutePath());
	    System.out.println("Carpeta padre: "+archivo.getParent());	    
	    System.out.println("Fecha de última modificación: "+new Date(archivo.lastModified()));
	    //Crida el mètode geetCreacion(File) x obtenir que retorna la data de creació
		try {
			System.out.println("Fecha de creación: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(getCreacion(archivo).toMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("Tipo de archivo: ");
	    if(archivo.isDirectory()){
	    	System.out.print("carpeta\n");
	    	System.out.println("Contenido de la carpeta: ");
	    	for(File i : archivo.listFiles()){
	    		System.out.println("\t\t- "+i.getName());
	    	}
	    }
	    else{
	    	System.out.print("fichero");
	    	if(archivo.isHidden())System.out.print(" oculto");
	    }
	}
	/**
	 * Aquest mètode extreu els atributs de la classe passada com
	 * a prámetre y retorna l'atribut de creció de l'arxiu.
	 * 
	 * @param file	Demana un objecte file x crear un objecte Path
	 * que la calse BasicFileAttributes llegira x guardar els atributs
	 * del arxiu.
	 * @return	tiempoCreacion retorna un objete tipus FileTime amb l'hora
	 * del creació del arxiu passat com a parámetre.
	 * @throws IOException Excepció obligatoria que llacen els objetes FileTime
	 * xo q no he implementat
	 */
	public static FileTime getCreacion(File file) throws IOException {
		Path p = Paths.get(file.getAbsolutePath());
		BasicFileAttributes view
		      = Files.getFileAttributeView(p, BasicFileAttributeView.class)
		                  .readAttributes();
		FileTime tiempoCreacion=view.creationTime();
		return tiempoCreacion;
	}	
}
