package snail.race.kata.domain;


import java.util.ArrayList;

public class RaceResultProviderSimulator implements RaceResultProvider {
    private SnailRaces snailRaces;
    private int nextId = 7894351;

    public RaceResultProviderSimulator() {
        this.snailRaces = new SnailRaces(new ArrayList<>());
    }

    @Override
    public SnailRaces races() {
        return snailRaces;
    }

    public void registerRaceResult(long datetime, Podium podium) {
        this.snailRaces = SnailRaces.withAdditionalResult(this.snailRaces, nextId++, datetime, podium);
    }
}

