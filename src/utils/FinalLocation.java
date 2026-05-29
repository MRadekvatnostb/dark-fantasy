package utils;


import engine.BattleMaster;
import go.Main;
import logic.Enemy;
import logic.Player;

public class FinalLocation {
    private Player player;
    public FinalLocation(Player player) { this.player = player; } // наш игрок это игрок тут

    public void startFinal() {
        GameUtils.slowPrint("\n=== ВЫ ВСТУПИЛИ В ЗАЛ ПРАВОСУДИЯ ===", 20);
        GameUtils.slowPrint("Огромный зал с треснувшим мраморным полом. Сквозь дыры в куполе падает бледный свет...",30);
        GameUtils.slowPrint("Перед вами высится СЛЕПОЙ КАРАТЕЛЬ. Его голос гремит под сводами:",30);
        GameUtils.slowPrint("\"Смертный, прежде чем получить прощение, ответь на мои вопросы...\"",30);
        GameUtils.drawLine(20,10);
        boolean failed = false; // переменная для проверки на правильность ответов

        GameUtils.slowPrint("\n1. Что тяжелее: сердце грешника или перо истины?",15);
        System.out.println("Ответы: 1. Сердце 2. Перо 3. Они равны");
        if (Main.cs.nextInt() != 3) failed = true; // если ответ не 3, то хана
        if (!failed) {
            GameUtils.slowPrint("\n2. Можно ли смыть кровь кровью?",15);
            System.out.println("Ответы: 1. Да 2. Нет");
            if (Main.cs.nextInt() != 2) failed = true;
        }
        if (!failed) {
            GameUtils.slowPrint("\n3. Кто твой главный враг в этом путешествии?",15);
            System.out.println("Ответы: 1. Монстры 2. Ты сам 3. Каратель");
            if (Main.cs.nextInt() != 2) failed = true;
        }
        if (failed) {
            GameUtils.slowPrint("\n\"ТВОЯ ДУША ТЯЖЕЛА, А РАЗУМ ТУМАНЕН! ПРИГОВОР — СМЕРТЬ!\"",25);
            Enemy boss = new Enemy("БОСС: СЛЕПОЙ КАРАТЕЛЬ", 500, 5, 50, 8, 20, 5,5,500); // создаем босса если ошибся
            BattleMaster battle = new BattleMaster(player, boss); // создаем арену для игрока и босса
            battle.beginningFight(); // запускаем битву
        } else {
            GameUtils.slowPrint("\n\"Твой разум чист. Ты достоин пройти дальше без боя...\"",20);

        }
        GameUtils.slowPrint("Теневые цепи спадают, и двери за спиной Карателя открываются.",25); // конец
    }
}
