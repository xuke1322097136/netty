package com.ctrip.flight.nio.handler3;

/**
 * Created by xuke
 * Description:
 * Date: 2019-08-07
 * Time: 23:48
 */
public class PersonProtocol {
    private int length; // 附加在消息之前的长度值
    private byte[] content;// 实际的数据

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
