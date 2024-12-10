
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Biblioteca{

    private static final Scanner in = new Scanner(System.in);

    ///Funcion para obtener fecha por teclado.
    private static Date inputFecha(){
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date fecha;
        while (true) { 
            System.out.println("Ingrese la fecha (YYYY-MM--DD).");
            try {
                fecha = df.parse(in.nextLine());
            } catch (ParseException e) {
                System.out.println("Dato ingresado no valido.");
                continue;
            }
            break;
        }
        return fecha;
    }

    private static void addGenero(){
        System.out.println("\nIngrese el nombre del genero.");
        String g = in.nextLine();
        Libro.addGenero(g);
    }

    private static void addCliente(ArrayList<Cliente> lista){
        System.out.println("\nIngrese Nombres.");
        String name = in.nextLine();
        System.out.println("\nIngrese Apellidos.");
        String lname = in.nextLine();

        lista.add(new Cliente(name, lname));
        System.out.println("Usuario registrado!!!"+lista.get(lista.size() - 1));
    }

    private static void addLibro(ArrayList<Libro> lista){
        System.out.println("\nIngrese el Titulo.");
        String title = in.nextLine();
        System.out.println("\nIngrese Autor.");
        String autor = in.nextLine();
        System.out.println("\nIngrese Editorial.");
        String edit = in.nextLine();
        System.out.println("\nIngrese Genero.");
        String gen = in.nextLine();

        System.out.println("\nIngrese valor por dia.");
        int value = in.nextInt();
        in.nextLine();
        System.out.println("\nIngrese Numero de Paginas.");
        int noPag = in.nextInt();
        in.nextLine();

        lista.add(new Libro(title, autor, edit, gen, value, noPag));
        System.out.println("Libro registrado!!!\n"+lista.get(lista.size() - 1));
    }

    private static void prestar(ArrayList<Cliente> listaC, ArrayList<Libro> listaL){
        ArrayList<Cliente> c = (ArrayList<Cliente>) listaC.clone();
        ArrayList<Libro> l = (ArrayList<Libro>) listaL.clone();
        ///Confirmar si hay clientes y libros.
        ArrayList<Libro> removal = new ArrayList<>();
        for(Libro unLibro: l){
            if(unLibro.someoneHasIt()){
                removal.add(unLibro);
            }
        }
        l.removeAll(removal);
        if(c.isEmpty() || l.isEmpty()){
            System.out.println("No hay clientes registrados y/o libros para prestar...");
            return;
        }

        ///Seleccion de cliente.
        int opc;
        while (true) {
            System.out.println("Seleccione un cliente:\n");
            Cliente.mostrarClientes(c);
            opc = in.nextInt();
            in.nextLine();

            if(opc < 1 || opc > c.size()){
                System.out.println("Opcion no valida.\n");
                continue;
            }
            break;
        }

        Cliente aux = c.get(opc - 1);

        while (true) {
            System.out.println("""
                                Elige una opcion:
                                1.-Prestar un libro.
                                2.-Prestar mas de un libro.
                                """);
            opc = in.nextInt();
            in.nextLine();

            if(opc != 1 && opc != 2){
                System.out.println("Opcion no valida.\n");
                continue;
            }
            break;
        }

        ///Seleccion de libros
        if(opc == 2){
            ArrayList<Libro> libros = new ArrayList<>();
            while (true) { 
                System.out.println("Elige una opcion.\n 0.-Terminar seleccion.");
                Libro.mostrarLibros(l);
                opc = in.nextInt();
                in.nextLine();

                if(opc == 0){
                    if(libros.isEmpty()){
                        System.out.println("No se ha seleccionado ningun libro.");
                        continue;
                    }
                    break;
                }else if(opc < 0 || opc > l.size()){
                    System.out.println("Opcion no valida.\n");
                    continue;
                }

                libros.add(l.get(opc - 1));
            }

            ///Prestamo de los libros seleccionados.
            aux.tomarLibros(libros, inputFecha());

            return;
        }


       ///Seleccion de un libro 
        Libro libro;
        while (true) { 
            System.out.println("Elige una opcion.\n 0.-Terminar seleccion.");
            Libro.mostrarLibros(l);
            opc = in.nextInt();
            in.nextLine();
            if(opc < 0 || opc > l.size()){
                System.out.println("Opcion no valida.\n");
                continue;
            }
            libro = l.get(opc - 1);
            break;
        }
        ///Prestamo de los libros seleccionados.
        aux.tomarLibro(libro, inputFecha());

    }

    private static void recibir(ArrayList<Cliente> listaC){
        ArrayList<Cliente> c = (ArrayList<Cliente>) listaC.clone();
        ///Confirmar si hay clientes y libros.
        ArrayList<Cliente> removal = new ArrayList<>();
        for(Cliente cliente: c){
            if(!cliente.hasABook()){
                removal.add(cliente);
            }
        }
        c.removeAll(removal);
        if(c.isEmpty()){
            System.out.println("No hay libros prestados...");
            return;
        }

        ///Seleccion de cliente.
        int opc;
        while (true) {
            System.out.println("Seleccione un cliente:\n");
            Cliente.mostrarClientes(c);
            opc = in.nextInt();
            in.nextLine();

            if(opc < 1 || opc > c.size()){
                System.out.println("Opcion no valida.\n");
                continue;
            }
            break;
        }

        ///Libros del cliente
        Cliente aux = c.get(opc - 1);
        ArrayList<Libro> lista = aux.getLibros();

        ///Cantidad de libros.
        while (true) {
            System.out.println("""
                                Elige una opcion:
                                1.-Recibir un libro.
                                2.-Recibir mas de un libro.
                                3.-Recibir todos los libros.
                                """);
            opc = in.nextInt();
            in.nextLine();

            if(opc < 1 || opc > 3){
                System.out.println("Opcion no valida.\n");
                continue;
            }
            break;
        }

        ///Seleccion de libros
        ArrayList<Libro> libros = new ArrayList<>();
        if(opc == 2){
            while (true) { 
                System.out.println("Elige una opcion.\n 0.-Terminar seleccion.");
                Libro.mostrarLibros(lista);
                opc = in.nextInt();
                in.nextLine();

                if(opc == 0){
                    if(libros.isEmpty()){
                        System.out.println("No se ha seleccionado ningun libro.");
                        continue;
                    }
                    break;
                }else if(opc < 0 || opc > lista.size()){
                    System.out.println("Opcion no valida.\n");
                    continue;
                }

                libros.add(lista.get(opc - 1));
            }

            ///Fecha de entrega

            ///Prestamo de los libros seleccionados.
            aux.regresarLibros(libros, inputFecha());

            return;
        }else if(opc == 3){///Devolver todos los libros.
            ///Fecha de entrega

            ///Prestamo de los libros seleccionados.
            aux.regresarLibros(inputFecha());
            return;
        }


       ///Seleccion de un libro 
        Libro libro;
        while (true) { 
            System.out.println("Elige una opcion.\n 0.-Terminar seleccion.");
            Libro.mostrarLibros(lista);
            opc = in.nextInt();
            in.nextLine();
            if(opc < 0 || opc > lista.size()){
                System.out.println("Opcion no valida.\n");
                continue;
            }
            libro = lista.get(opc - 1);
            break;
        }

        ///Fecha de entrega
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date fecha;
        while (true) { 
            System.out.println("Ingrese la fecha (YYYY-MM--DD).");
            try {
                fecha = df.parse(in.nextLine());
            } catch (ParseException e) {
                System.out.println("Dato ingresado no valido.");
                continue;
            }
            break;
        }
        ///Prestamo de los libros seleccionados.
        aux.regresarLibro(libro, fecha);
    }

    public static void main(String[] args){
        Byte control;
        boolean flag = true;

        ArrayList<Libro> libros = new ArrayList<>();
        ArrayList<Cliente> clientes = new ArrayList<>();

        ObjectInputStream reader;
        ObjectOutputStream writterL = null;
        ObjectOutputStream writterC = null;

        while(flag){
            System.out.println("""
                               \nElige una opcion:
                               1.-Registrar Genero Literario.
                               2.-Registrar un cliente.
                               3.-Registrar un libro.
                               4.-Prestar libro.
                               5.-Recibir libro prestado.
                               6.-Mostrar lista de generos literarios.
                               7.-Mostrar libros.
                               8.-Mostrar clientes.
                               9.-Salir.""");
            control = in.nextByte();
            in.nextLine();
            switch (control) {
                case 1 -> addGenero();

                case 2 -> addCliente(clientes);

                case 3 -> addLibro(libros);

                case 4 -> prestar(clientes, libros);

                case 5 -> recibir(clientes);

                case 6 -> System.out.println(Libro.showGenres());

                case 7 -> System.out.println(libros);

                case 8 -> System.out.println(clientes);

                case 9 -> {
                    System.out.println("\nBye bye.");
                    flag = false;
                }
                default -> System.out.println("\nEsa no es una opcion.");
            }
        }

    }
}