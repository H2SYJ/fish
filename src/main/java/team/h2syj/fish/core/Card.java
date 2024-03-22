package team.h2syj.fish.core;

public abstract class Card {

    public abstract String name();

    public abstract String desc();

    public abstract void execute(Biological target);

    public interface BuffCard {
    }

    public interface DeBuffCard {
    }

    public interface AttackCard {
    }

    public interface MagicCard {
    }

    @Override
    public String toString() {
        return String.format("%s：%s", name(), desc());
    }
}
