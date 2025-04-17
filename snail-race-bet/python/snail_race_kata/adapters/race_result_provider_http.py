import requests

from snail_race_kata.adapters.race_result_provider_http_anti_corruption_layer import (
    RaceResultProviderHttpAntiCorruptionLayer,
)
from snail_race_kata.adapters.race_result_provider_http_internal_api import (
    RaceResultProviderHttpInternalApi,
)
from snail_race_kata.domain.race_result_provider import SnailRaces, RaceResultProvider


class RaceResultProviderHttp(RaceResultProvider):
    def __init__(self, base_url: str = "http://localhost:8000"):
        self.base_url = base_url

    def races(self) -> SnailRaces:
        api_result = self.invoke_result_end_point()
        return RaceResultProviderHttpAntiCorruptionLayer.map_to_domain(api_result)
        pass

    def invoke_result_end_point(
        self,
    ) -> RaceResultProviderHttpInternalApi.RacesResponse:
        """Récupère les résultats de courses depuis l'API REST."""
        response = requests.get(f"{self.base_url}/results")
        data = response.json()

        races = []
        for race_data in data.get("races", []):
            snails = [
                RaceResultProviderHttpInternalApi.Snail(
                    duration=snail_data["duration"],
                    name=snail_data["name"],
                    number=snail_data["number"],
                )
                for snail_data in race_data.get("snails", [])
            ]
            race = RaceResultProviderHttpInternalApi.Race(
                raceId=race_data["raceId"],
                snails=snails,
                timestamp=race_data["timestamp"],
            )
            races.append(race)

        return RaceResultProviderHttpInternalApi.RacesResponse(races=races)
