package snail.race.kata.adapters;

import com.mongodb.client.MongoDatabase;
import snail.race.kata.domain.Bet;
import snail.race.kata.domain.BetRepository;

import java.util.List;

public class BetRepositoryMongoDb implements BetRepository {
    public BetRepositoryMongoDb(MongoDatabase database) {

    }

    @Override
    public void register(Bet bet) {
    }

    @Override
    public List<Bet> findByDateRange(long from, long to) {
        return null;
    }
}
