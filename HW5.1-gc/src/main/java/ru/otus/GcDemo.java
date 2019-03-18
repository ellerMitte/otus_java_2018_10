package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor on 15.03.19.
 */
/*
-Xms512m
-Xmx512m

//Parallel GC: ����� ������ �� OOM - 6 ���, ������� ����� ������ - 12 ���/���
GcStat{name='PS Scavenge', quantity=37, time=2721}
GcStat{name='PS MarkSweep', quantity=51, time=69976}

//G1 GC: ����� ������ �� OOM - 4 ���, ������� ����� ������ - 1,6 ���/���
GcStat{name='G1 Young Generation', quantity=76, time=3441}
GcStat{name='G1 Old Generation', quantity=2, time=3291}

//Serial GC: ����� ������ �� OOM - 4 ���, ������� ����� ������ - 4 ���/���
GcStat{name='Copy', quantity=22, time=1605}
GcStat{name='MarkSweepCompact', quantity=19, time=14240}

    ���������� ����� ������ �� "out of memory" ������� Parallel GC, �� "�����������" ������ 6 �����,
� ����� ����������� ��������� "��������" ���������� ���������.
� ���������� ������� ����� ������ GC � ������ � ���� ��������� ���� ����� ������� - ����� 12 ���/���.
    ��� ������ GC �������� �������� ���������� ����� �� OOM,
��� ���� ������ young ��������� G1 ���������� ������� ����������(76 ���),
�� �������� �������. Serial GC ��������� ���������� ���������� ���,
�� ��� ���� ������ Old ��������� ���������� ���� � �������� ������.
    �� ���� ������ � ���� ������� �����, ��� ��� ������ ������ � ������� �������
����� ������������ ������ GC ��� ������������ �����������.
���� ������� ���������� ������������������, ����� ����� ������������ Parallel GC,
�� ����� �� ���������� "��������������" Out of Memory ������ �� ������� "���������" ���������.
���� �� ������� ���������� �������� ������, ����� ������ �������� G1 GC,
� ������� �� �������� "������ ������" ������� ��������� GC.
*/
@Slf4j
public class GcDemo {

    private static List<GcStat> gcStats;

    public static void main(String... args) throws Exception {
        log.info("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();
        int size = 1_000_000;
        int loopCounter = 100_000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        mbean.run();

        log.info("time:" + (System.currentTimeMillis() - beginTime) / 1000);
        gcStats.forEach(System.out::println);
    }

    private static void switchOnMonitoring() {
        gcStats = new ArrayList<>();
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            GcStat gcStat = new GcStat(gcBean.getName());
            gcStats.add(gcStat);
            log.info("GC name:" + gcBean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();
                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();
                    gcStat.increase(duration);
                    log.info("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    log.info(gcStat.toString());
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
