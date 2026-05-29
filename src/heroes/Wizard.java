package heroes;

import logic.Entity;
import logic.Player;
import utils.EffectType;
import utils.GameUtils;

public class Wizard extends Player {
    public Wizard() {
        super("Маг", 100, 12,40 , 20, 20, 18,15,200);
    }
    @Override
    public String getAttackPhrase() {
        return " замахивается магическим посохом!";
    }
    @Override
    public void superAttack(Entity target) {
        GameUtils.slowPrint("супер-атака - <АНТИМАТЕРИЯ> - вы читаете долгое заклинание, вокруг собираются частички света что после взрыва стали непроглядной тьмой!!! ",30);
        this.addEffect(EffectType.ARMOR,5,2);
        this.addEffect(EffectType.CRIT,100,2);
        this.addEffect(EffectType.DMG,30,2);
        this.addEffect(EffectType.DODGE,-5,2);
        int supAtt = calculateRandomDamage();
        target.addEffect(EffectType.DMG,-5,3);
        target.addEffect(EffectType.POISON,10,2);
        GameUtils.slowPrint("Нанесено: " + supAtt, 0);
        target.takeDamage(supAtt);
    }

    @Override
    public void useUltimate(Entity target) {
        GameUtils.slowPrint("<КНЯЗЬ ТЬМЫ> Дезинтеграция материи!",30);
        int baseRoll = calculateRandomDamage();
        int dmg = baseRoll + 15 * 2 + (target.getHp() / 5);
        GameUtils.slowPrint("Вы читаете запретное темное заклинание! База: " + baseRoll + " -> Итого: " + dmg, 20);
        target.takeDamage(dmg);
        this.addEffect(EffectType.DODGE, 25, 3);
        this.energy = 0;
    }
}