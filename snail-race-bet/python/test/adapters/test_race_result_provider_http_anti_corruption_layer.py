from snail_race_kata.adapters.race_result_provider_http_anti_corruption_layer import (
    RaceResultProviderHttpAntiCorruptionLayer,
)
from snail_race_kata.adapters.race_result_provider_http_internal_api import (
    RaceResultProviderHttpInternalApi,
)
from snail_race_kata.domain.race_result_provider import SnailRaces, Podium


class TestRaceResultProviderHttpAntiCorruptionLayer:
    def test_map_to_domain_empty_races(self):
        # Arrange
        api_response = RaceResultProviderHttpInternalApi.RacesResponse(races=[])

        # Act
        result = RaceResultProviderHttpAntiCorruptionLayer.map_to_domain(api_response)

        # Assert
        assert isinstance(result, SnailRaces)
        assert len(result.races) == 0

    def test_map_to_domain_one_race(self):
        # Arrange
        api_snails = [
            RaceResultProviderHttpInternalApi.Snail(
                duration=10.5, name="Speedy", number=1
            ),
            RaceResultProviderHttpInternalApi.Snail(
                duration=12.3, name="Slow", number=2
            ),
            RaceResultProviderHttpInternalApi.Snail(
                duration=11.7, name="Average", number=3
            ),
        ]
        api_race = RaceResultProviderHttpInternalApi.Race(
            raceId=123, timestamp=1600000000000, snails=api_snails
        )
        api_response = RaceResultProviderHttpInternalApi.RacesResponse(races=[api_race])

        # Act
        result = RaceResultProviderHttpAntiCorruptionLayer.map_to_domain(api_response)

        # Assert
        assert isinstance(result, SnailRaces)
        assert len(result.races) == 1
        assert result.races[0].race_id == 123
        assert result.races[0].timestamp == 1600000000000

        # Vérifie le podium
        podium = result.races[0].podium
        assert isinstance(podium, Podium)
        assert podium.first.name == "Speedy"
        assert podium.first.number == 1
        assert podium.second.name == "Average"
        assert podium.second.number == 3
        assert podium.third.name == "Slow"
        assert podium.third.number == 2

    def test_map_to_domain_multiple_races(self):
        # Arrange
        race1 = RaceResultProviderHttpInternalApi.Race(
            raceId=123,
            timestamp=1600000000000,
            snails=[
                RaceResultProviderHttpInternalApi.Snail(
                    duration=10.5, name="Speedy", number=1
                ),
                RaceResultProviderHttpInternalApi.Snail(
                    duration=12.3, name="Slow", number=2
                ),
                RaceResultProviderHttpInternalApi.Snail(
                    duration=11.7, name="Average", number=3
                ),
            ],
        )
        race2 = RaceResultProviderHttpInternalApi.Race(
            raceId=456,
            timestamp=1600000100000,  # Plus récent que race1
            snails=[
                RaceResultProviderHttpInternalApi.Snail(
                    duration=9.8, name="Flash", number=4
                ),
                RaceResultProviderHttpInternalApi.Snail(
                    duration=10.2, name="Quick", number=5
                ),
                RaceResultProviderHttpInternalApi.Snail(
                    duration=10.9, name="Swift", number=6
                ),
            ],
        )
        api_response = RaceResultProviderHttpInternalApi.RacesResponse(
            races=[race1, race2]
        )

        # Act
        result = RaceResultProviderHttpAntiCorruptionLayer.map_to_domain(api_response)

        # Assert
        assert isinstance(result, SnailRaces)
        assert len(result.races) == 2

        # La course la plus récente devrait être la première
        assert result.races[0].race_id == 456
        assert result.races[1].race_id == 123

        # Vérifie que le podium est correctement ordonné pour la course la plus récente
        podium = result.races[0].podium
        assert podium.first.name == "Flash"
        assert podium.second.name == "Quick"
        assert podium.third.name == "Swift"

    def test_map_to_domain_race_with_less_than_three_snails(self):
        # Arrange
        race_with_two_snails = RaceResultProviderHttpInternalApi.Race(
            raceId=123,
            timestamp=1600000000000,
            snails=[
                RaceResultProviderHttpInternalApi.Snail(
                    duration=10.5, name="Speedy", number=1
                ),
                RaceResultProviderHttpInternalApi.Snail(
                    duration=12.3, name="Slow", number=2
                ),
            ],
        )
        api_response = RaceResultProviderHttpInternalApi.RacesResponse(
            races=[race_with_two_snails]
        )

        # Act
        result = RaceResultProviderHttpAntiCorruptionLayer.map_to_domain(api_response)

        # Assert
        assert isinstance(result, SnailRaces)
        assert (
            len(result.races) == 0
        )  # Cette course devrait être ignorée car pas assez d'escargots
