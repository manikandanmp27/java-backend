package com.apidashboard;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static void handleError(int statusCode) {
        switch (statusCode) {
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
                System.out.println("Unexpected error:" + statusCode);
        }
    }

    static void displayTodos(List<Todo> todos) {
        if (todos.isEmpty()) {
            System.out.println("No incomplete todos found for this user.");
            return;
        }
        System.out.println("Todo List\n");
        todos.stream()
                .forEach(todo -> {
                    System.out.println(todo.getId() + "-" + todo.getTitle() + "- Completed:" + todo.isCompleted());
                });

    }

    static List<Todo> getIncompleteTodosByUser(List<Todo> todos, int userId) {
        return todos.stream()
                .filter(todo -> todo.getUserId() == userId)
                .filter(todo -> !todo.isCompleted())
                .toList();
    }

    static long countCompletedTodos(List<Todo> todos, int userId) {
        return todos.stream()
                .filter(todo -> todo.isCompleted())
                .filter(todo -> todo.getUserId() == userId)
                .count();
    }

    static void displaySummary(int userId, long completed, long incomplete) {
        System.out.println("===== TODO DASHBOARD =====");
        System.out.println("User:" + userId);
        System.out.println("Completed:" + completed);
        System.out.println("Incomplete:" + incomplete);
        System.out.println("===== INCOMPLETE TODOS =====");
    }

    static void displayCompletedTodos(List<Todo> todos,int userId)
    {
        List<Todo> completedTodo=todos.stream()
        .filter(todo->todo.isCompleted())
        .filter(todo->todo.getUserId()==userId)
        .toList();
        if(completedTodo.isEmpty())
        {
            System.out.println("No completed todos");
            return;
        }
        displayTodos(completedTodo);
    }

    static void showMenu(){
        System.out.println("===== TODO MENU =====");
        System.out.println("1. Show incomplete todos\n" + //
                        "2. Show completed todos\n" + //
                        "3. Exit\n" + //
                        "Choose an option:");
    }

    public static void main(String[] args) throws Exception {
        // responsible forcommunicating over HTTP
        HttpClient client = HttpClient.newHttpClient();

        // creating HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
                .GET()
                .build();
        // System.out.println(request.uri());
        // send the request
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter User ID:");
        try {

            if (response.statusCode() == 200) {
                int userId = scanner.nextInt();
                
                List<Todo> todos = mapper.readValue(response.body(), new TypeReference<List<Todo>>() {
                });
                // System.out.println(todos.size());
                // List<Todo> incompleteTodos=getIncompleteTodos(todos);
                // List<Todo> userTodos=getTodosByUser(todos, 1);
                List<Todo> mainTodos = getIncompleteTodosByUser(todos, userId);
                // displaySummary(userId, countCompletedTodos(todos, userId), mainTodos.size());
                // displayTodos(mainTodos);
                // System.out.println("===============");

                showMenu();
                int choice=scanner.nextInt();

                switch(choice){
                    case 1:
                        displayTodos(mainTodos);
                        break;
                    case 2:
                        displayCompletedTodos(todos, userId);
                        break;
                    case 3:
                        System.out.println("Goodbye!!");
                        break;
                    default:
                        System.out.println("Invalid");
                }


            } else {
                handleError(response.statusCode());
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input,Enter Integer");
            scanner.nextLine();
        }

        scanner.close();

    }

}