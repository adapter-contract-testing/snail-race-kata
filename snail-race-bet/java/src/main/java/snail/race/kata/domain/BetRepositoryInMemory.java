package snail.race.kata.domain;

import java.util.ArrayList;
import java.util.List;

public class BetRepositoryInMemory implements BetRepository {
    private List<Bet> bets = new ArrayList<>();

    @Override
    public void register(Bet bet) {
        bets.add(bet);
    }

    @Override
    public List<Bet> findByDateRange(long from, long to) {
        return bets.stream().filter(bet -> bet.timestamp() >= from && bet.timestamp() < to)
                .toList();
    }
}
