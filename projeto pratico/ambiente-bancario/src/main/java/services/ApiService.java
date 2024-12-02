package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.ambiente.bancario.dto.AccountDto;
import com.mycompany.ambiente.bancario.dto.BalanceDto;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * ApiService responsável por realizar chamadas HTTP e retornar objetos
 * mapeados.
 */
public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/account/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public AccountDto postCreateUserAccount(Object requestBody) throws JsonProcessingException {
        return sendPostRequest(BASE_URL + "create", requestBody, AccountDto.class);
    }

    public AccountDto postAuthUserAccount(Object requestBody) throws JsonProcessingException {
        return sendPostRequest(BASE_URL + "auth", requestBody, AccountDto.class);
    }

    public BalanceDto patchBalanceMovement(Object requestBody) throws JsonProcessingException {
        return sendPatchRequest(BASE_URL + "balance-movement", requestBody, BalanceDto.class);
    }

    private <T> T sendPatchRequest(String url, Object requestBody, Class<T> responseType) {
        try {
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return objectMapper.readValue(response.body(), responseType);
            } else {
                System.out.println("Error during HTTP request: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error during HTTP request: " + e.getMessage());
            return null;
        }
    }

    private <T> T sendPostRequest(String url, Object requestBody, Class<T> responseType) {
        try {
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(response.body(), responseType);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error during HTTP request: " + e.getMessage());
            return null;
        }
    }
}
