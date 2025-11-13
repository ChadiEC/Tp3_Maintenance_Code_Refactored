package chadi.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chadi.models.CartItem;
import chadi.models.Item;
import chadi.services.InventoryService;
import chadi.exceptions.StockExeptions;

//Gère toute la logique du mode Client :
//affichage du menu
//ajout au panier
//retrait et passeage de la commande
//(sans mélanger la logique d'inventaire comme dans l'ancien code)

public class OrderService {
    private final InventoryService inventoryService;
    private final CartService cartService = new CartService();
    private int orderNumber = 1;

    public OrderService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

//Menu principal du client.
    public void clientMode(Scanner sc) {
        sc.nextLine();
        System.out.print("Nom du client: ");
        String name = sc.nextLine();
        System.out.println("Bienvenue " + name + " !");
        cartService.clear();

        boolean running = true;
        while (running) {
            System.out.println("\n=== MODE CLIENT ===");
            System.out.println("1. Voir menu");
            System.out.println("2. Ajouter un TRIO");
            System.out.println("3. Ajouter un item");
            System.out.println("4. Voir panier");
            System.out.println("5. Retirer du panier");
            System.out.println("6. Passer commande");
            System.out.println("7. Retour");
            System.out.print("Choix: ");

            int choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1 -> showMenu();
                    case 2 -> addTrio(sc);
                    case 3 -> addItem(sc);
                    case 4 -> viewCart();
                    case 5 -> removeFromCart(sc);
                    case 6 -> confirmOrder();
                    case 7 -> running = false;
                    default -> System.out.println("Choix invalide !");
                }
            } catch (StockExeptions e) {
                System.out.println("⚠️ " + e.getMessage());
            }
        }
    }
//Methodes utiliser

    private void showMenu() {
        System.out.println("\n=== MENU COMPLET ===");
        for (Item i : inventoryService.getInventory()) {
            System.out.println("- " + i);
        }
    }

    private void addItem(Scanner sc) throws StockExeptions {
        List<Item> list = inventoryService.getInventory();
        System.out.println("\n=== AJOUTER UN ITEM ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, list.get(i));
        }

        System.out.print("Votre choix: ");
        int index = sc.nextInt() - 1;
        if (index < 0 || index >= list.size())
            throw new StockExeptions("Choix invalide.");
        Item selected = list.get(index);
        cartService.addItem(selected);
        System.out.println("✓ " + selected.getName() + " ajouté au panier.");
    }

    private void addTrio(Scanner sc) throws StockExeptions {
        List<Item> mains = filterByType("main");
        List<Item> snacks = filterByType("snack");
        List<Item> drinks = filterByType("drink");

        System.out.println("\n=== CHOISIR UN TRIO ===");
        System.out.println("Plats principaux:");
        Item main = chooseItem(sc, mains);
        System.out.println("Accompagnements:");
        Item snack = chooseItem(sc, snacks);
        System.out.println("Boissons:");
        Item drink = chooseItem(sc, drinks);

        cartService.addTrio(main, snack, drink);
        System.out.println("✓ Trio ajouté au panier !");
    }

    private Item chooseItem(Scanner sc, List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, items.get(i));
        }
        System.out.print("Votre choix: ");
        int idx = sc.nextInt() - 1;
        return (idx >= 0 && idx < items.size()) ? items.get(idx) : null;
    }

    private void viewCart() {
        List<CartItem> cart = cartService.getCart();
        if (cart.isEmpty()) {
            System.out.println("\nPanier vide !");
            return;
        }
        System.out.println("\n=== VOTRE PANIER ===");
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            CartItem ci = cart.get(i);
            System.out.printf("%d. %s - %.2f$\n", i + 1, ci.getDescription(), ci.getPrice());
            total += ci.getPrice();
        }
        System.out.println("----------------------");
        System.out.printf("TOTAL: %.2f$\n", total);
    }

    private void removeFromCart(Scanner sc) {
        List<CartItem> cart = cartService.getCart();
        if (cart.isEmpty()) {
            System.out.println("Panier vide !");
            return;
        }
        viewCart();
        System.out.print("Numéro de l’item à retirer (0 pour annuler): ");
        int index = sc.nextInt();
        if (index > 0 && index <= cart.size()) {
            CartItem removed = cart.remove(index - 1);
            System.out.println("✓ " + removed.getDescription() + " retiré du panier !");
        }
    }

    private void confirmOrder() throws StockExeptions {
        List<CartItem> cart = cartService.getCart();
        if (cart.isEmpty()) {
            System.out.println("Panier vide !");
            return;
        }

//Vérification du stock avant confirmation
        for (CartItem ci : cart) {
            if (ci.isTrio()) {
                if (ci.getMainItem().getStock() <= 0 ||
                        ci.getSnackItem().getStock() <= 0 ||
                        ci.getDrinkItem().getStock() <= 0) {
                    throw new StockExeptions("Stock insuffisant pour " + ci.getDescription());
                }
            } else if (ci.getMainItem().getStock() <= 0) {
                throw new StockExeptions("Stock insuffisant pour " + ci.getMainItem().getName());
            }
        }

// Mise à jour du stock avec les setter
        for (CartItem ci : cart) {
            ci.getMainItem().setStock(ci.getMainItem().getStock() - 1);
            if (ci.isTrio()) {
                ci.getSnackItem().setStock(ci.getSnackItem().getStock() - 1);
                ci.getDrinkItem().setStock(ci.getDrinkItem().getStock() - 1);
            }
        }

//Reçu
        System.out.println("\n========= REÇU =========");
        System.out.println("Commande #" + orderNumber++);
        viewCart();
        System.out.println("========================");
        cartService.clear();
        System.out.println("✓ Commande passée avec succès !");
    }

    //util

    private List<Item> filterByType(String type) {
        List<Item> filtered = new ArrayList<>();
        for (Item item : inventoryService.getInventory()) {
            if (item.getType().equals(type)) filtered.add(item);
        }
        return filtered;
    }
}