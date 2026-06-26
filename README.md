# Programação Dinâmica e Algoritmos Gulosos para o Problema da Mochila 0-1

Este projeto implementa e compara duas abordagens distintas para solucionar o Problema da Mochila 0-1 (Knapsack Problem): Programação Dinâmica e Algoritmo Guloso. O sistema realiza testes automatizados com instâncias adversariais e aleatórias, gerando dados de desempenho para análise de tempo de execução e qualidade das soluções.

## Requisitos

Para compilar, executar e gerar os gráficos deste projeto, você vai precisar ter instalado na sua máquina:
- Java Development Kit (JDK): Versão 21 ou superior.
- Python: Versão 3.x.
- Bibliotecas Python: `pandas` e `matplotlib` (necessárias para a geração dos gráficos comparativos).

## Estrutura do Projeto
O projeto está configurado como um projeto Java e é composto pelos seguintes diretórios e arquivos principais:
- `src/`: Diretório que contém todo o código-fonte Java e também recebe o arquivo de dados exportado.
	- EstrategiaMochila.java: Interface que define o contrato base para a resolução da mochila.
	- MochilaPD.java: Classe que implementa a solução exata utilizando Programação Dinâmica.
	- MochilaGuloso.java: Classe que implementa a solução heurística utilizando o Algoritmo Guloso, ordenando os itens pela razão valor/peso.
	- Resultado.java: Classe auxiliar que armazena o valor total obtido e a lista de itens selecionados.
	- Main.java: Ponto de entrada da aplicação. Orquestra as instâncias adversariais, processa as instâncias aleatórias e exporta os dados.
	- resultados_mochila.csv: Arquivo gerado pelo código Java após os testes, contendo informações como o tamanho de entrada (N), tempos de execução médios de cada algoritmo, valores das soluções e o percentual de qualidade da solução gulosa.
- plotar.py: Script Python responsável por ler o arquivo CSV e gerar visualizações dos dados.
- grafico_tempo.png e grafico_valor.png: Arquivos de imagem gerados pelo script Python com a plotagem final do desempenho (tempo em milissegundos e valor da solução obtida por cada abordagem).

## Passo a Passo para Execução

Siga as instruções abaixo para executar todo o fluxo do trabalho, desde a geração de dados no Java até a plotagem dos gráficos no Python.

### 1. Executar o Código Java

O código Java gera o arquivo de resultados que será consumido posteriormente pelo script Python.

**Via IDE (IntelliJ, Eclipse, VS Code):**
Basta importar a pasta como um projeto Java, abrir o arquivo `src/Main.java` e executá-lo.

**Via Terminal de Comandos:**
Navegue até o diretório `src/` do projeto e compile os arquivos Java:
```
cd src

javac *.java
```

Em seguida, execute a classe principal. A flag `-Xmx8G` é utilizada para aumentar o limite de memória da JVM para 8GB, o que é altamente recomendado pois a matriz de Programação Dinâmica consome bastante memória RAM em instâncias maiores (ex: N=5000):
```
java -Xmx8G Main
```

Atenção aos caminhos de arquivo: ao rodar dentro da pasta `src/`, o script `Main.java` vai guardar o arquivo de métricas com o nome `resultados_mochila.csv` diretamente neste diretório. O script Python, executado a partir da raiz, está configurado para ler exatamente de lá (`src/resultados_mochila.csv`).

### 2. Preparar o Ambiente Python (com venv)

A melhor prática para gerenciar as dependências do Python é o uso de um ambiente virtual (`venv`). Isso isola as bibliotecas do projeto da sua instalação global do sistema.

Navegue de volta para a raiz do projeto (se ainda estiver na pasta `src`):
```
cd ..
```

1. **Criar o ambiente virtual:**
```
python -m venv venv
```

2. **Ativar o ambiente virtual:**

- **No Windows:**
```
venv\Scripts\activate
```

- **No Linux / macOS:**
```
source venv/bin/activate
```

3. **Instalar as dependências:**
Com o ambiente ativado (você verá `(venv)` no seu terminal), instale as bibliotecas necessárias:
```
pip install pandas matplotlib
```

### 3. Gerar os Gráficos Comparativos

Com as dependências instaladas e o arquivo `resultados_mochila.csv` posicionado corretamente no diretório `src/`, execute o script de plotagem a partir da raiz do projeto:
```
python plotar.py
```

O script vai confirmar a leitura dos dados e processar as imagens. Ao final da execução, os arquivos `grafico_tempo.png` e `grafico_valor.png` serão criados na raiz do seu repositório, demonstrando as diferenças de escalabilidade e assertividade entre as abordagens implementadas.

**Nota:** Após terminar de trabalhar, você pode desativar o ambiente virtual do Python simplesmente digitando `deactivate` no seu terminal.
