
package com.telenav.cloud.search.autonavi.entity.poi_generate;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class CrossList {

    @Expose
    private List<Cros> cross = new ArrayList<Cros>();

    /**
     * 
     * @return
     *     The cross
     */
    public List<Cros> getCross() {
        return cross;
    }

    /**
     * 
     * @param cross
     *     The cross
     */
    public void setCross(List<Cros> cross) {
        this.cross = cross;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cross).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CrossList) == false) {
            return false;
        }
        CrossList rhs = ((CrossList) other);
        return new EqualsBuilder().append(cross, rhs.cross).isEquals();
    }

}
