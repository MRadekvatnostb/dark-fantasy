package engine;

import utils.EffectType;

public class StatusEffect {
    private EffectType type;
    private int amount;
    private int duration;

    public StatusEffect(EffectType type, int amount, int duration) {
        this.type = type; // тип эффекта
        this.amount = amount; // длительность эффекта
        this.duration = duration; // отнимание длительность у эффекта
    }
    public int getDuration() { // делимся этим вычитанием
        return duration;
    }

    public void tick() { duration--; } // вычитаем 1 ход
    public boolean isExpired() { return duration <= 0; } // снимаем эффект если ходов не осталось
    public EffectType getType() {return type;}
    public int getAmount() { return amount; } // делимся длительностью
}