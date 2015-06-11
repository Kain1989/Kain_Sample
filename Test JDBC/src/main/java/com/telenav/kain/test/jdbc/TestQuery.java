package com.telenav.kain.test.jdbc;

import org.apache.commons.lang.ArrayUtils;

import java.sql.*;

/**
 * Created by zfshi on 6/8/2015.
 */
public class TestQuery {

    public static void main(String[] args) {
        try {
            String sql = "select * from ford_geocoder_cn_14q1_v3.CHN_addr_compact limit 100";

            Connection conn = DbConnectionUtils.getDbConnection("chn");
            PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setFetchSize(100);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Array doorRange = rs.getArray("door_range");
                System.out.println("Door range :" + doorRange);
                String[] doorRangeStrArray = (String[]) doorRange.getArray();
                if(ArrayUtils.isNotEmpty(doorRangeStrArray)) {
                    System.out.println("=============================" + doorRangeStrArray);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
