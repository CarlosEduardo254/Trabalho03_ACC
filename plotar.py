import pandas as pd
import matplotlib.pyplot as plt

# Lê o CSV que foi gerado pelo seu programa em Java
df = pd.read_csv('src/resultados_mochila.csv')

# --- Gráfico 1: Tempos de Execução ---
plt.figure(figsize=(10, 5))
plt.plot(df['N'], df['Tempo_PD_Media_ms'], marker='o', color='red', label='Programação Dinâmica')
plt.plot(df['N'], df['Tempo_Guloso_Media_ms'], marker='s', color='blue', label='Algoritmo Guloso')
plt.title('Comparação de Tempo de Execução')
plt.xlabel('Número de Itens (N)')
plt.ylabel('Tempo Médio (milissegundos)')
plt.grid(True, linestyle='--', alpha=0.7)
plt.legend()
plt.tight_layout()
plt.savefig('grafico_tempo.png')

# --- Gráfico 2: Valores das Soluções ---
plt.figure(figsize=(10, 5))
plt.plot(df['N'], df['Valor_PD_Media'], marker='o', color='green', label='PD (Ótimo)')
plt.plot(df['N'], df['Valor_Guloso_Media'], marker='x', color='purple', label='Guloso')
plt.title('Comparação do Valor da Solução')
plt.xlabel('Número de Itens (N)')
plt.ylabel('Valor Médio da Solução')
plt.grid(True, linestyle='--', alpha=0.7)
plt.legend()
plt.tight_layout()
plt.savefig('grafico_valor.png')

print("Gráficos gerados com sucesso! Verifique os arquivos grafico_tempo.png e grafico_valor.png.")