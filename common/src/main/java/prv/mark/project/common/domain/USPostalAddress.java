package prv.mark.project.common.domain;

import prv.mark.project.common.validation.USState;
import prv.mark.project.common.validation.Zip4;
import prv.mark.project.common.validation.ZipCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Domain object which conforms to USPS street address standard.
 *
 * @author
 */
public class USPostalAddress implements Serializable {
    @Size(min = 0, max = 2, message = "Pre-Directional must be between {min} and {max} characters.")
    private String preDirectional;
    @Size(min = 0, max = 10, message = "Primary Range must be between {min} and {max} characters.")
    private String primaryRange;
    @Size(min = 0, max = 29, message = "Primary Name must be between {min} and {max} characters.")
    private String primaryName;
    @Size(min = 0, max = 4, message = "Suffix must be between 0 and 4 characters.")
    private String suffix;
    @Size(min = 0, max = 2, message = "Post-Directional must be between {min} and {max} characters.")
    private String postDirectional;

    private List<String> unitDesignator;

    @Size(min = 0, max = 8, message = "Secondary Range must be between {min} and {max} characters.")
    private String secondaryRange;
    @Size(min = 0, max = 29, message = "Secondary Name must be between {min} and {max} characters.")
    private String secondaryName;
    @NotEmpty(message = "City is required.")
    private String city;
    @USState
    private String state;
    @ZipCode
    private String zipCode;
    @Zip4
    private String zip4;
    private String addressLine1;
    private String addressLine2;

    public String getPreDirectional() {
        return preDirectional;
    }

    public void setPreDirectional(String preDirectional) {
        this.preDirectional = preDirectional;
    }

    public String getPrimaryRange() {
        return primaryRange;
    }

    public void setPrimaryRange(String primaryRange) {
        this.primaryRange = primaryRange;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPostDirectional() {
        return postDirectional;
    }

    public void setPostDirectional(String postDirectional) {
        this.postDirectional = postDirectional;
    }

    public List<String> getUnitDesignator() {
        return unitDesignator;
    }

    public void setUnitDesignator(List<String> unitDesignator) {
        this.unitDesignator = unitDesignator;
    }

    public String getSecondaryRange() {
        return secondaryRange;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public void setSecondaryRange(String secondaryRange) {
        this.secondaryRange = secondaryRange;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZip4() {
        return zip4;
    }

    public void setZip4(String zip4) {
        this.zip4 = zip4;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        USPostalAddress that = (USPostalAddress) o;

        if ((city != null) && (that.city != null) && (!city.equals(that.city))) return false;
        if ((primaryName != null) && (that.primaryName != null) && (!primaryName.equals(that.primaryName)))
            return false;
        if ((primaryRange != null) && (that.primaryRange != null) && (!primaryRange.equals(that.primaryRange)))
            return false;
        if ((state != null) && (that.primaryRange != null) && (!state.equals(that.state))) return false;
        if ((zip4 != null) && (that.zip4 != null) && (!zip4.equals(that.zip4))) return false;
        if ((zipCode != null) && (that.zipCode != null) && (!zipCode.equals(that.zipCode))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = primaryRange.hashCode();
        result = 31 * result + primaryName.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + zip4.hashCode();
        return result;
    }

    public void setAddressLine2(String addressLine2) {

        this.addressLine2 = addressLine2;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("PreDirectional:").append(preDirectional)
                .append(" PrimaryRange:").append(primaryRange)
                .append(" PrimaryName:").append(primaryName)
                .append(" Suffix:").append(suffix)
                .append(" PostDirectional:").append(postDirectional)
                .append(" SecondaryRange:").append(secondaryRange)
                .append(" SecondaryName:").append(secondaryName)
                .append(" City:").append(city)
                .append(" State:").append(state)
                .append(" ZipCode:").append(zipCode)
                .append(" Zip4:").append(zip4)
                .append(" AddressLine1:").append(addressLine1)
                .append(" AddressLine2:").append(addressLine2);

        if ((unitDesignator != null) && (unitDesignator.size() > 0)) {
            for (String s : unitDesignator) {
                builder.append(" UnitDesignator:").append(s);
            }
        }

        return builder.toString();
    }
}
