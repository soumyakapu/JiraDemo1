package jirademo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class JiraRequest {

    private String repoterId;
    private String assigneeId;
    private String issueType;
    private String summary;
    private String projectKey;
    private String prioriy;
    private String description;
    private String start_date;
    private String due_date;
}
