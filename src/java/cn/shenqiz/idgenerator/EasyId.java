package cn.shenqiz.idgenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 神奇的Z on 2017-2-13.
 * 功能：仅根据当前时间创建ID，每毫秒最多生成1000个ID，最大时间长度为100年
 * 格式：最后12位表示当前序列，其他位表示当前时间
 */
public class EasyId implements Id<String> {

    private final long sequenceBits = 12L;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private final long timestampLeftShift = sequenceBits;
    private final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private long twepoch = 946656000000L;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public EasyId() {

    }

    public static void main(String[] args) throws ParseException {
        EasyId easyId = new EasyId();
        easyId.setTwepoch(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-01-01 00:00:00"));
        for (int i = 0; i <= 10; i++) {
            System.out.println(easyId.nextId());
        }
        easyId.setTwepoch(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2010-01-01 00:00:00"));
        for (int i = 0; i <= 10; i++) {
            System.out.println(easyId.nextId());
        }
        easyId.setTwepoch(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-01-01 00:00:00"));
        for (int i = 0; i <= 10; i++) {
            System.out.println(easyId.nextId());
        }

        System.out.println(easyId.getDateFromId(easyId.nextId()));
        System.out.println(easyId.getDateFromId(easyId.nextId()));
    }

    public void setTwepoch(Date date) {
        twepoch = date.getTime();
    }

    public String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        } else if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;

        Long idLong = ((timestamp - twepoch) << timestampLeftShift) | sequence;
        String id = idFromLong(idLong);

        return id;
    }

    public Date getDateFromId(String id) {
        long aLong = idToLong(id);
        long timestamp = (aLong >> timestampLeftShift) + twepoch;
        Date date = new Date(timestamp);
        return date;
    }

    protected String idFromLong(long aLong) {
        int systemLength = chars.length;
        if (aLong == 0L) return "0";
        else {
            String id = "";
            while (aLong > 0) {
                long bitLong = aLong % systemLength;
                id = String.valueOf(chars[(int) bitLong]) + id;
                aLong = (aLong / systemLength);
            }
            return id;
        }
    }

    protected Long idToLong(String id) {
        Long aLong = 0L;
        for (int i = 0; i < id.length(); i++) {
            char currentChar = id.charAt(id.length() - i - 1);
            long currentLong = (long) (getIndexOfChar(currentChar) * Math.pow(chars.length, i));
            aLong += currentLong;
        }
        return aLong;
    }

    protected int getIndexOfChar(char c) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                return i;
            }
        }
        return -1;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public String generate() {
        return null;
    }
}
