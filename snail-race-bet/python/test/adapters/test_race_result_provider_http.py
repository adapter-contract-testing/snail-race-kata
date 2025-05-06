from snail_race_kata.adapters.race_result_provider_http import RaceResultProviderHttp
from snail_race_kata.adapters.race_result_provider_http_internal_api import RaceResultProviderHttpInternalApi


class TestRaceResultProviderHttp:
    def test_provide_race_results(self):
        provider = RaceResultProviderHttp()

        races = provider.races()

        assert races is not None
        assert races.races.__len__() > 0

    def test_provide_something(self):
        provider = RaceResultProviderHttp()

        results = provider.invoke_result_end_point()

        assert isinstance(results, RaceResultProviderHttpInternalApi.RacesResponse)
        assert len(results.races) > 0