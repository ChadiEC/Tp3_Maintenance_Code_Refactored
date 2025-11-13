package chadi.models;

//Représente un item dans le panier du client.
//Peut etre un item simple ou un trio.
public class CartItem {
    private Item mainItem;
    private Item snackItem;   // optionnel pour trio
    private Item drinkItem;   // optionnel pour trio
    private boolean isTrio;

    //Constructeurs
    public CartItem(Item item) {
        this.mainItem = item;
        this.isTrio = false;
    }

    public CartItem(Item main, Item snack, Item drink) {
        this.mainItem = main;
        this.snackItem = snack;
        this.drinkItem = drink;
        this.isTrio = true;
    }

    //Méthodes métier ici
    public double getPrice() {
        double total = mainItem.getPrice();
        if (isTrio) {
            total += snackItem.getPrice() + drinkItem.getPrice();
            total *= 0.85; // 15% de rabais
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public String getDescription() {
        if (isTrio)
            return "TRIO: " + mainItem.getName() + " + " +
                    snackItem.getName() + " + " +
                    drinkItem.getName() + " (15% rabais)";
        return mainItem.getName();
    }

    //Getters(pas besoin de setters ici)
    public boolean isTrio() { return isTrio; }
    public Item getMainItem() { return mainItem; }
    public Item getSnackItem() { return snackItem; }
    public Item getDrinkItem() { return drinkItem; }
}
