package com.ctrip.flight.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * Created by xuke
 * Description: Thrift客户端
 * Date: 2019-09-09
 * Time: 0:55
 */
public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899), 600);// 超时时间设置为600ms
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();

            Person person = client.getPersonByUsername("张三");

            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("...............");

            Person person2 = new Person();
            person2.setName("李四");
            person2.setAge(15);
            person2.setMarried(true);

            client.savePerson(person2);


        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }finally {
            transport.close();
        }
    }
}
