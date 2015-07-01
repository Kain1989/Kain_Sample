
package com.telenav.cloud.search.entity.poi_generate;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Item {

    @Expose
    private String adcode;
    @Expose
    private String citycode;
    @Expose
    private String name;
    @Expose
    private String ename;
    @Expose
    private String total;

    /**
     * 
     * @return
     *     The adcode
     */
    public String getAdcode() {
        return adcode;
    }

    /**
     * 
     * @param adcode
     *     The adcode
     */
    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    /**
     * 
     * @return
     *     The citycode
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * 
     * @param citycode
     *     The citycode
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The ename
     */
    public String getEname() {
        return ename;
    }

    /**
     * 
     * @param ename
     *     The ename
     */
    public void setEname(String ename) {
        this.ename = ename;
    }

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

}
