package ru.otus.l161.server;

import ru.otus.l161.app.Msg;
import ru.otus.l161.app.MsgToDataBase;
import ru.otus.l161.app.MsgToFront;
import ru.otus.l161.app.MsgWorker;
import ru.otus.l161.channel.Blocks;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.messages.PingMsg;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoSocketMsgServer implements EchoSocketMsgServerMBean {
    private static final Logger logger = Logger.getLogger(EchoSocketMsgServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private boolean nextDbService;
    private final ExecutorService executor;
    private final List<MsgWorker> workers;
    private final List<MsgWorker> dbWorkerList;
    private final Map<String, MsgWorker> frontWorkerMap;

    public EchoSocketMsgServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        workers = new CopyOnWriteArrayList<>();
        dbWorkerList = new ArrayList<>();
        frontWorkerMap = new HashMap<>();
    }

    @Blocks
    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                workers.add(worker);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            for (MsgWorker worker : workers) {
                Msg msg = worker.poll();
                while (msg != null) {
                    if (msg instanceof MsgToFront) {
                        MsgWorker workerTo = frontWorkerMap.get(((MsgToFront) msg).getTo());
                        workerTo.send(msg);
                    } else if (msg instanceof MsgToDataBase) {
                        ((MsgToDataBase) msg).setFrom(worker.getWorkerId());
                        MsgWorker workerTo = dbWorkerList.get(nextDbService ? 0 : 1);
                        nextDbService = !nextDbService;
                        workerTo.send(msg);
                    } else if (msg instanceof PingMsg) {
                        String socketId = ((PingMsg) msg).getSocketId();
                        if (socketId.startsWith("Db")) {
                            dbWorkerList.add(worker);
                        } else if (socketId.startsWith("Front")) {
                            frontWorkerMap.put(worker.getWorkerId(), worker);
                        }
                    }
                    msg = worker.poll();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
            logger.info("Bye.");
        }
    }
}
