from snail_race_kata.adapters.race_result_provider_http_internal_api import (
    RaceResultProviderHttpInternalApi,
)
from snail_race_kata.domain.race_result_provider import SnailRaces, Snail, Podium


class RaceResultProviderHttpAntiCorruptionLayer:
    @staticmethod
    def map_to_domain(
        api_result: RaceResultProviderHttpInternalApi.RacesResponse,
    ) -> SnailRaces:
        result = SnailRaces()

        # Tri des courses par timestamp (de la plus récente à la plus ancienne)
        sorted_races = sorted(api_result.races, key=lambda r: r.timestamp, reverse=True)

        for race in sorted_races:
            # Trier les escargots par durée (du plus rapide au plus lent)
            sorted_snails = sorted(race.snails, key=lambda s: s.duration)

            # S'assurer qu'il y a au moins 3 escargots pour former un podium
            if len(sorted_snails) >= 3:
                first = sorted_snails[0]
                second = sorted_snails[1]
                third = sorted_snails[2]

                # Conversion en classes du domaine
                domain_first = Snail(number=first.number, name=first.name)
                domain_second = Snail(number=second.number, name=second.name)
                domain_third = Snail(number=third.number, name=third.name)

                podium = Podium(
                    first=domain_first, second=domain_second, third=domain_third
                )

                # Ajouter la course au résultat
                result.with_additional_result(race.raceId, race.timestamp, podium)

        return result
