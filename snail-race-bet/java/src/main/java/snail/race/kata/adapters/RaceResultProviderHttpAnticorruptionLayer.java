package snail.race.kata.adapters;

import snail.race.kata.domain.RaceResultProvider;

import java.util.Comparator;
import java.util.List;

public class RaceResultProviderHttpAnticorruptionLayer {

    public static RaceResultProvider.SnailRaces mapToDomain(RaceResultProviderHttpRecords.RaceData apiSnailRaces) {
        List<RaceResultProvider.SnailRace> races = apiSnailRaces.races().stream()
                .map(apiRace -> {
                    List<RaceResultProviderHttpRecords.Snail> orderedSnails = apiRace
                            .snails()
                            .stream()
                            .sorted(Comparator.comparingDouble(RaceResultProviderHttpRecords.Snail::duration))
                            .toList();

                    return new RaceResultProvider.SnailRace(
                            Math.toIntExact(apiRace.raceId()),
                            apiRace.timestamp(),
                            new RaceResultProvider.Podium(
                                    mapSnail(orderedSnails.get(0)),
                                    mapSnail(orderedSnails.get(1)),
                                    mapSnail(orderedSnails.get(2))
                            )
                    );
                })
                .toList();

        return new RaceResultProvider.SnailRaces(races);
    }

    private static RaceResultProvider.Snail mapSnail(RaceResultProviderHttpRecords.Snail apiSnail) {
        return new RaceResultProvider.Snail(apiSnail.number(), apiSnail.name());
    }
}
