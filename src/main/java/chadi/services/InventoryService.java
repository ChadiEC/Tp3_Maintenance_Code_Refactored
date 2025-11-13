package chadi.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import chadi.models.Item;

//Ici on fait tout la logique pour l'inventaire, sans mélanger cela avec la logique poru le mode client
public class InventoryService {
    private final List<Item> inventory = new ArrayList<>();

    public InventoryService() {
        inventory.add(new Item("Big Mac", 6.99, 50, "main"));
        inventory.add(new Item("Quarter Pounder", 7.49, 40, "main"));
        inventory.add(new Item("McChicken", 5.99, 45, "main"));
        inventory.add(new Item("Frites", 3.49, 100, "snack"));
        inventory.add(new Item("Nuggets (6)", 4.99, 60, "snack"));
        inventory.add(new Item("Coca-Cola", 2.49, 80, "drink", "Medium"));
        inventory.add(new Item("Sprite", 2.49, 70, "drink", "Medium"));
        inventory.add(new Item("Jus d'orange", 2.99, 50, "drink", "Medium"));
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void manageInventory(Scanner sc) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== INVENTAIRE ===");
            System.out.println("1. Afficher inventaire");
            System.out.println("2. Ajouter stock");
            System.out.println("3. Retirer stock");
            System.out.println("4. Retour");
            System.out.print("Choix: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> showInventory();
                case 2 -> addStock(sc);
                case 3 -> removeStock(sc);
                case 4 -> running = false;
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private void showInventory() {
        for (Item i : inventory)
            System.out.println(i.getName() + " - " + i.getStock() + " unités");
    }

    private void addStock(Scanner sc) {
        sc.nextLine();
        System.out.print("Nom de l’item: ");
        String name = sc.nextLine();
        inventory.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(item -> {
                    System.out.print("Quantité à ajouter: ");
                    int qty = sc.nextInt();
                    item.setStock(item.getStock() + qty);
                    System.out.println("Stock ajouté !");
                }, () -> System.out.println("Item non trouvé."));
    }

    private void removeStock(Scanner sc) {
        sc.nextLine();
        System.out.print("Nom de l’item: ");
        String name = sc.nextLine();
        inventory.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(item -> {
                    System.out.print("Quantité à retirer: ");
                    int qty = sc.nextInt();
                    if (item.getStock() >= qty) {
                        item.setStock(item.getStock() - qty);
                        System.out.println("Stock retiré !");
                    } else {
                        System.out.println("Pas assez de stock !");
                    }
                }, () -> System.out.println("Item non trouvé."));
    }
}