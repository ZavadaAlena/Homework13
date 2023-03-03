package Task1;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class UserMethod {
    public static void main(String[] args) throws IOException, InterruptedException {
        User user1 = new User(0,"Evgenii Zavada", "Commando", "zavada1997@gmail.com",
                "street: Admon 6" + "," + " " + "city: Katsrin", +38058888888L, "commando.com",
                "Tavtek");

        User user2 = new User(1,"Ira Garmash", "Monkey", "irina@gmail.com",
                "street: Dobrolubova 84" + "," + " " + "city: Nicopol", +380993568555L,
                "Cats", "cats.com");


        User userMethod1 = createNewUser(user1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("userMethod 1 = " + gson.toJson(userMethod1));

        User userMethod2 = updatingOFUser(user2);
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("userMethod 2 = " + gson2.toJson(userMethod2));

        System.out.println("Use method 3");
        userRemove(4);

        System.out.println("Use method 4");
        getInfoAboutAllUsers();

        System.out.println("Use method 5");
        getInfoUsersId(3);

        System.out.println("Use method 6");
        getInfoAboutUsersByHisUserName("Bret");

    }


    public static User createNewUser(User user) throws IOException, InterruptedException {
        String jsonUser = new Gson().toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonUser))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), User.class);


    }

    public static User updatingOFUser(User user) throws IOException, InterruptedException {
        String jsonUser = new Gson().toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + user.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonUser))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), User.class);
    }

    public static void userRemove(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
    }

    public static void getInfoAboutAllUsers() throws IOException, InterruptedException {
        Document doc = Jsoup.connect("https://jsonplaceholder.typicode.com/users")
                .ignoreContentType(true)
                .get();

        System.out.println(doc.body().wholeText());
    }

    public static void getInfoUsersId(int id) throws IOException {
        Document doc = Jsoup.connect("https://jsonplaceholder.typicode.com/users/" + id)
                .ignoreContentType(true)
                .get();
        System.out.println(doc.body().wholeText());

    }

    public static void getInfoAboutUsersByHisUserName(String username) throws IOException {
        Document doc = Jsoup.connect("https://jsonplaceholder.typicode.com/users?username=" + username)
                .ignoreContentType(true)
                .get();
        System.out.println(doc.body().wholeText());


    }


}
