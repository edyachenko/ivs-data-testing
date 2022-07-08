package utils.parsers;

import config.CustomConfig;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pojo.CurrEntBalSnapshot;

import java.io.FileReader;
import java.io.IOException;


/**
 * Class contains method which will update messages according to its content, but with given values
 */
public class CustomJsonParser {

    static final JSONParser parser = new JSONParser();
    private static final String ENRICH_SUPPLY_DEMAND_JSON = CustomConfig.getPropertyInstance().getProperty("jsonFile.enrichSupplyDemand");
    private static final String CALCULATE_ELIGIBILITY_JSON = CustomConfig.getPropertyInstance().getProperty("jsonFile.calculateEligibility");


    /**
     * Method gets prepared sample of json and updates it according testing purposes
     * @return JSONObject
     */
    public static JSONObject getUpdatedCalculateEligibilityJson() {
        Object jsonParser = null;
        try {
            jsonParser = parser.parse(new FileReader(CALCULATE_ELIGIBILITY_JSON));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) jsonParser;
        jsonObject.put("correlationId", "Autotest" + System.currentTimeMillis());

        return jsonObject;

    }

    /**
     * Method gets prepared sample of json and updates it according testing purposes
     * @param currEntBalSnapshot - takes values from this POJO class and updates json with it.
     * @return JSONObject
     */
    public static JSONObject getUpdatedMessageForEnrichSupplyDemandTopicCEBSnapshotFromPojo(CurrEntBalSnapshot currEntBalSnapshot) {
        Object jsonParser = null;
        try {
            jsonParser = parser.parse(new FileReader(ENRICH_SUPPLY_DEMAND_JSON));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) jsonParser;
        JSONObject dataJson = getUpdatedDataForEnrichSupplyDemandTopic(jsonObject, currEntBalSnapshot);
        JSONObject metaDataJson = getUpdatedMetaDataForEnrichSupplyDemandTopic(jsonObject, currEntBalSnapshot);
        jsonObject.put("data", dataJson);
        jsonObject.put("metadata", metaDataJson);
        jsonObject.put("correlationId", "Autotest" + System.currentTimeMillis());


        return jsonObject;
    }

    private static JSONObject getUpdatedMetaDataForEnrichSupplyDemandTopic(JSONObject jsObject,
                                                                           CurrEntBalSnapshot currEntBalSnapshot) {
        JSONObject metadata = (JSONObject) jsObject.get("metadata");
        metadata.put("TimeStamp", currEntBalSnapshot.lstUpdTs.toString());
        metadata.put("OperationName", "INSERT");
        return metadata;
    }

    private static JSONObject getUpdatedDataForEnrichSupplyDemandTopic(JSONObject jsObject,
                                                                       CurrEntBalSnapshot currEntBalSnapshot) {
        JSONObject dataJson = (JSONObject) jsObject.get("data");
        dataJson.put("DIVN_NBR", currEntBalSnapshot.divnNbr);
        dataJson.put("DEPT_NBR", currEntBalSnapshot.deptNbr);
        dataJson.put("VND_NUMERIC_DESC", currEntBalSnapshot.vndNumDesc);
        dataJson.put("MKST", currEntBalSnapshot.mkst);
        dataJson.put("CLR_NBR", currEntBalSnapshot.clrNbr);
        dataJson.put("SZ_NBR", currEntBalSnapshot.szNbr);
        dataJson.put("SKU_UPC_NBR", currEntBalSnapshot.skuUpcNbr);
        dataJson.put("LOC_NBR", currEntBalSnapshot.locNbr);
        dataJson.put("CNDTN_NBR", currEntBalSnapshot.cndtnNbr);
        dataJson.put("CREATE_DATE", currEntBalSnapshot.crtDt.toString());
        dataJson.put("PROD_DIM_ID", currEntBalSnapshot.prodDimId);
        dataJson.put("LOC_DIM_ID", currEntBalSnapshot.locDimId);
        dataJson.put("ZL_DIVN_NBR", currEntBalSnapshot.zlDivnNbr);
        dataJson.put("LOCN_TYP_CODE", currEntBalSnapshot.locTypCd);
        dataJson.put("PHYS_ON_HAND", currEntBalSnapshot.physOnHand);
        dataJson.put("ACCT_ON_HAND", currEntBalSnapshot.acctOnHand);
        dataJson.put("AVAIL_TO_SELL", currEntBalSnapshot.availToSell);
        dataJson.put("AVAIL_TO_PICK", currEntBalSnapshot.availToPick);
        dataJson.put("PICK_IN_PRGRSS", currEntBalSnapshot.pickInPrgrss);
        dataJson.put("RESV_IN_STOCK_NBK", currEntBalSnapshot.resvInStkNbk);
        dataJson.put("RESV_IN_STOCK_BK", currEntBalSnapshot.resvInStkBk);
        dataJson.put("RESV_ON_ORDR_NBK", currEntBalSnapshot.resvOnOrderNBk);
        dataJson.put("RESV_ON_ORDR_BK", currEntBalSnapshot.resvOnOrderBk);
        dataJson.put("VND_STOCK_AVAIL", currEntBalSnapshot.vndStkAvail);
        dataJson.put("AVAIL_ON_ORDR", currEntBalSnapshot.availOnOrd);
        dataJson.put("NOT_IN_LOC", currEntBalSnapshot.notInLoc);
        dataJson.put("OVER_SHORT_INV", currEntBalSnapshot.overShortInv);
        dataJson.put("OUT_RTE_AVAIL", currEntBalSnapshot.outRteAvail);
        dataJson.put("IN_RTE_AVAIL", currEntBalSnapshot.inRteAvail);
        dataJson.put("OUT_RTE_NON_AVAIL", currEntBalSnapshot.outRteNonAvail);
        dataJson.put("IN_RTE_NON_AVAIL", currEntBalSnapshot.inRteNonAvail);
        dataJson.put("FLR_SMPL_ON_HAND", currEntBalSnapshot.fltSmplOnHand);
        dataJson.put("DMG_ON_HAND", currEntBalSnapshot.dmgOnHand);
        dataJson.put("REFURB_PUT_AWAY", currEntBalSnapshot.refurbPutAway);
        dataJson.put("BOXED_FOR_ROUTE", currEntBalSnapshot.boxedForRoute);
        dataJson.put("LAST_UPD_USERID", currEntBalSnapshot.lstUpdUserId);
        dataJson.put("LAST_UPD_TS", currEntBalSnapshot.lstUpdTs.toString());
        dataJson.put("PRESALE_QTY", currEntBalSnapshot.presaleQty);

        return dataJson;
    }

}
