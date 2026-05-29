package go;

import data.DatabaseManager;
import engine.GameEngine;
import heroes.*;
import logic.Player;
import utils.GameUtils;

public class Menu {
    public static void menu() {
        Player player = null; // игрок изначально ничего
        System.out.println("=== ТЕНЬ БЕЗДНЫ ===");
        System.out.println("1. Новая игра");
        System.out.println("2. Продолжить путь (Загрузка)");

        int num = Main.cs.nextInt();
        if (num == 1) {
            while (player == null) { // цикл пока игрок ничего
                System.out.println("Выбери персонажа: 1. Рыцарь, 2. Вампир, 3. Лучник, 4. Маг, 5. Нежить ");
                if (Main.cs.hasNextInt()) { // проверка
                    int choice = Main.cs.nextInt();
                    switch (choice) {
                        case 1:
                            player = new Knight();
                            break;
                        case 2:
                            player = new Vampire();
                            break;
                        case 3:
                            player = new Archer();
                            break;
                        case 4:
                            player = new Wizard();
                            break;
                        case 5:
                            player = new Undeath();
                            break;
                        default:
                            System.out.println("ERROR, ENTER 1-5");
                            break;
                    }
                    if (player != null) {
                        DatabaseManager.savePlayer(player);
                    }
                } else {
                    String junk = Main.cs.next();
                    System.out.println("Слышь, '" + junk + "' — это не цифра. Давай по новой...");
                }
            }
        } else if(num == 2) {
            player = DatabaseManager.loadLastPlayer();
        } else
        GameUtils.pause(200);
        GameUtils.drawLine(20,10);
        GameUtils.slowPrint("Ты выбрал героя: " + player.getName(),15);
        GameUtils.slowPrint("Твое HP: " + player.getHp(),15);
        GameUtils.slowPrint("Твой урон: " + player.getMinDamage() + " - " + player.getMaxDamage(),15);
        GameUtils.slowPrint("Твоя защита: " + player.getArmor(),15);
        GameUtils.slowPrint("Твой критический шанс: " + player.getCritChance(),15);
        GameUtils.slowPrint("Твой вампиризм: " + player.getVampirism(),15);
        GameUtils.drawLine(20,10);
        GameEngine gameEngine = new GameEngine(player); // запуск игрока в игру
        GameUtils.slowPrint("\nПриветствуем тебя, " + player.getName() + "!",15);
        gameEngine.run(); // погнали
    }
}
