package com.ctrip.flight.thrift;

import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * Created by xuke
 * Description: 用于实现PersonService里面的两个接口（方法）
 * Date: 2019-09-09
 * Time: 0:36
 *首先，我们需要在环境变量path中配置thrift.exe的路径（注意：在这需要将下载的thrift-版本号.exe改为thrift.exe，
 *                                               不然在cmd窗口运行的时候得带上版本号）
 * 然后，我们需要使用命令：thrift --gen java src/thrift/data.thrift 生成对应的java文件，生成的java文件位于gen-java中，总用包含
 *      三个java文件，由于我们需要使用这三个文件，所以我们需要将thrift文件夹拷贝到java目录下。
 */
public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("get client param: " + username);

        Person person = new Person();

        person.setName(username);
        person.setAge(20);
        person.setMarried(false);

        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("get client param: ");

        System.out.println(person.getName());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
