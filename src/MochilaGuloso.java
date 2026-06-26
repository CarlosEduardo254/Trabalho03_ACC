import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MochilaGuloso implements EstrategiaMochila {

    // Classe auxiliar interna apenas para ajudar na ordenação
    private static class Item implements Comparable<Item> {
        int indice;
        int peso;
        int valor;
        double razao;

        public Item(int indice, int peso, int valor) {
            this.indice = indice;
            this.peso = peso;
            this.valor = valor;
            this.razao = (double) valor / peso;
        }

        @Override
        public int compareTo(Item outro) {
            return Double.compare(outro.razao, this.razao);
        }
    }

    @Override
    public Resultado resolver(int[] pesos, int[] valores, int W) {
        int n = pesos.length;
        List<Item> itens = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            itens.add(new Item(i, pesos[i], valores[i]));
        }

        // Ordenação O(n log n) - Usa o TimSort também
        Collections.sort(itens);

        int valorTotal = 0;
        int wAtual = W;
        List<Integer> itensSelecionados = new ArrayList<>();

        for (Item item : itens) {
            if (item.peso <= wAtual) {
                itensSelecionados.add(item.indice);
                valorTotal += item.valor;
                wAtual -= item.peso;
            }
        }

        Collections.sort(itensSelecionados);
        return new Resultado(valorTotal, itensSelecionados);
    }
}