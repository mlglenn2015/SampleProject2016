package prv.mark.project.stockservice.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import prv.mark.project.common.domain.TransactionDto;
import prv.mark.project.common.util.DateUtils;
import prv.mark.project.translog.jms.TranslogProducerJms;
import prv.mark.project.translog.schemas.TransactionLoggerMsgType;


/**
 * TODO
 *
 * Created by Owner on 2/19/2017.
 */
public class TransactionLoggerJmsClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionLoggerJmsClient.class);

    private TransactionDto transactionDto;

    @Autowired
    private TranslogProducerJms translogProducerJms;


    public TransactionLoggerJmsClient(TransactionDto transactionDto) {
        this.transactionDto = transactionDto;
    }

    public void sendMessage() {
        LOGGER.info("TransactionLoggerJmsClient.sendMessage(): Entry");

        TransactionLoggerMsgType transactionLoggerMsgType = new TransactionLoggerMsgType();
        LOGGER.info("TransactionLoggerJmsClient.sendMessage(): Instantiated new TransactionLoggerMsgType()");
        transactionLoggerMsgType.setLogDateTime(
                DateUtils.getCurrentXMLGregorianCalendarFromLocalDateTime(this.transactionDto.getLogDateTime()));
        transactionLoggerMsgType.setTransactionType(this.transactionDto.getTransactionType());
        transactionLoggerMsgType.setTransactionDetail(this.transactionDto.getTransactionDetail());

        LOGGER.info("TransactionLoggerJmsClient.sendMessage(): TransactionLoggerMsgType properties: " +
                "logDateTime=[{}], transactionType=[{}], transactionDetail=[{}] ",
                transactionLoggerMsgType.getLogDateTime(), transactionLoggerMsgType.getTransactionType(),
                transactionLoggerMsgType.getTransactionDetail());

        if (translogProducerJms != null) {
            translogProducerJms.sendMessage("TEST MESSAGE");
            //translogProducerJms.send TODO
        } else {
            LOGGER.info("TransactionLoggerJmsClient.sendMessage(): translogProducerJms is NULL!");
        }

        LOGGER.info("TransactionLoggerJmsClient.sendMessage(): Exit");
    }

    public TransactionDto getTransactionDto() {
        return transactionDto;
    }

    /*public void setTransactionDto(TransactionDto transactionDto) {
        this.transactionDto = transactionDto;
    }*/
}
