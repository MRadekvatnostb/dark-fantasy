package heroes;


import go.Main;
import logic.Entity;
import logic.Player;
import utils.EffectType;
import utils.GameUtils;

public class Archer extends Player {
    private final int chanceUltCrit = 65;
    public Archer() {
        super("Лучник", 100, 19, 35, 13, 30, 10,35,200);
    }
    @Override
    public String getAttackPhrase() {
        return " пускает свистящую стрелу!";
    }
    @Override // полиморфизм, тут спец атака лучника, играя за него будет она, а если играешь за рыцаря то другая, но используют один метод
    public void superAttack(Entity target) { // спец атака
        GameUtils.slowPrint("супер-атака - <СНАЙПЕР> - ярость кипит в жилах, натягивает тетиву до треска целясь в голову пробивая сквозную дыру!!! ",30);

        this.addEffect(EffectType.DMG,11,3);
        int supAtt = calculateRandomDamage() * 2;
        target.addEffect(EffectType.BLEED,7,2);
        GameUtils.slowPrint("Нанесено: " + supAtt, 0);
        target.takeDamage(supAtt);
    }
    @Override // полиморфизм, тут ульта лучника, играя за него будет она, а если играешь за рыцаря то другая, но используют один метод
    public void useUltimate(Entity target) {
        GameUtils.slowPrint("Ульта - <ИНКВИЗИТОР> - двойная атака и невероятный шанс крита, здоровье + 20",30);
        int totalUltimateDmg = 0;
        for (int i = 1; i < 3; i++) {
            int shotDmg = calculateRandomDamage(); // базовый урон стрелы
            this.addEffect(EffectType.DMG,10,3); // добавляем эффект себе
            this.addEffect(EffectType.DODGE,16,3);
            if (Main.rand.nextInt(100) < chanceUltCrit) {
                shotDmg *= 2;
                System.out.println("Выстрел #" + i + ": КРИТ! (" + shotDmg + ")");
            } else {
                System.out.println("Выстрел #" + i + ": Попал! (" + shotDmg + ")");
            }
            target.addEffect(EffectType.BLEED,5,2); // добавляем эффект врагу
            totalUltimateDmg += shotDmg;
        }
        GameUtils.slowPrint("Град стрел накрыл врага на: " + totalUltimateDmg, 30);
        target.takeDamage(totalUltimateDmg); // возвращаем то что получилось
        this.hp = Math.min(this.hp + 20, this.maxHp);
        this.energy = 0; // сбрасываем энергию

    }
}
