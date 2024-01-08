package kg.neo.mobimarket_2.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonProperty("user_id")
    private Integer userId;
}
