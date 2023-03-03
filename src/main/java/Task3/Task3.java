package Task3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Task3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        openedTasks();
    }

    public static void openedTasks() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/1/todos"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonArray array = new Gson().fromJson(response.body(), JsonArray.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<UserForTask3Json> users = new ArrayList<>();
        for (JsonElement element : array) {
            UserForTask3Json user = gson.fromJson(element, UserForTask3Json.class);
            if (!user.isCompleted()) {
                users.add(user);
            }
        }

        for (UserForTask3Json toShowUsersAsJson : users) {
            System.out.println(gson.toJson(toShowUsersAsJson));
        }

    }

}

