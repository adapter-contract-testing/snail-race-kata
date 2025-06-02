package snail.race.kata.adapters;

import org.junit.jupiter.api.Test;
import snail.race.kata.domain.RaceResultProvider.Podium;
import snail.race.kata.domain.RaceResultProvider.Snail;
import snail.race.kata.domain.RaceResultProvider.SnailRace;
import snail.race.kata.domain.RaceResultProvider.SnailRaces;
import snail.race.kata.adapters.RaceResultProviderHttprRecords.Races;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static snail.race.kata.adapters.RaceResultProviderHttpAntiCorruptionLayerTest.RaceBuilder.aRace;
import static snail.race.kata.adapters.RaceResultProviderHttpAntiCorruptionLayerTest.SnailBuilder.aSnail;

class RaceResultProviderHttpAntiCorruptionLayerTest {

    @Test
    void convert_infrastructure_data_with_right_podium() {
        var notOnPodium = aSnail().withDuration(15.0).build();
        var third = aSnail().withDuration(12.5).build();
        var second = aSnail().withDuration(10.3).build();
        var first = aSnail().withDuration(5.0).build();
        var firstRace = aRace().withSnails(
                third,
                first,
                second,
                notOnPodium).build();
        var raceData = new Races(List.of(firstRace));

        var snailRaces = RaceResultProviderHttpAntiCorruptionLayer.mapToDomain(raceData);

        var expectedRace = new SnailRace(
                firstRace.raceId(),
                firstRace.timestamp(),
                createPodium(first, second, third));
        var expectedSnailRace = new SnailRaces(List.of(expectedRace));
        assertThat(snailRaces).isEqualTo(expectedSnailRace);
    }

    @Test
    void map_all_api_race_to_a_snail_race_with_the_right_timestamp_and_id() {
        var races = List.of(
                aRace().build(),
                aRace().build(),
                aRace().build(),
                aRace().build());
        var raceData = new Races(races);

        var snailRaces = RaceResultProviderHttpAntiCorruptionLayer.mapToDomain(raceData);

        assertThat(snailRaces.races())
                .extracting(SnailRace::raceId, SnailRace::timestamp)
                .containsExactlyElementsOf(
                        races.stream()
                                .map(race -> tuple(race.raceId(), race.timestamp()))
                                .toList()
                );

    }

    private static Podium createPodium(RaceResultProviderHttprRecords.Snail first, RaceResultProviderHttprRecords.Snail second, RaceResultProviderHttprRecords.Snail third) {
        return new Podium(
                new Snail(first.number(), first.name()),
                new Snail(second.number(), second.name()),
                new Snail(third.number(), third.name())
        );
    }

    public static class RaceBuilder {

        private final int raceId = Math.toIntExact(Math.round(Math.random() * 10000 + 1));
        private final long timestamp = Math.round(Math.random() * 100000L * 100000L);
        private List<RaceResultProviderHttprRecords.Snail> snails = List.of(
                aSnail().build(),
                aSnail().build(),
                aSnail().build(),
                aSnail().build()
        );

        public static RaceBuilder aRace() {
            return new RaceBuilder();
        }

        public RaceBuilder withSnails(RaceResultProviderHttprRecords.Snail... snails) {
            this.snails = Arrays.stream(snails).toList();
            return this;
        }

        public RaceResultProviderHttprRecords.Race build() {
            return new RaceResultProviderHttprRecords.Race(
                    raceId,
                    timestamp,
                    snails);
        }
    }

    public static class SnailBuilder {
        private final int number = Math.toIntExact(Math.round(Math.random() * 10000));
        double duration = Math.random() * 1000;

        public static SnailBuilder aSnail() {
            return new SnailBuilder();
        }

        public SnailBuilder withDuration(double duration) {
            this.duration = duration;
            return this;
        }

        public RaceResultProviderHttprRecords.Snail build() {
            return new RaceResultProviderHttprRecords.Snail(
                    number,
                    String.format("Snail name %d", number),
                    duration);
        }
    }
}