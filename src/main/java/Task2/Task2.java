package Task2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        putCommentsIntoTheFile(10);
    }

    public static void putCommentsIntoTheFile(int lastId) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/1/posts?id=" + lastId))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray array = new Gson().fromJson(response.body(), JsonArray.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int userId = 0;
        int postId = 0;
        for (JsonElement element : array) {
            UserForTask2Json user = gson.fromJson(element, UserForTask2Json.class);
            userId = user.getUserId();
            postId = user.getId();
        }

        FileWriter writer = new FileWriter("user-" + userId + "-post-" + postId + "-comments.json");
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts/10/comments"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
        JsonArray array1 = new Gson().fromJson(response1.body(), JsonArray.class);
        List<UsersCommentsJson> listOfComments = new ArrayList<>();
        for (JsonElement element : array1) {
            listOfComments.add(gson.fromJson(element, UsersCommentsJson.class));
        }
        gson.toJson(listOfComments, writer);
        writer.close();

    }


}

