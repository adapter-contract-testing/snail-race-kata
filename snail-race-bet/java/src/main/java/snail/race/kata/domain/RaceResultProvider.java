package snail.race.kata.domain;

import java.util.ArrayList;
import java.util.List;

public interface RaceResultProvider {

    SnailRaces races();

    public record Snail(int number, String name) {}

    public record Podium(Snail first,
                         Snail second,
                         Snail third)
    {
    }

    public record SnailRace(int raceId,
                            long timestamp,
                            Podium podium)
    {
    }

    public record SnailRaces(List<SnailRace> races) {
        public static SnailRaces withAdditionalResult(SnailRaces snailRaces, int raceId, long datetime, Podium podium) {
            var races = new ArrayList<>(snailRaces.races());
            races.add(new SnailRace(raceId, datetime, podium));
            return new SnailRaces(races);
        }

        public SnailRace getLastRace() {
            // TODO make this actually get the last
            if (races.isEmpty())
                return null;
            return races.stream().min((a, b) -> Math.toIntExact(a.timestamp - b.timestamp)).get();
        }
    }
}
