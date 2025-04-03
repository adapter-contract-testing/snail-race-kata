package snail.race.kata.adapters;

import org.jetbrains.annotations.NotNull;
import snail.race.kata.domain.RaceResultProvider;

import java.util.Comparator;
import java.util.List;

public class RaceResultProviderHttpAnticorruptionLayer {

    public static RaceResultProvider.SnailRaces mapToDomain(RaceResultProviderHttpRecords.RaceData apiSnailRaces) {
        List<RaceResultProvider.SnailRace> races = apiSnailRaces.races().stream()
                .map(RaceResultProviderHttpAnticorruptionLayer::mapSingleRace)
                .toList();

        return new RaceResultProvider.SnailRaces(races);
    }

    private static RaceResultProvider.SnailRace mapSingleRace(RaceResultProviderHttpRecords.Race apiRace) {
        return new RaceResultProvider.SnailRace(
                Math.toIntExact(apiRace.raceId()),
                apiRace.timestamp(),
                createPodium(apiRace)
        );
    }

    private static RaceResultProvider.Podium createPodium(RaceResultProviderHttpRecords.Race apiRace) {
        List<RaceResultProviderHttpRecords.Snail> orderedSnails = apiRace
                .snails()
                .stream()
                .sorted(Comparator.comparingDouble(RaceResultProviderHttpRecords.Snail::duration))
                .toList();

        RaceResultProvider.Podium podium = new RaceResultProvider.Podium(
                mapSnail(orderedSnails.get(0)),
                mapSnail(orderedSnails.get(1)),
                mapSnail(orderedSnails.get(2))
        );
        return podium;
    }

    private static RaceResultProvider.Snail mapSnail(RaceResultProviderHttpRecords.Snail apiSnail) {
        return new RaceResultProvider.Snail(apiSnail.number(), apiSnail.name());
    }
}
