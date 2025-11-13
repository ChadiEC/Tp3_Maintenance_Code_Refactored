package chadi.exceptions;

//Façon propre et reutilisable de d'empêcher les invalidité du systeme(utilisé presque partout dans le projet)
public class StockExeptions extends Exception{
    public StockExeptions(String message){
        super(message);
    }
}
