
package com.telenav.cloud.search.entity.poi_generate;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AutonaviResponse {

    @Expose
    private Ats ats;

    /**
     * 
     * @return
     *     The ats
     */
    public Ats getAts() {
        return ats;
    }

    /**
     * 
     * @param ats
     *     The ats
     */
    public void setAts(Ats ats) {
        this.ats = ats;
    }

}
