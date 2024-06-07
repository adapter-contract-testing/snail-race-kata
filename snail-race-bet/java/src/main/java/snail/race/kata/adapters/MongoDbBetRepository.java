package snail.race.kata.adapters;

import com.mongodb.client.MongoDatabase;
import snail.race.kata.domain.Bet;
import snail.race.kata.domain.BetRepository;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.StreamSupport;

public class MongoDbBetRepository implements BetRepository {
    private final MongoDatabase database;

    public MongoDbBetRepository(MongoDatabase database) {

        this.database = database;
    }

    @Override
    public void register(Bet bet) {
        database.getCollection("bet", Bet.class).insertOne(bet);
    }

    @Override
    public List<Bet> findByDateRange(int from, int to) {
        Spliterator<Bet> bet = database.getCollection("bet", Bet.class).find().spliterator();
        return StreamSupport.stream(bet, false).filter(b -> b.timestamp() > from && b.timestamp() < to).toList();
    }
}
