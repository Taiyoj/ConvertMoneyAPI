import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/dbc12f19bcbb2f3b39378ae6/latest/USD";

    public static void main(String[] args) {
        try {
            // Obtener tasas de cambio desde la API
            JsonObject rates = cmapi.fetchExchangeRates(API_URL);

            // Crear un menú para el usuario
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Seleccione la conversión de moneda:");
                System.out.println("1. USD a EUR");
                System.out.println("2. EUR a USD");
                System.out.println("3. COP a USD");
                System.out.println("4. Salir");

                int choice = scanner.nextInt();

                if (choice == 4) {
                    break;
                }

                System.out.println("Ingrese la cantidad a convertir:");
                double amount = scanner.nextDouble();
                double convertedAmount = 0;

                switch (choice) {
                    case 1:
                        convertedAmount = convertCurrency(rates, "USD", "EUR", amount);
                        System.out.printf("%.2f USD es igual a %.2f EUR%n", amount, convertedAmount);
                        break;
                    case 2:
                        convertedAmount = convertCurrency(rates, "EUR", "USD", amount);
                        System.out.printf("%.2f EUR es igual a %.2f USD%n", amount, convertedAmount);
                        break;
                    case 3:
                        convertedAmount = convertCurrency(rates, "COP", "USD", amount);
                        System.out.printf("%.2f COP es igual a %.2f USD%n", amount, convertedAmount);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double convertCurrency(JsonObject rates, String fromCurrency, String toCurrency, double amount) {
        double rateFrom = rates.getAsJsonObject("conversion_rates").get(fromCurrency).getAsDouble();
        double rateTo = rates.getAsJsonObject("conversion_rates").get(toCurrency).getAsDouble();
        return amount * (rateTo / rateFrom);
    }
}