package ru.shoe.l51;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.*;

class GCTimeUtil {
    private static int countMinorGC;
    private static long timeMinorGC;
    private static int countMajorGC;
    private static long startTime;
    private static long count = 0;

    private static long timeMajorGC;
    private static NotificationListener gcHandler = (notification, handback) -> {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo gcInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            if (gcInfo.getGcAction().equals("end of minor GC")) {
                countMinorGC++;
                timeMinorGC += gcInfo.getGcInfo().getDuration();
            } else if (gcInfo.getGcAction().equals("end of major GC")) {
                countMajorGC++;
                timeMajorGC += gcInfo.getGcInfo().getDuration();
            }
            long time = System.currentTimeMillis();
            if (time - startTime >= 1000 * 60) {
                startTime = time;
                count++;
                System.out.println("count minor GC per " + count + " minute: " + countMinorGC);
                System.out.println("time minor GC per " + count + " minute: " + timeMinorGC + "ms");
                System.out.println("count major GC per " + count + " minute: " + countMajorGC);
                System.out.println("time major GC per " + count + " minute: " + timeMajorGC + "ms \n");
                countMinorGC = 0;
                countMajorGC = 0;
                timeMinorGC = 0;
                timeMajorGC = 0;
            }
        }
    };


    /**
     * Запускает процесс мониторинга сборок мусора.
     */
    static void startGCMonitor() {
        startTime = System.currentTimeMillis();
        for (GarbageCollectorMXBean mBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) mBean).addNotificationListener(gcHandler, null, null);
        }
    }

    /**
     * Останавливает процесс мониторинга сборок мусора.
     */
    static void stopGCMonitor() {
        for (GarbageCollectorMXBean mBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ((NotificationEmitter) mBean).removeNotificationListener(gcHandler);
            } catch (ListenerNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}