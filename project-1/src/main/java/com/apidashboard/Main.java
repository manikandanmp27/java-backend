import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {
        // responsible forcommunicating over HTTP
        HttpClient client = HttpClient.newHttpClient();

        // creating HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                .GET()
                .build();
        System.out.println(request.uri());
        // send the request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();

        if (response.statusCode() == 200) {

            Todo todo = mapper.readValue(response.body(), Todo.class);
            System.out.println(todo.getId());
            System.out.println(todo.getTitle());
            System.out.println(todo.isCompleted());
        } else {
            System.out.println("Error:" + response.statusCode());
        }

    }

}