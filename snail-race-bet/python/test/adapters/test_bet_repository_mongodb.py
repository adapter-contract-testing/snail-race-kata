import pytest
from pymongo import MongoClient

from snail_race_kata.adapters.bet_repository_mongodb import BetRepositoryMongoDb


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

    def test_register_a_bets(self):
        # register a bet
        # How can we verify the bet is actually inserted ?
        # "Rien n'est plus dangereux qu'une idée quand on en a qu'une" (Emile Chartier)
        # TIPS : Find some (>=3) options before commiting to one
        assert False, "TODO : write the test, then the implementation😉"

    def test_retrieve_only_bets_inside_the_time_range(self):
        assert False , "TODO : write the test, then the implementation😉"
