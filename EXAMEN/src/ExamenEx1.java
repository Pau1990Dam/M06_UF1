import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/*
(3 punts) Demaneu per pantalla una ruta. Heu de comprovar que aquesta ruta és un directori i heu de fer un llistat amb
els fitxers o directoris. De cada un dels fitxers heu de mostrar l'última data de modificació.

Ampliacio:
Feu un llistat recurrent, és a dir, que si hi ha un directori dintre d'un directori, faci el llistat d'aquell directori.
 S'ha d'indicar d'alguna manera que estem dintre del directori.

 L'usuari entra per pantalla un directori i un fitxer. Es comprova si el directori conté el fitxer.
 Si és així s'esborra el fitxer.
 */
public class ExamenEx1 {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ExamenEx1 ex1= new ExamenEx1();
        String ruta;
        File path;

        System.out.println("Introduzca una ruta que conduzca a una carptea: ");
        try {
            ruta=entrada.nextLine();
            path=new File(ruta);
            //Comprova que la ruta introduïda condueix a una carpeta
            if(path.isDirectory()){
                ex1.buscadorRecursiu(path);
            }else{
                System.out.println("La ruta entrada no conduce a ningún directorio. Prueva de nuevo: ");
                ExamenEx1.main(args);
            }
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     * Mostra tots el fitxers i carpetes d'una carpeta, última data de modificació dels fitxers i quina es la carpeta
     * pare de cada arxiu.
     * @param file
     */

    public void buscadorRecursiu(File file){
        if(file.isDirectory()){
            for(File i : file.listFiles()){
                buscadorRecursiu(i);

                System.out.println("\nLocalización: "+file.getParentFile()+"\n\t<DIR> "+file.getName()+"" +
                        "\n\tLocalizacion: "+file.getParentFile());
            }
        }else{
            System.out.println("\nLocalización: "+file.getParentFile()+"\n\tArchivo: "+file.getName()+"\n\t\túltima " +
                    "modificación: "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(file.lastModified())));

        }
    }

}

