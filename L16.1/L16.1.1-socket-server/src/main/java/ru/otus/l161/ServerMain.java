package ru.otus.l161;

import ru.otus.l161.runner.ProcessRunnerImpl;
import ru.otus.l161.server.EchoSocketMsgServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tully.
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    private static final String CLIENT_START_COMMAND = "java -jar ../L16.1.2-socket-client/target/client.jar DBService";
    private static final String FRONT_CLIENT_START_COMMAND = "java -jar " + System.getenv("JETTY_HOME") + "/start.jar";
    private static final String FRONT_CLIENT_2_START_COMMAND = "java -jar " + System.getenv("JETTY_HOME") + "/start.jar jetty-otherserver.xml";
    private static final String FRONT_PATH = "../L16.1.2-socket-client-front/target";
    private static final String JETTY_PATH = System.getenv("JETTY_HOME") + "/webapps/root.war";
    private static final String JETTY_PATH_2 = System.getenv("JETTY_HOME") + "/other-webapps/root.war";
    private static final int CLIENT_START_DELAY_SEC = 1;


    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        File source = new File(FRONT_PATH, "root.war");
        File dest = new File(JETTY_PATH);
        File dest2 = new File(JETTY_PATH_2);
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(source.toPath(), dest2.toPath(), StandardCopyOption.REPLACE_EXISTING);
        startClient(executorService);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        EchoSocketMsgServer server = new EchoSocketMsgServer();
        mbs.registerMBean(server, name);

        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService) {
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(FRONT_CLIENT_START_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
        executorService.schedule(() -> {
            try {
                new ProcessRunnerImpl().start(FRONT_CLIENT_2_START_COMMAND);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
        for (int i = 0; i < 2; i++) {
            executorService.schedule(() -> {
                try {
                    new ProcessRunnerImpl().start(CLIENT_START_COMMAND);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
        }

    }

}
