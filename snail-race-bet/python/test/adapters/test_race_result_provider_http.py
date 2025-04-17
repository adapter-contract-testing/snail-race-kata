from snail_race_kata.adapters.race_result_provider_http import RaceResultProviderHttp
from snail_race_kata.adapters.race_result_provider_http_internal_api import (
    RaceResultProviderHttpInternalApi,
)
from test.domain.race_result_provider_contract import RaceResultProviderContract


class TestRaceResultProviderHttp(RaceResultProviderContract):
    def test_provide_something(self):
        provider = self.create_race_result_provider()
        results = provider.invoke_result_end_point()

        assert isinstance(results, RaceResultProviderHttpInternalApi.RacesResponse)
        assert len(results.races) > 0

    def create_race_result_provider(self):
        provider = RaceResultProviderHttp()
        return provider
