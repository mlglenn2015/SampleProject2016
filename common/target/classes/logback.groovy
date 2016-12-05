import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

setupAppenders()
setupLoggers()
jmxConfigurator()

def setupAppenders() {
    def timestampFmt = "%d{MM-dd-yyyy HH:mm:ss.SSS}"
    def logArchiveDateFmt = "%d{MMddyyyy}"
    def logFmt = "${timestampFmt} %-5level %logger{36} - %msg%n"
    def logDir = System.getProperty('LOG_LOCATION')

    if (logDir == null) {
        logDir = "/etc/log"
    }
    def logFileName = "${logDir}/StockTicker.log"

    appender('console', ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = logFmt
        }
    }

    appender('application_log_file', RollingFileAppender) {
        file = logFileName
        encoder(PatternLayoutEncoder) {
            pattern = logFmt
        }
        rollingPolicy(TimeBasedRollingPolicy) {
            fileNamePattern = "${logFileName}.${logArchiveDateFmt}"
            maxHistory = 30
        }
    }

}

def setupLoggers() {
    def appLogLevel = getLogLevel()

    // SOAP request/response logging to console
    logger 'org.springframework.ws.client.MessageTracing.sent', getSoapTracingLevel(), ['console'], false
    logger 'org.springframework.ws.client.MessageTracing.received', getSoapTracingLevel(), ['console'], false
    logger 'org.springframework.ws.server.MessageTracing.sent', getSoapTracingLevel(), ['console'], false
    logger 'org.springframework.ws.server.MessageTracing.received', getSoapTracingLevel(), ['console'], false

    // Application loggers
    logger 'prv.mark.project', appLogLevel, ['application_log_file','console'], false

    root Level.ERROR, ['console']
}

def getSoapTracingLevel() {
    def tracing = System.getProperty("app.soap.tracing")
    (tracing.equals("true") ? Level.TRACE : Level.ERROR)
}

def getLogLevel() {
    def env = System.getProperty("ENVIRONMENT")
    (env.equals("prod") ? Level.ERROR : Level.DEBUG )
}

