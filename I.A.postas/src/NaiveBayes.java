import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * 
 * A classe NaiveBayes é onde constará o algoritmo e a base de dados.
 * O ArrayList database é composto por toda a base de dados.
 * 
 */

public class NaiveBayes {
	
	//ArrayList para armazenar a base de dados e testes
	private static ArrayList<Aposta> database = new ArrayList<>();
	private static ArrayList<Aposta> testes = new ArrayList<>();
	
	public void setDatabase(ArrayList<Aposta> database) {
		NaiveBayes.database = database;
	}
	
	
	/*
	 * 
	 * Método estático carregarDatabase() é responsável por carregar a base de dados vinda de um arquivo .csv
	 * e armazená-la no ArrayList database.
	 * 
	 * O método recebe como argumento o caminho onde o arquivo .csv com a base de dados se encontra.
	 * Ex.: D:\\PEDRO\\UFRPE\\5º Período\\Inteligência Artificial\\Projeto\\IA\\csv\\data.csv (para o SO windows)
	 * 
	 * Para ler o arquivo usa-se um objeto do tipo BufferedReader, no seu construtor passamos um objeto do tipo
	 * FileReader e no seu construtor passamos a String que recebemos como argumento com o caminho do arquivo.
	 * 
	 * Cada linha do arquivo (cada resultado de um jogo) é lido e armazenado na String resultado na parte do teste
	 * condicional, enquanto tiver resultados de jogos a serem lidos vamos armazenar os dados.
	 * 
	 * Um vetor de Strings é usado para armazenar as características de cada exemplo, o separador contido no .csv
	 * é passado como argumento do método split (nesse caso, no arquivo indicado no caminho acima, o separador
	 * de campos é ',').
	 * 
	 * Após isso, alteramos os atributos de um objeto Aposta com o vetor de Strings contendo todas as característi-
	 * cas do exemplo. Ao final adicionamos ao ArrayList database.
	 * 
	 */
	public static void carregarDatabase(String datapath) {
		
		String separador = ",";	//Separador de campos no arquivo .csv
		String resultado = "";	//String para armazenar cada linha lida do arquivo .csv
		BufferedReader conteudo = null;	//Objeto usado para ler a base de dados
		
		try {
			
			//abrindo o arquivo
			conteudo = new BufferedReader(new FileReader(datapath));
			while((resultado = conteudo.readLine()) != null) { //lendo cada linha do arquivo
				
				Aposta exemplo = new Aposta();	//Objeto que será usado para guardar os valores presentes no vetor de
				//Strings e logo depois adicionado ao atributo da classe, database.
				
				//após a leitura, separa cada campo, especificando o separador, e 
				//armazenando no vetor de Strings
				String[] caracteristica = resultado.split(separador);
				
				try { 	//Setando os atributos do objeto exemplo (Aposta) com os elementos
						//do vetor de Strings que contém as características
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
					System.out.println("Erro na Formatação: \n" + e.getMessage());
				}
				
				//adicionando o resultado à base de dados
				NaiveBayes.database.add(exemplo);
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado: \n" + e.getMessage());
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
	 * Método estático carregartestes() é responsável por carregar os testes vinda de um arquivo .csv
	 * e armazená-la no ArrayList testes.
	 * 
	 * O método recebe como argumento o caminho onde o arquivo .csv com os testes se encontra.
	 * Ex.: D:\\PEDRO\\UFRPE\\5º Período\\Inteligência Artificial\\Projeto\\IA\\csv\\data.csv (para o SO windows)
	 * 
	 * Para ler o arquivo usa-se um objeto do tipo BufferedReader, no seu construtor passamos um objeto do tipo
	 * FileReader e no seu construtor passamos a String que recebemos como argumento com o caminho do arquivo.
	 * 
	 * Cada linha do arquivo (cada resultado de um jogo) é lido e armazenado na String resultado na parte do teste
	 * condicional, enquanto tiver resultados de jogos a serem lidos vamos armazenar os dados.
	 * 
	 * Um vetor de Strings é usado para armazenar as características de cada exemplo, o separador contido no .csv
	 * é passado como argumento do método split (nesse caso, no arquivo indicado no caminho acima, o separador
	 * de campos é ',').
	 * 
	 * Após isso, alteramos os atributos de um objeto Aposta com o vetor de Strings contendo todas as característi-
	 * cas do exemplo. Ao final adicionamos ao ArrayList testes.
	 * 
	 */
	public static void carregarTestes(String datapath) {
		
		String separador = ",";	//Separador de campos no arquivo .csv
		String resultado = "";	//String para armazenar cada linha lida do arquivo .csv
		BufferedReader conteudo = null;	//Objeto usado para ler a base de dados dos testes
		
		try {
			
			//abrindo o arquivo
			conteudo = new BufferedReader(new FileReader(datapath));
			while((resultado = conteudo.readLine()) != null) { //lendo cada linha do arquivo
				
				Aposta test = new Aposta();	//Objeto que será usado para guardar os valores presentes no vetor de
				//Strings e logo depois adicionado ao atributo da classe, database.
				
				//após a leitura, separa cada campo, especificando o separador, e 
				//armazenando no vetor de Strings
				String[] caracteristica = resultado.split(separador);
				
				try { 	//Setando os atributos do objeto exemplo (Aposta) com os elementos
						//do vetor de Strings que contém as características
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
					System.out.println("Erro na Formatação: \n" + e.getMessage());
				}
				
				//adicionando o resultado à base de dados de testes
				NaiveBayes.testes.add(test);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado: \n" + e.getMessage());
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
	 * Método run é o método que contém o algoritmo em si, ele recebe como argumento a aposta que o usuário quer
	 * classificar, tal objeto tem o campo classe vazio.
	 * 
	 * O funcionamento do algoritmo é o mesmo que vimos em sala, primeiro calculamos P(Sim) e P(Nao) que estão
	 * armazenados nas variáveis pSim e pNao respectivamente. Após isso calculamos P(X|Sim) e P(X|Nao), onde X
	 * é cada atributo do vetor de caracteristica da classe Aposta.
	 * 
	 * Todas as probabilidades dos atributos estão armazenadas nas variáves pXSim e pXNao.
	 * 
	 */
	public static String classificar(Aposta aposta) {
		
		//Variáveis para o cálculo final.
		double probabilidadeDeSim = 0;
		double probabilidadeDeNao = 0;
		
		String resultado = "";	//String que retorna a classe final.
		
		double pSim = 0, pNao = 0;	//Variáveis que guardam P(Sim) e P(Não).
		int countPSim = 0, countPNao = 0; //Variáveis que guardam o número de exemplos classificados como Sim e Nao.
		int countSim = 0, countNao = 0;	//Variáveis que guardam o numero de atributos na base de dados iguais ao 
										//atributo do objeto passado como argumento.
		
		//Variáveis que guardam P(X|Sim) e P(X|Nao)
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
		
		//Cálculo P(Sim) e P(Não)
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
		
		//Cálculo P(EmCasa|Sim) e P(EmCasa|Nao).
		pEmCasaSim = (double) countSim / countPSim;
		pEmCasaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(ForaDeCasa|Sim) e P(ForaDeCasa|Nao).
		pForaDeCasaSim = (double) countSim / countPSim;
		pForaDeCasaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
			
		//Cálculo P(Vitorias5jogos|Sim) e P(Vitorias5jogos|Nao).
		pVitorias5jogosSim = (double) countSim / countPSim;
		pVitorias5jogosNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(Derrotas5jogos|Sim) e P(Derrotas5jogos|Nao).
		pDerrotas5jogosSim = (double) countSim / countPSim;
		pDerrotas5jogosNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(Streak|Sim) e P(Streak|Nao).
		pStreakSim = (double) countSim / countPSim;
		pStreakNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(Conferencia|Sim) e P(Conferencia|Nao).
		pConferenciaSim = (double) countSim / countPSim;
		pConferenciaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(Score|Sim) e P(Score|Nao).
		pScoreSim = (double) countSim / countPSim;
		pScoreNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(Posicao|Sim) e P(Posicao|Nao).
		pPosicaoSim = (double) countSim / countPSim;
		pPosicaoNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(b2b|Sim) e P(b2b|Nao).
		pb2bSim = (double) countSim / countPSim;
		pb2bNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(RoadTrip|Sim) e P(RoadTrip|Nao).
		pRoadTripSim = (double) countSim / countPSim;
		pRoadTripNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(TitularLesionado|Sim) e P(TitularLesionado|Nao).
		pTitularLesionadoSim = (double) countSim / countPSim;
		pTitularLesionadoNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(AllStars|Sim) e P(AllStars|Nao).
		pAllStarsSim = (double) countSim / countPSim;
		pAllStarsNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppEmCasa|Sim) e P(OppEmCasa|Nao).
		pOppEmCasaSim = (double) countSim / countPSim;
		pOppEmCasaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppForaDeCasa|Sim) e P(OppForaDeCasa|Nao).
		pOppForaDeCasaSim = (double) countSim / countPSim;
		pOppForaDeCasaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppVitorias5jogos|Sim) e P(OppVitorias5jogos|Nao).
		pOppVitorias5jogosSim = (double) countSim / countPSim;
		pOppVitorias5jogosNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppDerrotas5jogos|Sim) e P(OppDerrotas5jogos|Nao).
		pOppDerrotas5jogosSim = (double) countSim / countPSim;
		pOppDerrotas5jogosNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppStreak|Sim) e P(OppStreak|Nao).
		pOppStreakSim = (double) countSim / countPSim;
		pOppStreakNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppConferencia|Sim) e P(OppConferencia|Nao).
		pOppConferenciaSim = (double) countSim / countPSim;
		pOppConferenciaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppScore|Sim) e P(OppScore|Nao).
		pOppScoreSim = (double) countSim / countPSim;
		pOppScoreNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppPosicao|Sim) e P(OppPosicao|Nao).
		pOppPosicaoSim = (double) countSim / countPSim;
		pOppPosicaoNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppB2b|Sim) e P(OppB2b|Nao).
		pOppB2bSim = (double) countSim / countPSim;
		pOppB2bNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppRoadTrip|Sim) e P(OppRoadTrip|Nao).
		pOppRoadTripSim = (double) countSim / countPSim;
		pOppRoadTripNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppTitularLesionado|Sim) e P(OppTitularLesionado|Nao).
		pOppTitularLesionadoSim = (double) countSim / countPSim;
		pOppTitularLesionadoNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
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
		
		//Cálculo P(OppAllStars|Sim) e P(OppAllStars|Nao).
		pOppAllStarsSim = (double) countSim / countPSim;
		pOppAllStarsNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
		countSim = 0;
		countNao = 0;
			
		//Calcular P(X|Sim) e P(X|Nao) para os outros atributos.
		
		//Cálculo final da probabilidade
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
		
		System.out.println(probabilidadeDeSim + " " + probabilidadeDeNao);
		
		resultado = (probabilidadeDeSim >= probabilidadeDeNao) ? "sim" : "nao";
		return resultado;
		
	}
	
	/*
	 * Método que classifica todos os testes armazenados no ArrayList
	 * */
	public static void classificarTestes() {
		
		int totalTesteCorreto = 0, totalTesteIncorreto = 0;
		
		for (int i = 0; i < testes.size(); i++) {
			Aposta aposta = testes.get(i);
			String resultado = classificar(aposta);
			System.out.println("Aposta " + (i+1) + " = " + resultado);
			if (aposta.getClasse().equals(resultado)) {
				totalTesteCorreto++;
			} else {
				totalTesteIncorreto++;
			}
		}
		
		System.out.println("\nTestes classificados corretamente = " + totalTesteCorreto);
		System.out.println("Testes classificados incorretamente = " + totalTesteIncorreto);
		double taxa = (double) totalTesteCorreto/ (double) (totalTesteIncorreto + totalTesteCorreto) * 100;
		System.out.printf("Taxa de acerto = %.2f %%", taxa);
	}
	
}
