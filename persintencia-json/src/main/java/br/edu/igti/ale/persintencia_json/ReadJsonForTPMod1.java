package br.edu.igti.ale.persintencia_json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReadJsonForTPMod1 {
	public static void main(String[] args) {
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("accounts.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray accountList = (JSONArray) obj;
			System.out.println(accountList);


			Gson gson = new Gson();
			Type accountListType = new TypeToken<ArrayList<Account>>() {
			}.getType();
			List<Account> accounts = gson.fromJson(accountList.toString(), accountListType);

			// Total de REGISTROS:
			System.out.println("Total de registros: " + accounts.size());

			// 1ro - Soma total de depósitos de todas as agências é:

			Integer sumBalance = accounts.stream().map(x -> x.getBalance()).reduce(0, (a, b) -> a + b);
			System.out.println("01) Soma total dos balances é: " + sumBalance);

			IntSummaryStatistics estadisticaBalances = accounts.stream()
					.collect(Collectors.summarizingInt(p -> p.balance));
			System.out.println("   Estadistica dos balances: " + estadisticaBalances);

			// 2do - O número total de contas com mais de 100 reais de saldo é:

			Integer sumTotalContasMaior100Reais = (int) accounts.stream().filter(s -> s.getBalance() > 100).count();
			System.out.println("02) Suma de total de contas mas de 100 Reais: " + sumTotalContasMaior100Reais);
			Integer sumTotalContasMenorOuIgual100Reais = (int) accounts.stream().filter(s -> s.getBalance() <= 100)
					.count();
			System.out.println(
					"   Suma de total de contas con menos o igual a 100 Reais: " + sumTotalContasMenorOuIgual100Reais);

			// 3ro - o número de contas com mmais de 100 reais de saldo na agência 33 é:

			Integer sumContasCom100ReaisAgencia33 = (int) accounts.stream()
					.filter(s -> s.getBalance() > 100 && s.getAgencia() == 33).count();
			System.out.println("03) Número de contas com mmais de 100 reais de saldo na agência 33 é: "
					+ sumContasCom100ReaisAgencia33);

			// 4to - Agencia que tem a conta com maior saldo é a:

			Account agenciaComSaldoMaior = accounts.stream().max(Comparator.comparing(Account::getBalance)).get();
			System.out.println("04) Agencia que tem a conta com maior saldo é a: " + agenciaComSaldoMaior.getAgencia()
					+ ", com balance: " + agenciaComSaldoMaior.getBalance());

			// 5to - Considere o cliente com o maior saldo em cada agência (caso haja mais
			// de um cliente com o maior saldo, escolha apena um). O valor total desses
			// saldos é:

			Map<Integer, Account> maxLikesPerPostType = accounts.stream()
					.collect(
							Collectors
									.groupingBy(Account::getAgencia,
											Collectors
													.collectingAndThen(
															Collectors
																	.reducing((Account a1,
																			Account a2) -> a1.getBalance() > a2
																					.getBalance() ? a1 : a2),
															Optional::get)));

			System.out.println("05) O valor total desses saldos é: "
					+ maxLikesPerPostType.values().stream().map(a -> a.getBalance()).reduce(0, (a, b) -> a + b));

			// 6to - Nome da cliene com maior saldo na agência 10

			Account nomeClienteMaiorSaldoAgencia10 = accounts.stream().filter(s -> s.getAgencia() == 10)
					.max(Comparator.comparing(Account::getBalance)).get();
			System.out.println(
					"06) Nome da cliene com maior saldo na agência 10 é: " + nomeClienteMaiorSaldoAgencia10.getName());

			// 7to - Nome da cliene com menor saldo na agência 47

			Account nomeClienteMenorSaldoAgencia47 = accounts.stream().filter(s -> s.getAgencia() == 47)
					.min(Comparator.comparing(Account::getBalance)).get();
			System.out.println(
					"07) Nome da cliene com menor saldo na agência 47: " + nomeClienteMenorSaldoAgencia47.getName());

			// 8to - Você deve mostrar os nomes dos três clientes com menor saldo em ordem
			// crescente (de saldo
			// menor para o maior), separados por vírgula de agência 47. Qual seria a sua
			// saída do programa?

			System.out.println("08) Nome da cliene com menor saldo na agência 47: ");
			accounts.stream().filter(s -> s.getAgencia() == 47).sorted(Comparator.comparing(Account::getBalance))
					.limit(3).forEach(s -> System.out.print(s.getName() + ","));

			// 9no - Quantos clientes estão na agência 47:
			Integer sumQuantidadeClientesAgencia47 = (int) accounts.stream()
					.filter(s -> s.getAgencia() == 47).count();
			System.out.println("\n09) Quantos clientes estão na agência 47: " + sumQuantidadeClientesAgencia47);

			// 10mo - Quantos clientes que têm "Maria" no nome estão na agencia 47:
			Integer sumQuantidadeMariasTemAgencia47 = (int) accounts.stream()
					.filter(s -> s.getAgencia() == 47 && s.getName().toLowerCase().contains("maria")).count();
			System.out.println("10) Quantos clientes que têm \"Maria\" no nome estão na agencia 47: " + sumQuantidadeMariasTemAgencia47);

			// 11vo - Considerando qeu o id deve ser único e é sequencial, qual o próximo id possível para conta?
			System.out.println("11) Considerando qeu o id deve ser único e é sequencial, qual o próximo id possível para conta?: " + (accounts.size() + 1));

			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
