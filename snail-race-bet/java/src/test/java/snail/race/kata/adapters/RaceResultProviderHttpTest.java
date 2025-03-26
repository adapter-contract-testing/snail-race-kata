package snail.race.kata.adapters;

import org.junit.jupiter.api.Test;
import snail.race.kata.domain.RaceResultProvider;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class RaceResultProviderHttpTest {

    @Test
    void provide_race_results() {
        RaceResultProviderHttp raceResultProviderHttp = new RaceResultProviderHttp();

        RaceResultProvider.SnailRaces races = raceResultProviderHttp.races();

        assertThat(races).isNotNull();
        assertThat(races.races()).isNotEmpty();
    }


    @Test
    public void provide_something() throws IOException, InterruptedException {
        RaceResultProviderHttpRecords.RaceData raceData = RaceResultProviderHttp.invokeResultEndPoint();

        assertThat(raceData.races()).isNotEmpty();
    }

}
