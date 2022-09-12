package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.next();
		System.out.println();

		List<Sale> list = new ArrayList<>();
		Map<String, Double> saleMap = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String[] saleInfo = line.split(",");
				Sale sale = new Sale(Integer.parseInt(saleInfo[0]), Integer.parseInt(saleInfo[1]), saleInfo[2],
						Integer.parseInt(saleInfo[3]), Double.parseDouble(saleInfo[4]));
				list.add(sale);
				line = br.readLine();
			}

			list.stream().forEach(s -> saleMap.put(s.getSeller(), 
					list.stream()
						.filter(x -> x.getSeller().equals(s.getSeller()))
						.map(x -> x.getTotal())
						.reduce(0.0, (x, y) -> x + y)
					));
			
			System.out.println("Total de vendas por vendedor:");
			for (String seller : saleMap.keySet()) {
				System.out.println(seller + " - R$ " + String.format("%.2f", saleMap.get(seller)));
			}

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
}
