package 传输;
/**
 * @author kate
 * @create 2019/3/3
 * @since 1.0.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName PlainNioServer
 * @Description 未使用Netty的异步网络编程: 异步(NIO)版本实现, 非阻塞IO
 * 使用JDK API的应用程序的(OIO)阻塞版本和异步(NIO)版本实现
 * @Author Huang Xiaoqiu
 * @Date 2019/3/3 20:24
 * @Version 1.0.0
 **/
public class PlainNioServer {
  private static final Logger LOG = LoggerFactory.getLogger(PlainOioServer.class);

  public void serve(int port) throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    ServerSocket serverSocket = serverSocketChannel.socket();
    InetSocketAddress address = new InetSocketAddress(port);
    //将服务器绑定到选定的端口
    serverSocket.bind(address);
    //打开Selector来处理Channel
    Selector selector = Selector.open();
    //将ServerSocket注册到Selector以接受链接
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    final ByteBuffer msg = ByteBuffer.wrap("Hi\r\n".getBytes());
    for (;;) {
      try {
        //等待需要处理的新事件；阻塞将一直持续到下一个传入事件
        selector.select();
      } catch (IOException ex) {
        ex.printStackTrace();
        break;
      }
      //获取所有接收事件的SelectionKey
      Set<SelectionKey> readyKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = readyKeys.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();

        try {
          //检测事件是否是一个新的已经就绪可以被接受的链接
          if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            // 接受客户端的，并将它注册到选择器
            client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ,
                msg.duplicate());
            LOG.info("Accepted connection from " + client);
          }

          //检测套接字是否已经准备好写数据
          if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
            while (byteBuffer.hasRemaining()) {
              if (client.write(byteBuffer) == 0) {
                break;
              }
            }
            //关闭连接
            client.close();
          }

        } catch (IOException ex) {
            key.cancel();
            key.channel().close();
        }
      } //end while

    }// end for
  }
}