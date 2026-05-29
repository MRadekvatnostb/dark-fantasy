package heroes;

import logic.Entity;
import logic.Player;
import utils.EffectType;
import utils.GameUtils;

public class Vampire extends Player {
    public Vampire() {
        super("Вампир", 120, 16, 25, 25, 18, 50,15,200);
    }
    @Override
    public String getAttackPhrase() {
        return " делает укус!";
    }
    @Override
    public void superAttack(Entity target) {
        GameUtils.slowPrint("супер-атака - <ДОМИНИРОВАНИЕ> - взгляд становиться суровым, воздух тяжелеет, пространство давит, страх овладел врагом!!! ",30);
        this.addEffect(EffectType.DMG,10,2);
        this.addEffect(EffectType.VAMP,10,2);
        this.addEffect(EffectType.DODGE,20,2);
        int supAtt = calculateRandomDamage();
        target.addEffect(EffectType.DMG,-9,3);
        target.addEffect(EffectType.BLEED,10,3);
        GameUtils.slowPrint("Нанесено: " + supAtt, 0);
        target.takeDamage(supAtt);
    }

    @Override
    public void useUltimate(Entity target) {
        GameUtils.slowPrint("<ВАССАЛ> Великая жатва!",30);
        int baseRoll = calculateRandomDamage();
        baseRoll += 30;
        this.hp = Math.min(this.hp * 2, this.maxHp);
        this.addEffect(EffectType.VAMP, 30, 3);
        this.addEffect(EffectType.DODGE, 30, 3);
        GameUtils.slowPrint("Вы чувствуете прилив сил! Итого:  " + baseRoll + ", ваше здоровье " + this.hp + ", ваш вампиризм" + getVampirism(), 20);
        target.takeDamage(baseRoll);
        this.energy = 0;
    }
}
