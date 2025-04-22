/*Librerías a utilizar */
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

/*Clase Principal */
public class App {
    /*Creación de objeto para crear binario .dat */
    private static final String Archivo = "saldo.dat";

    /*Creación de Objeto para poder ingresar datos por el teclado */
    public static Scanner sc = new Scanner(System.in);

    /*Método para Limpiar caracteres de la Consola */
    public static void LimpiarConsola (){
    System.out.print("\033[H\033[2J");      /*Este código  */
    System.out.flush();
    }

    /*Metodo para Crear Archivo.dat */
    private static void CrearArch() {
        try (FileOutputStream fos = new FileOutputStream(Archivo);      /*crea una variable para ingresar datos como referencia del archivo */
            DataOutputStream dos = new DataOutputStream(fos)) {            
            /*Nombre y Saldo inicial */
            String nombre = "Armando Reyes";
            double saldo = 2001.16;
            /*Ingreso de los datos enviandolos como referencia */
            dos.writeUTF(nombre);
            dos.writeDouble(saldo);

            System.out.println("Archivo creado con éxito.");
        } catch (IOException e) {                                               /*Manejo de errores al crear archivo */
            System.err.println("Error al crear archivo: " + e.getMessage());
        }
    }

    /*Método para Consultar el Saldo */
    public static void Saldo (){
        LimpiarConsola();                                                           //Primero Limpia la consola
        try (FileInputStream fis = new FileInputStream(Archivo);                //Creación de variables usando referencias del archivo
            DataInputStream dis = new DataInputStream(fis)) {
            String nombre = dis.readUTF();                                      //Lectura de Cadena en formato UTF para el nombre
            double saldo = dis.readDouble();                                    //Lectura de saldo decimal
            System.out.println("Estimado " +nombre);
            System.out.println("Su saldo actual es: Q. " + saldo);
        } catch (IOException e) {
            System.err.println("Error al consultar saldo: " + e.getMessage());          //Manejo de errores de salida
        }    
    }

    /*Método para realizar retiros */
    private static void Retiro() {
        LimpiarConsola();
        try (FileInputStream fis = new FileInputStream(Archivo);                    //Creación de variables para obtener los datos del archivo
            DataInputStream dis = new DataInputStream(fis)) {

            String nombre = dis.readUTF();                                          //Lee el nombre guardado en el archivo
            double saldo = dis.readDouble();                                        //Lee el saldo

            System.out.print("Ingrese el monto a retirar: ");
            double monto = sc.nextDouble();                                         //Ingreso de monto que se desea retirar

            if (monto > saldo) {                                                    //Validación que el saldo sea suficiente para hacer retiro
                System.out.println("Saldo insuficiente.");                         //Si el monto a retirar es mayor al disponible genera un mensaje de error.
            } else {
                saldo -= monto;                                                     //Si el saldo es suficiente al saldo se le retira el monto y queda guardado el nuevo saldo.

                try (FileOutputStream fos = new FileOutputStream(Archivo);
                    DataOutputStream dos = new DataOutputStream(fos)) {

                    dos.writeUTF(nombre);                                           //Cuando válida el saldo entonces escribe en el archivo
                    dos.writeDouble(saldo);                                         //El nombre y el nuevo saldo

                    System.out.println("Retiro realizado con éxito. Saldo actual: " + saldo);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al retirar dinero: " + e.getMessage());       //Si sucede un error por ingreso de dato erroreo genera un error
        } catch (InputMismatchException e){
            System.out.println("Error, ha ingresado un caracter invalido, debe ingresar un número.");
            sc.next(); 
        }
    }

    //Método para Anunciar que la opción del menú es inválida.
    public static void OpcInc(){
        System.out.println("La opción seleccionada, no es válida ingrese una nuevamente.");
    }



    /*Método Menú de Opciones */
    public static void Menu () {
        int opcion=0;   
        CrearArch();                                             /*Llama al método para crear Archivo.dat */
        LimpiarConsola();                                        /*Limpia los caracteres de la consola */
        /*Ciclo Hacer Mientras para desplegar el menú */
        do{
        System.out.println("           Bienvenido");
        System.out.println("1)Consultar Saldo");
        System.out.println("2)Retirar Dinero");
        System.out.println("3)Salir");
        System.out.print("Por favor seleccione una opción: ");
        try {
            opcion = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: debe ingresar un número.");
            sc.next(); 
        }
                switch (opcion) { 
                    case 1: Saldo();
                                break;
                    case 2: Retiro();
                                break;
                    case 3: sc.close();
                            LimpiarConsola();
                            System.out.println("Saliendo del programa....");
                                break;
    
                    default: OpcInc();
                                break;
                }
        
        }while(opcion!=3);
    }

    /*Método Principal */
    public static void main(String[] args) throws Exception {
        Menu(); /*Llama al Menú de Opciones */  
    }
}   