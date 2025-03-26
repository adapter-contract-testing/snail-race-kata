package snail.race.kata.adapters;

import java.util.List;

public class RaceResultProviderHttpRecords {
    public record RaceData(List<Race> races) {
    }

    public record Race(long raceId, long timestamp, List<Snail> snails) {
    }

    public record Snail(int number, String name, double duration) {
    }
}
