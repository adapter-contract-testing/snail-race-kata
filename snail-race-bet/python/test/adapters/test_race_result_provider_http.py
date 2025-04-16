from src.adapters.race_result_provider_http import RaceResultProviderHttp
from test.domain.race_result_provider_contract import RaceResultProviderContract


class TestRaceResultProviderHttp(RaceResultProviderContract):

    def create_race_result_provider(self):
        provider = RaceResultProviderHttp()
        return provider

