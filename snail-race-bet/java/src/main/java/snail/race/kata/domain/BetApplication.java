package snail.race.kata.domain;

import java.util.List;
import java.util.stream.Stream;

public class BetApplication {
    private final BetRepository betRepository;
    private final RaceResultProvider raceResultProvider;

    BetApplication(
            BetRepository betRepository,
            RaceResultProvider raceResultProvider) {
        this.betRepository = betRepository;
        this.raceResultProvider = raceResultProvider;
    }

    void placeBet(
             String gambler,
             long timestamp,
             int first,
             int second,
             int third) {
        this.betRepository.register(new Bet(gambler, new PodiumPronostic( first, second, third), timestamp));

    }

    List<Winner> getWinnersForLastRace() {
        // Sort races by date and get the last one
        var races = raceResultProvider.races();
        var sortedRaces = races.races()
                .stream()
                .sorted((a, b) -> Long.compare(b.timestamp(), a.timestamp()))
                .toList();

        if (sortedRaces.isEmpty()) {
            return List.of();
        }

        var previousRaceTimestamp = 0;
        if( sortedRaces.size() > 1) {
            previousRaceTimestamp = Math.toIntExact(sortedRaces.get(1).timestamp());
        }

        var lastRace = sortedRaces.get(0);
        int lastRaceTimestamp = Math.toIntExact(lastRace.timestamp());
        var bets = betRepository.findByDateRange(previousRaceTimestamp, lastRaceTimestamp);

        var winningBets = findExactMatchBets(bets, lastRace);

        return winningBets.map(bet -> new Winner(bet.gambler())).toList()   ;

    }

    private Stream<Bet> findExactMatchBets(List<Bet> bets, RaceResultProvider.SnailRace race) {
        return bets.stream()
                .filter(bet -> bet.isInTimeFor(race))
                .filter(bet -> bet.betIsOn(race.podium()));
    }

}
