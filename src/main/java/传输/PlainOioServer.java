package 传输;
/**
 * @author kate
 * @create 2019/3/3
 * @since 1.0.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.oops.OopUtilities;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @ClassName PlainOioServer
 * @Description 未使用Netty的阻塞网络编程: 这个是示例是阻塞版本
 * 使用JDK API的应用程序的(OIO)阻塞版本和异步(NIO)版本实现
 * @Author Huang Xiaoqiu
 * @Date 2019/3/3 20:07
 * @Version 1.0.0
 **/
public class PlainOioServer {
  private static final Logger LOG = LoggerFactory.getLogger(PlainOioServer.class);

  public void serve(int port) throws IOException {
    //将服务器绑定到指定端口
    final ServerSocket socket = new ServerSocket(port);
    try {
      for (;;) {
        //接收链接
        final Socket clientSocket = socket.accept();
        LOG.info("Accepted connetion from " + clientSocket);
        // 创建一个新的线程来处理该链接
        new Thread(new Runnable() {
          public void run() {
            OutputStream outputStream = null;
            try {
              outputStream = clientSocket.getOutputStream();
              //将消息写给已经连接的客户端
              outputStream.write("Hi\r\n".getBytes());
              Charset.forName("UTF-8");
              outputStream.flush();
            } catch (IOException e) {
              e.printStackTrace();
            } finally {
              //关闭连接
              try {
                clientSocket.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}