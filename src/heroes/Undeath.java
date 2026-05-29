package heroes;

import logic.Entity;
import logic.Player;
import utils.EffectType;
import utils.GameUtils;

public class Undeath extends Player {
    public Undeath() {
        super("Нежить", 130, 15, 28, 30, 15, 15,7,200);
    }
    @Override
    public String getAttackPhrase() {
        return " бьет своими кулаками!";
    }
    @Override
    public void superAttack(Entity target) {
        GameUtils.slowPrint("супер-атака - <ГНИЕНИЕ> - глубокий хрип доносится из пасти, пальцы налились ядом и гнойным запахом, атака рубящими ударами!!! ",30);
        this.addEffect(EffectType.CRIT,100,2);
        this.addEffect(EffectType.DMG,16,2);
        this.addEffect(EffectType.POISON,-5,1);
        this.addEffect(EffectType.ARMOR,7,2);
        int baseRoll1 = calculateRandomDamage() / 2;
        int baseRoll2 = calculateRandomDamage() / 2;
        int baseRoll3 = calculateRandomDamage() / 2;
        int supAtt = baseRoll1 + baseRoll2 + baseRoll3;
        target.addEffect(EffectType.POISON,10,3);

        GameUtils.slowPrint("Нанесено: " + supAtt, 0);
        target.takeDamage(supAtt);
    }
    @Override
    public void useUltimate(Entity target) {
        GameUtils.slowPrint("<ЖЕРТВА> Кровавое безумие!",30);
        int baseRoll = calculateRandomDamage();
        int massiveDmg = baseRoll * 5;
        GameUtils.slowPrint("Вы вырываете свое сердце и съедаете! База: " + baseRoll + " * 5 -> Итого: " + massiveDmg, 20);
        target.takeDamage(massiveDmg);
        this.hp -= 35;
        if (this.hp <= 0) {
            this.hp = 0;
            System.out.println("Вы зашли слишком далеко и погибли от собственной жажды крови...");
        }
        this.addEffect(EffectType.DODGE, 30, 4);
        this.addEffect(EffectType.CRIT,15,3);
        this.energy = 0;
    }
}