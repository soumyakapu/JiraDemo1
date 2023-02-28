package jirademo.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jirademo.entity.JiraRequest;
import jirademo.entity.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JiraService {
    private final JiraMetaData jiraMetaData;
   private JiraService(final JiraMetaData jiraMetaData){
        this.jiraMetaData=jiraMetaData;
    }
    public Response create(JiraRequest jiraRequest) throws UnirestException {
        ObjectNode payload=buildPayload(jiraRequest);
        HttpResponse<JsonNode> response = Unirest.post("https://xafa.atlassian.net/rest/api/3/issue")
                .basicAuth("xafawi2657@iucake.com", "ATATT3xFfGF0kvC1GQkFkGtsjCe12EpTNOsBZ-surFtB4YkukOhJnRS0g5_TaZRAL5SZu8pPSo6vpEDeJR5a6JgCmOxfkOut2aYk1QSCfNWk0Bn3UAbad2Xh6wwihihXy9l8szyaM256uIhtWhoTkKNqn5P0S_RUmpXt0rQNwctmURIXgRC4W0E=849484D9")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(payload)
                .asJson();
        JsonNode jsonNode=response.getBody();
        return  buildResponse(jsonNode);
    }
    public ObjectNode buildPayload(JiraRequest jiraRequest) throws UnirestException {
        JsonNode metaData = jiraMetaData.getMetaData(jiraRequest);

        JSONArray jsonArray = metaData.getArray();
        JSONObject projectMainJsonObject = jsonArray.getJSONObject(0);
        JSONArray projectJsonArray = projectMainJsonObject.getJSONArray("projects");

        JSONObject projectObject = projectJsonArray.getJSONObject(0);

        JSONArray issueTypesJsonArray = projectObject.getJSONArray("issuetypes");
        JSONObject issueObject = issueTypesJsonArray.getJSONObject(0);
        JSONObject fieldsObject = issueObject.getJSONObject("fields");

        JsonNodeFactory jnf = JsonNodeFactory.instance;
        ObjectNode payload = jnf.objectNode();
        ObjectNode fields = payload.putObject("fields");
        fields.put("summary", jiraRequest.getSummary());

        ObjectNode description = fields.putObject("description");
        description.put("type", "doc");
        description.put("version", 1);
        ArrayNode contentMain = description.putArray("content");
        ObjectNode content0 = contentMain.addObject();
        content0.put("type", "paragraph");
        ArrayNode content1 = content0.putArray("content");
        ObjectNode currentObj = content1.addObject();
        currentObj.put("text", jiraRequest.getDescription());
        currentObj.put("type", "text");

        ObjectNode issueType = fields.putObject("issuetype");
        issueType.put("id", issueObject.getString("id"));
        ObjectNode project = fields.putObject("project");
        project.put("id", projectObject.getString("id"));

        ObjectMapper objectMapper = new ObjectMapper();

        Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
            @Override
            public <T> T readValue(String s, Class<T> aClass) {
                try {
                    return objectMapper.readValue(s, aClass);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String writeValue(Object o) {
                try {
                    return objectMapper.writeValueAsString(o);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return payload;
    }
    public Response buildResponse(JsonNode jsonNode){
        Response response= new Response();
        response.setId((String) jsonNode.getObject().get("id"));
        response.setKey((String) jsonNode.getObject().get("key"));
        response.setSelf((String) jsonNode.getObject().get("self"));
       return response;
    }
    public void getAllUsers() throws UnirestException {
                HttpResponse<JsonNode> response = Unirest.get("https://xafa.atlassian.net/rest/api/3/users/search")
                        .basicAuth("xafawi2657@iucake.com", "ATATT3xFfGF0kvC1GQkFkGtsjCe12EpTNOsBZ-surFtB4YkukOhJnRS0g5_TaZRAL5SZu8pPSo6vpEDeJR5a6JgCmOxfkOut2aYk1QSCfNWk0Bn3UAbad2Xh6wwihihXy9l8szyaM256uIhtWhoTkKNqn5P0S_RUmpXt0rQNwctmURIXgRC4W0E=849484D9")
                        .header("Accept", "application/json")
                        .asJson();
                System.out.println(response.getBody());
            }
}
