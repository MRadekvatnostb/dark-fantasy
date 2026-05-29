package go;
import data.DatabaseManager;
import java.util.Scanner;
import java.util.Random;
public class Main {
    public static final Scanner cs = new Scanner(System.in);
    public static final Random rand = new Random();
    public static void main(String[] args){
        DatabaseManager.createTableIfNotExists();
        Menu.menu();
    }
}
