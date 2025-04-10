package snail.race.kata.domain;

import java.util.ArrayList;
import java.util.List;

public class BetRepositorySimulator implements BetRepository {
    private List<Bet> inMemoryBets = new ArrayList<>();

    @Override
    public void register(Bet bet) {
       inMemoryBets.add(bet);
    }

    @Override
    public List<Bet> findByDateRange(long from, long to) {
        return inMemoryBets.stream().filter(bet -> bet.timestamp() > from && bet.timestamp() < to).toList();
    }
}
