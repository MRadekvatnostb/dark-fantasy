package engine;


import go.Main;
import logic.Enemy;
import logic.Player;
import utils.EffectType;
import utils.GameItem;
import utils.GameUtils;

import java.util.ArrayList;
public class BattleMaster {
    private Player player;
    private Enemy enemy;
    private final int specialAttackChance = 15;
    private final int maxEnergy = 100;
    private final int escapeChance = 40;
    public BattleMaster(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void beginningFight() { // вход бой
        GameUtils.slowPrint("!!! В БОЙ !!!", 100);
        GameUtils.pause(500);
        System.out.println(player.getName() + " VS " + enemy.getName());
        GameUtils.pause(1000);

        while (player.isAlive() && enemy.isAlive()) { // цикл пока игрок и враг живы
            boolean escaped = playerTurn(); // попытка побега
            if (escaped) return; // убежал? конец боя
            player.updateEffects(); // не сбежал? обновляем эффекты
            if (player.isAlive() && enemy.isAlive()) { // проверка на жизнь после эффектов
                GameUtils.pause(800);
                enemyTurn();
                enemy.updateEffects();
            }
        }
        if (player.isAlive()) { // проверка кто выйграл
            System.out.println("Ты выиграл - " + enemy.getName() + " - ликвидирован");
        } else {
            System.out.println("Тебя на части разорвало.. - душа летит туда.. в начало");
        }
    }

    public boolean attackMove() {
        if(Main.rand.nextInt(100) < specialAttackChance) {
            GameUtils.slowPrint("Вы чувствуете прилив сил! ",25);
            player.superAttack(enemy); // спец атака шанс 17%
        } else {
            player.attack(enemy); // атака
        }
        return true; // ход сделан
    }

    public boolean inventoryChekEmpty(ArrayList<GameItem> inv, ArrayList<GameItem> consumables) {
        for (GameItem item : inv) {
            if (item.getHealBuff() > 0 || item.getDuration() > 0) {
                consumables.add(item);
            }
        }
        if (consumables.isEmpty()) {
            System.out.println("Никакой еды или зелий... Придется биться на сухую.");
            return false;
        } else {
            for (int i = 0; i < consumables.size(); i++) {
                GameItem item = consumables.get(i);
                String stats = (item.getHealBuff() > 0 ? " +" + item.getHealBuff() + "HP" : "") +
                        (item.getDuration() > 0 ? " [Бафф на " + item.getDuration() + "х]" : "");
                System.out.println(i + ". " + item.getName() + stats);
            }
            return true;
        }
    }

    public void instHeal(GameItem itemToUse) {
        if (itemToUse.getHealBuff() > 0) {
            int oldHp = player.getHp();
            player.setHp(Math.min(player.getHp() + itemToUse.getHealBuff(), player.getMaxHp()));
            System.out.println(">> Восстановлено " + (player.getHp() - oldHp) + " HP");
        }
    }

    public void addTimeEffect(GameItem itemToUse) {
        int dur = itemToUse.getDuration();
        if (dur > 0) {
            if (itemToUse.getDmgBuff() > 0) player.addEffect(EffectType.DMG, itemToUse.getDmgBuff(), dur);
            if (itemToUse.getArmorBuff() > 0) player.addEffect(EffectType.ARMOR, itemToUse.getArmorBuff(), dur);
            if (itemToUse.getCritChanceBuff() > 0) player.addEffect(EffectType.CRIT, itemToUse.getCritChanceBuff(), dur);
            if (itemToUse.getDodgeBuff() > 0) player.addEffect(EffectType.DODGE, itemToUse.getDodgeBuff(), dur);
            if (itemToUse.getStunBuff() > 0) player.addEffect(EffectType.STUN, itemToUse.getStunBuff(), dur);
        }
    }

    public boolean managerInventory(ArrayList<GameItem> inv, ArrayList<GameItem> consumables) {
        if (!inventoryChekEmpty(inv, consumables)) {
            return false;
        }

        System.out.println("Выбери номер предмета или 99 для отмены:");
        if (Main.cs.hasNextInt()) {
            int choiceIdx = Main.cs.nextInt();
            if (choiceIdx == 99) {
                return false; // Нажал отмену — ход не тратится
            }

            if (choiceIdx < consumables.size() && choiceIdx >= 0 ) {
                GameItem itemToUse = consumables.get(choiceIdx);

                instHeal(itemToUse);
                addTimeEffect(itemToUse);

                System.out.println("Вы использовали: " + itemToUse.getName());
                inv.remove(itemToUse); // Удаляем из основного инвентаря игрока
                return true; // Предмет успешно использован, ход сделан!
            } else {
                System.out.println("Нет такого номера предмета!");
                return false;
            }
        } else {
            Main.cs.next(); // чистим мусор из сканера
            return false;
        }
    }

    public boolean inventoryMove() {
        GameUtils.drawLine(20,10);
        System.out.println("Ваш инвентарь: ");
        ArrayList<GameItem> inv = player.getInventory();
        ArrayList<GameItem> consumables = new ArrayList<>();
        return managerInventory(inv, consumables);
    }
    public boolean ultaMove() {
        if (player.getEnergy() >= maxEnergy) { // если энергии 100, то можно использовать ульту
            GameUtils.drawLine(20, 10);
            player.useUltimate(enemy); // ультуем
            return true; // ход сделан
        } else { // если не 100
            System.out.println("Ярости мало! (Нужно 100, у тебя " + player.getEnergy() + ")");
            GameUtils.drawLine(20, 10);
            return false;
        }

    }
    public boolean runMove() {
        if (enemy.getName().toUpperCase().contains("БОСС")) { // проверяем, не босс ли это
            GameUtils.slowPrint("!!! ВЫ НЕ МОЖЕТЕ СБЕЖАТЬ ОТ БОССА !!!", 30);
            GameUtils.slowPrint("Страх сковывает ваши ноги, " + enemy.getName() + " заблокировал выход!", 20);
            GameUtils.drawLine(20, 10);
            // не ставим turnMade = true, чтобы игрок мог выбрать другое действие
            return false;
        } else {
            // логика побега для обычных мобов
            if (Main.rand.nextInt(100) < escapeChance) {
                GameUtils.slowPrint("Вы делаете пару ловких движений и убегаете с боя!", 20);
                return true; // повезло - убежали
            } else {
                GameUtils.slowPrint("Вы пытаетесь убежать! Враг бьет в спину!", 20);
                return false; // не повезло - враг бьет
            }

        }
    }

    public boolean playerTurn() { // ход игрока
        boolean turnMade = false; // проверка совершение хода

        while (!turnMade) { // цикл пока не сделаем ход
            System.out.println("\n" + "=".repeat(50));
            System.out.printf(" %-15s HP: [%-10s] Энергия: [%-3d] Эффекты: %s\n",
                    player.getName(), player.getHp() + "/" + player.getMaxHp(), player.getEnergy(), player.getEffectsList()); // без понятия, что за %-10s и так далее

            System.out.printf(" %-15s HP: [%-10s] Эффекты: %s\n",
                    enemy.getName(), enemy.getHp(), enemy.getEffectsList());
            System.out.println("=".repeat(50));
            GameUtils.pause(300);
            System.out.println("Выбери действие: 1 Атака | 2 Исцеление | 3 Ульта | 4 Побег");


            if (!Main.cs.hasNextInt()) { // проверка на цифру
                String junk = Main.cs.next(); // очищаем сканер
                System.out.println("Слышь, '" + junk + "' — это не цифра. Давай по новой...");
                continue; // цикл заново
            }

            int choice = Main.cs.nextInt();
            switch (choice) { // выбор действия
                case 1:
                    turnMade = attackMove();
                    break;
                case 2:
                    turnMade = inventoryMove();
                    break;
                case 3:
                    turnMade = ultaMove();
                    break;
                case 4:
                    if (enemy.getName().toUpperCase().contains("БОСС")) {
                        runMove(); // Просто вызовет сообщение, turnMade останется false
                    } else {
                        // Для обычного моба:
                        boolean success = runMove();
                        if (success) {
                            return true; // Если побег удался, сразу выходим из всего боя!
                        } else {
                            turnMade = true; // Если провалился, ход сделан (враг бьет в спину)
                        }
                    }
                    break;
                default:
                    System.out.println("Нет такого варианта, не тупи!");
                    GameUtils.drawLine(20,10);
                    break;
            }
        }
        player.gainEnergy(20); // энергия, с новым ходом прибавляется
        return false; // возвращаем значение false ходу
    }
    public void enemyTurn(){ // ход врага
        if (enemy.getEffectsList().contains("STUN")) {
            System.out.println("--- " + enemy.getName() + " оглушен и пропускает ход! ---");
            return;
        }
        System.out.println("\n--- Ход врага ---");
        GameUtils.pause(400);
        enemy.attack(player); // атака
        GameUtils.pause(500);

    }

}
