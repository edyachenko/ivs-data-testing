package pojo;


import utils.pojo.PojoHelper;
import com.google.cloud.Timestamp;

public class AvailabilityEligibilityByChannel extends PojoHelper {

    public long divnNbr;
    public long skuUpcNbr;
    public long locNbr;
    public String ruleTyp;
    public String dmdCh;
    public String dmdSubCh;
    public String correltnId;
    public String fullTyp;
    public String reasonCd;
    public boolean ruleElg;
    public String sysEvntTyp;
    public Timestamp sysLstUpdTs;
    public String sysLstUpdUser;
    public Timestamp sysSrcLstUpdTs;
    public String sysSrcLstUpdUser;
    public boolean dedfulElg;
    public String ruleElgFlags;

    public AvailabilityEligibilityByChannel(String cebValues) {
        this.updateFields(cebValues);

    }

    private void updateFields(String values) {
        updateFields(values, AVAILABILITY_ELIGIBILITY_BY_CHANNEL_FIELDS_SPANNER, this.getClass().getDeclaredFields(),this, true);
    }

}
