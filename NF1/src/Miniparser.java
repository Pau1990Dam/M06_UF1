import java.io.*;
/*
 * Punt 2 - MiniParser
Hi ha un fitxer xml penjat al campus virtual que conté una serie de llibres amb alguns
camps. De cada llibre heu d'extreure la informació i imprimir una frase. Per exemple, el
llibre:
<libro>
<titulo>El último hombre</titulo>
<autor>Mary Shelley</autor>
<fecha>1820</fecha>
</libro>
s'ha de convertir en una frase del tipus:
El llibre amb títol ''El último hombre'' té com autor ''Mary Shelley'' i va ser publicat l'any
1820.
S'han de transformar tots els ítems del fitxer. El nombre d'ítems pot variar, però no
l'estructura dels ítems. Aquesta text s'ha de guardar en un fitxer anomenat «resum.txt».
 */

/**
 * En el main de la clase Miniparser se extrae la información de los libros
 * y se llama a un método static,llamado Escritor, que recoge un String con 
 * toda la información extraída para crear el archivo resumen.txt.
 * @author pau
 * @version 24/09/2015
 */
public class Miniparser {
	public static void main (String [] args){

		try{
			//System.getProperty("user.dir") = directorio actual de ejecución de la jvm, es decir, desde donde se esta
			// ejecutando el programa. Puede ser qualquier lugar donde el usuario tenga permisos de ejecutar la jvm
			File archivo = new File(System.getProperty("user.dir")+"/NF1", "biblio.xml");

			BufferedReader lectorXML = new BufferedReader( new FileReader (archivo));
			//Utilizo un StringBuilder para almacenar la información extraida xq es mucho más
			//eficiente que los Strings a la hora de concatenar texto. Con el método append 
			//del objeto StringBuilder concateno texto
			StringBuilder str = new StringBuilder().append("\n\n");
			String [] arr ={"El llibre amb títol \'","\' té com autor \'","\' i va ser publicat l'any "};
			String texto = lectorXML.readLine();
			int i= 0;
			//En este loop se extrae y da forma a toda la información relevante contenida en biblio.xml
			while(texto != null){
				//Cuando se llega a la etiqueta de entrada libro, en este este if, se ejecutan
				//las instruccuiones para leer las 3 etiquetas siguientes, una detras de otra,
				//con ayuda de la variable int i y continue, que salta al siguiente while.
				if(texto.contains("<libro") || i>0){
					texto = lectorXML.readLine();
					//Concatena el texto contenido en cada casilla del array de Strings con el contenido
					//correspondiente que hay entre los tags del xml
					str.append(arr[i]+texto.substring(texto.indexOf(">")+1,texto.lastIndexOf("<")));
					
					i++;
					if(i<3)continue;
					str.append("\n");
				}
				//Reinicia el valor de la variable "int i" xa no entrar en el if antes de tiempo
				i=0;
				texto = lectorXML.readLine();
			}

			lectorXML.close();
			System.out.println("\t\t\t\t\tAsi quedará elresultado:"+str.toString());
			Escritor(str.toString());

		}catch(FileNotFoundException e){
			System.out.println("Error1: Ruta --> "+e.getMessage()+" No encontrada.");
			e.printStackTrace();
		}catch(IOException e){
			System.out.println("Error2: "+e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("Error3: "+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * Este método pide como argumento el String que utilizará para escribir
	 * el archivo resumen.txt que creará en la carpeta indicada por el usuario.
	 * Funciona tanto en Linux como en Windows
	 * @param xml	String cuyo contenido se copiará en el archivo a crear
	 * resumen.txt
	 */
	public static void Escritor(String xml){

		System.out.print("Resultado -> ");
		File resum = new File(System.getProperty("user.dir"));


		try{
			//
			BufferedWriter escritor = new BufferedWriter (new FileWriter(new File(resum, "/NF1/resum.txt")));
			escritor.write(xml);
			escritor.close();
			System.out.print(resum.getAbsolutePath()+"/resum.txt");
		}catch(IOException e){
			System.out.println("Error4: "+e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("Error5: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
