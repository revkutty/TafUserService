package tekarchFlights.TafUserService.Service;


import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import tekarchFlights.TafUserService.DTO.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tekarchFlights.TafUserService.DTO.UserResponse;


@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DATASTORE_BASE_URL = "http://localhost:8081/api/users";

    public ResponseEntity<?> registerUser(UserRequest userRequest) {
        try {
            return restTemplate.postForEntity(DATASTORE_BASE_URL, userRequest, UserResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getResponseBodyAsString());
            }
            throw e;
        }
    }

  /*  public ResponseEntity<UserResponse> registerUser(UserRequest userRequest) {
        String url = DATASTORE_BASE_URL;
        // Send POST request to TafDatastoreService
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(url, userRequest, UserResponse.class);
        return response;
    }

   */
    public ResponseEntity<UserResponse> getUserById(Long userId) {
        String url = DATASTORE_BASE_URL + "/" + userId;
        // Send GET request to TafDatastoreService
        ResponseEntity<UserResponse> response = restTemplate.getForEntity(url, UserResponse.class);
        return response;
    }

    public ResponseEntity<String> updateUser(Long userId, UserRequest userRequest) {
        String url = DATASTORE_BASE_URL + "/" + userId;
        // Send PUT request to TafDatastoreService
        restTemplate.put(url, userRequest);
        return ResponseEntity.ok("User updated successfully.");
    }
}