package heroes;

import logic.Entity;
import logic.Player;
import utils.EffectType;
import utils.GameUtils;

public class Knight extends Player {
    public Knight() {
        super("Рыцарь", 150, 17, 28,45, 10, 0,8,200);
    }
    @Override
    public String getAttackPhrase() {
        return " с размаху рубит мечом!";
    }
    @Override
    public void superAttack(Entity target) {
        GameUtils.slowPrint("супер-атака - <ПАЛЛАДИН> - отбрасывает щит и достает из-за спины массивный, длинный двуручный меч, медленно замахиваясь он наносит сокрушительный удар!!! ",30);
        this.addEffect(EffectType.VAMP,30,3);
        this.addEffect(EffectType.DMG,15,3);
        this.addEffect(EffectType.DODGE,16,3);
        this.addEffect(EffectType.ARMOR,-5,3);
        int supAtt = calculateRandomDamage() + 20;
        target.addEffect(EffectType.BLEED,5,2);
        GameUtils.slowPrint("Нанесено: " + supAtt, 0);
        target.takeDamage(supAtt);
    }
    @Override
    public void useUltimate(Entity target) {
        GameUtils.slowPrint("<ВОЕВОДА> Гнев предков! Урон x3!",30);
        int baseRoll = calculateRandomDamage();

        int massiveDmg = baseRoll * 3;

        GameUtils.slowPrint("Гнев предков усиливает твой удар! База: " + baseRoll + " -> Итого: " + massiveDmg, 20);
        this.addEffect(EffectType.DODGE, 50, 3); // даем +50 к доджу на 3 хода
        this.addEffect(EffectType.ARMOR, 5, 3); // даем +20 к броне на 3 хода
        target.takeDamage(massiveDmg);
        this.energy = 0;
    }
}
