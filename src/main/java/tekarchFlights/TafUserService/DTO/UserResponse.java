package tekarchFlights.TafUserService.DTO;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String createdAt;
    private String updatedAt;
}
