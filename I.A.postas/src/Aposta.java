
/*
 * 
 * Classe que representa o objeto Aposta.
 * 
 * Contém todo o vetor de características presente na base de dados.
 * 
 * A base de dados será armazenada em um array de objetos do tipo aposta.
 * 
 * O exemplo que se deseja classificar será um objeto aposta com o atributo classe em branco.
 * 
 * A classe contém dois contrutores, métodos getters e setters e o método toString.
 * 
 */

public class Aposta {
	
	private String classe;
	private int emCasa;
	private int foraDeCasa;
	private int vitorias5jogos;
	private int derrotas5jogos;
	private int streak;
	private String conferencia;
	private double score;
	private int posicao;
	private int b2b;
	private int roadTrip;
	private int titularLesionado;
	private int allStars;
	private int oppEmCasa;
	private int oppForaDeCasa;
	private int oppVitorias5jogos;
	private int oppDerrotas5jogos;
	private int oppStreak;
	private String oppConferencia;
	private double oppScore;
	private int oppPosicao;
	private int oppb2b;
	private int oppRoadTrip;
	private int oppTitularLesionado;
	private int oppAllStars;
	
	public Aposta() {
		
	}

	public Aposta(String classe, int emCasa, int foraDeCasa, int vitorias5jogos, int derrotas5jogos, int streak,
			String conferencia, double score, int posicao, int b2b, int roadTrip, int titularLesionado, int allStars,
			int oppEmCasa, int oppForaDeCasa, int oppVitorias5jogos, int oppDerrotas5jogos, int oppStreak,
			String oppConferencia, double oppScore, int oppPosicao, int oppb2b, int oppRoadTrip,
			int oppTitularLesionado, int oppAllStars) {
		
		super();
		this.setEmCasa(emCasa);
		this.setForaDeCasa(foraDeCasa);
		this.setVitorias5jogos(vitorias5jogos);
		this.setDerrotas5jogos(derrotas5jogos);
		this.setStreak(streak);
		this.setConferencia(conferencia);
		this.setScore(score);
		this.setPosicao(posicao);
		this.setB2b(b2b);
		this.setRoadTrip(roadTrip);
		this.setTitularLesionado(titularLesionado);
		this.setAllStars(allStars);
		this.setOppEmCasa(oppEmCasa);
		this.setOppForaDeCasa(oppForaDeCasa);
		this.setOppVitorias5jogos(oppVitorias5jogos);
		this.setOppDerrotas5jogos(oppDerrotas5jogos);
		this.setOppStreak(oppStreak);
		this.setOppConferencia(oppConferencia);
		this.setOppScore(oppScore);
		this.setOppPosicao(oppPosicao);
		this.setOppb2b(oppb2b);
		this.setOppRoadTrip(oppRoadTrip);
		this.setOppTitularLesionado(oppTitularLesionado);
		this.setOppAllStars(oppAllStars);
		this.setClasse(classe);
		
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public int getEmCasa() {
		return emCasa;
	}

	public void setEmCasa(int emCasa) {
		this.emCasa = emCasa;
	}

	public int getForaDeCasa() {
		return foraDeCasa;
	}

	public void setForaDeCasa(int foraDeCasa) {
		this.foraDeCasa = foraDeCasa;
	}

	public int getVitorias5jogos() {
		return vitorias5jogos;
	}

	public void setVitorias5jogos(int vitorias5jogos) {
		this.vitorias5jogos = vitorias5jogos;
	}

	public int getDerrotas5jogos() {
		return derrotas5jogos;
	}

	public void setDerrotas5jogos(int derrotas5jogos) {
		this.derrotas5jogos = derrotas5jogos;
	}

	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public String getConferencia() {
		return conferencia;
	}

	public void setConferencia(String conferencia) {
		this.conferencia = conferencia;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public int getB2b() {
		return b2b;
	}

	public void setB2b(int b2b) {
		this.b2b = b2b;
	}

	public int getRoadTrip() {
		return roadTrip;
	}

	public void setRoadTrip(int roadTrip) {
		this.roadTrip = roadTrip;
	}

	public int getTitularLesionado() {
		return titularLesionado;
	}

	public void setTitularLesionado(int titularLesionado) {
		this.titularLesionado = titularLesionado;
	}

	public int getAllStars() {
		return allStars;
	}

	public void setAllStars(int allStars) {
		this.allStars = allStars;
	}

	public int getOppEmCasa() {
		return oppEmCasa;
	}

	public void setOppEmCasa(int oppEmCasa) {
		this.oppEmCasa = oppEmCasa;
	}

	public int getOppForaDeCasa() {
		return oppForaDeCasa;
	}

	public void setOppForaDeCasa(int oppForaDeCasa) {
		this.oppForaDeCasa = oppForaDeCasa;
	}

	public int getOppVitorias5jogos() {
		return oppVitorias5jogos;
	}

	public void setOppVitorias5jogos(int oppVitorias5jogos) {
		this.oppVitorias5jogos = oppVitorias5jogos;
	}

	public int getOppDerrotas5jogos() {
		return oppDerrotas5jogos;
	}

	public void setOppDerrotas5jogos(int oppDerrotas5jogos) {
		this.oppDerrotas5jogos = oppDerrotas5jogos;
	}

	public int getOppStreak() {
		return oppStreak;
	}

	public void setOppStreak(int oppStreak) {
		this.oppStreak = oppStreak;
	}

	public String getOppConferencia() {
		return oppConferencia;
	}

	public void setOppConferencia(String oppConferencia) {
		this.oppConferencia = oppConferencia;
	}

	public double getOppScore() {
		return oppScore;
	}

	public void setOppScore(double oppScore) {
		this.oppScore = oppScore;
	}

	public int getOppPosicao() {
		return oppPosicao;
	}

	public void setOppPosicao(int oppPosicao) {
		this.oppPosicao = oppPosicao;
	}

	public int getOppb2b() {
		return oppb2b;
	}

	public void setOppb2b(int oppb2b) {
		this.oppb2b = oppb2b;
	}

	public int getOppRoadTrip() {
		return oppRoadTrip;
	}

	public void setOppRoadTrip(int oppRoadTrip) {
		this.oppRoadTrip = oppRoadTrip;
	}

	public int getOppTitularLesionado() {
		return oppTitularLesionado;
	}

	public void setOppTitularLesionado(int oppTitularLesionado) {
		this.oppTitularLesionado = oppTitularLesionado;
	}

	public int getOppAllStars() {
		return oppAllStars;
	}

	public void setOppAllStars(int oppAllStars) {
		this.oppAllStars = oppAllStars;
	}

	@Override
	public String toString() {
		return "Aposta [classe=" + classe + ", emCasa=" + emCasa + ", foraDeCasa=" + foraDeCasa + ", vitorias5jogos="
				+ vitorias5jogos + ", derrotas5jogos=" + derrotas5jogos + ", streak=" + streak + ", conferencia="
				+ conferencia + ", score=" + score + ", posicao=" + posicao + ", b2b=" + b2b + ", roadTrip=" + roadTrip
				+ ", titularLesionado=" + titularLesionado + ", allStars=" + allStars + ", oppEmCasa=" + oppEmCasa
				+ ", oppForaDeCasa=" + oppForaDeCasa + ", oppVitorias5jogos=" + oppVitorias5jogos
				+ ", oppDerrotas5jogos=" + oppDerrotas5jogos + ", oppStreak=" + oppStreak + ", oppConferencia="
				+ oppConferencia + ", oppScore=" + oppScore + ", oppPosicao=" + oppPosicao + ", oppb2b=" + oppb2b
				+ ", oppRoadTrip=" + oppRoadTrip + ", oppTitularLesionado=" + oppTitularLesionado + ", oppAllStars="
				+ oppAllStars + "]";
	}
	
}
