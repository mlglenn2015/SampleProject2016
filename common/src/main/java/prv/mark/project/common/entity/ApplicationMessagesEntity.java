package prv.mark.project.common.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * JPA Entity for the APPLICATION_MESSAGES table.
 *
 * @author mlglenn
 */
@Entity
@Table(name = "APPLICATION_MESSAGES")
public class ApplicationMessagesEntity implements Serializable {

    private static final long serialVersionUID = 5148997916515761856L;

    @Id
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(
            name = "SEQ_APPLICATION_MESSAGES", sequenceName = "SEQ_APPLICATION_MESSAGES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION_MESSAGES")
    private Long id;

    @Basic
    @Column(name = "MESSAGE_KEY", nullable = false, length = 100)
    private String messageKey;

    @Basic
    @Column(name = "MESSAGE", nullable = false, length = 500)
    private String message;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
