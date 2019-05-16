package application;
/**
 * Classe para tratamento de erros
 * 
 * @author ViníciusAraújo
 * @author Paulo Hainosz
 * @author Augusto Cézar
 *
 */
public class Excessao extends Exception {

	private static final long serialVersionUID = -5977373508888201087L;

	public Excessao(String msg) {
		super(msg);
	}
}
