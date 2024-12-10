
import java.util.ArrayList;
import java.util.Date;

public class Libro{
    private static int NoLibros = 0;
    private static ArrayList<String> generos = new ArrayList<>();

    private final String Titulo;
    private final String Autor;
    private final String Editorial;
    private final String Genero;
    private final int noPag;

    private final int valuePerDay;
    private Date fechaEntrega;
    private boolean prestado = false;

    ///Constructores.
    public Libro(String Title, String autor, String Editorial, String Genero, int value, int noPag){
        this.Titulo = Title;
        this.Autor = autor;
        this.Editorial = Editorial;
        addGenero(Genero);
        this.Genero = Genero;
        this.noPag = noPag;
        valuePerDay = value;
        NoLibros++;
    }

    public Libro(String Title, String autor, String Editorial, int gIndex, int value, int noPag){
        this.Titulo = Title;
        this.Autor = autor;
        this.Editorial = Editorial;
        this.Genero = generos.get(gIndex);
        this.noPag = noPag;
        valuePerDay = value;
        NoLibros++;
    }

    ///Metodos estaticos.
    public static void mostrarLibros(ArrayList<Libro> lista){
        int i;
        Libro aux;
        for(i = 0; i < lista.size(); i ++){
            aux = lista.get(i);
            System.out.println((i + 1) + ".-" + (aux.Titulo));
        }
    }

    public static int addGenero(String name){
        if(generos.indexOf(name) < 0){
            System.out.println("El genero ya esta registrado.");
            return -1;
        }
        generos.add(name);
        return generos.indexOf(name);
    }

    public static String showGenres(){
        return generos.toString();
    }

    public static boolean isHere(String g){
        return generos.indexOf(g) != -1;
    }

    ///Metodos.
    public void prestar(Date fecha){
        this.fechaEntrega = fecha;
        prestado = false;
    }

    public double regresar(Date fecha){
        double deuda = (fecha.getYear()-2000) * fecha.getMonth() * fecha.getDay()
                        - (fechaEntrega.getYear()-2000) * fechaEntrega.getMonth() * fechaEntrega.getDay();
        fechaEntrega = null;
        if(deuda < 0){
            return 0;
        }
        deuda *= valuePerDay;
        return deuda;
    }

    @Override
    public String toString(){
        return "\nTitulo: "+ Titulo
        +"\nAutor: "+ Autor
        +"\nEditorial: "+ Editorial
        +"\nGenero: "+ Genero
        +"\nNumero de Paginas: "+ noPag
        +"\nEstado: "+ ((prestado)? "Prestado.": "Accesible.")
        +"\n";
    }
}