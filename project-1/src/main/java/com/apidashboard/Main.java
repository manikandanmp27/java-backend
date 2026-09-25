package com.apidashboard;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

public class Main {

    static void handleError(int statusCode) {
        switch(statusCode)
        {
            case 400:
                System.out.println("Bad Request");
                break;
            case 401:
                System.out.println("Unauthorized");
                break;
            case 403:
                System.out.println("Forbidden");
                break;
            case 404:
                System.out.println("Resource not found");
                break;
            case 500:
                System.out.println("Server error");
                break;
            default:
                System.out.println("Unexpected error:"+statusCode);
        }
    }
    static List<Todo> getIncompleteTodos(List<Todo> todos)
    {
        return todos.stream()
                 .filter(todo->!todo.isCompleted())
                 .toList();
    }
    static void displayTodos(List<Todo> todos)
    {
        System.out.println("Todo List\n");
        todos.stream()
        .forEach(todo->{
            System.out.println(todo.getId()+"-"+todo.getTitle()+"- Completed:"+todo.isCompleted());
        });
        
    }
    static List<Todo> getTodosByUser(List<Todo> todos,int userId)
    {
        return todos.stream()
        .filter(todo->todo.getUserId()==userId)
        .toList();
    }
    static List<Todo> getIncompleteTodosByUser(List<Todo> todos,int userId)
    {
        return todos.stream()
        .filter(todo->todo.getUserId()==userId)
        .filter(todo->!todo.isCompleted())
        .toList();
    }
    public static void main(String[] args) throws Exception {
        // responsible forcommunicating over HTTP
        HttpClient client = HttpClient.newHttpClient();

        // creating HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
                .GET()
                .build();
        System.out.println(request.uri());
        // send the request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();

        if (response.statusCode() == 200) {

            List<Todo> todos = mapper.readValue(response.body(),new TypeReference<List<Todo>>(){});
            // System.out.println(todos.size());
            // List<Todo> incompleteTodos=getIncompleteTodos(todos);
            // List<Todo> userTodos=getTodosByUser(todos, 1);
            List<Todo> mainTodos=getIncompleteTodosByUser(todos, 1);
            displayTodos(mainTodos);
        }
        else{
            handleError(response.statusCode());
        }

    }

}