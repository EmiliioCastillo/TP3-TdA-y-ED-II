import java.util.Comparator;

class MyComparator implements Comparator<NodoHuffman> {

    @Override
    public int compare(NodoHuffman o1, NodoHuffman o2) {
        return o1.frecuenciaCaracter - o2.frecuenciaCaracter;
    }
}