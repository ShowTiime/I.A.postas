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
	
	//ArrayList para armazenar a base de dados
	private static ArrayList<Aposta> database = new ArrayList<>();
	
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
	 * de campos é ';').
	 * 
	 * Após isso, alteramos os atributos de um objeto Aposta com o vetor de Strings contendo todas as característi-
	 * cas do exemplo. Ao final adicionamos ao ArrayList database.
	 * 
	 */
	public static void carregarDatabase(String datapath) {
		
		String separador = ";";	//Separador de campos no arquivo .csv
		String resultado = "";	//String para armazenar cada linha lida do arquivo .csv
		BufferedReader conteudo = null;	//Objeto usado para ler a base de dados
		Aposta exemplo = new Aposta();	//Objeto que será usado para guardar os valores presentes no vetor de
										//Strings e logo depois adicionado ao atributo da classe, database.
		
		try {
			
			//abrindo o arquivo
			conteudo = new BufferedReader(new FileReader(datapath));
			while((resultado = conteudo.readLine()) != null) { //lendo cada linha do arquivo
				
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
					exemplo.setOppb2b(Integer.parseInt(caracteristica[20]));
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
	public static String run(Aposta aposta) {
		
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
		double pOppb2bSim = 0, pOppb2bNao = 0;
		double pOppRoadTripSim = 0, pOppRoadTripNao = 0;
		double pOppTitularLesionadoSim = 0, pOppTitularLesionadoNao = 0;
		double pOppAllStarsSim = 0, pOppAllStarsNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como sim e nao na base de dados.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse() == "sim") {
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
			if(NaiveBayes.database.get(i).getClasse() == "sim") {
				if(aposta.getEmCasa() == NaiveBayes.database.get(i).getEmCasa()) {
					countSim++; //Conta os exemplos classificados como Sim
				}
			} else {
				if(aposta.getEmCasa() == NaiveBayes.database.get(i).getEmCasa()) {
					countNao++; //Conta os exemplos classificados como Sim
				}
			}
		}
		
		//Cálculo P(EmCasa|Sim) e P(EmCasa|Nao).
		pEmCasaSim = (double) countSim / countPSim;
		pEmCasaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
		countSim = 0;
		countNao = 0;
		
		//Loop para contar a quantidade de exemplos classificados como Sim e Nao tem o valor do atributo ForaDeCasa
		//igual ao atributo ForaDeCasa do objeto passado como argumento.
		for(int i = 0; i < NaiveBayes.database.size(); i++) {
			if(NaiveBayes.database.get(i).getClasse() == "sim") {
				if(aposta.getForaDeCasa() == NaiveBayes.database.get(i).getForaDeCasa()) {
					countSim++;
				}
			} else {
				if(aposta.getForaDeCasa() == NaiveBayes.database.get(i).getForaDeCasa()) {
					countNao++;
				}
			}
		}
		
		//Cálculo P(ForaDeCasa|Sim) e P(ForaDeCasa|Nao).
		pForaDeCasaSim = (double) countSim / countPSim;
		pForaDeCasaNao = (double) countNao / countPNao;
		//Zerando as variáveis para uso no próximo atributo.
		countSim = 0;
		countNao = 0;
		
		//@TODO
		//Calcular P(X|Sim) e P(X|Nao) para os outros atributos.
		
		//Cálculo final da probabilidade
		probabilidadeDeSim = pSim * pEmCasaSim * pForaDeCasaSim * pVitorias5jogosSim * pDerrotas5jogosSim * 
				pStreakSim * pConferenciaSim * pScoreSim * pPosicaoSim * pb2bSim * pRoadTripSim * pTitularLesionadoSim * 
				pAllStarsSim * pOppEmCasaSim * pOppForaDeCasaSim * pOppVitorias5jogosSim * pOppDerrotas5jogosSim * 
				pOppStreakSim * pOppConferenciaSim * pOppScoreSim * pOppPosicaoSim * pOppb2bSim * pOppRoadTripSim * 
				pOppTitularLesionadoSim * pOppAllStarsSim;
		
		//probabilidadeDeNao = mesmo cálculo feito acima;
		
		resultado = (probabilidadeDeSim >= probabilidadeDeNao) ? "sim" : "nao";
		return resultado;
		
	}

}
