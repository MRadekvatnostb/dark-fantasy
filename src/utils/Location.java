package utils;

import java.util.ArrayList;
public class Location {
    private final ArrayList<Rooms> locations = new ArrayList<>(); // создаем список с локациями и врагами
    public Location(){
        Rooms forest = new Rooms("Шепчущий лес", "Это безумно опасный лес, своим шептанием он сводит с ума"); // локация и описание
        forest.addEnemy(EnemyFactory.createEnemy(EnemyType.FACELESS)); // враг
        forest.addEnemy(EnemyFactory.createEnemy(EnemyType.OWL));
        forest.addEnemy(EnemyFactory.createEnemy(EnemyType.ORC));
        forest.setBoss(EnemyFactory.createBoss(EnemyType.BOSS_LESHY)); // босс
        locations.add(forest);

        Rooms workshop = new Rooms("Безмолвный цех", "Когда-то громоздкий и шумный цех в один день замолчал, больше в нем не было ни звука");
        workshop.addEnemy(EnemyFactory.createEnemy(EnemyType.CANNIBAL_LOCKSMITH));
        workshop.addEnemy(EnemyFactory.createEnemy(EnemyType.BARE_ELECTRICIAN));
        workshop.addEnemy(EnemyFactory.createEnemy(EnemyType.CONVICT));
        workshop.setBoss(EnemyFactory.createBoss(EnemyType.BOSS_BRIGADIER));
        locations.add(workshop);

        Rooms fieldSoul = new Rooms("Поле блуждающих душ", "Бывшее зеленое поле стало пристанищем душ, с тех пор жизни там больше нет");
        fieldSoul.addEnemy(EnemyFactory.createEnemy(EnemyType.HORSEHEAD));
        fieldSoul.addEnemy(EnemyFactory.createEnemy(EnemyType.FALLEN_ANGEL));
        fieldSoul.addEnemy(EnemyFactory.createEnemy(EnemyType.WANDERER));
        fieldSoul.setBoss(EnemyFactory.createBoss(EnemyType.BOSS_SOUL_COLLECTOR));
        locations.add(fieldSoul);

        Rooms rustyDrain = new Rooms("Ржавый водосток", "Гнилые подвальные катакомбы, наполненные бесконечно тянущимися ржавыми трубами и плесенью");
        rustyDrain.addEnemy(EnemyFactory.createEnemy(EnemyType.ROTTING_PLUMBER));
        rustyDrain.addEnemy(EnemyFactory.createEnemy(EnemyType.DIVER));
        rustyDrain.addEnemy(EnemyFactory.createEnemy(EnemyType.RATMAN));
        rustyDrain.setBoss(EnemyFactory.createBoss(EnemyType.BOSS_PIRANHA_HANDED_URKA));
        locations.add(rustyDrain);

}
    public Rooms getRoom(int id) { return locations.get(id); } // доступ к локации и ее врагам
    public int getSize(){ return locations.size(); } // доступ к размеру
}
