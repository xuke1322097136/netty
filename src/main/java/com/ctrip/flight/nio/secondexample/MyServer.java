package com.ctrip.flight.nio.secondexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by xuke
 * Description:
 * Date: 2019-07-21
 * Time: 17:35
 */

/**
 *  传统的阻塞模型OIO（Old InputStream and OutputStream）通信模式：
 *      1. 服务器端会创建大量线程，一个socket(一次会话)连接服务器端就会创建一个线程，专门服务于这个连接，这个线程就会一直等待这个连接，
 *          和客户端向它发送的数据，然后将响应的结果发回给客户端，再来一个请求服务器端就会再创建一个线程，100个请求服务器端就会创建100个线程。
 *      2. 线程之间的切换代价较高；
 *      3. 由于一个socket与之有一个线程对应，但是服务器端每个线程有可能没事干造成大量浪费。因为当连接建立好之后，未必每个时刻都有数据传输，
 *      当没有数据传输的时候，但是服务器端的连接还是要保持的，也就是说服务器端的socket连接是不能释放的，但是此时有没有数据发送，所以会造成
 *      资源浪费。
 *
 *  Reactor模式的角色构成（5种）：
 *  1. Handler（句柄或者描述符）：windows系统上可以翻译为句柄，linux系统上翻译为描述符。本质上表示的是一种资源（或者说是一个文件描述符），
 *            是由操作系统提供的，该资源表示一个一个的事件，比如文件描述符、或者是针对网络编程中的Socket描述符。Handle本身是事件产生的发源地。
 *            事件既可以来自于内部也可以来自于外部。外部事件可以是客户端的连接请求、客户端发送过来的数据等；
 *            内部事件可以是操作系统产生的定时器事件等。
 *
 *   2. Synchronous Event Demultiplexer（同步事件分离器）：它本身是一个系统调用，用于等待事件的发生（可以是一个事件，也可以是多个事件，
 *             可以是OP_ACCEPT,OP_READd等事件），调用方在调用它的时候会阻塞，它会一直阻塞到同步分离器上有事件产生为止。
 *             对于Linux系统来说，同步事件分离器指的就是常见的多路复用机制，比如：select、poll、epoll等。
 *             在Java NIO中，同步事件分离器对应的组件就是Selector，对应的阻塞方法就是select()方法。
 *             所以，它是bossGroup来实现的？？负责监听客户端的连接请求事件？
 *
 *   3. Event Handler（事件处理器）：本身由多个回调方法构成，这些回调方法构成了与应用相关的对于某个事件反馈机制。在NIO种没有与之对应的角色，
 *            但是netty中的XXXHandler就是事件处理器，它相比Java NIO来说，在事件处理器上进行了升级，为开发者提供了大量的回调方法，
 *            供我们在特定事件产生的时候实现相应的回调方法（如channelRead0方法等）进行业务逻辑的处理。
 *
 *   4. Concrete Event Handler（具体事件处理器）：是事件处理器Event Handler的一个实现，它本身实现了事件处理器的各个回调方法，从而实现了
 *            特定于业务的逻辑。它本质上就是我们编写的一个个处理器的实现，在我们的netty中，对应的组件就是我们自己实现的handler（如MyServerHandler）
 *            其中channelRead0方法、exceptionCaught方法里面的实现就是我们写的业务逻辑，其中channelRead0方法调用者其实是线程池中的某一个线程，
 *            ，即workerGroup来负责调度完成的。
 *
 *   5. Initiation Dispatcher（初始分发器）:其实它就是Reactor角色，即MainReactor（bossGroup） + SubReactor(workerGroup)。它本身定义了一些规范，
 *            这些规范用于控制事件的调度方式，同时又提供了应用进行事件处理器的注册、删除等设施。它可以拿到一个一个的
 *            事件Handler（selector.select()阻塞方法产生的结果），即selection key的一个集合，它是整个事件处理器的核心所在。
 *            也就是说，Initiation Dispatcher会通过同步事件分离器来等待事件的发生，一旦事件发生了，Initiation Dispatcher
 *            首先会分离每一个事件（即取出每一个selection key集合里面的每一个selection key），然后调用事件处理器（具体事件处理器，此时
 *            Initiation Dispatcher相当于SubReactor）的回调方法（channelRead0方法）来处理这些事件(Handle)。
 *
 * Reactor模式所有组件运转的流程：
 *                    首先Initiation Dispatcher会将若干个Concrete Event Handler注册到它上面，由于Concrete Event Handler内存含有Handle信息，
 *                    因此在注册的同时，就会指定某一个事件处理器（Concrete Event Handler）它感兴趣的事件是什么（OP_ACCEPT等），注册完毕之后。
 *                    紧接着Initiation Dispatcher 的事件循环就会启动。
 *                    当Handle变为ready状态时，即产生了某一个事件Handle的时候，Synchronous Event Demultiplexer就会被调用，并通知Initiation Dispatcher。
 *                    由于Synchronous Event Demultiplexer是同步阻塞的，等待着一个或多个事情的发生，接着返回给Initiation Dispatcher所产生的那些事件集合，
 *                    Initiation Dispatcher会根据事件寻找到该事件所关联到的已经注册好的Handlers，遍历Handlers里面的每一个Handler，
 *                    具体来说是将处于ready状态的Handle作为key，找到与之对应的Concrete Event Handler中的回调方法来处理事件。
 *
 *      有多个输入源，有多个不同的EventHandler（RequestHandler）来处理不同的请求，Initiation Dispatcher用于
 *      管理EventHander，EventHandler首先要注册到Initiation Dispatcher中，然后Initiation Dispatcher根据输入
 *      的Event分发给注册的EventHandler；然而Initiation Dispatcher并不监听Event的到来，这个工作交
 *      给Synchronous Event Demultiplexer来处理。
 *      可以参考下：http://www.blogjava.net/DLevin/archive/2015/09/02/427045.html
 *   Acceptor：用来完成bossGroup和和workerGroup之间的交接工作。查看netty的源码，从blind()方法点进去就可以看到ServerBootstrap里面的init()方法，
 *             它完成了往ChannelInitializer里面的ChannelPipeline添加一个ServerBootstrapAcceptor（本质上是ChannelInboundHandlerAdapter），由此可见，
 *             ServerBootstrapAcceptor其实也是一个Handler，紧接着是通过ServerBootstrap的channelRead()方法来完成
 *             bossGroup（MainReactor）和workerGroup（SubReactor）之间转移的工作。
 *
 *     Channel & ChannelHandler & ChannelHandlerContext  & ChannelPipeline之间的关系：
 *    1.  当服务端去创建一个Channel的时候，就会为该Channel创建一个ChannelPipeline对象，Channel和ChannelPipeline一旦创建好之后，就会建立起
 *     二者之间的关联关系，并且这种关联关系在Channel的整个生命周期是不会发生改变的，换句话说，一旦某一个Channel绑定到一个ChannelPipeline
 *     上之后，就不可能绑定到其他的ChannelPipeline对象上，二者之间是一对一的关系。
 *     2. 对于ChannelPipeline对象来说，它里面维护的是ChannelHandler的一个双向链表，在创建每一个ChannelHandler的同时，又会创建一个与之对应的
 *       ChannelHandlerContext对象，ChannelHandlerContext对象是连接ChannelHandler和ChannelPipeline的桥梁和纽带。从本质上来说，其实ChannelPipeline
 *       里面维护的是一个又一个的 ChannelHandlerContext对象，而 ChannelHandlerContext对象里面是可以引用到对应的ChannelHandler这个处理器对象的。
 *     3. ChannelHandler对象又是分成两个方向的，一个是InBound，一个是OutBound，InBound指的是进入到我们的程序当中，OutBound指的是将我们的数据发送
 *     到外部，当一个数据从外界进来，对于Netty来说，他会检查ChannelPipeline当中的每一个ChannelHandler，然后判断这个ChannelHandler是不是一个
 *     InBound类型的ChannelHandler，使用的是instance of 关键字来判断的，然后接着判断下一个ChannelHandler，以此类推。总之，ChannelPipeline是将
 *     InBound和OutBound是拆分开的，这一点跟Servlet里面的Filter、或者是Struts2和Spring MVC里面的Inceptor有相似之处，但是也有不同的地方，无论是
 *     Filter还是Inceptor，它们是既过滤或者拦截进来的，也过滤或者拦截出去的，进和出都会经过它，而对于Netty的这个行为不是这样的，对于ChannelHandler，
 *     InBound的话，它只会拦截InBound的数据，如果是OutBound，它只会拦截OutBound的数据，一个InBound类型的数据是不会流经OutBoundHandler的，同理，一个
 *     一个OutBound类型的数据是不会流经InBoundHandler的，不过我们也是可以定义一个ChannelHandler既是InBoundHandler又是OutBoundHandler，只需要分别实现
 *     相应的接口就好，但是很多情况下是不会这么做的，最好还是分开处理。
 */
