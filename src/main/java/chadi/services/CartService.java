package chadi.services;

import java.util.ArrayList;
import java.util.List;
import chadi.models.CartItem;
import chadi.models.Item;
import chadi.exceptions.StockExeptions;

public class CartService {
    private final List<CartItem> cart = new ArrayList<>();

    public void addItem(Item item) throws StockExeptions {
        if (item.getStock() <= 0)
            throw new StockExeptions("Stock insuffisant pour " + item.getName());
        cart.add(new CartItem(item));
    }

    public void addTrio(Item main, Item snack, Item drink) throws StockExeptions {
        if (main.getStock() <= 0 || snack.getStock() <= 0 || drink.getStock() <= 0)
            throw new StockExeptions("Stock insuffisant pour ce trio");
        cart.add(new CartItem(main, snack, drink));
    }

    public double getTotal() {
        return cart.stream().mapToDouble(CartItem::getPrice).sum();
    }

    public void clear() {
        cart.clear();
    }

    public List<CartItem> getCart() {
        return cart;
    }
}