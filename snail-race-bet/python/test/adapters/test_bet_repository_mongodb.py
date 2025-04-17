import pytest
from pymongo import MongoClient

from src.adapters.bet_repository_mongodb import BetRepositoryMongoDb
from test.domain.bet_repository_contract import BetRepositoryContract


class TestBetRepositoryMongoDb(BetRepositoryContract):
    @pytest.fixture(autouse=True)
    def setup_and_teardown(self):
        self.client = MongoClient("mongodb://localhost:27017")
        self.database = self.client["test_snail_race"]
        self.collection = self.database["bets"]
        self.collection.delete_many({})  # Nettoyage avant chaque test
        yield
        self.client.close()

    def get_repository(self):
        return BetRepositoryMongoDb(self.database)
