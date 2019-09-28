#指定命名空间，即包名
namespace java thrift.generated

#换一个别名
typedef i32 int
typedef i64 long
typedef bool boolean

#thrift支持struct/service/exception
struct Person{
    1: optional string name,
    2: optional int age,
    3: optional boolean married
}

exception DataException{
    1: optional string message,
    2: optional string callStack,
    3: optional string date
}

#service其实就可以看成是方法的一个集合，和java中的接口比较相似
service PersonService{
     Person getPersonByUsername(1: required string username) throws (1: DataException dataException),

     void savePerson(1: required Person person) throws (1: DataException dataException)
}