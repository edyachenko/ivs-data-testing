package dataproviders;

import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.List;

public class ZolaStoreLocationsDP {

    @DataProvider(name = "zola1")
    public Object[][] zola1() {

        String curEntBalStringCase1 = "0, 0, 860, 12, 576, 9, 482, 772004886, 0, 801, 0, 0, 0, 0, 0, '2022-05-24', 0, 0, 0, 0, 'STR', '2022-05-24 05:10:44.144', 'evgauto ', 0, 0, 0, 0, 30, 0, 0, 40903001749464612, 0, 0, 0, 0, 0, 0, 72";
        String curEntBalStringSnapshotCase1 = "12, 860, 801, 482, 0, 0, 772004886, 5042, 0, '2022-05-26', 0, 8373, 71, 'STR', 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'evgauto', '2022-05-30T13:30:46.416000000Z', 0, 6, '96f5cb7b-539f-4252-b7b7-1de9b74eeb4c', '2022-05-30T13:30:46.416000000Z', 'INSERT', 'autZola', 'Au', '100', '2022-05-30T13:30:46.416000000Z'";
        String shipToHomeZolaTrue = "860, 12, '2022-03-28', 4, 0, 'TOYS ', 3, 'MCOM', 772004886, 'ZOLA', 12, false, false, 'pg000000331-21041954-1:00000000040370AF96C2', NULL, NULL, false, 2, true, 'fulfillment', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', 6";
        String shipToHomeMcomFalse = "860, 12, '2022-03-28', 4, 0, 'TOYS ', 3, 'MCOM', 772004886, 'MCOM', 12, false, false, 'pg000000331-21041954-1:00000000040370AF96C2', NULL, NULL, false, 2, false, 'fulfillment', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', 6";
        String shipToStoreZolaFalse = "860, 12, '2022-03-28', 4, 0, 'TOYS ', 4, 'MCOM', 772004886, 'ZOLA', 12, false, false, 'pg000000331-21041954-1:00000000040370AF96C2', NULL, NULL, false, 2, false, 'fulfillment', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', 6";
        String shipToStoreMcomTrue = "860, 12, '2022-03-28', 4, 0, 'TOYS ', 4, 'MCOM', 772004886, 'MCOM', 12, false, false, 'pg000000331-21041954-1:00000000040370AF96C2', NULL, NULL, false, 2, true, 'fulfillment', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', '2022-05-30T16:58:52.297000000Z', 'eligibility-merge-pipeline', 6";
        List<String> rules = Arrays.asList(shipToHomeZolaTrue, shipToStoreZolaFalse, shipToHomeMcomFalse, shipToStoreMcomTrue);

        return new Object[][]{{
            curEntBalStringCase1, curEntBalStringSnapshotCase1, rules}};
    }


}
