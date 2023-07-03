import java.util.Scanner;

public class Principal {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        String cadena;


        System.out.print("Ingrese la cadena para su posterior codificacion: ");
        cadena = scanner.next();

        Huffman h = new Huffman();

        h.codificarCadena(cadena);
        h.imprimirResultado();
    }
}
