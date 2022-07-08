package assertions;

import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Struct;
import pojo.CurrEntBalSnapshot;
import utils.spanner.DBClient;
import utils.spanner.DBClientHelper;
import org.testng.Assert;
import pojo.AvailabilityEligibilityByChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableAssertions {


    public static void verifyAECTable(CurrEntBalSnapshot cebSnapshot) {

        ArrayList<Struct> queryResponse = sendRequestAndGetResults();

        Assert.assertNotEquals(queryResponse.size(), 0);

        List<AvailabilityEligibilityByChannel> aecTableRecords = new ArrayList<>();
        for (Struct line : queryResponse){
            aecTableRecords.add(
                    new AvailabilityEligibilityByChannel(
                            line.toString()
                    )
            );
        }
        AvailabilityEligibilityByChannel sthMcomMcomFalse = aecTableRecords.stream().filter(aecRecord ->
            aecRecord.ruleTyp.equals("shipToHome") && aecRecord.dmdCh.equals("MCOM") && aecRecord.dmdSubCh.equals("MCOM")
        ).collect(Collectors.toList()).get(0);

        AvailabilityEligibilityByChannel sthMcomZolaTrue = aecTableRecords.stream().filter(aecRecord ->
                aecRecord.ruleTyp.equals("shipToHome") && aecRecord.dmdCh.equals("MCOM") && aecRecord.dmdSubCh.equals("ZOLA")
        ).collect(Collectors.toList()).get(0);

        AvailabilityEligibilityByChannel stsMcomMcomTrue = aecTableRecords.stream().filter(aecRecord ->
                aecRecord.ruleTyp.equals("shipToStore") && aecRecord.dmdCh.equals("MCOM") && aecRecord.dmdSubCh.equals("MCOM")
        ).collect(Collectors.toList()).get(0);

        AvailabilityEligibilityByChannel sthMcomZolaFalse = aecTableRecords.stream().filter(aecRecord ->
                aecRecord.ruleTyp.equals("shipToStore") && aecRecord.dmdCh.equals("MCOM") && aecRecord.dmdSubCh.equals("ZOLA")
        ).collect(Collectors.toList()).get(0);

        Assert.assertTrue(sthMcomZolaTrue.ruleElg);
        Assert.assertTrue(stsMcomMcomTrue.ruleElg);

        Assert.assertFalse(sthMcomMcomFalse.ruleElg);
        Assert.assertEquals(sthMcomMcomFalse.reasonCd, "6070");

        Assert.assertFalse(sthMcomZolaFalse.ruleElg);
        Assert.assertEquals(sthMcomZolaFalse.reasonCd, "7070");

    }

        //TODO: parameterise timings and request
    private static ArrayList<Struct> sendRequestAndGetResults() {
        ResultSet rs = DBClientHelper.singleUseQuery("SELECT * FROM AvailabilityEligibilityByChannel WHERE SkuUpcNbr =772004886", DBClient.getATPInstance());
        int counter = 10;
        while (!rs.next()) {
            counter--;
            if (counter == 0) break;
            try {
                Thread.sleep(60000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rs = DBClientHelper.singleUseQuery("SELECT * FROM AvailabilityEligibilityByChannel WHERE SkuUpcNbr =772004886", DBClient.getATPInstance());

        }
        ArrayList<Struct> rows = new ArrayList<>();
        while (rs.next()) {
            rows.add(rs.getCurrentRowAsStruct());
        }
        return rows;
    }

}
