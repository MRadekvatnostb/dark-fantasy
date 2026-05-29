package utils;

public class GameItem { // чертеж для создания предметов
    private final String name;
    private final int healBuff;
    private final int stanBuff;
    private final int dmgBuff;
    private final int dodgeBuff;
    private final int critChanceBuff;
    private final int critBuff;
    private final int ignoreDmgBuff;
    private final int resurrectionBuff;
    private final int doubleAttackBuff;
    private final int stillHpBuff;
    private final int moneyBuff;
    private final int armorBuff;
    private int duration;

    private GameItem(Builder builder) {
        this.name = builder.name;
        this.healBuff = builder.healBuff;
        this.stanBuff = builder.stunBuff;
        this.dmgBuff = builder.dmgBuff;
        this.dodgeBuff = builder.dodgeBuff;
        this.critBuff = builder.critBuff;
        this.critChanceBuff = builder.critChanceBuff;
        this.ignoreDmgBuff = builder.ignoreDmgBuff;
        this.resurrectionBuff = builder.resurrectionBuff;
        this.doubleAttackBuff = builder.doubleAttackBuff;
        this.stillHpBuff = builder.stillHpBuff;
        this.moneyBuff = builder.moneyBuff;
        this.armorBuff = builder.armorBuff;
        this.duration = builder.duration;
    }
    public static class Builder {
        private String name; // Обязательный параметр
        private int healBuff = 0; // По умолчанию 0
        private int dmgBuff = 0;
        private int critChanceBuff = 0;
        private int armorBuff = 0;
        private int dodgeBuff = 0;
        private int critBuff = 0;
        private int ignoreDmgBuff = 0;
        private int resurrectionBuff = 0;
        private int doubleAttackBuff = 0;
        private int stillHpBuff = 0;
        private int moneyBuff = 0;
        private int stunBuff = 0;
        private int duration = 0;

        public Builder(String name) {
            this.name = name;
        }
        public Builder setCritBuff(int val) {
            this.critBuff = val;
            return this; // Возвращаем сам билдер, чтобы строить цепочкой
        }

        public Builder setHealBuff(int val) {
            this.healBuff = val;
            return this;
        }
        public Builder setDuration(int val) {
            this.duration = val;
            return this;
        }
        public Builder setStillHpBuff(int val) {
            this.stillHpBuff = val;
            return this;
        }
        public Builder setMoneyBuff(int val) {
            this.moneyBuff = val;
            return this;
        }
        public Builder setStunBuff(int val) {
            this.stunBuff = val;
            return this;
        }
        public Builder setDodgeBuff(int val) {
            this.dodgeBuff = val;
            return this;
        }
        public Builder setIgnoreDmgBuff(int val) {
            this.ignoreDmgBuff = val;
            return this;
        }
        public Builder setResurrectionBuff(int val) {
            this.resurrectionBuff = val;
            return this;
        }
        public Builder setDoubleAttackBuff(int val) {
            this.doubleAttackBuff = val;
            return this;
        }

        public Builder setDmgBuff(int val) {
            this.dmgBuff = val;
            return this;
        }

        public Builder setCritChanceBuff(int val) {
            this.critChanceBuff = val;
            return this;
        }

        public Builder setArmorBuff(int val) {
            this.armorBuff = val;
            return this;
        }

        public GameItem build() {
            return new GameItem(this);
        }
    }
    public String getName() { return name; }
    public int getHealBuff() { return healBuff; }
    public int getStunBuff() {return stanBuff;}
    public int getDmgBuff() {return dmgBuff;}
    public int getDodgeBuff() {return dodgeBuff;}
    public int getCritBuff() {return critBuff;}
    public int getCritChanceBuff() {return critChanceBuff;}
    public int getIgnoreDmgBuff() {return ignoreDmgBuff;}
    public int getResurrectionBuff() {return resurrectionBuff;}
    public int getDoubleAttackBuff() {return doubleAttackBuff;}
    public int getStillHpBuff() {return stillHpBuff;}
    public int getMoneyBuff() {return moneyBuff;}
    public int getArmorBuff() {return armorBuff;}
    public int getDuration() {return duration;}
}
