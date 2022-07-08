package tests.end2end;

import assertions.TableAssertions;
import base.ZolaBaseTest;
import com.google.cloud.spanner.Statement;
import dataproviders.ZolaStoreLocationsDP;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import pojo.CurrEntBal;
import pojo.CurrEntBalSnapshot;
import pojo.SellChnlEligibilityRule;
import utils.parsers.CustomJsonParser;
import utils.spanner.DBClient;

import java.util.ArrayList;
import java.util.List;

public class ZolaStoreLocationTest extends ZolaBaseTest {

    @Test(dataProviderClass = ZolaStoreLocationsDP.class, dataProvider = "zola1")
    public void cebInsert(String cebValues, String cebSnapshotValues, List<String> rules) throws IllegalAccessException {

        /*
        todo - extend verifications
        todo - add check in MktngPrtr table if upc is enabled.
        todo - for future - print list of upcs which in use by automation
        */

        //prepare fields for given tables
        CurrEntBal currEntBal = new CurrEntBal(cebValues);
        CurrEntBalSnapshot currEntBalSnapshot = new CurrEntBalSnapshot(cebSnapshotValues);
        List<SellChnlEligibilityRule> listOfRules = new ArrayList<>();
        rules.forEach(rule -> listOfRules.add(new SellChnlEligibilityRule(rule)));

        //Delete all the required data
        executeBatchDml(getDeleteStatements(currEntBalSnapshot.skuUpcNbr));
        executeBatchDml(getDeleteStatementsATP(currEntBalSnapshot.skuUpcNbr), DBClient.getATPInstance());

        //Building jsonMessage according to inserted data
        JSONObject enrichSupplyDemandMessage = CustomJsonParser.getUpdatedMessageForEnrichSupplyDemandTopicCEBSnapshotFromPojo(currEntBalSnapshot);

        //insert rules
        listOfRules.forEach(SellChnlEligibilityRule::insert);

        //Insert CEB data
        currEntBal.insert();
        currEntBalSnapshot.insert();

        //Trigger cebInsertEvent by sending message to the topic
        sendInsertPubSubMessage(enrichSupplyDemandMessage, ENRICH_SUPPLY_DEMAND_TOPIC_NAME);

        TableAssertions.verifyAECTable(currEntBalSnapshot);

    }

    private ArrayList<Statement> getDeleteStatements(long skuUpc) {
        //DELETE
        ArrayList<Statement> removeQueriesList = new ArrayList<>();
        removeQueriesList.add(Statement.of("delete from CurrEntBal WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from CurrEntBalsnapshot WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from SellChnlEligibilityRule WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from PriceStatusByItemLocation WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from LocationAvailability WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from AvailabilityPush WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from NetworkAvailability WHERE SkuUpcNbr in (" + skuUpc + ")"));
        return removeQueriesList;
    }

    private ArrayList<Statement> getDeleteStatementsATP(long skuUpc) {
        //DELETE
        ArrayList<Statement> removeQueriesList = new ArrayList<>();
        removeQueriesList.add(Statement.of("delete from AvailabilitySupply WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from AvailabilityDemand WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from AvailabilityThreshold WHERE SkuUpcNbr in (" + skuUpc + ")"));
        removeQueriesList.add(Statement.of("delete from AvailabilityEligibilityByChannel WHERE SkuUpcNbr in (" + skuUpc + ")"));
        return removeQueriesList;
    }


}
