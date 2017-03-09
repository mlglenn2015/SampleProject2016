package prv.mark.project.translog.jms;

import prv.mark.project.translog.schemas.TransactionLoggerMsgType;

/** TODO
 * Created by Owner on 2/26/2017.
 */
public interface TransactionLogMessageSender {

    void sendMessage(String message);
    void sendMessage(TransactionLoggerMsgType message);
}
