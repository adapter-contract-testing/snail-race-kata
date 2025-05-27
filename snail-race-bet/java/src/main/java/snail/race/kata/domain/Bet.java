package snail.race.kata.domain;

public record Bet(
        String gambler,
        PodiumPronostic pronostic,
        long timestamp
){

    public boolean isInTimeFor(RaceResultProvider.SnailRace race) {
        return timestamp < race.timestamp() - 2;
    }

    boolean betIsOn(RaceResultProvider.Podium podium) {
        return pronostic().first() == podium.first().number() &&
               pronostic().second() == podium.second().number() &&
               pronostic().third() == podium.third().number();
    }
}

