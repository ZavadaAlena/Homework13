package Task2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Task2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        inputtingTheComments();
    }
    public static FileWriter createACorrectNameOfTheFile() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/1/posts"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray array = new Gson().fromJson(response.body(), JsonArray.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        UserForTask2Json fromJson = gson.fromJson(array.get(array.size() - 1), UserForTask2Json.class);
        return new FileWriter("user-" + fromJson.getUserId() + "-post-" + fromJson.getId() + "-comments.json");
    }

    public static void inputtingTheComments() throws IOException, InterruptedException {
        FileWriter writer = createACorrectNameOfTheFile();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/10/comments"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> responseComments = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
        JsonArray comments = new Gson().fromJson(responseComments.body(), JsonArray.class);

        gson.toJson(comments, writer);
        writer.close();
    }
}
