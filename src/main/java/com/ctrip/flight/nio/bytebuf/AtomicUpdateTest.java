package com.ctrip.flight.nio.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 *    Netty对于ByteBuf的管理也是采用引用计数（Reference counted）的方式完成的，当一个引用计数对象被创建的时候，它的引用计数是1.
 *    主要关注ReferenceCounted接口和AbstractReferenceCountedByteBuf抽象类的源码。
 *
 *  * 粗略理解：从InBound里读取的ByteBuf要手动释放，还有自己创建的ByteBuf要自己负责释放。这两处要调用这个release方法。
 *  *           write ByteBuf到OutBound时由netty负责释放，不需要手动调用release。（具体的解释还是官方文档比较详细）
 *  * Reference counted objects官方文档： https://netty.io/wiki/reference-counted-objects.html
 *  * 中文解释版（简略）：https://blog.csdn.net/yangguosb/article/details/77868228,
 *                        https://blog.csdn.net/hannuotayouxi/article/details/78827499
 *
 *      JDK5中中利用AtomicIntegerFieldUpdater完成原子更新操作的一个抽象类：基于反射机制（refCnt这个成员变量）的一个辅助类，
 *      可以完成对volatile类型变量的原子更新，采用的是CAS操作完成的。，如果一个ByteBuf对象的引用计数变成了0，那我们就不能再
 *      使用这个ByteBuf对象了。
 *      AtomicIntegerFieldUpdater要点总结：
 *      1. 更新器更新的必须是int类型的变量，不能是包装类型（Integer）；
 *      2.  更新器更新的必须是volatile类型的变量，确保线程共享变量时的可见性。
 *      3. 变量不能是static类型的，必须是实例变量，因为Unsafe.objectFieldOffset()方法不支持静态变量（CAS操作本质上是通过对象实例的偏移量
 *         来直接进行赋值）。
 *       4. 更新器只能修改可见范围内的变量（不能进行反射），因为更新器是通过反射得到这个变量的，如果变量不可见就会报错。
 *
 *    疑问：为啥Netty采用的是AtomicIntegerFieldUpdater而不是AtomicInteger来保证原子性呢？
 *          因为AtomicInteger实际上是对int的一个封装，在Netty中，ByteBuf其实用的是非常多的，如果这些ByteBuf中的每一个ByteBuf都含有一个AtomicInteger，
 *          然后AtomicInteger里面又对int进行一个封装，这种方式在性能上是由损耗的。而Netty使用的是全局的方式（AtomicIntegerFieldUpdater是static的），
 *          这样就可以对所有的ByteBuf里面的引用计数的一个更新。无论创建多少个实例，其实AtomicIntegerFieldUpdater变量只会有一份，这样就能对所有的ByteBuf里面的
 *          refCnt变量进行更新。
 *
 *
 */
public class AtomicUpdateTest {
    public static void main(String[] args) {
        /**
         *      Person person = new Person();
         *        for (int i = 0; i < 10; i++){
         *            Thread thread = new Thread(() -> {
         *
         *                try {
         *                    Thread.sleep(200); // 线程交出CPU时间片，待200ms之后加入就需队列，等到下一次CPU分配时间片
         *                } catch (InterruptedException e) {
         *                    e.printStackTrace();
         *                }
         *
         *                System.out.println(person.age++);
         *            });
         *            thread.start();
         *        }
         */
         Person person = new Person();
        AtomicIntegerFieldUpdater<Person> updater = AtomicIntegerFieldUpdater.
                newUpdater(Person.class, "age");

        for (int i = 0; i < 10; i++){
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 输出结果的顺序是不确定的，但是一定是原子更新的
                System.out.println( updater.getAndIncrement(person));
            });
            thread.start();
        }
    }
}
class Person{
    volatile int age = 1;// 这个变量必须是volatile类型的才行
}
