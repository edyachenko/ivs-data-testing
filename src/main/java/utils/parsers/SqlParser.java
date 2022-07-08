package utils.parsers;

import com.google.cloud.Date;
import com.google.cloud.Timestamp;
import com.google.cloud.spanner.Mutation;
import utils.pojo.PojoHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pojo.CurrEntBal;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser {

    private static final Logger logger = LogManager.getLogger(DateTimeParser.class);

    /**
     * Method builds Mutation.Builder object, it requires a Map fulfilled with keys and values.
     * Values should be converted to the required types but passed here as Objects.
     * Method will define which type of object to apply for the certain field.
     *
     * @param pojo      - filled Pojo class with values.
     * @param tableName - table Name where to make operation
     * @param <T>       - POJO class (e.g. object representation of Table)
     * @return Mutation.Builder object which is ready for insertion into spanner.
     */
    public static <T extends PojoHelper> Mutation.WriteBuilder getInsertMutationBuilder(T pojo, String tableName) {
        Mutation.WriteBuilder insertBuilder = Mutation.newInsertBuilder(tableName);

        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field field : fields) {

            Object vl = null;
            try {
                vl = field.get(pojo);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (vl instanceof Long) {
                insertBuilder.set(field.getName()).to((Long) vl);
            } else if (vl instanceof Date) {
                insertBuilder.set(field.getName()).to((Date) vl);
            } else if (vl instanceof Timestamp) {
                insertBuilder.set(field.getName()).to((Timestamp) vl);
            } else if (vl instanceof String) {
                insertBuilder.set(field.getName()).to((String) vl);
            } else if (vl instanceof Boolean) {
                insertBuilder.set(field.getName()).to((Boolean) vl);
            }

        }
        return insertBuilder;
    }


    private static String getUpdatedStringIfItContainsJson(String valuesWithJson){

        String pattern = "(\\{.*})";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(valuesWithJson);
        String editLine = "";
        if (m.find()) {
            editLine =m.group(0).replaceAll(",", ";");
            return valuesWithJson.replaceAll(pattern, editLine);
        }else{
            return valuesWithJson;
        }

    }

    /**
     * This method maps the line with Field Names (sequence from spanner table)
     * with values (sequence is also matters)
     * returns the Map <String, String>
     *
     * @param fieldNamesInTable  - line with filed names can be copied from DBeaver, must be comma-separated
     * @param fieldValuesInTable - line with values names can be copied from DBeaver, must be comma-separated
     * @return a map as a representation of table and fields (all data is type of String)
     */

    public static HashMap<String, String> getMapFromConcatenatedStrings(String fieldNamesInTable, String fieldValuesInTable, Boolean jsonInString ) {

        if(fieldValuesInTable.startsWith("[")){
            fieldValuesInTable = fieldValuesInTable.substring(1, fieldValuesInTable.length()-1);
        }

        //checks
        fieldValuesInTable = jsonInString ? getUpdatedStringIfItContainsJson(fieldValuesInTable) : fieldValuesInTable;

        String[] fieldNamesArray = fieldNamesInTable.split(",");
        String[] fieldValuesArray = fieldValuesInTable.split(",");
        HashMap<String, String> map = new HashMap<>();

        try {
            if (fieldNamesArray.length == fieldValuesArray.length) {
                for (int i = 0; i < fieldNamesArray.length; i++) {
                    String key = String.valueOf(fieldNamesArray[i]).trim();
                    String value = String.valueOf(fieldValuesArray[i]).trim();

                    if (value.startsWith("'") && value.endsWith("'")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    map.put(key, value);
                }
            } else {
                throw new Exception("Numbers of table fields does not correspond to number of values");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Number of fields in " + CurrEntBal.class.getName() + " table does not correspond to" +
                    " number of given values");
        }
        return map;
    }
    }
