package engine;

import data.DatabaseManager;
import go.Main;
import logic.Enemy;
import logic.Player;
import utils.*;

public class GameEngine {
    private Player player;
    Location location = new Location();
    ListItem item = new ListItem();
    public GameEngine(Player player) {
        this.player = player; // создает игрока в этом классе в которого мы впихиваем нашего игрока
    }
    public void run() {
        System.out.println("\n--- ЗАПУСК ---");

        // --- ВСТАВКА №1: ОПРЕДЕЛЯЕМ ТОЧКУ СТАРТА ---
        int startLocIndex = 0;
        for (int i = 0; i < location.getSize(); i++) {
            // Если имя локации из базы совпадает с именем в нашем списке — начинаем оттуда
            if (location.getRoom(i).getName().equals(player.getCurrentLocation())) {
                startLocIndex = i;
                break;
            }
        }

        Rooms currentRoom;
        // Теперь цикл начинается не с 0, а с startLocIndex
        for (int locIndex = startLocIndex; locIndex < location.getSize(); locIndex++) {

            if (!player.isAlive()) break;

            currentRoom = location.getRoom(locIndex);
            player.setCurrentLocation(currentRoom.getName());

            GameUtils.slowPrint("\n>>> ПЕРЕХОД: " + currentRoom.getName() + " <<<", 50);
            GameUtils.pause(500);
            GameUtils.slowPrint(currentRoom.getDesc(), 30);
            GameUtils.pause(1000);

            for (int battleNum = 1; battleNum <= 6; battleNum++) {
                if (!player.isAlive()) break;

                System.out.println("\n=== Этап " + battleNum + " из 6 ===");

                if (battleNum < 6) {
                    Enemy commonEnemy = currentRoom.getRandomEnemy();
                    startBattle(commonEnemy);

                    if (player.isAlive()) {
                        if(Main.rand.nextInt(100) < 60) player.addItem(item.getRandomHeal());
                        if(Main.rand.nextInt(100) < 40) player.addItem(item.getRandomFood());
                        if(Main.rand.nextInt(100) < 30) player.addItem(item.getRandomArtefact());
                        if(Main.rand.nextInt(100) < 90) randomEvent();
                    }
                } else {
                    System.out.println("!!! ТРЕВОГА !!! Воздух стал тяжелым...");
                    Enemy boss = currentRoom.getBoss();
                    startBattle(boss);
                    if (!player.isAlive()) break;
                }
            }
            if (!player.isAlive()) {
                System.out.println("Игра окончена.");
                break;
            }
            if (player.isAlive()) {
                System.out.println("\n** ВЫ ОДОЛЕЛИ ВСЕХ В " + currentRoom.getName() + " **");
                player.setHp(this.player.getHp() + 50);

                // ВАЖНО: Проверяем, есть ли следующая локация, и заранее записываем её игроку
                if (locIndex + 1 < location.getSize()) {
                    player.setCurrentLocation(location.getRoom(locIndex + 1).getName());
                } else {
                    player.setCurrentLocation("Финал");
                }

                DatabaseManager.savePlayer(player); // Теперь в базу улетит УЖЕ СЛЕДУЮЩАЯ локация
                System.out.println(">>> Прогресс сохранен! <<<");
            }

        }

        // Финальная сцена (если все локации пройдены)
        if (player.isAlive()) {
            GameUtils.pause(2000);
            GameUtils.slowPrint("\n...Труп Урки медленно сползает по ржавой стене...", 40);
            GameUtils.pause(1000);
            GameUtils.slowPrint("Внезапно, гул труб затихает. Ржавчина начинает осыпаться серебряной пылью.", 30);
            GameUtils.pause(2000);

            FinalLocation finalScene = new FinalLocation(this.player);
            finalScene.startFinal();
        }
    }

    public void startBattle(Enemy enemy) { // метод начала боя
        System.out.println("Внимание! На пути " + enemy.getName());
        BattleMaster fight = new BattleMaster(this.player, enemy); // создаем бой и пихаем туда игрока и врага
        fight.beginningFight(); // старт боя
    }

    public void randomEvent() {
    }
}

