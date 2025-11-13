package chadi.ui;
import java.util.*;

import chadi.services.InventoryService;
import chadi.services.OrderService;

//C'est ici qu'on gère la premiere page "UI" on peut dire quand on run le code un peu la meme logique
//Mais maitnenant avec un switch case au lieu de if repetitif, et aussi dans un autre fichier

public class ConsoleUi {
    public static Scanner scanner = new Scanner(System.in);
    private final InventoryService inventoryService = new InventoryService();
    private final OrderService orderService = new OrderService(inventoryService);

    public void start() {
        System.out.println("=== MCDONALDS ===");
        boolean running = true;

        while (running) {
            System.out.println("\n1. Mode Client");
            System.out.println("2. Mode Inventaire");
            System.out.println("3. Quitter");
            System.out.print("Choix: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> orderService.clientMode(scanner);
                case 2 -> inventoryService.manageInventory(scanner);
                case 3 -> running = false;
                default -> System.out.println("Choix invalide !");
            }
        }
    }
}