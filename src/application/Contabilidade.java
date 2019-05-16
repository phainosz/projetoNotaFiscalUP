package application;

public class Contabilidade {
	
	public static Double somarValorNotasPorEmpresa(Empresa empresaSomarNotas) throws Excessao {
		double soma = 0;
		for (NotaFiscal notaFiscal : empresaSomarNotas.notas) {
			if (notaFiscal.getValor()>5000) {
				soma += notaFiscal.getValor();
			}
		}
		return soma;
	}
}
