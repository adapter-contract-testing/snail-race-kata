package snail.race.kata.domain;

public record Bet(
        String gambler,
        PodiumPronostic pronostic,
        int timestamp
){

}

