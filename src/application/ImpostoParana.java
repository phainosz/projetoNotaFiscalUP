package application;
/**
 * 
 * Classe que representa o imposto do Estado do Paran�.
 * 
 * @author Augusto César
 * @author Paulo Henrique
 * @author Vin�cius Ara�jo
 *
 */
public class ImpostoParana extends Imposto {

	/**
	 * Aliquota Estadual do Estado do Paraná: 5%
	 */
	public static double aliquotaEstadual = 0.05;

	public ImpostoParana(Double valor) {
		super(valor);
	}

	/**
	 * Método que calcula o valor do imposto estadual de acordo com o valor da nota
	 * e a aliquota estadual.
	 */
	public double calcularImpostoEstadual() {
		return this.valor * aliquotaEstadual;
	}
}
