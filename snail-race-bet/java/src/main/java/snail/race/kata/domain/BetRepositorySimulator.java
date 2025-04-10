package snail.race.kata.domain;

import java.util.ArrayList;
import java.util.List;

public class BetRepositorySimulator implements BetRepository {
    private final List<Bet> inMemoryBets = new ArrayList<>();

    @Override
    public void register(Bet bet) {
        inMemoryBets.add(bet);
    }

    @Override
    public List<Bet> findByDateRange(int from, int to) {
        return inMemoryBets.stream()
                .filter(bet -> bet.timestamp() >= from && bet.timestamp() < to)
                .map(b -> new Bet(
                        b.gambler(),
                        new PodiumPronostic(
                                b.pronostic().first(),
                                b.pronostic().second(),
                                b.pronostic().third()),
                        b.timestamp()
                ))
                .toList();
    }
}
