package application;
/**
 * 
 * Classe que representa o imposto do Estado do Amapá.
 * 
 * @author Paulo Henrique
 *
 */
public class ImpostoAmapa extends Imposto{
	
	/**
	 * Aliquota Estadual do Estado do Amapá: 18.5%
	 */
	
	public static double aliquotaEstadual = 0.185;
	
	public ImpostoAmapa(Double valor) {
		super(valor);
	}
	
	/**
	 * Método que calcula o valor do imposto estadual de acordo com o valor da nota
	 * e a aliquota estadual.
	 */
	@Override
	public double calcularImpostoEstadual() {
		return this.valor * aliquotaEstadual;
	}

}
