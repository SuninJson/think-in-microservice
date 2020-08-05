package com.order.service.util;

public class SnowFlakeGenerator {
    private long roomId; //机房id
    private long workerId; //机器id
    private long roomIdBit = 5L; //占用5个bit位
    private long workerIdBit = 5L; //占用5个bit位
    private long sequenceBits = 12L; //12bit的递增序列.
    private long sequence; //递增开始的序列

    public SnowFlakeGenerator(long roomId, long workerId, long sequence) {
        long maxWorkerId = ~(-1L << workerIdBit);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("woker Id 错误");
        }
        //声明roomId最大的正整数 32
        long maxRoomId = ~(-1L << roomIdBit);
        if (roomId > maxRoomId || roomId < 0) {
            throw new IllegalArgumentException("room Id 错误");
        }
        this.roomId = roomId;
        this.workerId = workerId;
        this.sequence = sequence;
    }

    //记录sequence能够存储的最大的长度
    private long sequenceMask = ~(-1L << sequenceBits);
    private long lastTimeStamp = -1L; //存储上一次生成的id的时间戳
    private long workerIdShift = sequenceBits;

    private long roomIdShift = sequenceBits + workerIdBit;
    private long timeStampShift = sequenceBits + workerIdBit + roomIdBit;

    public static void main(String[] args) throws InterruptedException {
        SnowFlakeGenerator snowFlakeGenerator = new SnowFlakeGenerator(1, 1, 1);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1);
            System.out.println(snowFlakeGenerator.nextVal());
        }
    }

    private synchronized long nextVal() {
        //开始去生成ID
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimeStamp) {
            throw new RuntimeException("时间戳异常");
        }
        if (lastTimeStamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //如果==0，表示sequence的值超过了4095.
                timestamp = waitToNextMills(lastTimeStamp);
            }
        } else {
            sequence = timestamp & 1; //如果进入到了新的时间毫秒，sequence从0开始
        }
        lastTimeStamp = timestamp;
        //初始的时间值
        long twepoch = 1596372483166L;
        return ((timestamp - twepoch) << timeStampShift | (roomId << roomIdShift) |
                (workerId << workerIdShift) | sequence);
    }

    //执行到下一个时间毫秒
    private long waitToNextMills(long lastTimeStamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimeStamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}