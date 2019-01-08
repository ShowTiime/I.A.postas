import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * 
 * A classe NaiveBayes � onde constar� o algoritmo e a base de dados.
 * O ArrayList database � composto por toda a base de dados.
 * 
 */

public class NaiveBayes {
	
	//ArrayList para armazenar a base de dados e testData
	private static ArrayList<Aposta> database = new ArrayList<>();
	private static ArrayList<Aposta> trainingData = new ArrayList<>();
	private static ArrayList<Aposta> testData = new ArrayList<>();
	
	
	/*
	 * 
	 * M�todo est�tico carregarDatabase() � respons�vel por carregar a base de dados vinda de um arquivo .csv
	 * e armazen�-la no ArrayList database.
	 * 
	 * O m�todo recebe como argumento o caminho onde o arquivo .csv com a base de dados se encontra.
	 * Ex.: D:\\PEDRO\\UFRPE\\5� Per�odo\\Intelig�ncia Artificial\\Projeto\\IA\\csv\\data.csv (para o SO windows)
	 * 
	 * Para ler o arquivo usa-se um objeto do tipo BufferedReader, no seu construtor passamos um objeto do tipo
	 * FileReader e no seu construtor passamos a String que recebemos como argumento com o caminho do arquivo.
	 * 
	 * Cada linha do arquivo (cada resultado de um jogo) � lido e armazenado na String resultado na parte do teste
	 * condicional, enquanto tiver resultados de jogos a serem lidos vamos armazenar os dados.
	 * 
	 * Um vetor de Strings � usado para armazenar as caracter�sticas de cada exemplo, o separador contido no .csv
	 * � passado como argumento do m�todo split (nesse caso, no arquivo indicado no caminho acima, o separador
	 * de campos � ',').
	 * 
	 * Ap�s isso, alteramos os atributos de um objeto Aposta com o vetor de Strings contendo todas as caracter�sti-
	 * cas do exemplo. Ao final adicionamos ao ArrayList database.
	 * 
	 */
	public static void carregarDatabase(String datapath) {
		
		String separador = ";";	//Separador de campos no arquivo .csv
		String resultado = "";	//String para armazenar cada linha lida do arquivo .csv
		BufferedReader conteudo = null;	//Objeto usado para ler a base de dados
		
		try {
			
			//abrindo o arquivo
			conteudo = new BufferedReader(new FileReader(datapath));
			while((resultado = conteudo.readLine()) != null) { //lendo cada linha do arquivo
				
				Aposta exemplo = new Aposta();	//Objeto que ser� usado para guardar os valores presentes no vetor de
				//Strings e logo depois adicionado ao atributo da classe, database.
				
				//ap�s a leitura, separa cada campo, especificando o separador, e 
				//armazenando no vetor de Strings
				String[] caracteristica = resultado.split(separador);
				
				try { 	//Setando os atributos do objeto exemplo (Aposta) com os elementos
						//do vetor de Strings que cont�m as caracter�sticas
					exemplo.setEmCasa(Integer.parseInt(caracteristica[0]));
					exemplo.setForaDeCasa(Integer.parseInt(caracteristica[1]));
					exemplo.setVitorias5jogos(Integer.parseInt(caracteristica[2]));
					exemplo.setDerrotas5jogos(Integer.parseInt(caracteristica[3]));
					exemplo.setStreak(Integer.parseInt(caracteristica[4]));
					exemplo.setConferencia(caracteristica[5]);
					exemplo.setScore(Double.parseDouble(caracteristica[6]));
					exemplo.setPosicao(Integer.parseInt(caracteristica[7]));
					exemplo.setB2b(Integer.parseInt(caracteristica[8]));
					exemplo.setRoadTrip(Integer.parseInt(caracteristica[9]));
					exemplo.setTitularLesionado(Integer.parseInt(caracteristica[10]));
					exemplo.setAllStars(Integer.parseInt(caracteristica[11]));
					exemplo.setOppEmCasa(Integer.parseInt(caracteristica[12]));
					exemplo.setOppForaDeCasa(Integer.parseInt(caracteristica[13]));
					exemplo.setOppVitorias5jogos(Integer.parseInt(caracteristica[14]));
					exemplo.setOppDerrotas5jogos(Integer.parseInt(caracteristica[15]));
					exemplo.setOppStreak(Integer.parseInt(caracteristica[16]));
					exemplo.setOppConferencia(caracteristica[17]);
					exemplo.setOppScore(Double.parseDouble(caracteristica[18]));
					exemplo.setOppPosicao(Integer.parseInt(caracteristica[19]));
					exemplo.setOppB2b(Integer.parseInt(caracteristica[20]));
					exemplo.setOppRoadTrip(Integer.parseInt(caracteristica[21]));
					exemplo.setOppTitularLesionado(Integer.parseInt(caracteristica[22]));
					exemplo.setOppAllStars(Integer.parseInt(caracteristica[23]));
					exemplo.setClasse(caracteristica[24]);
				} catch (NumberFormatException e) {
					System.out.println("Erro na Formata��o: \n" + e.getMessage());
				}
				
				//adicionando o resultado � base de dados
				NaiveBayes.database.add(exemplo);
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo n�o encontrado: \n" + e.getMessage());
		}catch (IOException e) {
			System.out.println("IO Exception: \n" + e.getMessage());
		} finally {
			if(conteudo != null) {
				try {
					conteudo.close(); //fechando o arquivo
				} catch (IOException e) {
					System.out.println("IO Error: \n" + e.getMessage());
				}
			}
		}
		
		dividirDatabase(database);
		
	}
	
	/*
	 * 
	 * M�todo est�tico carregartestData() � respons�vel por carregar os testData vinda de um arquivo .csv
	 * e armazen�-la no ArrayList testData.
	 * 
	 * O m�todo recebe como argumento o caminho onde o arquivo .csv com os testData se encontra.
	 * Ex.: D:\\PEDRO\\UFRPE\\5� Per�odo\\Intelig�ncia Artificial\\Projeto\\IA\\csv\\data.csv (para o SO windows)
	 * 
	 * Para ler o arquivo usa-se um objeto do tipo BufferedReader, no seu construtor passamos um objeto do tipo
	 * FileReader e no seu construtor passamos a String que recebemos como argumento com o caminho do arquivo.
	 * 
	 * Cada linha do arquivo (cada resultado de um jogo) � lido e armazenado na String resultado na parte do teste
	 * condicional, enquanto tiver resultados de jogos a serem lidos vamos armazenar os dados.
	 * 
	 * Um vetor de Strings � usado para armazenar as caracter�sticas de cada exemplo, o separador contido no .csv
	 * � passado como argumento do m�todo split (nesse caso, no arquivo indicado no caminho acima, o separador
	 * de campos � ',').
	 * 
	 * Ap�s isso, alteramos os atributos de um objeto Aposta com o vetor de Strings contendo todas as caracter�sti-
	 * cas do exemplo. Ao final adicionamos ao ArrayList testData.
	 * 
	 */
	public static void carregarTestData(String datapath) {
		
		String separador = ",";	//Separador de campos no arquivo .csv
		String resultado = "";	//String para armazenar cada linha lida do arquivo .csv
		BufferedReader conteudo = null;	//Objeto usado para ler a base de dados dos testData
		
		try {
			
			//abrindo o arquivo
			conteudo = new BufferedReader(new FileReader(datapath));
			while((resultado = conteudo.readLine()) != null) { //lendo cada linha do arquivo
				
				Aposta test = new Aposta();	//Objeto que ser� usado para guardar os valores presentes no vetor de
				//Strings e logo depois adicionado ao atributo da classe, database.
				
				//ap�s a leitura, separa cada campo, especificando o separador, e 
				//armazenando no vetor de Strings
				String[] caracteristica = resultado.split(separador);
				
				try { 	//Setando os atributos do objeto exemplo (Aposta) com os elementos
						//do vetor de Strings que cont�m as caracter�sticas
					test.setEmCasa(Integer.parseInt(caracteristica[0]));
					test.setForaDeCasa(Integer.parseInt(caracteristica[1]));
					test.setVitorias5jogos(Integer.parseInt(caracteristica[2]));
					test.setDerrotas5jogos(Integer.parseInt(caracteristica[3]));
					test.setStreak(Integer.parseInt(caracteristica[4]));
					test.setConferencia(caracteristica[5]);
					test.setScore(Double.parseDouble(caracteristica[6]));
					test.setPosicao(Integer.parseInt(caracteristica[7]));
					test.setB2b(Integer.parseInt(caracteristica[8]));
					test.setRoadTrip(Integer.parseInt(caracteristica[9]));
					test.setTitularLesionado(Integer.parseInt(caracteristica[10]));
					test.setAllStars(Integer.parseInt(caracteristica[11]));
					test.setOppEmCasa(Integer.parseInt(caracteristica[12]));
					test.setOppForaDeCasa(Integer.parseInt(caracteristica[13]));
					test.setOppVitorias5jogos(Integer.parseInt(caracteristica[14]));
					test.setOppDerrotas5jogos(Integer.parseInt(caracteristica[15]));
					test.setOppStreak(Integer.parseInt(caracteristica[16]));
					test.setOppConferencia(caracteristica[17]);
					test.setOppScore(Double.parseDouble(caracteristica[18]));
					test.setOppPosicao(Integer.parseInt(caracteristica[19]));
					test.setOppB2b(Integer.parseInt(caracteristica[20]));
					test.setOppRoadTrip(Integer.parseInt(caracteristica[21]));
					test.setOppTitularLesionado(Integer.parseInt(caracteristica[22]));
					test.setOppAllStars(Integer.parseInt(caracteristica[23]));
					test.setClasse(caracteristica[24]);
				} catch (NumberFormatException e) {
					System.out.println("Erro na Formata��o: \n" + e.getMessage());
				}
				
				//adicionando o resultado � base de dados de testData
				NaiveBayes.testData.add(test);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo n�o encontrado: \n" + e.getMessage());
		}catch (IOException e) {
			System.out.println("IO Exception: \n" + e.getMessage());
		} finally {
			if(conteudo != null) {
				try {
					conteudo.close(); //fechando o arquivo
				} catch (IOException e) {
					System.out.println("IO Error: \n" + e.getMessage());
				}
			}
		}
		
	}
	
	/*
	 * 
	 * Esse método é responsável pela divisão da base de dados, essa divisão se
	 * dá em 2/3 para treinamento e 1/3 para testes.
	 * 
	 * O método recebe como argumento a base de dados (que é um ArrayList de Aposta) e
	 * ao final da sua execução a base de dados é dividida em outros dois ArrayLists,
	 * um para os exemplos que serão utilizados no treinamento e outro para os exemplos
	 * que serão utilizados no teste. 
	 * 
	 */
	private static void dividirDatabase(ArrayList<Aposta> database) {
		
		Random numero = new Random(); //gerador de números aleatórios para gerar os índices.
		
		int qtdExemplos = database.size(); //variável com a quantidade de exemplos na base de dados.
		int tamTreino = (qtdExemplos * 2) / 3; //separando 2/3 da base de dados para treinamento.
		int tamTeste = qtdExemplos - tamTreino; //separando 1/3 da base de dados para teste.
		
		int indice = 0; //variável que guardará o índice.
		int count = 0;
		//ArrayList com os índices dos elementos da base de dados que foram usados pelo conjunto de treinamento.
		ArrayList<Integer> indiceTreino = new ArrayList<>();  
		
		// Enquanto o contador for menor que 2/3 da base de dados (tamanho do conjunto de treinamento),
		// um número aleatório é gerado e posto na variável índice. Após isso verifica-se se esse índice
		// já foi escolhido anteriormente; caso não tenha sido escolhido é adicionado ao Array indiceTreino
		// e o elemento da base de dados, cujo índice foi escolhido aleatoriamente, é adicionado ao conjunto
		// de treinamento.
		while(count < tamTreino) {
			
			indice = numero.nextInt(qtdExemplos);
			
			if(!indiceTreino.contains(indice)) {
				
				indiceTreino.add(indice);
				trainingData.add(NaiveBayes.database.get(indice));
				count++;
			}
			
		}
		
		count = 0;
		indice = 0;
		//ArrayList com os índices dos elementos da base de dados que foram usados pelo conjunto de treinamento.
		ArrayList<Integer> indiceTeste = new ArrayList<>();
		
		// Enquanto o contador for menor que 1/3 da base de dados (tamanho do conjunto de testes),
		// um número aleatório é gerado e posto na variável índice. Após isso verifica-se se esse índice
		// já foi escolhido anteriormente; caso não tenha sido escolhido é adicionado ao Array indiceTeste
		// e o elemento da base de dados, cujo índice foi escolhido aleatoriamente, é adicionado ao conjunto
		// de testes.
		while(count < tamTeste) {
			
			indice = numero.nextInt(qtdExemplos);
			
			if(!indiceTreino.contains(indice) && !indiceTeste.contains(indice)) {
				
				indiceTeste.add(indice);
				testData.add(NaiveBayes.database.get(indice));
				count++;
				
			}
			
		}
		
		
	}
	
	/*
	 * 
	 * M�todo run � o m�todo que cont�m o algoritmo em si, ele recebe como argumento a aposta que o usu�rio quer
	 * classificar, tal objeto tem o campo classe vazio.
	 * 
	 * O funcionamento do algoritmo � o mesmo que vimos em sala, primeiro calculamos P(Sim) e P(Nao) que est�o
	 * armazenados nas vari�veis pSim e pNao respectivamente. Ap�s isso calculamos P(X|Sim) e P(X|Nao), onde X
	 * � cada atributo do vetor de caracteristica da classe Aposta.
	 * 
	 * Todas as probabilidades dos atributos est�o armazenadas nas vari�ves pXSim e pXNao.
	 * 
	 */
	public static String classificar(Aposta aposta) {
		
		//Vari�veis para o c�lculo final.
		double probabilidadeDeSim = 0;
		double probabilidadeDeNao = 0;
		
		String resultado = "";	//String que retorna a classe final.
		
		double pSim = 0, pNao = 0;	//Vari�veis que guardam P(Sim) e P(N�o).
		int countPSim = 0, countPNao = 0; //Vari�veis que guardam o n�mero de exemplos classificados como Sim e Nao.
		int countSim = 0, countNao = 0;	//Vari�veis que guardam o numero de atributos na base de dados iguais ao 
										//atributo do objeto passado como argumento.
		
		//Vari�veis que guardam P(X|Sim) e P(X|Nao)
		double pEmCasaSim = 0, pEmCasaNao = 0;
		double pForaDeCasaSim = 0, pForaDeCasaNao = 0;
		double pVitorias5jogosSim = 0, pVitorias5jogosNao = 0;
		double pDerrotas5jogosSim = 0, pDerrotas5jogosNao = 0;
		double pStreakSim = 0, pStreakNao = 0;
		double pConferenciaSim = 0, pConferenciaNao = 0;
		double pScoreSim = 0, pScoreNao = 0;
		double pPosicaoSim = 0, pPosicaoNao = 0;
		double pb2bSim = 0, pb2bNao = 0;
		double pRoadTripSim = 0, pRoadTripNao = 0;
		double pTitularLesionadoSim = 0, pTitularLesionadoNao = 0;
		double pAllStarsSim = 0, pAllStarsNao = 0;
		double pOppEmCasaSim = 0, pOppEmCasaNao = 0;
		double pOppForaDeCasaSim = 0, pOppForaDeCasaNao = 0;
		double pOppVitorias5jogosSim = 0, pOppVitorias5jogosNao = 0;
		double pOppDerrotas5jogosSim = 0, pOppDerrotas5jogosNao = 0;
		double pOppStreakSim = 0, pOppStreakNao = 0;
		double pOppConferenciaSim = 0, pOppConferenciaNao = 0;
		double pOppScoreSim = 0, pOppScoreNao = 0;
		double pOppPosicaoSim = 0, pOppPosicaoNao = 0;
		double pOppB2bSim = 0, pOppB2bNao = 0;
		double pOppRoadTripSim = 0, pOppRoadTripNao = 0;
		double pOppTitularLesionadoSim = 0, pOppTitularLesionadoNao = 0;
		double pOppAllStarsSim = 0, pOppAllStarsNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como sim e nao na base de dados.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				countPSim++;
			} else {
				countPNao++;
			}
		}
		
		//C�lculo P(Sim) e P(N�o)
		pSim = (double) countPSim / NaiveBayes.database.size();
		pNao = (double) countPNao / NaiveBayes.database.size();
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo EmCasa
		//igual ao atributo EmCasa do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getEmCasa() == NaiveBayes.database.get(i).getEmCasa()) {
					countSim++; //Conta os exemplos classificados como Sim
				}
			} else {
				if(aposta.getEmCasa() == NaiveBayes.database.get(i).getEmCasa()) {
					countNao++; //Conta os exemplos classificados como Sim
				}
			}
		}
		
		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(EmCasa|Sim) e P(EmCasa|Nao).
		pEmCasaSim = (double) countSim / countPSim;
		pEmCasaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo ForaDeCasa
		//igual ao atributo ForaDeCasa do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getForaDeCasa() == NaiveBayes.database.get(i).getForaDeCasa()) {
					countSim++;
				}
			} else {
				if(aposta.getForaDeCasa() == NaiveBayes.database.get(i).getForaDeCasa()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(ForaDeCasa|Sim) e P(ForaDeCasa|Nao).
		pForaDeCasaSim = (double) countSim / countPSim;
		pForaDeCasaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo Vitorias5jogos
		//igual ao atributo Vitorias5jogos do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getVitorias5jogos() == NaiveBayes.database.get(i).getVitorias5jogos()) {
					countSim++;
				}
			} else {
				if(aposta.getVitorias5jogos() == NaiveBayes.database.get(i).getVitorias5jogos()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
			
		//C�lculo P(Vitorias5jogos|Sim) e P(Vitorias5jogos|Nao).
		pVitorias5jogosSim = (double) countSim / countPSim;
		pVitorias5jogosNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo Derrotas5jogos
		//igual ao atributo Derrotas5jogos do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getDerrotas5jogos() == NaiveBayes.database.get(i).getDerrotas5jogos()) {
					countSim++;
				}
			} else {
				if(aposta.getDerrotas5jogos() == NaiveBayes.database.get(i).getDerrotas5jogos()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(Derrotas5jogos|Sim) e P(Derrotas5jogos|Nao).
		pDerrotas5jogosSim = (double) countSim / countPSim;
		pDerrotas5jogosNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo Streak
		//igual ao atributo Streak do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getStreak() == NaiveBayes.database.get(i).getStreak()) {
					countSim++;
				}
			} else {
				if(aposta.getStreak() == NaiveBayes.database.get(i).getStreak()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(Streak|Sim) e P(Streak|Nao).
		pStreakSim = (double) countSim / countPSim;
		pStreakNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo Conferencia
		//igual ao atributo Conferencia do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getConferencia().equals(NaiveBayes.database.get(i).getConferencia())) {
					countSim++;
				}
			} else {
				if(aposta.getConferencia().equals(NaiveBayes.database.get(i).getConferencia())) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(Conferencia|Sim) e P(Conferencia|Nao).
		pConferenciaSim = (double) countSim / countPSim;
		pConferenciaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo Score
		//igual ao atributo Score do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getScore() - 0.05 >= NaiveBayes.database.get(i).getScore() && aposta.getScore() + 0.05 <= NaiveBayes.database.get(i).getScore()) {
					countSim++;
				}
			} else {
				if(aposta.getScore() - 0.05 >= NaiveBayes.database.get(i).getScore() && aposta.getScore() + 0.05 <= NaiveBayes.database.get(i).getScore()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(Score|Sim) e P(Score|Nao).
		pScoreSim = (double) countSim / countPSim;
		pScoreNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo Posicao
		//igual ao atributo Posicao do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getPosicao() == NaiveBayes.database.get(i).getPosicao()) {
					countSim++;
				}
			} else {
				if(aposta.getPosicao() == NaiveBayes.database.get(i).getPosicao()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(Posicao|Sim) e P(Posicao|Nao).
		pPosicaoSim = (double) countSim / countPSim;
		pPosicaoNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo b2b
		//igual ao atributo b2b do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getB2b() == NaiveBayes.database.get(i).getB2b()) {
					countSim++;
				}
			} else {
				if(aposta.getB2b() == NaiveBayes.database.get(i).getB2b()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(b2b|Sim) e P(b2b|Nao).
		pb2bSim = (double) countSim / countPSim;
		pb2bNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo RoadTrip
		//igual ao atributo RoadTrip do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getRoadTrip() == NaiveBayes.database.get(i).getRoadTrip()) {
					countSim++;
				}
			} else {
				if(aposta.getRoadTrip() == NaiveBayes.database.get(i).getRoadTrip()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(RoadTrip|Sim) e P(RoadTrip|Nao).
		pRoadTripSim = (double) countSim / countPSim;
		pRoadTripNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo TitularLesionado
		//igual ao atributo TitularLesionado do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getTitularLesionado() == NaiveBayes.database.get(i).getTitularLesionado()) {
					countSim++;
				}
			} else {
				if(aposta.getTitularLesionado() == NaiveBayes.database.get(i).getTitularLesionado()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(TitularLesionado|Sim) e P(TitularLesionado|Nao).
		pTitularLesionadoSim = (double) countSim / countPSim;
		pTitularLesionadoNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo AllStars
		//igual ao atributo AllStars do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getAllStars() == NaiveBayes.database.get(i).getAllStars()) {
					countSim++;
				}
			} else {
				if(aposta.getAllStars() == NaiveBayes.database.get(i).getAllStars()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(AllStars|Sim) e P(AllStars|Nao).
		pAllStarsSim = (double) countSim / countPSim;
		pAllStarsNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//
		//
		//
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppEmCasa
		//igual ao atributo OppEmCasa do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppEmCasa() == NaiveBayes.database.get(i).getOppEmCasa()) {
					countSim++; //Conta os exemplos classificados como Sim
				}
			} else {
				if(aposta.getOppEmCasa() == NaiveBayes.database.get(i).getOppEmCasa()) {
					countNao++; //Conta os exemplos classificados como Sim
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppEmCasa|Sim) e P(OppEmCasa|Nao).
		pOppEmCasaSim = (double) countSim / countPSim;
		pOppEmCasaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppForaDeCasa
		//igual ao atributo OppForaDeCasa do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppForaDeCasa() == NaiveBayes.database.get(i).getOppForaDeCasa()) {
					countSim++;
				}
			} else {
				if(aposta.getOppForaDeCasa() == NaiveBayes.database.get(i).getOppForaDeCasa()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppForaDeCasa|Sim) e P(OppForaDeCasa|Nao).
		pOppForaDeCasaSim = (double) countSim / countPSim;
		pOppForaDeCasaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppVitorias5jogos
		//igual ao atributo OppVitorias5jogos do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppVitorias5jogos() == NaiveBayes.database.get(i).getOppVitorias5jogos()) {
					countSim++;
				}
			} else {
				if(aposta.getOppVitorias5jogos() == NaiveBayes.database.get(i).getOppVitorias5jogos()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppVitorias5jogos|Sim) e P(OppVitorias5jogos|Nao).
		pOppVitorias5jogosSim = (double) countSim / countPSim;
		pOppVitorias5jogosNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppDerrotas5jogos
		//igual ao atributo OppDerrotas5jogos do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppDerrotas5jogos() == NaiveBayes.database.get(i).getOppDerrotas5jogos()) {
					countSim++;
				}
			} else {
				if(aposta.getOppDerrotas5jogos() == NaiveBayes.database.get(i).getOppDerrotas5jogos()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppDerrotas5jogos|Sim) e P(OppDerrotas5jogos|Nao).
		pOppDerrotas5jogosSim = (double) countSim / countPSim;
		pOppDerrotas5jogosNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppStreak
		//igual ao atributo OppStreak do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppStreak() == NaiveBayes.database.get(i).getOppStreak()) {
					countSim++;
				}
			} else {
				if(aposta.getOppStreak() == NaiveBayes.database.get(i).getOppStreak()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppStreak|Sim) e P(OppStreak|Nao).
		pOppStreakSim = (double) countSim / countPSim;
		pOppStreakNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppConferencia
		//igual ao atributo OppConferencia do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppConferencia().equals(NaiveBayes.database.get(i).getOppConferencia())) {
					countSim++;
				}
			} else {
				if(aposta.getOppConferencia().equals(NaiveBayes.database.get(i).getOppConferencia())) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppConferencia|Sim) e P(OppConferencia|Nao).
		pOppConferenciaSim = (double) countSim / countPSim;
		pOppConferenciaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppScore
		//igual ao atributo OppScore do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppScore() - 0.05 >= NaiveBayes.database.get(i).getOppScore() && aposta.getOppScore() + 0.05 <= NaiveBayes.database.get(i).getOppScore()) {
					countSim++;
				}
			} else {
				if(aposta.getOppScore() - 0.05 >= NaiveBayes.database.get(i).getOppScore() && aposta.getOppScore() + 0.05 <= NaiveBayes.database.get(i).getOppScore()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppScore|Sim) e P(OppScore|Nao).
		pOppScoreSim = (double) countSim / countPSim;
		pOppScoreNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppPosicao
		//igual ao atributo OppPosicao do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppPosicao() == NaiveBayes.database.get(i).getOppPosicao()) {
					countSim++;
				}
			} else {
				if(aposta.getOppPosicao() == NaiveBayes.database.get(i).getOppPosicao()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppPosicao|Sim) e P(OppPosicao|Nao).
		pOppPosicaoSim = (double) countSim / countPSim;
		pOppPosicaoNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppB2b
		//igual ao atributo OppB2b do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppB2b() == NaiveBayes.database.get(i).getOppB2b()) {
					countSim++;
				}
			} else {
				if(aposta.getOppB2b() == NaiveBayes.database.get(i).getOppB2b()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppB2b|Sim) e P(OppB2b|Nao).
		pOppB2bSim = (double) countSim / countPSim;
		pOppB2bNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppRoadTrip
		//igual ao atributo OppRoadTrip do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppRoadTrip() == NaiveBayes.database.get(i).getOppRoadTrip()) {
					countSim++;
				}
			} else {
				if(aposta.getOppRoadTrip() == NaiveBayes.database.get(i).getOppRoadTrip()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppRoadTrip|Sim) e P(OppRoadTrip|Nao).
		pOppRoadTripSim = (double) countSim / countPSim;
		pOppRoadTripNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppTitularLesionado
		//igual ao atributo OppTitularLesionado do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppTitularLesionado() == NaiveBayes.database.get(i).getOppTitularLesionado()) {
					countSim++;
				}
			} else {
				if(aposta.getOppTitularLesionado() == NaiveBayes.database.get(i).getOppTitularLesionado()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppTitularLesionado|Sim) e P(OppTitularLesionado|Nao).
		pOppTitularLesionadoSim = (double) countSim / countPSim;
		pOppTitularLesionadoNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo OppAllStars
		//igual ao atributo OppAllStars do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse().equals("sim")) {
				if(aposta.getOppAllStars() == NaiveBayes.database.get(i).getOppAllStars()) {
					countSim++;
				}
			} else {
				if(aposta.getOppAllStars() == NaiveBayes.database.get(i).getOppAllStars()) {
					countNao++;
				}
			}
		}

		if (countSim == 0) countSim++;
		if (countNao == 0) countNao++;
		
		//C�lculo P(OppAllStars|Sim) e P(OppAllStars|Nao).
		pOppAllStarsSim = (double) countSim / countPSim;
		pOppAllStarsNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
			
		//Calcular P(X|Sim) e P(X|Nao) para os outros atributos.
		
		//C�lculo final da probabilidade
		probabilidadeDeSim = pSim * pEmCasaSim * pForaDeCasaSim * pVitorias5jogosSim * pDerrotas5jogosSim * 
				pStreakSim * pConferenciaSim * pScoreSim * pPosicaoSim * pb2bSim * pRoadTripSim * pTitularLesionadoSim * 
				pAllStarsSim * pOppEmCasaSim * pOppForaDeCasaSim * pOppVitorias5jogosSim * pOppDerrotas5jogosSim * 
				pOppStreakSim * pOppConferenciaSim * pOppScoreSim * pOppPosicaoSim * pOppB2bSim * pOppRoadTripSim * 
				pOppTitularLesionadoSim * pOppAllStarsSim;
		
		probabilidadeDeNao = pNao * pEmCasaNao * pForaDeCasaNao * pVitorias5jogosNao * pDerrotas5jogosNao * 
				pStreakNao * pConferenciaNao * pScoreNao * pPosicaoNao * pb2bNao * pRoadTripNao * pTitularLesionadoNao * 
				pAllStarsNao * pOppEmCasaNao * pOppForaDeCasaNao * pOppVitorias5jogosNao * pOppDerrotas5jogosNao * 
				pOppStreakNao * pOppConferenciaNao * pOppScoreNao * pOppPosicaoNao * pOppB2bNao * pOppRoadTripNao * 
				pOppTitularLesionadoNao * pOppAllStarsNao;
		
		//System.out.println(probabilidadeDeSim + " " + probabilidadeDeNao);
		
		resultado = (probabilidadeDeSim >= probabilidadeDeNao) ? "sim" : "nao";
		return resultado;
		
	}
	
	/*
	 * M�todo que classifica todos os testData armazenados no ArrayList
	 * */
	public static void classificarTestData() {
		
		int verdadeiroPositivo = 0, verdadeiroNegativo = 0, falsoPositivo = 0, falsoNegativo = 0;
		
		for (int i = 0; i < testData.size(); i++) {
			Aposta aposta = testData.get(i);
			String resultado = classificar(aposta);
			//System.out.println("Aposta " + (i+1) + " = " + resultado);
			
			if (aposta.getClasse().equals(resultado)) {
				if(aposta.getClasse().equals("sim")) {
					verdadeiroPositivo++;
				} else {
					verdadeiroNegativo++;
				}
			} else {
				if(aposta.getClasse().equals("sim")) {
					falsoNegativo++;
				} else {
					falsoPositivo++;
				}
			}
			
		}
		
		System.out.println("Quantidade de Exemplos: " + NaiveBayes.database.size());
		System.out.println("Quantidade de Exemplos de Treino: " + NaiveBayes.trainingData.size());
		System.out.println("Quantidade de Exemplos de Teste: " + NaiveBayes.testData.size());
		
		imprimeMatrizConfusao(verdadeiroPositivo, verdadeiroNegativo, falsoPositivo, falsoNegativo);
		exibirMetricas(verdadeiroPositivo, verdadeiroNegativo, falsoPositivo, falsoNegativo);
		
	}
	
	/*
	 * 
	 * Método que imprime a Matriz de Confusão
	 * 
	 * Os argumentos do método são:
	 * 	-> Verdadeiro Positivo
	 *  -> Verdadeiro Negativo
	 *  -> Falso Positivo
	 *  -> Falso Negativo
	 *  
	 */
	private static void imprimeMatrizConfusao(int vp, int vn, int fp, int fn) {
		System.out.println("\n========== Matriz de Confusao ==========\n");
		System.out.printf("%6s %6s  \t<-- classificado como\n", "a", "b");
		System.out.printf("%6d %6d %4c\ta = sim\n", vp, fn, '|');
		System.out.printf("%6d %6d %4c\tb = nao\n\n", fp, vn, '|');
	}
	
	
	/*
	 * 
	 * Método que imprime as Métricas para a Avaliação de Desempenho, tais métricas são:
	 *  + Acurácia
	 *  + Erro
	 *  + Precisão
	 *  + Relevância
	 *  + F-Measure
	 * 
	 * Os argumentos do método são:
	 * 	-> Verdadeiro Positivo
	 *  -> Verdadeiro Negativo
	 *  -> Falso Positivo
	 *  -> Falso Negativo
	 *  
	 */
	private static void exibirMetricas(int vp, int vn, int fp, int fn) {
		
		float acuracia = (float) (vp + vn) / (float) (vp + vn + fp + fn);
		float erro = (float) (fp + fn) / (float) (vp + vn + fp + fn);
		float precision = (float) vp / (float) (vp + fp);
		float recall = (float) vp / (float) (vp + fn);
		float fMeasure = (2 * ((precision * recall) / (precision + recall)));
		
		System.out.printf("Acuracia: %7.4f\n", acuracia);
		System.out.printf("Erro: %11.4f\n", erro);
		System.out.printf("Precision: %6.4f\n", precision);
		System.out.printf("Recall: %9.4f\n", recall);
		System.out.printf("F-Measure: %6.4f\n", fMeasure);
		
	}
	
}
