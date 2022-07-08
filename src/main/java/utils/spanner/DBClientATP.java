package utils.spanner;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;
import config.CustomConfig;

public class DBClientATP {

    private static DatabaseClient atpInstance = null;

    private DBClientATP() {
    }

    public static DatabaseClient getInstance(String projectId, String instaceId, String databaseId) {
        if (atpInstance != null) {
            return atpInstance;
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
        atpInstance =
                spanner.getDatabaseClient(DatabaseId.of(options.getProjectId(), instanceId, databaseId));

        return atpInstance;

    }


}
