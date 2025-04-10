package snail.race.kata.domain;

import java.util.ArrayList;
import java.util.List;

public class BetRepositorySimulator implements BetRepository {
    private final List<Bet> bets = new ArrayList<>();

    @Override
    public void register(Bet bet) {
        bets.add(bet);
    }

    @Override
    public List<Bet> findByDateRange(long from, long to) {
        return bets.stream()
                .filter(bet -> bet.timestamp() >= from && bet.timestamp() < to)
                .map(bet -> new Bet(
                        bet.gambler(),
                        new PodiumPronostic(bet.pronostic().first(), bet.pronostic().second(), bet.pronostic().third()),
                        bet.timestamp()
                ))
                .toList();
    }
}
