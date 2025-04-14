from pymongo import MongoClient
import requests
from dataclasses import dataclass
from typing import List
import json

def test_mongo_database_is_reachable():
    mongo_client = MongoClient("mongodb://localhost:27017")
    database_names = mongo_client.list_database_names()
    assert len(database_names) > 0  # Vérifie qu'il y a au moins une base de données
    mongo_client.close()


def test_race_result_server_is_accessible():
    # given
    url = "http://localhost:8000/results"

    # when
    response = requests.get(url)

    # then check network response
    assert response.status_code == 200
    assert "json" in response.headers["Content-Type"]

    # then check JSON deserialization
    snail_races_results = ServerResult(**json.loads(response.text))
    assert len(snail_races_results.races) > 0

@dataclass
class ServerResult:
    races: List[any]