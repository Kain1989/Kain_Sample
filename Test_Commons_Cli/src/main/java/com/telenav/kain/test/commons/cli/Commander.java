package com.telenav.kain.test.commons.cli;

//import com.telenav.search.geocoding.db.configuration.DBConfiguration;
import org.apache.commons.lang.StringUtils;

/**
 * Created by xingfeiy on 5/20/15.
 */
public class Commander {

    private String pbfURL = StringUtils.EMPTY;

    private String unidbSchema = StringUtils.EMPTY;

    private String tngeoSchema = StringUtils.EMPTY;

    private String previousTnGeoSchema = StringUtils.EMPTY;

    private String countries = StringUtils.EMPTY;

    private boolean preProcessUniDB = true;

    private boolean processPBF = true;

    public boolean isProcessPBF() {
        return processPBF;
    }

    public void setProcessPBF(boolean processPBF) {
        this.processPBF = processPBF;
    }

    private static Commander instance = null;

    private Commander() {}

    public static Commander getInstance() {
        if(instance == null) {
            instance = new Commander();
        }
        return instance;
    }

    public String getPbfURL() {
//        if(StringUtils.isBlank(pbfURL)) {
//            return DBConfiguration.getInstance().getPBFURL();
//        }
        return pbfURL;
    }

    public void setPbfURL(String pbfURL) {
        this.pbfURL = pbfURL;
    }

    public String getUnidbSchema() {
//        if(StringUtils.isBlank(unidbSchema)) {
//            return DBConfiguration.getInstance().getUniDBCurrentSchema();
//        }
        return unidbSchema;
    }

    public void setUnidbSchema(String unidbSchema) {
        this.unidbSchema = unidbSchema;
    }

    public String getTngeoSchema() {
//        if(StringUtils.isBlank(tngeoSchema)) {
//            return DBConfiguration.getInstance().getGeoCoderCurrentSchema();
//        }
        return tngeoSchema;
    }

    public void setTngeoSchema(String tngeoSchema) {
        this.tngeoSchema = tngeoSchema;
    }

    public String getPreviousTnGeoSchema() {
        return previousTnGeoSchema;
    }

    public void setPreviousTnGeoSchema(String previousTnGeoSchema) {
        this.previousTnGeoSchema = previousTnGeoSchema;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public boolean isPreProcessUniDB() {
        return preProcessUniDB;
    }

    public void setPreProcessUniDB(boolean preProcessUniDB) {
        this.preProcessUniDB = preProcessUniDB;
    }
}
