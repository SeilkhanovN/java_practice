package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;
//efjwefjkwn
@Component
public class ConsoleRunner implements CommandLineRunner {
    private final ShopService shopService;
    private final Scanner scanner;

    public ConsoleRunner(ShopService shopService) {
        this.shopService = shopService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) {
        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();

            try {
                processCommand(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void processCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0];

        switch (command) {
            case "/addCustomer":
                if (parts.length < 2) {
                    System.out.println("Usage: /addCustomer name");
                    return;
                }
                Customer customer = new Customer();
                customer.setName(parts[1]);
                shopService.saveCustomer(customer);
                break;

            case "/addProduct":
                if (parts.length < 3) {
                    System.out.println("Usage: /addProduct title price");
                    return;
                }
                Product product = new Product();
                product.setTitle(parts[1]);
                product.setPrice(new BigDecimal(parts[2]));
                shopService.saveProduct(product);
                break;

            case "/buy":
                if (parts.length < 3) {
                    System.out.println("Usage: /buy customer_name product_titleee");
                    return;
                }
                shopService.buyProduct(parts[1], parts[2]);
                break;

            case "/showProductsByPerson":
                if (parts.length < 2) {
                    System.out.println("Usage: /showProductsByPerson customer_name");
                    return;
                }
                shopService.getProductsByCustomer(parts[1])
                        .forEach(p -> System.out.println(p.getTitle() + " - " + p.getPrice()));
                break;

            case "/findPersonsByProductTitle":
                if (parts.length < 2) {
                    System.out.println("Usage: /findPersonsByProductTitle product_title");
                    return;
                }
                shopService.getCustomersByProduct(parts[1])
                        .forEach(c -> System.out.println(c.getName()));
                break;

            case "/removePerson":
                if (parts.length < 2) {
                    System.out.println("Usage: /removePerson customer_name");
                    return;
                }
                shopService.removeCustomer(parts[1]);
                break;

            case "/removeProduct":
                if (parts.length < 2) {
                    System.out.println("Usage: /removeProduct product_title");
                    return;
                }
                shopService.removeProduct(parts[1]);
                break;

            default:
                System.out.println("Unknown command");
        }
    }
}

