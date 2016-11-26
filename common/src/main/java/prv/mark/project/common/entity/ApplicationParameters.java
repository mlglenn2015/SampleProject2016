package prv.mark.project.common.entity;

import prv.mark.project.common.entity.converter.BooleanToStringConverter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * JPA Entity for the APPLICATION_PARAMETERS table.
 *
 * @author mlglenn.
 */
@Entity
@Table(name = "APPLICATION_PARAMETERS")
@NamedQueries({
        @NamedQuery(name = "ApplicationParameters.findActiveByKey",
                query = "select s from ApplicationParameters s where s.key = ?1 and s.enabled = ?2")
})
public class ApplicationParameters implements Serializable {


    private static final long serialVersionUID = -5961263486065232744L;

    private Long id;
    private String key;
    private String property;
    private Boolean enabled;
    private Date created;

    @Id
    @SequenceGenerator(name = "SEQ_APPLICATION_PARAMETERS", sequenceName = "SEQ_APPLICATION_PARAMETERS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION_PARAMETERS")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "KEY", nullable = false, length = 255)
    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "PROPERTY", nullable = false, length = 1024)
    public String getProperty() {
        return property;
    }

    public void setProperty(final String property) {
        this.property = property;
    }

    @Column(name = "ENABLED", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    @Convert(converter = BooleanToStringConverter.class)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }



    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        ApplicationParameters props = (ApplicationParameters) o;

        if (!key.equals(props.key)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "ApplicationParameters{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", property='" + property + '\'' +
                ", enabled=" + enabled + '\'' +
                ", created=" + created + '\'' +
                '}';
    }

}
