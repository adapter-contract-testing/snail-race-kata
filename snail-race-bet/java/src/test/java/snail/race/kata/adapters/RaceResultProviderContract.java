package snail.race.kata.adapters;

import org.junit.jupiter.api.Test;
import snail.race.kata.domain.RaceResultProvider;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class RaceResultProviderContract {
    @Test
    void provide_race_results() {

        RaceResultProvider.SnailRaces races = getRaceResultProvider().races();

        assertThat(races).isNotNull();
        assertThat(races.races()).isNotEmpty();
    }

    protected abstract RaceResultProvider getRaceResultProvider();
}
