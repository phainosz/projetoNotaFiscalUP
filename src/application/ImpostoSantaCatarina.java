package application;
/**
 * Classe que representa o imposto do Estado de Santa Catarina.
 * 
 * @author Augusto C�sar
 * @author Paulo Henrique
 * @author Vin�cius Ara�jo
 *
 */
public class ImpostoSantaCatarina extends Imposto {

	/**
	 * Al�quota Estadual do Estado de Santa Catarina: 10%
	 */
	public static double aliquotaEstadual = 0.10;

	public ImpostoSantaCatarina(Double valor) {
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
