from dataclasses import dataclass
from typing import List, Optional
import requests

from src.adapters.race_result_provider_http_internal_api import RaceResultProviderHttpInternalApi


class RaceResultProviderHttp:
    def __init__(self, base_url: str = "http://localhost:8000"):
        self.base_url = base_url

    def invoke_result_end_point(self) -> RaceResultProviderHttpInternalApi.RacesResponse:
        """Récupère les résultats de courses depuis l'API REST."""
        response = requests.get(f"{self.base_url}/results")
        data = response.json()

        races = []
        for race_data in data.get("races", []):
            snails = [
                RaceResultProviderHttpInternalApi.Snail(
                    duration=snail_data["duration"],
                    name=snail_data["name"],
                    number=snail_data["number"]
                )
                for snail_data in race_data.get("snails", [])
            ]
            race = RaceResultProviderHttpInternalApi.Race(
                raceId=race_data["raceId"],
                snails=snails,
                timestamp=race_data["timestamp"]
            )
            races.append(race)

        return RaceResultProviderHttpInternalApi.RacesResponse(races=races)