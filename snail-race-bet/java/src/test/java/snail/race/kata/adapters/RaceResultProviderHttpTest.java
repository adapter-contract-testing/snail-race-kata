package snail.race.kata.adapters;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import snail.race.kata.domain.RaceResultProvider;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class RaceResultProviderHttpTest extends RaceResultProviderContract {

    @Override
    protected RaceResultProvider getRaceResultProvider() {
        return new RaceResultProviderHttp();
    }


    @Test
    public void provide_something() throws IOException, InterruptedException {
        RaceResultProviderHttpRecords.RaceData raceData = RaceResultProviderHttp.invokeResultEndPoint();

        assertThat(raceData.races()).isNotEmpty();
    }

}
