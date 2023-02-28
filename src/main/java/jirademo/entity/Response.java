package jirademo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Data
@Setter
@Getter
public class Response  {
    private String key;
    private String self;
    private String id;
}
