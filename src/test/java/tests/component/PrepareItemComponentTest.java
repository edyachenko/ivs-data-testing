package tests.component;

import base.BaseTest;
import config.CustomConfig;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.parsers.CustomJsonParser;
import utils.pubsub.PubSubReceiver;
import utils.pubsub.PubSubSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrepareItemComponentTest extends BaseTest {

    @Test
    public void testPrepareItemComponent(){

        //Getting JSON prepared for sending to appropriate topic
        JSONObject updatedCalculateEligibilityJson = CustomJsonParser.getUpdatedCalculateEligibilityJson();
        String correlationId = updatedCalculateEligibilityJson.get("correlationId").toString();
         Map<String, String> attributes = new HashMap<>();
        attributes.put("operationName", "INSERT_OR_UPDATE");
        attributes.put("correlationId", correlationId);

        //Send JSON object
        PubSubSender pbSender = new PubSubSender();
        pbSender.project(SPANNER_PROJECT_NAME)
                .topic(CustomConfig.getPropertyInstance().getProperty("spanner.topic.prepareItem"))
                .attributes(attributes)
                .jsonMessages(updatedCalculateEligibilityJson)
                .sendMessages();

        List<String> messages = PubSubReceiver.getMessageFromLog(SPANNER_PROJECT_NAME, PREPARE_ITEM_SUBSCRIPTION );
        List<String> actualMessages = messages.stream().filter(message -> message.contains(correlationId)).collect(Collectors.toList());

        Assert.assertTrue(actualMessages.size() > 0);
        //TODO: add more verifications
    }

}
