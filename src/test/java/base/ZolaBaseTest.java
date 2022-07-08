package base;

import utils.pubsub.PubSubSender;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZolaBaseTest extends BaseTest {
    /**
     * Sends message to pointed subscription and
     * @param jsonObject
     * @param topicName
     */
    public void sendInsertPubSubMessage(JSONObject jsonObject, String topicName){
        Map<String, String> attributes = new HashMap<>();
        attributes.put("operation", "INSERT");
        attributes.put("operationName", "INSERT");
        attributes.put("correlationId", (String) jsonObject.get("correlationId"));
       new PubSubSender()
                .project(SPANNER_PROJECT_NAME)
                .topic(topicName)
                .attributes(attributes)
                .jsonMessages(jsonObject)
                .sendMessages();

    }


}
