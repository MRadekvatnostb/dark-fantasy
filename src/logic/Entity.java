package logic;

import static utils.EffectType.*;
import engine.StatusEffect;
import go.Main;
import utils.EffectType;
import utils.GameUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
public abstract class Entity {
    protected List<StatusEffect> activeEffects = new ArrayList<>(); // список эффектов
    protected int dodge;
    protected String name;
    protected int hp = 100;
    protected int minDamage;
    protected int maxDamage;
    protected int armor = 0;
    protected int critChance = 0;
    protected int vampirism = 0;
    protected int maxHp = 200;

    private String currentLocation = "Шепчущий лес"; // начальная точка
    private int experience = 0;
    private int money = 0;
    private int stun = 0;
    private int totalDmg;


    public Entity(String name, int hp, int minDamage, int maxDamage, int armor, int critChance, int vampirism, int dodge, int maxHp) { // чертеж для сущностей
        this.name = name;
        this.hp = hp;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.armor = armor;
        this.critChance = critChance;
        this.vampirism = vampirism;
        this.dodge = dodge;
        this.maxHp = maxHp;
    }
    public abstract String getAttackPhrase();
    public String getEffectsList() { // делимся эффектом с классами
        if (activeEffects.isEmpty()) return "Нет";

        StringBuilder sb = new StringBuilder(); // не знаю что это
        for (StatusEffect e : activeEffects) { // создаем переменную статуса эффекта
            sb.append("[").append(e.getType()).append(":").append(e.getDuration()).append("х] "); // команды не знаю, но добавляет эффектам рамочку
        }
        return sb.toString();
    }
    public void addEffect(EffectType type, int amount, int duration) { // добавление эффекта
        this.activeEffects.add(new StatusEffect(type, amount, duration));
        System.out.println(">>> " + name + " получил эффект " + type + " (+" + amount + ") на " + duration + " х.");
    }
    public void updateEffects() { // обновление эффекта
        Iterator<StatusEffect> it = activeEffects.iterator(); // объявляем итератор для списка эффектов
        while (it.hasNext()) { // цикл просмотра следующего элемента
            StatusEffect e = it.next(); // взять предмет из списка

            // Используем switch по Enum вместо громоздкого if с .equals()
            switch (e.getType()) {
                case POISON, BLEED -> { // для яда и кровотечения
                    int periodicDmg = Math.abs(e.getAmount()); // только положительные числа берем
                    this.hp -= periodicDmg; // хп минус эти эффекты

                    if (this.hp < 0) this.hp = 0; // проверка не ушло ли число в -

                    System.out.println("(!) " + name + " страдает от " + e.getType() + ": -" + periodicDmg + " HP");
                }
                // Если будут другие эффекты (например, DMG), они просто пройдут мимо этой логики урона
                default -> {}
            }

            e.tick(); // длительность эффекта уменьшается на ход
            if (e.isExpired()) { // если эффект закончился
                System.out.println(">>> Эффект " + e.getType() + " у " + name + " закончился.");
                it.remove(); // удаляем эффект с помощью итератора
            }
        }
    }
    private int getBonusFor(EffectType type) { // получение бонуса
        int sum = 0;
        for (StatusEffect e : activeEffects) { // если эффект есть, то он дает свой эффект
            if (e.getType() == type) sum += e.getAmount(); // тип эффекта получает сумму и прибавляет длину
        }
        return sum;
    }
    public int calculateRandomDamage() { // расчеты урона
        int min = getMinDamage();
        int max = getMaxDamage();

        int spread = max - min; // узнаем разницу
        if (spread < 0) spread = 0; // на случай если дебаффы ушли в -

        return Main.rand.nextInt(spread + 1) + min;  // разница + минимальный урон = урон
    }
    public boolean isAlive(){ // проверка на жизнь
        return this.hp > 0;
    }
    public int attackCalculation(int dmg) {
        int finalDmg = dmg - (dmg * getArmor() / 100); // финальный урон = урон - броня
        if (finalDmg < 0) finalDmg = 2; // броня все поглотила? тогда урон 2
        return finalDmg;
    }
    public void checkDead() {
        if (this.hp < 0) this.hp = 0;
    }
    public void checkDoubleAttack(Entity target) {
        if (getDoubleAttack() > 0 && Main.rand.nextInt(100) < getDoubleAttack()) {
            System.out.println("!!! ДВОЙНОЙ УДАР !!!");
            totalDmg = performHit(target); // Бьем еще раз и плюсуем урон для вампиризма
        }
    }
    public boolean checkDodge(Entity target) {
        if (Main.rand.nextInt(100) < target.getDodge()) {
            System.out.println(target.getName() + " проявил ловкость и уклонился!");
            return true; // Уклонился!
        }
        return false; // Не уклонился
    }
    public void checkStun(Entity target) {
        if (getStun() > 0 && Main.rand.nextInt(100) < getStun()) {
            target.addEffect(EffectType.STUN, 0, 1);
            System.out.println(">>> " + target.getName() + " ОГЛУШЕН!");
        }
    }
    public void checkVamp(Entity target) {
        if (getVampirism() > 0) {
            int heal = (totalDmg * getVampirism()) / 100;
            this.setHp(this.getHp() + heal);
            System.out.println(this.name + " восстановил " + heal + " HP вампиризмом!");
        }
    }
    public void takeDamage(int dmg) { // получение урона

        this.hp -= attackCalculation(dmg); // хп - фин урон
        if (this.hp <= 0 && getResurrection() > 0) {
            if (Main.rand.nextInt(100) < getResurrection()) {
                this.hp = getMaxHp() / 3; // Встаем с 33% ХП
                System.out.println("!!! ВОСКРЕШЕНИЕ !!! " + name + " обманул смерть!");
                return;
            }
        }
        checkDead();
        System.out.println(this.name + " получил " + dmg + " урона. Осталось HP: " + this.hp);
    }
    private int performHit(Entity target) {
        int dmg = calculateRandomDamage(); // твой расчет (база + бонусы)
        if (Main.rand.nextInt(100) < getCritChance()) {
            dmg *= 2;
            System.out.println("КРИТИЧЕСКИЙ УРОН!");
        }
        target.takeDamage(dmg); // Наносим урон цели
        return dmg; // ВОЗВРАЩАЕМ число урона для вампиризма
    }
    public void attack(Entity target) { // атака
        GameUtils.slowPrint(getName() + this.getAttackPhrase(),20);
        if (checkDodge(target)) return;
        totalDmg = performHit(target); // Выносим удар в метод, чтобы вызвать его дважды
        checkDoubleAttack(target);// ПРОВЕРКА НА ДВОЙНУЮ АТАКУ
        checkStun(target);// ПРОВЕРКА НА СТАН (Оглушение)
        checkVamp(target);
        GameUtils.drawLine(20,10);
        this.hp = Math.min(this.hp, this.maxHp);
    }
    public String getCurrentLocation() {return currentLocation;}
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    public int getExperience() {return experience;}
    public void setExperience(int experience) { this.experience = experience; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHp() { return this.hp; }
    public int getDodge() { return dodge + getBonusFor(EffectType.DODGE); } // возвращаем значение + эффект если есть
    public int getMinDamage() { return minDamage + getBonusFor(EffectType.DMG); }
    public int getMaxDamage() { return maxDamage + getBonusFor(EffectType.DMG); }
    public int getArmor() { return armor + getBonusFor(EffectType.ARMOR); }
    public int getCritChance() { return critChance + getBonusFor(EffectType.CRIT); }
    public int getVampirism() { return vampirism + getBonusFor(EffectType.VAMP); }
    public void setHp(int hp) { this.hp = hp; }
    public int getResurrection() { return 0 + getBonusFor(EffectType.RES); }
    public int getDoubleAttack() { return 0 + getBonusFor(EffectType.DUBATK); }
    public int getStun() { return stun + getBonusFor(EffectType.STUN); }
    public void setMoney(int money) { this.money = money; }
    public int getMoney() { return money + getBonusFor(EffectType.MONEY); }
    public int getMaxHp() { return maxHp; }
    public void setMinDamage(int minDamage) { this.minDamage = minDamage; }
    public void setMaxDamage(int maxDamage) { this.maxDamage = maxDamage; }
    public void setDodge(int dodge) { this.dodge = dodge; }
    public void setArmor(int armor) { this.armor = armor; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    public void setCritChance(int critChance) { this.critChance = critChance; }
    public void setVampirism(int vampirism) { this.vampirism = vampirism; }
}
