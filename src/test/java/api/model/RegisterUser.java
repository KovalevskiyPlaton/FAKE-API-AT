package api.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
