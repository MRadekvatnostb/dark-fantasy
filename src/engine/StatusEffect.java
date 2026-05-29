package engine;

import utils.EffectType;

public class StatusEffect {
    private String type;
    private int amount;
    private int duration;

    public StatusEffect(EffectType type, int amount, int duration) {
        this.type = String.valueOf(type); // тип эффекта
        this.amount = amount; // длительность эффекта
        this.duration = duration; // отнимание длительность у эффекта
    }
    public int getDuration() { // делимся этим вычитанием
        return duration;
    }

    public void tick() { duration--; } // вычитаем 1 ход
    public boolean isExpired() { return duration <= 0; } // снимаем эффект если ходов не осталось
    public String getType() { return type; } // делимся типом
    public int getAmount() { return amount; } // делимся длительностью
}