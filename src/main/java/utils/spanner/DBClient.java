package utils.spanner;

import com.google.cloud.spanner.DatabaseClient;
import config.CustomConfig;

    /**
     * 2 separated classes for 2 instances are created due to Singleton pattern
     */
public class DBClient {

    /**
     * @return instance of regular spanner client
     */
    public static DatabaseClient getInstance() {
        String projectId = CustomConfig.getPropertyInstance().getProperty("spanner.project");
        String instanceId = CustomConfig.getPropertyInstance().getProperty("spanner.instance");
        String databaseId = CustomConfig.getPropertyInstance().getProperty("spanner.database");


        return DBClientRegular.getInstance(projectId, instanceId, databaseId);
    }

    /**
     * @return instance of ATP spanner client
     */
    public static DatabaseClient getATPInstance() {
        String projectId = CustomConfig.getPropertyInstance().getProperty("spanner.project");
        String instanceId = CustomConfig.getPropertyInstance().getProperty("spanner.instance.atp");
        String databaseId = CustomConfig.getPropertyInstance().getProperty("spanner.database");

        return DBClientATP.getInstance(projectId, instanceId, databaseId);
    }


}
