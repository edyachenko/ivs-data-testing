package pojo;


import com.google.cloud.Timestamp;
import utils.pojo.PojoHelper;

import com.google.cloud.Date;

import static utils.parsers.DateTimeParser.getDateMinusMonth;

public class CurrEntBalSnapshot extends PojoHelper{

    public long divnNbr;
    public long deptNbr;
    public long vndNumDesc;
    public long mkst;
    public long clrNbr;
    public long szNbr;
    public long skuUpcNbr;
    public long locNbr;
    public long cndtnNbr;
    public Date crtDt;
    public long prodDimId;
    public long locDimId;
    public long zlDivnNbr;
    public String locTypCd;
    public long physOnHand;
    public long acctOnHand;
    public long availToSell;
    public long availToPick;
    public long pickInPrgrss;
    public long resvInStkNbk;
    public long resvInStkBk;
    public long resvOnOrderNBk;
    public long resvOnOrderBk;
    public long vndStkAvail;
    public long availOnOrd;
    public long notInLoc;
    public long overShortInv;
    public long outRteAvail;
    public long inRteAvail;
    public long outRteNonAvail;
    public long inRteNonAvail;
    public long fltSmplOnHand;
    public long dmgOnHand;
    public long refurbPutAway;
    public long boxedForRoute;
    public String lstUpdUserId;
    public Timestamp lstUpdTs;
    public long presaleQty;
    public long shardId;
    public String uuid;
    public Timestamp spannerCommitTs;
    public String operationType;
    public String txnId;
    public String fileName;
    public String fileOffset;
    public Timestamp ggts;

    public CurrEntBalSnapshot(String cebSnapshotValues) {
        this.updateFields(cebSnapshotValues);

    }

    public void insert() throws IllegalAccessException {
        insertData(this, this.getClass().getSimpleName());
    }

    /**
     * Method updates all fields through the reflection.
     * !!Do not delete or add any new fields into POJO class. It should contain only those fields which are
     * in use by the given table
     * @param values - input parameter for further parsing
     */

    private void updateFields(String values) {
        updateFields(values, CURR_ENT_BAL_SNAPSHOT_FIELDS_SPANNER, this.getClass().getDeclaredFields(),this);
        //to be sure that current date and all the timestamps are the same for the given entity
        this.crtDt = getDateMinusMonth();;
        this.lstUpdTs = staticTimestamp;
        this.spannerCommitTs = staticTimestamp;
        this.ggts = staticTimestamp;
    }

}
