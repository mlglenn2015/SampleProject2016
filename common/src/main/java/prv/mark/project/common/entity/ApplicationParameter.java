package prv.mark.project.common.entity;

import prv.mark.project.common.entity.converter.BooleanToStringConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * JPA Entity for the APPLICATION_PARAMETER table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "APPLICATION_PARAMETER")
@NamedQueries({
        @NamedQuery(name = "ApplicationParameter.findEnabledByParameterKey",
                query = "select p from ApplicationParameter p where p.parameterKey = ?1 and p.enabled = ?2")
})
public class ApplicationParameter implements Serializable {

    private static final long serialVersionUID = -5961263486065232744L;

    private Long id;
    private String parameterKey;
    private String parameterValue;
    private Boolean enabled;
    private Date createDate;

    /*protected ApplicationParameter() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public ApplicationParameter(Long id, String propKey, String propProperty, String enabled, Date created) {
        this.id = id;
        this.propKey = propKey;
        this.propProperty = propProperty;
        this.enabled = enabled;
        this.created = created;
    }*/


    @Id
    @SequenceGenerator(name = "SEQ_APPLICATION_PARAMETER_ID",
            sequenceName = "SEQ_APPLICATION_PARAMETER_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION_PARAMETER_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PARAMETER_KEY", nullable = false, length = 100)
    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    @Basic
    @Column(name = "PARAMETER_VALUE", nullable = false, length = 1024)
    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Column(name = "ENABLED", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    @Convert(converter = BooleanToStringConverter.class)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        ApplicationParameter that = (ApplicationParameter) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (parameterKey != null ? !parameterKey.equals(that.parameterKey) : that.parameterKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parameterKey != null ? parameterKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationParameter{" +
                "id=" + id +
                ", parameterKey='" + parameterKey + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                ", enabled=" + enabled + '\'' +
                ", createDate=" + createDate + '\'' +
                '}';
    }

}
