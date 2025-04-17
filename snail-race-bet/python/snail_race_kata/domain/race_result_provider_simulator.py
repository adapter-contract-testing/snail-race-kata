from snail_race_kata.domain.race_result_provider import SnailRaces, Podium


class RaceResultProviderSimulator:
    def __init__(self, snail_races: SnailRaces = None):
        self.snail_races = snail_races if snail_races else SnailRaces()

    def races(self) -> SnailRaces:
        return self.snail_races

    def simulate_race_result(self, race_id: int, datetime: int, podium: Podium) -> None:
        self.snail_races = SnailRaces.with_additional_result(
            self.snail_races, race_id, datetime, podium
        )
