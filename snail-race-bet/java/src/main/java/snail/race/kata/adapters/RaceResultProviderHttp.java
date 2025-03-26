package snail.race.kata.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import snail.race.kata.domain.RaceResultProvider;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class RaceResultProviderHttp implements RaceResultProvider {
    @Override
    public SnailRaces races() {
        RaceResultProviderHttpRecords.RaceData raceData = invokeResultEndPoint();
        return RaceResultProviderHttpAnticorruptionLayer.mapToDomain(raceData);
    }

    static RaceResultProviderHttpRecords.RaceData invokeResultEndPoint() {
        try {
            var httpClient = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(URI.create("http://localhost:8000/results/")).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return new ObjectMapper().readValue(response.body(), RaceResultProviderHttpRecords.RaceData.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
