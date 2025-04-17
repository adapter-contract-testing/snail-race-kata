from typing import List

from pymongo import MongoClient
from pymongo.collection import Collection

from snail_race_kata.domain.bet import Bet
from snail_race_kata.domain.podium_pronostic import PodiumPronostic


class BetRepositoryMongoDb:
    def __init__(self, database: MongoClient):
        self.database = database

    def register(self, bet: Bet) -> None:
        pass

    def find_by_date_range(self, from_timestamp: int, to_timestamp: int) -> List["Bet"]:
        pass

    def _get_collection(self):
        return self.database.get_collection("bets")
