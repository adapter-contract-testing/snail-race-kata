package snail.race.kata.adapters;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static snail.race.kata.adapters.RaceResultProviderHttpAnticorruptionLayer.mapToDomain;
import static snail.race.kata.domain.RaceResultProvider.Podium;
import static snail.race.kata.domain.RaceResultProvider.Snail;


class RaceResultProviderHttpAnticorruptionLayerTest {
    @Test
    void map_http_records_to_domain_classes() {
        var firstSnail = new RaceResultProviderHttpRecords.Snail(3, "Speedy", 12.5);
        var secondSnail = new RaceResultProviderHttpRecords.Snail(8, "Flash", 13.0);
        var thirdSnail = new RaceResultProviderHttpRecords.Snail(10, "Zoom", 14.2);
        var notInPodium = new RaceResultProviderHttpRecords.Snail(2, "Joly", 15.5);
        var apiRace = new RaceResultProviderHttpRecords.Race(
                101,
                1698765432000L,
                List.of(notInPodium, firstSnail, thirdSnail, secondSnail));
        var apiSnailRaces = new RaceResultProviderHttpRecords.RaceData(List.of(apiRace));

        // Mapping
        var domainSnailRaces = mapToDomain(apiSnailRaces);

        // Vérification du résultat
        var firstRace = domainSnailRaces.races().get(0);
        assertThat(firstRace.raceId()).isEqualTo(101);
        assertThat(firstRace.timestamp()).isEqualTo(1698765432000L);

        var podium = firstRace.podium();
        assertThat(podium).isEqualTo(new Podium(
                new Snail(3, "Speedy"),
                new Snail(8, "Flash"),
                new Snail(10, "Zoom")));
    }


}