package utils.pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.util.Map;

public class PubSubSender {
    JSONObject jsonObject;
    String projectName;
    String topicName;
    private Map<String, String> headers;

    public PubSubSender() {
    }

    public PubSubSender project(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public PubSubSender topic(String topicName) {
        this.topicName = topicName;
        return this;
    }

    public PubSubSender jsonMessages(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        return this;
    }

    public PubSubSender attributes(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public void sendMessages() {

        TopicName topic = TopicName.of(this.projectName, this.topicName);
        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topic).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

            PubsubMessage message = PubsubMessage.newBuilder()
                    .setData(ByteString.copyFromUtf8(jsonObject.toString()))
                    .putAllAttributes(this.headers)
                    .build();
        Assert.assertNotNull(publisher);
        publisher.publish(message);
        publisher.shutdown();

    }


}
