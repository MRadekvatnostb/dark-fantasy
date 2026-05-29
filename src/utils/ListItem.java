package utils;


import go.Main;

public class ListItem {
    public GameItem getRandomHeal() { // получение рандомной хилки
        int choice = Main.rand.nextInt(3);
        return switch (choice) {
            case 0 -> new GameItem.Builder("Склянка йода")
                    .setHealBuff(25)
                    .setDuration(1)
                    .build();
            case 1 -> new GameItem.Builder("Аптечный бинт")
                    .setHealBuff(45)
                    .setDuration(1)
                    .build();
            default -> new GameItem.Builder("Святая вода")
                    .setHealBuff(60)
                    .setDuration(1)
                    .build();
        };
    }
    public GameItem getRandomFood() { // получение рандомной еды
        int choice = Main.rand.nextInt(3);
        return switch (choice) {
            case 0 -> new GameItem.Builder("Плесневелый хлеб")
                    .setDmgBuff(10)
                    .setHealBuff(25)
                    .setArmorBuff(10)
                    .setDuration(3)
                    .build();
            case 1 -> new GameItem.Builder("Оливье")
                    .setDmgBuff(20)
                    .setCritChanceBuff(20)
                    .setHealBuff(35)
                    .setDuration(3)
                    .build();
            default -> new GameItem.Builder("Сух-паек")
                    .setDmgBuff(30)
                    .setIgnoreDmgBuff(20)
                    .setDodgeBuff(20)
                    .setHealBuff(50)
                    .setDuration(3)
                    .build();
        };
    }
    public GameItem getRandomArtefact() { // получение рандомного артефакта
        int choice = Main.rand.nextInt(11);
        return switch (choice) {
            case 0 -> new GameItem.Builder("Ржавый ключ")
                    .setDmgBuff(15)
                    .setHealBuff(15)
                    .build();
            case 1 -> new GameItem.Builder("Массивная труба")
                    .setDmgBuff(10)
                    .build();
            case 2 -> new GameItem.Builder("Провод электрика")
                    .setStunBuff(15)
                    .setCritBuff(5)
                    .setCritChanceBuff(5)
                    .build();
            case 3 -> new GameItem.Builder("Мантия безликого")
                    .setIgnoreDmgBuff(15)
                    .setDmgBuff(7)
                    .build();
            case 4 -> new GameItem.Builder("Копыто конеголового")
                    .setMoneyBuff(20)
                    .build();
            case 5 -> new GameItem.Builder("???")
                    .setCritBuff(20)
                    .build();
            case 6 -> new GameItem.Builder("Мешок собирателя душ")
                    .setStillHpBuff(10)
                    .build();
            case 7 -> new GameItem.Builder("Глаз филина")
                    .setCritChanceBuff(15)
                    .build();
            case 8 -> new GameItem.Builder("Ветвь лешего")
                    .setIgnoreDmgBuff(15)
                    .setHealBuff(20)
                    .build();
            case 9 -> new GameItem.Builder("Зубы пираньи")
                    .setDoubleAttackBuff(15)
                    .build();
            default -> new GameItem.Builder("Крыло падшего ангела")
                    .setResurrectionBuff(30)
                    .build();
        };
    }
    public static GameItem createItemByName(String name) {
        return switch (name) {
            case "Ржавый ключ" -> new GameItem.Builder("Ржавый ключ").setDmgBuff(15).build();
            case "Оливье" -> new GameItem.Builder("Оливье").setHealBuff(30).setDuration(2).build();
            case "Зубы пираньи" -> new GameItem.Builder("Зубы пираньи").setDoubleAttackBuff(15).build();
            case "Крыло падшего ангела" -> new GameItem.Builder("Крыло падшего ангела").setResurrectionBuff(30).build();
            case "Ветвь лешего" -> new GameItem.Builder("Ветвь лешего").setIgnoreDmgBuff(15).setHealBuff(20).build();
            case "Глаз филина" -> new GameItem.Builder("Глаз филина").setCritChanceBuff(15).build();
            case "Мешок собирателя душ" -> new GameItem.Builder("Мешок собирателя душ").setStillHpBuff(10).build();
            case "???" -> new GameItem.Builder("???").setCritBuff(20).build();
            case "Копыто конеголового" -> new GameItem.Builder("Копыто конеголового").setMoneyBuff(20).build();
            case "Мантия безликого" -> new GameItem.Builder("Мантия безликого").setIgnoreDmgBuff(15).setDmgBuff(7).build();
            case "Провод электрика" -> new GameItem.Builder("Провод электрика").setStunBuff(15).setCritBuff(5).setCritChanceBuff(5).build();
            case "Массивная труба" -> new GameItem.Builder("Массивная труба").setDmgBuff(10).build();
            case "Сух-паек" -> new GameItem.Builder("Сух-паек").setDmgBuff(30).setIgnoreDmgBuff(20).setDodgeBuff(20).setHealBuff(50).setDuration(3).build();
            case "Плесневелый хлеб" -> new GameItem.Builder("Плесневелый хлеб").setDmgBuff(10).setHealBuff(25).setArmorBuff(10).setDuration(3).build();
            case "Склянка йода" -> new GameItem.Builder("Склянка йода").setHealBuff(25).setDuration(1).build();
            case "Аптечный бинт" -> new GameItem.Builder("Аптечный бинт").setHealBuff(45).setDuration(1).build();
            case "Святая вода" -> new GameItem.Builder("Святая вода").setHealBuff(60).setDuration(1).build();

            // Допиши сюда всё, что у тебя есть в игре!
            default -> new GameItem.Builder(name).build();
        };
    }
}



