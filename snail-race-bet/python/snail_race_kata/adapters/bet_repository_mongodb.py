from typing import List

from pymongo import MongoClient
from pymongo.collection import Collection

from snail_race_kata.domain.bet import Bet
from snail_race_kata.domain.podium_pronostic import PodiumPronostic


class BetRepositoryMongoDb:
    def __init__(self, database: MongoClient):
        self.database = database

    def register(self, bet: Bet) -> None:
        collection: Collection = self._get_collection()
        collection.insert_one(convert_bet_to_document(bet))

    def find_by_date_range(self, from_timestamp: int, to_timestamp: int) -> List["Bet"]:
        query = {
            "$and": [
                {"timestamp": {"$gte": from_timestamp}},
                {"timestamp": {"$lt": to_timestamp}},
            ]
        }
        collection: Collection = self._get_collection()
        results = collection.find(query)
        return list(map(convert_document_to_bet, results))

    def _get_collection(self):
        return self.database.get_collection("bets")


def convert_document_to_bet(document) -> Bet:
    return Bet(
        document["gambler"],
        PodiumPronostic(
            document["pronostic"]["first"],
            document["pronostic"]["second"],
            document["pronostic"]["third"],
        ),
        document["timestamp"],
    )


def convert_bet_to_document(bet):
    return {
        "gambler": bet.gambler,
        "pronostic": {
            "first": bet.pronostic.first,
            "second": bet.pronostic.second,
            "third": bet.pronostic.third,
        },
        "timestamp": bet.timestamp,
    }
