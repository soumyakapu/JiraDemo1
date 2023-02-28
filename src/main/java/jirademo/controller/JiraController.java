package jirademo.controller;

import com.atlassian.httpclient.api.Response;
import com.mashape.unirest.http.exceptions.UnirestException;
import jirademo.entity.JiraRequest;
import jirademo.service.JiraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jira")
public class JiraController {
    private JiraService jiraService;
    JiraController(JiraService jiraService){
        this.jiraService=jiraService;
    }
    @PostMapping("/create")
    public ResponseEntity<Response> create(@RequestBody JiraRequest jiraRequest) throws UnirestException {
         return new ResponseEntity<Response>((Response) jiraService.create(jiraRequest),HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public void getUsers() throws UnirestException {
        jiraService.getAllUsers();
    }
}
