
package com.telenav.cloud.search.autonavi.entity.poi_generate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Road {

    @Expose
    private String width;
    @Expose
    private String citycode;
    @Expose
    private String level;
    @Expose
    private String id;
    @Expose
    private String name;
    @SerializedName("__text")
    @Expose
    private String Text;

    /**
     * 
     * @return
     *     The width
     */
    public String getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(String width) {
        this.width = width;
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
     *     The level
     */
    public String getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
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
     *     The Text
     */
    public String getText() {
        return Text;
    }

    /**
     * 
     * @param Text
     *     The __text
     */
    public void setText(String Text) {
        this.Text = Text;
    }

}
