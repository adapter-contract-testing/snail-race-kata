from typing import List

from pymongo import MongoClient
from pymongo.collection import Collection

from src.domain.bet import Bet
from src.domain.podium_pronostic import PodiumPronostic


class BetRepositoryMongoDb:
    def __init__(self, database: MongoClient):
        self.database = database

    def register(self, bet: Bet) -> None:
        collection: Collection = self.database.get_collection("bet")
        collection.insert_one({ "gambler": bet.gambler,
                                "pronostic": { "first" : bet.pronostic.first,
                                            "second": bet.pronostic.second,
                                            "third": bet.pronostic.third },
                                "timestamp": bet.timestamp })

    def find_by_date_range(self, from_timestamp: int, to_timestamp: int) -> List['Bet']:
        query = {
            "$and": [
                {"timestamp": {"$gte": from_timestamp}},
                {"timestamp": {"$lt": to_timestamp}}
            ]
        }
        collection: Collection = self.database.get_collection("bet")
        results = collection.find(query)
        return list(map(create_bet, results))

def create_bet(document) -> Bet :
    return Bet(
        document['gambler'],
        PodiumPronostic(
            document['pronostic']['first'],
            document['pronostic']['second'],
            document['pronostic']['third']
        ),
        document['timestamp']
    )