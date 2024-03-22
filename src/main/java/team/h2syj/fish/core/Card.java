package team.h2syj.fish.core;

import java.util.List;

public interface Card {

    String name();

    String desc();

    void execute(List<Biological> target);

    abstract class AbstractCard implements Card {
        @Override
        public String toString() {
            return String.format("%s：%s", name(), desc());
        }
    }

    abstract class BuffCard extends AbstractCard {
    }

    abstract class DeBuffCard extends AbstractCard {
    }

    abstract class AttackCard extends AbstractCard {
    }

    abstract class MagicCard extends AbstractCard {
    }

}
