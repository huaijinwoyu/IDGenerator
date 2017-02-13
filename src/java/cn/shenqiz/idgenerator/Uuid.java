package cn.shenqiz.idgenerator;

import java.util.UUID;

/**
 * Created by 神奇的Z on 2017-2-13.
 */
public class Uuid implements Id<String> {

    public static void main(String[] args) {

    }

    public String generate() {
        return UUID.randomUUID().toString();
    }
}
