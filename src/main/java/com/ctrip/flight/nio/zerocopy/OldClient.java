package com.ctrip.flight.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-20
 * Time: 13:35
 */
public class OldClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 8899);

        String file =  "E:\\材料\\就业推荐表.doc";
        InputStream inputStream = new FileInputStream(file);

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        long readCount = 0;
        long total = 0;
        byte[] buffer = new byte[4096];

        long startTime = System.currentTimeMillis();

        // 将inputStream里面的数据读取到buffer里面
        while ((readCount = inputStream.read(buffer)) >= 0){
            total += readCount;
            outputStream.write(buffer);// 将buffer里面的数据写到输出流中
        }

       long endTime = System.currentTimeMillis();
        System.out.println("读取的字节数有：" + total + ",所花费的时间是：" + (endTime - startTime));

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
