package pojo;


import com.google.cloud.Timestamp;
import utils.pojo.PojoHelper;

import com.google.cloud.Date;

import static utils.parsers.DateTimeParser.getDateMinusMonth;

public class CurrEntBal extends PojoHelper {

    public long clrNbr;
    public long cndtnNbr;
    public long deptNbr;
    public long divnNbr;
    public long locDimId;
    public long locNbr;
    public long mkst;
    public long skuUpcNbr;
    public long szNbr;
    public long vndNumDesc;
    public long acctOnHand;
    public long availOnOrd;
    public long availToPick;
    public long availToSell;
    public long boxedForRoute;
    public Date crtDt;
    public long dmgOnHand;
    public long fltSmplOnHand;
    public long inRteAvail;
    public long inRteNonAvail;
    public String locTypCd;
    public Timestamp lstUpdTs;
    public String lstUpdUserId;
    public long notInLoc;
    public long outRteAvail;
    public long outRteNonAvail;
    public long overShortInv;
    public long physOnHand;
    public long pickInPrgrss;
    public long presaleQty;
    public long prodDimId;
    public long refurbPutAway;
    public long resvInStkBk;
    public long resvInStkNbk;
    public long resvOnOrderBk;
    public long resvOnOrderNBk;
    public long vndStkAvail;
    public long zlDivnNbr;

    public CurrEntBal(String cebValues) {
        this.updateFields(cebValues);

    }

    public void insert() throws IllegalAccessException {
        insertData(this, this.getClass().getSimpleName());
    }

    private void updateFields(String values) {
        updateFields(values, CURR_ENT_BAL_FIELDS_SPANNER, this.getClass().getDeclaredFields(),this);
        //to be sure that current date and all the timestamps are the same for the given entity
        this.crtDt = getDateMinusMonth();;
        this.lstUpdTs = staticTimestamp;
    }

}
