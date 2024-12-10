import java.util.ArrayList;
import java.util.Date;

public final class Cliente{
    private static int noClientes = 0;

    private final String idCliente;
    private final String nombres;
    private final String apellidos;
    private final ArrayList<Libro> libros = new ArrayList<>();
    private int librosLeidos;
    private double deuda;

    public Cliente(String name, String lstName){
        noClientes ++;
        nombres = name;
        apellidos = lstName;
        idCliente = nombres.substring(0, 2) + apellidos.substring(0, 2) + noClientes;
    }

    public static void mostrarClientes(ArrayList<Cliente> lista){
        int i;
        Cliente aux;
        for(i = 0; i < lista.size(); i ++){
            aux = lista.get(i);
            System.out.println((i + 1) + ".-" + (aux.nombres) + " " + aux.apellidos + " ID: " +aux.idCliente);
        }
    }

    public void tomarLibro(Libro libro, Date fecha){
        libro.prestar(fecha);
        libros.add(libro);
    }

    public void tomarLibros(ArrayList<Libro> lista, Date fecha){
        for(Libro var: lista){
            var.prestar(fecha);
            libros.add(var);
        }
    }

    public void regresarLibro(Libro libro, Date fecha){
        endeudar(libro.regresar(fecha));
        libros.remove(libros.indexOf(libro));
    }

    public void regresarLibros(ArrayList<Libro> lista, Date fecha){
        double total = 0;
        for(Libro libro: lista){
            total += libro.regresar(fecha);
            libros.remove(libro);
        }
        endeudar(total);
    }

    public void regresarLibros(Date fecha){
        double total = 0;
        for(Libro libro: libros){
            total += libro.regresar(fecha);
        }
        endeudar(total);
    }

    private void endeudar(double extra){
        deuda += extra;
    }

    public void pagarDeuda(){
        deuda = 0;
    }

    public boolean pagarDeuda(double pago){
        if(pago < deuda){
            deuda -= pago;
            return true;
        }
        return false;
    }

    public ArrayList<Libro> getLibros(){
        return libros;
    }
    @Override
    public String toString(){
        return "\nID del cliente: "+ idCliente
        + "\nNombre: " + nombres + " " + apellidos
        + "\nNumero de libros leidos: " + librosLeidos;
    }

}