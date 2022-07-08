package utils.pojo;

import com.google.cloud.Timestamp;
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.Mutation;
import com.google.cloud.Date;
import utils.parsers.DateTimeParser;
import utils.parsers.SqlParser;
import utils.spanner.DBClient;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static utils.parsers.SqlParser.getMapFromConcatenatedStrings;


public class PojoHelper {

    public static final String CURR_ENT_BAL_FIELDS_SPANNER = "ClrNbr, CndtnNbr, DeptNbr, DivnNbr, LocDimId, LocNbr, Mkst, SkuUpcNbr, SzNbr, VndNumDesc, AcctOnHand, AvailOnOrd, AvailToPick, AvailToSell, BoxedForRoute, CrtDt, DmgOnHand, FltSmplOnHand, InRteAvail, InRteNonAvail, LocTypCd, LstUpdTs, LstUpdUserId, NotInLoc, OutRteAvail, OutRteNonAvail, OverShortInv, PhysOnHand, PickInPrgrss, PresaleQty, ProdDimId, RefurbPutAway, ResvInStkBk, ResvInStkNbk, ResvOnOrderBk, ResvOnOrderNBk, VndStkAvail, ZlDivnNbr";
    public static final String CURR_ENT_BAL_SNAPSHOT_FIELDS_SPANNER = "DivnNbr, DeptNbr, VndNumDesc, Mkst, ClrNbr, SzNbr, SkuUpcNbr, LocNbr, CndtnNbr, CrtDt, ProdDimId, LocDimId, ZlDivnNbr, LocTypCd, PhysOnHand, AcctOnHand, AvailToSell, AvailToPick, PickInPrgrss, ResvInStkNbk, ResvInStkBk, ResvOnOrderNBk, ResvOnOrderBk, VndStkAvail, AvailOnOrd, NotInLoc, OverShortInv, OutRteAvail, InRteAvail, OutRteNonAvail, InRteNonAvail, FltSmplOnHand, DmgOnHand, RefurbPutAway, BoxedForRoute, LstUpdUserId, LstUpdTs, PresaleQty, ShardId, Uuid, SpannerCommitTS, OperationType, TxnID, FileName, FileOffset, GGTS";
    public static final String SELL_CHNL_ELIGIBILITY_RULE_FIELDS_SPANNER = "DeptNbr, DivnNbr, EffDt, FulfilLocTypVal, LocNbr, PrdGrpCd, RuleTypNbr, SellChnl, SkuUpcNbr, SubChnl, VndNbr, DemandPrcStatOvrrdFlg, DropMrgnOvrrdFlg, EliCorrltnId, ExpDt, FulPrcStat, FulPrcStatOvrrdFlg, Priority, RuleTypVal, SysEvntTyp, SysLstUpdTs, SysLstUpdUser, SysSrcLstUpdTs, SysSrcLstUpdUser, UploadTyp";
    public static final String AVAILABILITY_ELIGIBILITY_BY_CHANNEL_FIELDS_SPANNER = "DivnNbr, SkuUpcNbr, LocNbr, RuleTyp, DmdCh, DmdSubCh, CorreltnId, FullTyp, ReasonCd, RuleElg, SysEvntTyp, SysLstUpdTs, SysLstUpdUser, SysSrcLstUpdTs, SysSrcLstUpdUser, DedfulElg, RuleElgFlags";

    public static final Timestamp staticTimestamp = DateTimeParser.getCurrentTime();
    public static final Date staticDate = DateTimeParser.getCurrentDate();
    public static DatabaseClient instance = DBClient.getInstance();

    /**
     *
     * @param pojo - pojo with data to insert
     * @param tableName - table to insert the data from pojo
     * @param <T> - extension for POJO class
     */
    public static <T extends PojoHelper> void insertData(T pojo, String tableName) {
        Mutation.WriteBuilder insertBuilder = SqlParser.getInsertMutationBuilder(pojo, tableName);
        Mutation build = insertBuilder.build();
        instance.write(Collections.singleton(build));
    }

    /**
     * Method updates all fields through the reflection.
     * !Do not delete or add any new fields into POJO class after its generation. It should contain only those fields which are
     * in use by the given table
     *
     * @param values - input values from test
     * @param tableColumnNames - name of each column on for the needed table
     * @param fields - field names of certain POJO class
     * @param pojo - POJO class which requires update
     * @param containsJson - option which defines whether @values contain json or not, to let the parser correctly parse data
     * @param <T> - extension for POJO class
     */
    protected <T extends PojoHelper> void updateFields(String values, String tableColumnNames, Field[] fields, T pojo, boolean containsJson) {
        Map<String, String> map = getMapFromConcatenatedStrings(tableColumnNames, values, containsJson);
        updateFieldsThroughReflection(fields, map, pojo);

    }

    protected <T extends PojoHelper> void updateFields(String values, String tableColumnNames, Field[] fields, T pojo) {
        this.updateFields(values, tableColumnNames, fields, pojo, false);
    }

    /**
     * This class gets names of fields to be updated, values for that fields, entity of pojo class and then updates
     * this pojo with given values.
     * @param parameters - Fields from POJO class
     * @param map - map with table {name : vale}
     * @param pojo - pojo class which require update
     * @param <T> - extension for POJO Classes only
     */
    protected <T extends PojoHelper> void updateFieldsThroughReflection(Field[] parameters, Map<String, String> map, T pojo) {

        /*
        field name starts with lowerCase letter, but input data will contain UpperCase the first letter.
        That's why we need tp make the same for both sides.
        So then reflection will take all the required fields
         */
        Map<String, String> withLowerCases = new HashMap<>();
        map.forEach((key, value) -> withLowerCases.put(key.toLowerCase(), value));


        for (Field field : parameters) {
            try {
                switch (field.getType().getName().toLowerCase()) {
                    case "long":
                        String s = withLowerCases.get(field.getName().toLowerCase());
                        field.set(pojo, Long.parseLong(checkFieldOnNull(s)));
                        break;
                    case "com.google.cloud.timestamp":
                        field.set(pojo, DateTimeParser.getCurrentTime());
                        break;
                    case "java.sql.date":
                        field.set(pojo, DateTimeParser.getCurrentDate());
                        break;
                    case "boolean":
                        String s2 = withLowerCases.get(field.getName().toLowerCase());
                        field.set(pojo, Boolean.parseBoolean(s2));
                        break;
                    case "java.lang.string":
                        String s3 = withLowerCases.get(field.getName().toLowerCase());
                        field.set(pojo, s3);
                        break;
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String checkFieldOnNull(String line) {

        if (line.equalsIgnoreCase("null")) {
            line = "0"; // zero can be parsed as boolean or long
        }
        return line;
    }

}
