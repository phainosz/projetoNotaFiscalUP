package application;
/**
 * 
 * Classe que representa o imposto do Estado de S�o Paulo.
 * 
 * @author Augusto C�sar
 * @author Paulo Henrique
 * @author Vin�cius Ara�jo
 *
 */
public class ImpostoSaoPaulo extends Imposto {

	/**
	 * Al�quota Estadual do Estado de S�o Paulo: 18%
	 */
	public static double aliquotaEstadual = 0.18;

	public ImpostoSaoPaulo(Double valor) {
		super(valor);
	}

	/**
	 * M�todo que calcula o valor do imposto estadual de acordo com o valor da nota
	 * e a al�quota do estado.
	 */
	public double calcularImpostoEstadual() {
		return this.valor * aliquotaEstadual;
	}
}
