package chadi.app;

import chadi.ui.ConsoleUi;

public class AppMcDonalds {
    public static void main(String[] args) {
        //Au lieu de gérer les modes dans la même page ici on gère le tout dans une page ui dédier à cela
        ConsoleUi ui = new ConsoleUi();
        ui.start();
    }
}
