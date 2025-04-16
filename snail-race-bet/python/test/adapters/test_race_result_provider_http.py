import requests

from src.adapters.race_result_provider_http import RaceResultProviderHttp
from src.adapters.race_result_provider_http_internal_api import RaceResultProviderHttpInternalApi
from src.domain.race_result_provider import SnailRaces


class TestRaceResultProviderHttp:

    def test_provide_something(self):
        provider = RaceResultProviderHttp()
        results = provider.invoke_result_end_point()

        assert isinstance(results, RaceResultProviderHttpInternalApi.RacesResponse)
        assert len(results.races) > 0

    def test_provide_race_result(self):
        provider = RaceResultProviderHttp()

        races_result = provider.races()

        assert isinstance(races_result, SnailRaces)
        assert len(races_result.races) > 0

