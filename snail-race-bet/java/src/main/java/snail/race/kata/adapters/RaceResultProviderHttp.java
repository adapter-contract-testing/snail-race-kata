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
        return null;
    }

    static RaceResultProviderHttpRecords.RaceData invokeResultEndPoint() throws IOException, InterruptedException {
        var httpClient = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8000/results/")).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        RaceResultProviderHttpRecords.RaceData raceData = new ObjectMapper().readValue(response.body(), RaceResultProviderHttpRecords.RaceData.class);
        return raceData;
    }

}
