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
        inputtingTheComments(1);
    }

    public static UserForTask2Json getUserForTask2Json(int userId) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://jsonplaceholder.typicode.com/users/%d/posts", userId)))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray array = new Gson().fromJson(response.body(), JsonArray.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(array.get(array.size() - 1), UserForTask2Json.class);
    }

    public static void inputtingTheComments(int userId) throws IOException, InterruptedException {
        UserForTask2Json fromJson = getUserForTask2Json(userId);

        FileWriter writer = new FileWriter(String.format("user-%d-post-%d-comments.json",
                fromJson.getUserId(), fromJson.getId()));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://jsonplaceholder.typicode.com/posts/%d/comments",
                        fromJson.getId())))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> responseComments = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
        JsonArray comments = new Gson().fromJson(responseComments.body(), JsonArray.class);

        gson.toJson(comments, writer);
        writer.close();
    }
}
