

Start Admin Server:

    1. cd C:\Oracle\Middleware\wls12212\user_projects\domains\mydomain

    2. startWebLogic.cmd

Start Managed Server (Without Node Manager):

    1. cd C:\Oracle\Middleware\wls12212\user_projects\domains\mydomain\bin

    2. startManagedWebLogic.cmd [managedSvr1|stocksMgdServer1] http://localhost:7001     weblogic/weblogic123

        startManagedWebLogic.cmd managedSvr1 http://localhost:7001

        -Xms512m -Xmx1024m -Dspring.profiles.active=dev -DLOG_LOCATION=/etc/log -DENVIRONMENT=dev -Dapp.jpa.logging=SEVERE

        startManagedWebLogic_DEBUG.cmd managedSvr1 http://localhost:7001

        -Xrunjdwp:transport=dt_socket,address=10171,server=y,suspend=n -Xmx512m -XX:MaxPermSize=256m -debug










