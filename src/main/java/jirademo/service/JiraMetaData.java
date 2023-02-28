package jirademo.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jirademo.entity.JiraRequest;
import jirademo.entity.Response;
import org.springframework.stereotype.Service;

@Service
public class JiraMetaData {


    public JsonNode getMetaData(JiraRequest jiraRequest) throws UnirestException {
        String api=String.format(
                "https://xafa.atlassian.net/rest/api/3/issue/createmeta?projectKeys=%s&issuetypeNames=%s&expand=projects.issuetypes.fields",
                jiraRequest.getProjectKey(),
                jiraRequest.getIssueType()
        );
        HttpResponse<JsonNode> response = Unirest.get(api)
                .basicAuth("xafawi2657@iucake.com", "ATATT3xFfGF0kvC1GQkFkGtsjCe12EpTNOsBZ-surFtB4YkukOhJnRS0g5_TaZRAL5SZu8pPSo6vpEDeJR5a6JgCmOxfkOut2aYk1QSCfNWk0Bn3UAbad2Xh6wwihihXy9l8szyaM256uIhtWhoTkKNqn5P0S_RUmpXt0rQNwctmURIXgRC4W0E=849484D9")
                .header("Accept", "application/json")
                .asJson();
        return response.getBody();
    }


}
