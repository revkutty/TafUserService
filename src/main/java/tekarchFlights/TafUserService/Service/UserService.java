package tekarchFlights.TafUserService.Service;


import org.springframework.beans.factory.annotation.Value;
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

    @Value("${datasource.service.url}")
    private String DATASTORE_BASE_URL;


   // private static final String DATASTORE_BASE_URL = "http://localhost:8081/api/users";

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


    public ResponseEntity<UserResponse> getUserById(Long userId) {
        String url = DATASTORE_BASE_URL + "/" + userId;
        // Send GET request to TafDatastoreService
        return restTemplate.getForEntity(url, UserResponse.class);
    }

    public ResponseEntity<String> updateUser(Long userId, UserRequest userRequest) {
        String url = DATASTORE_BASE_URL + "/" + userId;
        // Send PUT request to TafDatastoreService
        restTemplate.put(url, userRequest);
        return ResponseEntity.ok("User updated successfully.");
    }
}