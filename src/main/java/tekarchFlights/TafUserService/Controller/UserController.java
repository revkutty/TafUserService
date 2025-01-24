package tekarchFlights.TafUserService.Controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import tekarchFlights.TafUserService.DTO.UserRequest;
import tekarchFlights.TafUserService.DTO.UserResponse;
import tekarchFlights.TafUserService.Service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
    private UserService userService;

 /*  @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

  */

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            // Call the service layer to register the user
            return userService.registerUser(userRequest);
        //    ResponseEntity<?> userResponse = userService.registerUser(userRequest);
         //   return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

        } catch (HttpClientErrorException.Conflict e) {
            // Handle 409 Conflict (username already taken) error gracefully
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the user.");
        }
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(userId, userRequest);
    }
}