public class MyServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 下面的这些操作只是完成赋值的一些操作，group()方法是将bossGroup和workerGroup分别赋值给AbstractBootstrap类和ServerBootstrap里面的成员变量
            // channel方法通过反射机制完成ChannelFactory的赋值操作，实际上的创建是在下面的blind()方法里完成的
            // handler（这个方法也是有的，只是我们没用它）和childHandler方法的区别就在于handler()方法对应的是bossGroup，childHandler方法对应的是workerGroup
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(LogLevel.WARN)).
                    childHandler(new MyServerInitializer());

            /**
             *    1. sync 一定得加上，他表示server一直会在这等待。bind方法（该方法完成了很多事情）会创建handler并且绑定到它上面去。
             *    2.  bind方法会跳转到ServerBootstrap的init()，可以看看里面怎么创建handler的（其实就是我们创建handler方式是一样的
             *          都是先创建XXXInitializer，然后里面通过pipeline将handler添加进去，pipeline是在AbstractChannel类里面的构造函数创建出来的）。
             *    3. 快捷键：ctrl+alt+b 查看哪些类实现了该方法
             *    4.  ChannelOption这个类本身是不维护任何值的信息的，它只维护这个option（名字/key，在这就是"SO_RCVBUF"）本身，
             *           它主要是完成对ChannelConfig中TCP/IP协议等里面的一些设置项（如"SO_RCVBUF"）进行设定，只存储这些key的名字，ChannelOption不保存具体该key对应的值。
             *      例如下面：
             *           public static final ChannelOption<Integer> SO_RCVBUF = valueOf("SO_RCVBUF");
             *           真正值的类型为Integer，具体值的大小单独设置，不在ChannelOption中设置，在ChannelConfig具体的实现类（如DefaultChannelConfig）中进行设置。
             *
             *      5. AttributeKey也是和ChannelOption类似的，它主要维护的是一些业务数据key-value对，key存放在AttributeKey中，value存放在Attribute中。
             *      6. 我们在创建每一个Channel的同时都有一个与之对应的ChannelPipeline，而ChannelPipeline则相当于是一个容器，里面存放着大量的ChannelHandler。
             *         ChannelHandlerContext是ChannelHandler与ChannelPipeline之间交流的桥梁和纽带。
             *         注意：同一个ChannelHandler对象被添加到了ChannelPipeline中几次（不管是否是相同的ChannelPipeline）就会产生几个
             *                与之对应的ChannelHandlerContext对象。
             *       7. 在jdk中，一般addListener()方法或者是addXXXListener()方法，一般情况下，使用的都是观察者模式。
             */
            /**
             *   Jdk 提供的Future接口， Netty提供的Future接口、ChannelFuture接口、ChannelPromise之间的关系：
             *    a.) jdk提供的Future接口只能通过手工方式（调用get()方法或者isDone()方法）检查执行结果，而这个操作是会被阻塞的，要等到该方法执行完了才能拿到执行结果；
             *        Netty则对ChannelFuture进行了增强，通过ChannelFutureListener以回调的方式来获取执行结果，这样做去除了手工检查阻塞的操作，值得注意的是，
             *         ChannelFutureListener（或者是GenericFutureListener）的optionComplete()方法是由I/O线程执行的，因此要注意的是，不要在这里执行耗时操作，
             *         否则需要通过另外的线程或者线程池来执行。
             *    观察者模式：Netty里面的Future上注册了很多个对该Future结果感兴趣的Listener，当Future里面的结果拿到了，Future就会遍历所有注册在它上面的
             *                每一个Listener(ChannelFutureListener/GenericFutureListener)，然后调用每一个监听器上的operationComplete方法，而且每一个Future
             *                对象自己也会传递给每一个Listener，由于Future对象可以通过channel()方法拿到与这个Future对象相关联的Channel对象，因此在每一个
             *                监听器里面operationComplete方法也可以拿到这个对象，拿到之后就可以对Channel进行任何操作和处理。
             *                （ChannelFutureListener的源码可以看到上述原理）
             *
             *
             *
             */
            ChannelFuture channelFuture =  serverBootstrap.bind(8899).sync();
           channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
