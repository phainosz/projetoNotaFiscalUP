package application;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe principal onde roda todas as funcionalidades do sistema
 * 
 * @author Augusto César
 * @author Paulo Henrique
 * @author Vinícius Araújo
 *
 */
public class Principal {

	static ArrayList<Empresa> empresas = new ArrayList<>();
	static Empresa empresa;

	public static void main(String[] args) {

		String[] opcoes = { "Cadastrar Empresa", "Listar Empresas", "Excluir Empresa", "Emitir Nota Fiscal",
				"Cancelar Nota Fiscal", "Relatório: Notas Fiscais por Empresa",
				"Relatório: Notas Fiscais Canceladas por Empresa",
				"Relatório: Notas Fiscais Por Empresa (Ord.: valor)", 
				"Somar a notas geradas pela empresa acima de R$5.000,00", "Cancelar notas em lote por valor informado" };

		Boolean continuar = true;

		do {
			int opcao = Console.mostrarMenu(opcoes, "Escolha a opção da operação você deseja fazer:", null);

			switch (opcao) {
			case 1:
				//Criar Empresa
				Empresa novaEmpresa = criarEmpresa();
				try {
					//Cadastrar Empresa
					cadastrarEmpresa(novaEmpresa);
					System.out.println("Empresa cadastrada com sucesso!");
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
					//Listar empresas cadastradas
					listarEmpresas();
				break;
			case 3:
				//Excluir empresa
				try {
					Empresa empresaASerExcluida = encontrarEmpresaPeloCNPJ();
					excluirEmpresa(empresaASerExcluida);
					System.out.println("Empresa excluída com sucesso!");
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				//Emitir nota fiscal
				try {
					Empresa empresaLancamentoNota = encontrarEmpresaPeloCNPJ();
					NotaFiscal notaFiscalGerada = gerarNotaFiscal();

					verificarNumeracao(notaFiscalGerada, empresaLancamentoNota);

					System.out.println("Nota Fiscal Autorizada e Processada com Sucesso!");
					System.out.println(notaFiscalGerada);
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				//Cancelar nota fiscal
				try {
					Empresa empresaDaNota = encontrarEmpresaPeloCNPJ();
					System.out.println("Segue abaixo relação de Notas Fiscais dessa empresa:");
					listarNotasFiscais(empresaDaNota);
					Integer numeroDaNota = Console.recuperaInteiro("Digite o número da nota que deseja cancelar:");
					NotaFiscal notaCancelamento = encontrarNotaFiscal(empresaDaNota, numeroDaNota);
					notaCancelamento.setCancelada(true);
					System.out.println("Nota cancelada com sucesso!");
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}

				break;
			case 6:
				//Listar notas fiscais por empresa
				try {
					Empresa empresaDaNota = encontrarEmpresaPeloCNPJ();
					listarNotasFiscais(empresaDaNota);
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				//Listar notas fiscais canceladas por empresa
				try {
					Empresa empresaDaNota = encontrarEmpresaPeloCNPJ();
					listarNotasFiscaisCanceladas(empresaDaNota);
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}
				break;
			case 8:
				//Listar notas fiscais ordenadas pelo valor por empresa
				try {
					Empresa empresaDaNota = encontrarEmpresaPeloCNPJ();
					listarNotasFiscaisPorValor(empresaDaNota);
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case 9:
				//somar notas acima de R$5.000,00 geradas pela empresa
				try {
					Empresa empresaSomarNota = encontrarEmpresaPeloCNPJ();
					double somaNotas = Contabilidade.somarValorNotasPorEmpresa(empresaSomarNota);
					System.out.println("A soma das notas fiscais geradas pela empresa é: "+String.format("%.2f", somaNotas));
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}
				
				break;
				
			case 10:
				//cancelar notas fiscais em lote da empresa selecionada de um determinado valor
				try {
					Empresa empresaCancelarNotaEmLotePorValorDoUsuario = encontrarEmpresaPeloCNPJ();
					cancelarNotasEmLote(empresaCancelarNotaEmLotePorValorDoUsuario);
				} catch (Excessao e) {
					System.out.println(e.getMessage());
				}

				break;
			case -1:
				System.out.println("Saindo..");
				continuar = false;
				break;
			}

		} while (continuar);
	}

	/**
	 * Método criado para criar uma empresa com o Nome e CNPJ
	 * 
	 * @return uma empresa com o nome e CNPJ.
	 */
	private static Empresa criarEmpresa() {
		String nome = Console.recuperaTexto("Insira o nome da empresa:");
		String cnpj = Console.recuperaTexto("Insira o CNPJ da empresa:");
		return new Empresa(nome, cnpj);
	}

	/**
	 * Método que recebe uma empresa e verifica se ela deve ou não ser cadastrada
	 * no sistema. Seu funcionamento se dá pela verificação se a empresa já
	 * existe, caso não, ele cadastra no array "empresas", senão, retorna um erro
	 * informando que o CNPJ já está cadastrado em outra empresa.
	 * 
	 * @param novaEmpresa - Empreas a ser cadastrada
	 * @throws Excessao - Caso o CNPJ já esteja cadastrado, retorna a mensagem
	 *                  "CNPJ Existente".
	 */
	private static void cadastrarEmpresa(Empresa novaEmpresa) throws Excessao {
		for (Empresa empresa : empresas) {
			if (empresa.equals(novaEmpresa)) {
				throw new Excessao("CNPJ existente");
			}
		}
		empresas.add(novaEmpresa);
	}

	/**
	 * Método que varre o array "empresas" e imprime na tela cada uma delas.
	 * @throws Excessao 
	 */
	private static void listarEmpresas() {
		for (Empresa empresa : empresas) {
			System.out.println(empresa);
		}
	}

	/**
	 * Método que pergunta qual o CNPJ da empresa que o usuário está buscando e
	 * realiza uma varredura no array "empresas" buscando a empresa do CNPJ
	 * informado.
	 * 
	 * @return - A empresa com o CNPJ informado pelo cliente.
	 * @throws Excessao - Caso não exista a empresa, ele retorna um erro informando
	 *                  que a empesa não foi encontrada.
	 */
	private static Empresa encontrarEmpresaPeloCNPJ() throws Excessao {
		String cnpj = Console.recuperaTexto("Informe o CNPJ da empresa:");
		for (Empresa empresa : empresas) {
			if (empresa.getCnpj().equalsIgnoreCase(cnpj)) {
				return empresa;
			}
		}
		throw new Excessao("Empresa não encontrada!");
	}

	/**
	 * Método criado para excluir uma empresa do sistema. Seu funcionamento se dá
	 * por meio do
	 * 
	 * @param empresaASerExcluida
	 * @throws Excessao
	 */
	private static void excluirEmpresa(Empresa empresaASerExcluida) throws Excessao {
		if (empresaASerExcluida.getNotasFiscaisValidas().size() > 0) {
			throw new Excessao("A empresa contém notas fiscais válidas.\n"
					+ "Faça o cancelamento das mesmas para realizar a exclusão da empresa.");
		} else {
			empresas.remove(empresaASerExcluida);
		}
	}

	/**
	 * Método que coleta as informações e gera a nota fiscal.
	 * 
	 * @return - Uma nota fiscal com as informações necessárias para o sistema.
	 * @throws Excessao 
	 */
	private static NotaFiscal gerarNotaFiscal() throws Excessao {

		String descricao = Console.recuperaTexto("Descrição da Nota Fiscal:");
		int numero = Console.recuperaInteiro("Informe o número da Nota Fiscal:");
		double valor = Console.recuperaDecimal("Informe o valor monetário da Nota Fiscal:");
			if(valor > 150000) {		
				throw new Excessao("Valor da nota acima do permitido!! Valor máximo de R$150.000,00");
			}
			else {
				Imposto imposto = verificarEstado(valor);
				NotaFiscal notaFiscal = new NotaFiscal(numero, descricao, imposto, valor);
				return notaFiscal;
			}
	}

	/**
	 * Método que verifica se a numeração da Nota Fiscal já foi utilizada por
	 * outra nota na mesma empresa, evitando duplicidade.
	 * 
	 * @param novaNotaFiscal    - Nota Fiscal a ser incluída na empresa.
	 * @param empresaLancamento - Empresa na qual a nota fiscal será incluída.
	 * @throws Excessao - Caso a numeração já tenha sido utilizada, o sistema
	 *                  retorna uma mensagem informando que o número já foi
	 *                  utilizado.
	 */
	private static void verificarNumeracao(NotaFiscal novaNotaFiscal, Empresa empresaLancamento) throws Excessao {
		for (NotaFiscal notaFiscal : empresaLancamento.notas) {
			if (notaFiscal.equals(novaNotaFiscal)) {
				throw new Excessao("Número já utilizado");
			}
		}
		empresaLancamento.notas.add(novaNotaFiscal);
	}

	/**
	 * Método que varre o array de Notas Fiscais de uma empresa e imprime na tela
	 * cada uma delas.
	 * 
	 * @param empresa - A empresa em que o sistema verificará as notas fiscais.
	 */
	private static void listarNotasFiscais(Empresa empresa) {
		System.out.println("--------------------------------");
		for (NotaFiscal notaFiscal : empresa.notas) {
			System.out.println(notaFiscal);
			System.out.println("--------------------------------");
		}
		System.out.println("\n");
	}

	/**
	 * Método que varre o array de Notas Fiscais de uma empresa e imprime na tela
	 * cada uma delas.
	 * 
	 * @param empresa - A empresa em que o sistema verificará as notas fiscais.
	 */
	private static void listarNotasFiscaisCanceladas(Empresa empresa) {
		System.out.println("Notas Fiscais Canceladas:\n\n");
		System.out.println("--------------------------------");
		for (NotaFiscal notaFiscal : empresa.getNotasFiscaisCanceladas()) {
			System.out.println(notaFiscal);
			System.out.println("--------------------------------");
		}
		System.out.println("\n");
	}

	/**
	 * Método que varre o array de Notas Fiscais de uma empresa e imprime na tela
	 * cada uma delas. ordenado por valor.
	 * 
	 * @param empresa - A empresa em que o sistema verificará as notas fiscais.
	 */
	private static void listarNotasFiscaisPorValor(Empresa empresa) {
		System.out.println("--------------------------------");
		Collections.sort(empresa.notas);
		for (NotaFiscal notaFiscal : empresa.notas) {
			System.out.println(notaFiscal);
			System.out.println("--------------------------------");
		}
		System.out.println("\n");
	}

	/**
	 * Método que busca uma nota fiscal pelo número dela em uma empresa.
	 * 
	 * @param empresaDaNota - Empresa em que o sistema procurará pela nota.
	 * @param numeroDaNota  - Número da nota que o sistema deve procurar.
	 * @return - Nota Fiscal com o número solicitado pelo usuário.
	 * @throws Excessao - Caso não haja nenhuma nota na empresa com tal
	 *                  numeração, o sistema retorna informando que não encontrou
	 *                  nenhuma nota com tal numeração.
	 */
	private static NotaFiscal encontrarNotaFiscal(Empresa empresaDaNota, Integer numeroDaNota) throws Excessao {
		for (NotaFiscal notaFiscal : empresaDaNota.notas) {
			if (notaFiscal.getNumero() == numeroDaNota) {
				return notaFiscal;
			}
		}
		throw new Excessao("Nota fiscal não encontrada.");
	}

	/**
	 * Verifica o estado para o qual a nota será emitida e retorna o imposto de tal
	 * estado.
	 * 
	 * @param valor - Valor da nota para realização do cálculo do imposto.
	 * @return - Retorna o imposto para o estado escolhido. OBS: Tem um 'return
	 *         null' que foi utilizado somente para o Java compilar, pois o sistema
	 *         ficará no do while e retornará o imposto de algum estado, porém o
	 *         Java não entende isso.
	 */
	private static Imposto verificarEstado(Double valor) {
		String[] UFs = { "Paraná", "Santa Catarina", "São Paulo", "Amapá" };

		Boolean continuar = true;

		do {
			int opcao = Console.mostrarMenuSemVoltar(UFs, "Escolha o estado destino da Nota Fiscal:", null);

			switch (opcao) {
			case 1:
				Imposto impostoParana = new ImpostoParana(valor);
				return impostoParana;
			case 2:
				Imposto impostoSantaCatarina = new ImpostoSantaCatarina(valor);
				return impostoSantaCatarina;
			case 3:
				Imposto impostoSaoPaulo = new ImpostoSaoPaulo(valor);
				return impostoSaoPaulo;
			case 4:
				Imposto impostoAmapa = new ImpostoAmapa(valor);
				return impostoAmapa;
			}

		} while (continuar);
		return null;
	}
 
	/**
	 * método para cancelar notas em lote por um determinado valor informado
	 *@author Paulo
	 *@param Empresa empresaParaCancelarNota é a empresa selecionada para cancelamento da nota
	 */
	private static void cancelarNotasEmLote(Empresa empresaParaCancelarNota) throws Excessao {
		Double valoDaNota = Console.recuperaDecimal("Informe o valor das notas que deseja cancelar");
		if (empresaParaCancelarNota.getNotasFiscaisValidas().size() == 0) {
			throw new Excessao("Empresa não contém notas válidas com o valor informado.");
		} else {
			for (NotaFiscal notaFiscal : empresaParaCancelarNota.getNotasFiscaisValidas()) {
				if (notaFiscal.getValor() <= valoDaNota) {
					notaFiscal.setCancelada(true);
					System.out.println("Nota cancelada com sucesso!!");
				}
			}
		}
	}
}