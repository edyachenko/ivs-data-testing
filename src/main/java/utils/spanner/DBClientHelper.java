package utils.spanner;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Statement;

import java.util.ArrayList;

public class DBClientHelper {

    static DatabaseClient dbSpannerClient = DBClient.getInstance();

    //TODO: may be add some logger to track the info about executed queries
    public static void sendBatchQuery(ArrayList<Statement> queryStatementList, DatabaseClient client) {
        client.readWriteTransaction()
                .run(transactionContext -> {
                    transactionContext.batchUpdate(queryStatementList);
                    return null;
                });

    }

    //By default we use regular db instance
    public static void sendBatchQuery(ArrayList<Statement> queryStatementList) {
        sendBatchQuery(queryStatementList, dbSpannerClient);
    }


    public static ResultSet singleUseQuery(String query, DatabaseClient client) {
        return client
                .singleUse() // Execute a single read or query against Cloud Spanner.
                .executeQuery(Statement.of(query));

    }

    public static ResultSet singleUseQuery(String query) {
        return singleUseQuery(query, dbSpannerClient);

    }


}
