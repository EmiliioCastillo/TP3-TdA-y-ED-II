import java.util.*;
import java.util.stream.Collectors;

public class Huffman {


    //Almacena el codigo generado para caracter
    Map<Character, String> mapCodigos;
    //Para las frecuencias de cada uno
    Map<Character, Integer> mapFrecuencia;
    //La cola de prioridad fundamental
    PriorityQueue<NodoHuffman> colaDePrioridad;
    //cadena a comprimir
    private String cadenaOriginal;
    //para almacenar todos los caracteres de la cadena original
    private List<Character> charList;

    NodoHuffman raiz;

    public Huffman(){
    inicializarEstructurasCodificacion();
    }
    private void inicializarEstructurasCodificacion(){
        this.mapCodigos = new HashMap<>();
        this.mapFrecuencia = new HashMap<>();
        NodoHuffman nodoHuffman= new NodoHuffman();
        raiz = null;
    }

    //En este metodo lo que hacemos es:
    // 1) convertir la cadena original en una lista de caracteres
    // 2) Recorremos la lista
    //A) Si el caracter no esta presente, se lo inicializa en 1 y sino se le suma
    // De esta forma se contabiliza su frecuencia
    private void ContabilizadorDeFrecuencia() {
        charList = this.cadenaOriginal.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        this.charList.forEach(character ->
                mapFrecuencia.put(character, !mapFrecuencia.containsKey(character) ? 1 : mapFrecuencia.get(character) + 1));
    }


    //función recursiva para generar los códigos de Huffman para cada carácter utilizando la estructura de
    // un árbol binario.
    // caso base; si la izquierda y la derecha son nulos
    // entonces es un nodo hoja, que significa que llegamos a un caracter y lo agregamos con su correspondiente codigo
    //Por eso se utiliza un Map con formato <K,V>
    // si vamos a la izquierda, agregamos "0" al código.
    // si vamos a la derecha agregamos "1" al código.
    // Hacemos llamadas recursivas para el sub arbol de la izquierda y
    // subárbol derecho del árbol generado.

    public void generarCodigo(NodoHuffman nodo, String s) {
        if (nodo.izquierdo == null && nodo.derecho == null && Character.isLetter(nodo.character)){
            this.mapCodigos.put(nodo.character, s);
            return;
        }
        if (nodo.izquierdo == null) throw new AssertionError();
        generarCodigo(nodo.izquierdo, s + "0");
        generarCodigo(nodo.derecho, s + "1");
    }



    // primero se recorre el mapa de frecuencias que contiene todos los caracteres de la frase y
    // la cantidad de ocurrencias de cada uno, y se obtiene los primeros nodos para generar el arbol de huffman
    // Generamos los nodos con las sumas para completar el arbol
    //La parte mas importante es la del while por que los de menor prioridad son los mayores y los de mayor prioridad son los menores
    //Entonces obtenemos los de menor prioridad para sumar sus valores(la frecuencia de su aparicion)
    //Y generamos un nuevo nodo para almacenarlo, sumar las frecuencias y a
    // la variable "f" se la utiliza para almacenar la suma de esa frecuencia y posteriormente convertirla en un nodo raiz

    public void crearArbolHuffman() {
        for (Map.Entry<Character, Integer> e : this.mapFrecuencia.entrySet()) {
            NodoHuffman nodo = new NodoHuffman();
            nodo.character = e.getKey();
            nodo.frecuenciaCaracter = e.getValue();
            nodo.izquierdo = null;
            nodo.derecho = null;
            this.colaDePrioridad.add(nodo);
        }
        while (colaDePrioridad.size() > 1) {
            //Se extraerán los nodos con las dos menores
            // frecuencias del montículo en cada iteración hasta que solo quede un nodo en el montículo.
            NodoHuffman x = colaDePrioridad.peek();
            colaDePrioridad.poll();
            NodoHuffman y = colaDePrioridad.peek();
            colaDePrioridad.poll();
            NodoHuffman f = new NodoHuffman();
            if (y == null) {
                throw new AssertionError();
            }
            f.frecuenciaCaracter = x.frecuenciaCaracter + y.frecuenciaCaracter;
            f.character = '-';
            f.izquierdo = x;
            f.derecho = y;
            this.raiz = f;
            colaDePrioridad.add(f);
        }
    }

    public void codificarCadena(String cadena){
        this.cadenaOriginal = cadena;
        // utilizamos una cola de prioridad para obtener los nodos con menor frecuencia y de esa manera generar el arbol de huffman
        colaDePrioridad = new PriorityQueue<>(cadenaOriginal.length(), new MyComparator());
        inicializarEstructurasCodificacion();
        ContabilizadorDeFrecuencia();
        crearArbolHuffman();
       generarCodigo(this.raiz, "");
        imprimirResultado();
    }

    public void imprimirResultado() {
        StringBuilder espaciador = new StringBuilder();
        if (this.cadenaOriginal.length() > 15) {
            espaciador.append(" ".repeat(this.cadenaOriginal.length() - 15));
        }
        StringBuilder cadenaResultante = new StringBuilder();
        List<Character> list = this.charList;
        for (Character caracter : list) cadenaResultante.append(this.mapCodigos.get(caracter));
        System.out.println("bits: " + cadenaResultante + " total de: " + cadenaResultante.length());
    }


}


