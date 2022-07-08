package pojo;


import com.google.cloud.Timestamp;
import com.google.cloud.Date;
import utils.pojo.PojoHelper;

import static utils.parsers.DateTimeParser.*;

public class SellChnlEligibilityRule extends PojoHelper {

  public long deptNbr;
  public long divnNbr;
  public Date effDt;
  public long fulfilLocTypVal;
  public long locNbr;
  public String prdGrpCd;
  public long ruleTypNbr;
  public String sellChnl;
  public long skuUpcNbr;
  public String subChnl;
  public long vndNbr;
  public boolean demandPrcStatOvrrdFlg;
  public boolean dropMrgnOvrrdFlg;
  public String eliCorrltnId;
  public Date expDt;
  public long fulPrcStat;
  public boolean fulPrcStatOvrrdFlg;
  public long priority;
  public boolean ruleTypVal;
  public String sysEvntTyp;
  public Timestamp sysLstUpdTs;
  public String sysLstUpdUser;
  public Timestamp sysSrcLstUpdTs;
  public String sysSrcLstUpdUser;
  public long uploadTyp;

  public SellChnlEligibilityRule(String values){
    this.updateFields(values);
  }

  public void insert() {
    insertData(this, this.getClass().getSimpleName());
  }

  private void updateFields(String values) {
    updateFields(values, SELL_CHNL_ELIGIBILITY_RULE_FIELDS_SPANNER, this.getClass().getDeclaredFields(),this);
    this.sysSrcLstUpdTs = getYesterdayTime();
    this.sysLstUpdTs = getYesterdayTime();
    this.expDt = getDatePlusMonth();
    this.effDt = getDateMinusMonth();

  }

}
