package utils;


import go.Main;
import logic.Enemy;

import java.util.ArrayList;
public class Rooms { // чертеж для комнат, врагов и боссов
    private String name;
    private String desc;
    private Enemy boss;
    private ArrayList<Enemy> enemies = new ArrayList<>(); // список для врагов

    public Rooms(String name, String desc) { // имя и описание локации
        this.name = name;
        this.desc = desc;
    }

    public void setBoss(Enemy boss) { // создание босса
        this.boss = boss;
    }

    public Enemy getBoss() { // делимся боссом с другими классами
        return this.boss;
    }

    public void addEnemy(Enemy enemy) { // создание врага
        enemies.add(enemy); // кладем в список
    }

    public Enemy getRandomEnemy() { // метод для получения случайного врага из этой локации
        int index = Main.rand.nextInt(enemies.size());
        Enemy template = enemies.get(index);
        return new Enemy(template);
    }
    public String getName() { return this.name; } // делимся именем
    public String getDesc() { return this.desc; } // делимся описанием
}
