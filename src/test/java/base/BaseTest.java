package base;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Statement;
import config.CustomConfig;
import utils.spanner.DBClientHelper;

import java.util.ArrayList;

public abstract class BaseTest {

    public final static String SPANNER_PROJECT_NAME = CustomConfig.getPropertyInstance().getProperty("spanner.project");
    public final static String ENRICH_SUPPLY_DEMAND_TOPIC_NAME = CustomConfig.getPropertyInstance().getProperty("spanner.topic.enrich-supply-demand");
    public final static String PREPARE_ITEM_SUBSCRIPTION = CustomConfig.getPropertyInstance().getProperty("spanner.subscription.prepareItem");


    /**
     * This method may execute INSERT/UPDATE/DELETE requests
     * @param queryStatementList - list of querries in Statement format
     */
    public void executeBatchDml(ArrayList<Statement> queryStatementList){
        DBClientHelper.sendBatchQuery(queryStatementList);

    }

    /**
     * This method may execute INSERT/UPDATE/DELETE requests
     * @param queryStatementList - list of querries in Statement format
     * @param client - you can point right db client
     */
    public void executeBatchDml(ArrayList<Statement> queryStatementList, DatabaseClient client){
        DBClientHelper.sendBatchQuery(queryStatementList, client);

    }

    /**
     * This method may execute SELECT requests
     * @param query - string with query
     */
    public ResultSet singleUseQuery(String query) {
        return DBClientHelper.singleUseQuery(query);

    }

    /**
     * This method may execute SELECT requests
     * @param query - string with query
     * @param client - you can point right db client
     */
    public ResultSet singleUseQuery(String query, DatabaseClient client) {
        return DBClientHelper.singleUseQuery(query, client);

    }


}
