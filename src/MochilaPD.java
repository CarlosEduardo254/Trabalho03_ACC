import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MochilaPD implements EstrategiaMochila {

    @Override
    public Resultado resolver(int[] pesos, int[] valores, int W) {
        int n = pesos.length;
        // Alocação da matriz com rastreamento implícito
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                int pesoAtual = pesos[i - 1];
                int valorAtual = valores[i - 1];

                if (pesoAtual <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - pesoAtual] + valorAtual);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int valorOtimo = dp[n][W];
        List<Integer> itensSelecionados = new ArrayList<>();
        int wAtual = W;

        // Backtracking
        for (int i = n; i > 0; i--) {
            if (dp[i][wAtual] != dp[i - 1][wAtual]) {
                int indiceItem = i - 1;
                itensSelecionados.add(indiceItem);
                wAtual -= pesos[indiceItem];
            }
        }

        Collections.reverse(itensSelecionados);
        return new Resultado(valorOtimo, itensSelecionados);
    }
}