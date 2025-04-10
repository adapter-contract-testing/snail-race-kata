package snail.race.kata.domain;


import java.util.ArrayList;

public class RaceResultProviderSimulator implements RaceResultProvider {
    private SnailRaces snailRaces;

    public RaceResultProviderSimulator() {
        this.snailRaces = new SnailRaces(new ArrayList<>());
    }

    public RaceResultProviderSimulator(SnailRaces snailRaces) {
        this.snailRaces = snailRaces;
    }

    @Override
    public SnailRaces races() {
        return snailRaces;
    }

    public void simulateRaceResult(int raceId, long datetime, Podium podium) {
        this.snailRaces = SnailRaces.withAdditionalResult(this.snailRaces, raceId, datetime, podium);
    }
}

