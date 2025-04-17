from copy import deepcopy

import pytest
from pymongo import MongoClient

from snail_race_kata.adapters.bet_repository_mongodb import BetRepositoryMongoDb
from snail_race_kata.domain.bet import Bet
from snail_race_kata.domain.podium_pronostic import PodiumPronostic


class TestBetRepositoryMongoDb():
    @pytest.fixture(autouse=True)
    def setup_and_teardown(self):
        self.client = MongoClient("mongodb://localhost:27017")
        self.database = self.client["test_snail_race"]
        self.collection = self.database["bets"]
        self.collection.delete_many({})  # Nettoyage avant chaque test
        self.repository = BetRepositoryMongoDb(self.database)
        yield
        self.client.close()

    def test_register_a_bet(self):
        self.repository.register(Bet("Mathieu", PodiumPronostic(20, 12, 4), 12345))

        bets = self.repository.find_by_date_range(12345, 12346)
        assert len(bets) == 1
        assert bets[0] == Bet("Mathieu", PodiumPronostic(20, 12, 4), 12345)

    def test_retrieve_only_bets_inside_the_time_range(self):
        from_timestamp = 12346
        to_timestamp = 12370

        _bet_before_from = self.register_bet_at_timestamp(
            self.repository, from_timestamp - 1
        )
        bet_on_from = self.register_bet_at_timestamp(self.repository, from_timestamp)
        bet_after_from = self.register_bet_at_timestamp(self.repository, from_timestamp + 1)
        bet_before_to = self.register_bet_at_timestamp(self.repository, to_timestamp - 1)
        _bet_on_to = self.register_bet_at_timestamp(self.repository, to_timestamp)
        _bet_after_to = self.register_bet_at_timestamp(self.repository, to_timestamp + 1)

        bets = self.repository.find_by_date_range(from_timestamp, to_timestamp)

        assert bets == [bet_on_from, bet_after_from, bet_before_to]


    def register_bet_at_timestamp(self, repository, timestamp):
        bet = Bet("Mathieu", PodiumPronostic(20, 12, 4), timestamp)
        repository.register(bet)
        return deepcopy(bet)