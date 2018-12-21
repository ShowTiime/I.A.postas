import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * 
 * A classe NaiveBayes � onde constar� o algoritmo e a base de dados.
 * O ArrayList database � composto por toda a base de dados.
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
	 * de campos � ';').
	 * 
	 * Ap�s isso, alteramos os atributos de um objeto Aposta com o vetor de Strings contendo todas as caracter�sti-
	 * cas do exemplo. Ao final adicionamos ao ArrayList database.
	 * 
	 */
	public static void carregarDatabase(String datapath) {
		
		String separador = ";";	//Separador de campos no arquivo .csv
		String resultado = "";	//String para armazenar cada linha lida do arquivo .csv
		BufferedReader conteudo = null;	//Objeto usado para ler a base de dados
		Aposta exemplo = new Aposta();	//Objeto que ser� usado para guardar os valores presentes no vetor de
										//Strings e logo depois adicionado ao atributo da classe, database.
		
		try {
			
			//abrindo o arquivo
			conteudo = new BufferedReader(new FileReader(datapath));
			while((resultado = conteudo.readLine()) != null) { //lendo cada linha do arquivo
				
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
					exemplo.setOppb2b(Integer.parseInt(caracteristica[20]));
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
	public static String run(Aposta aposta) {
		
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
		
		//C�lculo P(Sim) e P(N�o)
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
		
		//C�lculo P(EmCasa|Sim) e P(EmCasa|Nao).
		pEmCasaSim = (double) countSim / countPSim;
		pEmCasaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
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
		
		//C�lculo P(ForaDeCasa|Sim) e P(ForaDeCasa|Nao).
		pForaDeCasaSim = (double) countSim / countPSim;
		pForaDeCasaNao = (double) countNao / countPNao;
		//Zerando as vari�veis para uso no pr�ximo atributo.
		countSim = 0;
		countNao = 0;
		
		//@TODO
		//Calcular P(X|Sim) e P(X|Nao) para os outros atributos.
		
		//C�lculo final da probabilidade
		probabilidadeDeSim = pSim * pEmCasaSim * pForaDeCasaSim * pVitorias5jogosSim * pDerrotas5jogosSim * 
				pStreakSim * pConferenciaSim * pScoreSim * pPosicaoSim * pb2bSim * pRoadTripSim * pTitularLesionadoSim * 
				pAllStarsSim * pOppEmCasaSim * pOppForaDeCasaSim * pOppVitorias5jogosSim * pOppDerrotas5jogosSim * 
				pOppStreakSim * pOppConferenciaSim * pOppScoreSim * pOppPosicaoSim * pOppb2bSim * pOppRoadTripSim * 
				pOppTitularLesionadoSim * pOppAllStarsSim;
		
		//probabilidadeDeNao = mesmo c�lculo feito acima;
		
		resultado = (probabilidadeDeSim >= probabilidadeDeNao) ? "sim" : "nao";
		return resultado;
		
	}

}
