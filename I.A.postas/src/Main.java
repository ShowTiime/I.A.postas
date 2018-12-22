
public class Main {

	public static void main(String[] args) {
		
	NaiveBayes.carregarDatabase("C:\\Users\\Amaury\\Desktop\\Amaury\\Base-Dados-CSV.csv");
	NaiveBayes.carregarTestes("C:\\Users\\Amaury\\Desktop\\Amaury\\Base-Dados-Testes-CSV.csv");
	NaiveBayes.classificarTestes();
	
	}

}
