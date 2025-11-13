package chadi.models;


//Represente un item du menu (plat, snack, boisson).
//Respecte l'encapsulation et expose uniquement ce qui est nécessaire.

public class Item {
    private String name;
    private double price;
    private int stock;
    private String type; // "main", "snack", "drink"
    private String size; // optionnel pour les boissons

    //Constructeurs
    public Item(String name, double price, int stock, String type) {
        this(name, price, stock, type, null);
    }

    public Item(String name, double price, int stock, String type, String size) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.size = size;
    }

    //Getters et Setters necessaire ici pour modifier la quantité d'un item
    //Contrairement au cart item
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getType() { return type; }
    public String getSize() { return size; }

    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("Le stock ne peut pas être négatif");
        this.stock = stock;
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Le prix ne peut pas être négatif");
        this.price = price;
    }

    @Override
    public String toString() {
        String display = name + " - " + price + "$ (" + stock + " en stock)";
        if (size != null) display += " [" + size + "]";
        return display;
    }
}
