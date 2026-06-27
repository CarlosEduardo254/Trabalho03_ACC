import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Main {

    public static void executarAdversariais(
        EstrategiaMochila pd,
        EstrategiaMochila guloso
    ) {
        System.out.println("=== Teste nas intâncias adversariais ===");

        // Instância 1
        int[] p1 = { 10, 20, 30 };
        int[] v1 = { 60, 100, 120 };
        int W1 = 50;
        System.out.println("\n[Instancia Adversarial 1]");
        Resultado rPD1 = pd.resolver(p1, v1, W1);
        Resultado rGuloso1 = guloso.resolver(p1, v1, W1);
        System.out.println(
            "PD: Valor = " + rPD1.valor + ", Itens = " + rPD1.itens
        );
        System.out.println(
            "Guloso: Valor = " + rGuloso1.valor + ", Itens = " + rGuloso1.itens
        );

        // Instância 2
        int[] p2 = { 1, 50, 50 };
        int[] v2 = { 10, 99, 99 };
        int W2 = 100;
        System.out.println("\n[Instancia Adversarial 2]");
        Resultado rPD2 = pd.resolver(p2, v2, W2);
        Resultado rGuloso2 = guloso.resolver(p2, v2, W2);
        System.out.println(
            "PD: Valor = " + rPD2.valor + ", Itens = " + rPD2.itens
        );
        System.out.println(
            "Guloso: Valor = " + rGuloso2.valor + ", Itens = " + rGuloso2.itens
        );
    }

    public static void main(String[] args) {
        EstrategiaMochila pd = new MochilaPD();
        EstrategiaMochila guloso = new MochilaGuloso();

        executarAdversariais(pd, guloso);

        int[] tamanhos = { 100, 500, 1000, 2000, 5000 };
        int numInstancias = 5;
        int numExecucoes = 10;

        try (FileWriter writer = new FileWriter("resultados_mochila.csv")) {
            writer.write(
                "N,Tempo_PD_Media_ms,Tempo_PD_DP_ms,Tempo_Guloso_Media_ms,Tempo_Guloso_DP_ms,Valor_PD_Media,Valor_Guloso_Media,Qualidade_Guloso_Percentual\n"
            );

            System.out.println("Iniciando bateria de experimentos pesados...");

            for (int n : tamanhos) {
                System.out.println("Processando tamanho N = " + n + "...");

                double somaValorPDGeral = 0,
                    somaValorGulosoGeral = 0;
                double somaQualidade = 0;

                // 2. Listas para armazenar individualmente cada amostra de tempo
                List<Double> temposPD = new ArrayList<>();
                List<Double> temposGuloso = new ArrayList<>();

                for (int id = 0; id < numInstancias; id++) {
                    int seed = 42 + id;
                    Random rand = new Random(seed);

                    int[] pesos = new int[n];
                    int[] valores = new int[n];
                    for (int i = 0; i < n; i++) {
                        pesos[i] = rand.nextInt(100) + 1;
                        valores[i] = rand.nextInt(100) + 1;
                    }
                    int W = 50 * n;

                    int valPD = 0;
                    for (int e = 0; e < numExecucoes; e++) {
                        long inicio = System.nanoTime();
                        valPD = pd.resolver(pesos, valores, W).valor;
                        long fim = System.nanoTime();
                        // Guardar tempo exato desta execução
                        temposPD.add((fim - inicio) / 1_000_000.0);
                    }

                    int valGuloso = 0;
                    for (int e = 0; e < numExecucoes; e++) {
                        long inicio = System.nanoTime();
                        valGuloso = guloso.resolver(pesos, valores, W).valor;
                        long fim = System.nanoTime();
                        temposGuloso.add((fim - inicio) / 1_000_000.0);
                    }

                    somaValorPDGeral += valPD;
                    somaValorGulosoGeral += valGuloso;
                    somaQualidade += (double) valGuloso / valPD;
                }

                // 3. Cálculo das Médias Totais
                double somaTempoPD = 0,
                    somaTempoGuloso = 0;
                for (double t : temposPD) somaTempoPD += t;
                for (double t : temposGuloso) somaTempoGuloso += t;

                double mediaTempoPD = somaTempoPD / temposPD.size();
                double mediaTempoGuloso = somaTempoGuloso / temposGuloso.size();

                // 4. Cálculo do Desvio Padrão
                double somaVarPD = 0,
                    somaVarGuloso = 0;
                for (double t : temposPD)
                    somaVarPD += Math.pow(t - mediaTempoPD, 2);
                for (double t : temposGuloso)
                    somaVarGuloso += Math.pow(t - mediaTempoGuloso, 2);

                double desvioPadraoPD = Math.sqrt(
                    somaVarPD / (temposPD.size() - 1)
                );
                double desvioPadraoGuloso = Math.sqrt(
                    somaVarGuloso / (temposGuloso.size() - 1)
                );

                double mediaValorPD = somaValorPDGeral / numInstancias;
                double mediaValorGuloso = somaValorGulosoGeral / numInstancias;
                double mediaQualidade = (somaQualidade / numInstancias) * 100.0;

                // 5. Escrita no CSV
                writer.write(
                    String.format(
                        Locale.US,
                        "%d,%.4f,%.4f,%.4f,%.4f,%.2f,%.2f,%.2f\n",
                        n,
                        mediaTempoPD,
                        desvioPadraoPD,
                        mediaTempoGuloso,
                        desvioPadraoGuloso,
                        mediaValorPD,
                        mediaValorGuloso,
                        mediaQualidade
                    )
                );

                System.out.printf(
                    Locale.US,
                    " -> Concluido N=%d. Qualidade Guloso: %.2f%%\n",
                    n,
                    mediaQualidade
                );
            }
            System.out.println(
                "Experimentos finalizados. Dados salvos em 'resultados_mochila.csv'."
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
