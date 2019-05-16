package application;
/**
 * 
 * Classe abstrata que representa o imposto.
 * 
 * @author Augusto C�sar
 * @author Paulo Henrique
 * @author Vin�cius Ara�jo
 * 
 */
public abstract class Imposto {

	private static Double ALIQUOTA_FEDERAL = 0.15;

	protected Double valor;

	public Imposto(Double valor) {
		this.valor = valor;
	}

	/**
	 * Calcula o imposto total de uma nota fiscal, somando o valor do imposto
	 * federal com o imposto estadual.
	 * 
	 * @return
	 */
	public double calcularImpostoTotal() {
		return calcularImpostoFederal() + calcularImpostoEstadual();
	}

	/**
	 * M�todo que calcula o vlaor do imposto federal de uma nota. F�rumla:
	 * valorDaNota * aliquotaFederal O valor do imposto federal � uma constante e
	 * est� declarado no in�cio da classe.
	 *
	 * @return - O valor do imposto federal.
	 */
	public double calcularImpostoFederal() {
		return this.valor * ALIQUOTA_FEDERAL;
	}

	/**
	 * Classe abstrata que calcula o imposto estadual de acordo com as informa��es
	 * de cada estado.
	 * 
	 * @return - O valor do Imposto Estadual.
	 */
	public abstract double calcularImpostoEstadual();
}
