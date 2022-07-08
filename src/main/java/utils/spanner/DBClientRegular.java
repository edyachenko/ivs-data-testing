package utils.spanner;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;

public class DBClientRegular {

    private static DatabaseClient regularInstance = null;

    private DBClientRegular() {
    }

    public static DatabaseClient getInstance(String projectId, String instaceId, String databaseId) {
        if (regularInstance != null) {
            return regularInstance;
        } else {
            return getDBClient(projectId, instaceId, databaseId);

        }
    }

    private static DatabaseClient getDBClient(String projectId, String instanceId, String databaseId) {
        SpannerOptions options = SpannerOptions.newBuilder()
                .setProjectId(projectId)
                .build();
        Spanner spanner = options.getService();

        // Creates a database client
        regularInstance =
                spanner.getDatabaseClient(DatabaseId.of(options.getProjectId(), instanceId, databaseId));

        return regularInstance;
    }



}
