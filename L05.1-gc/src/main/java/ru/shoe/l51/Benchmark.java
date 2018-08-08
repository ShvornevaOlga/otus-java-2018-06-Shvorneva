package ru.shoe.l51;

import java.util.ArrayList;
import java.util.List;

class Benchmark implements BenchmarkMBean {
    private volatile int size = 0;

    void run() {
        long startTime = System.currentTimeMillis();
        GCTimeUtil.startGCMonitor();
        System.out.println("Starting the loop");
        List<Integer> list = new ArrayList<>();
        while (System.currentTimeMillis() - startTime < 6 * 1000 * 60) {
            allocate(list);
        }
        GCTimeUtil.stopGCMonitor();
    }

    private void allocate(List list) {
        int local = size;
        for (int i = 0; i < local; i++) {
            list.add(i);
        }
        System.out.println("Created " + local + " objects.");
        int size = list.size();
        for (int i = size - 1; i > (size - 0.975 * local) - 1; i--) {
            list.remove(i);
        }
        System.out.println("Removed " + (size - (size - 0.975 * local)) + " objects.");
        System.out.println("Objects left in memory: "+list.size());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void allocate() {
        int local = size;
        Object[] array = new Object[local];

        for (int i = 0; i < local; i++) {
            array[i] = new String(new char[0]);
        }
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

}
