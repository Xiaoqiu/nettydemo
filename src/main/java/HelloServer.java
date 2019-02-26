/**
 * @author kate
 * @create 2018/11/6
 * @since 1.0.0
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.hyperic.sigar.SigarException;

/**
 * netty 服务器
 * @author huangxiaoqiu
 * @since 1.0.0
 */

public class HelloServer {

  public static void main(String[] args) throws SigarException {

    //boss 线程监听端口，worker线程负责数据读写
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    try {
      //辅助启动类
      ServerBootstrap bootstrap = new ServerBootstrap();
      //设置线程池
      bootstrap.group(boss,worker);
      //设置socket工厂
      bootstrap.channel(NioServerSocketChannel.class);
      //设置管道工厂
      bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
          //获取管道
          ChannelPipeline pipeline = socketChannel.pipeline();
          //字符串解码器
          pipeline.addLast(new StringDecoder());
          //字符串编码器
          pipeline.addLast(new StringEncoder());
          //处理类
          pipeline.addLast(new ServerHandler4());

        }
      });
      //设置TCP参数
      //1. 链接缓冲池大小（ServerSocketChannel的设置）
      bootstrap.option(ChannelOption.SO_BACKLOG,1024);
      // 维持链接的活跃，清除死链接（SocketChannel的设置）
      bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
      //关闭延迟发送
      bootstrap.childOption(ChannelOption.TCP_NODELAY,true);
      //绑定端口
      ChannelFuture future = bootstrap.bind(8866).sync();
      //logger.error("server start....");
      System.out.print("server start....\n");
      future.channel().closeFuture().sync();

    } catch (InterruptedException e){
      e.printStackTrace();
    } finally {
      //优雅退出，释放线程池资源
      boss.shutdownGracefully();
      worker.shutdownGracefully();
    }

  }

  /**
   *
   */
  static class ServerHandler4 extends SimpleChannelInboundHandler<String> {


    //读取客户端发送的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
       System.out.print("client response :" + msg + "\n");
        ctx.channel().writeAndFlush("i am server !");

    }

    /**
     * 新客户端接入
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      System.out.print("channelActive\n");
      //ogger.error("channelActive");
    }

    /**
     * 客户端断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      System.out.print("channelInactive");
      //logger.error("channelInactive");
    }

    /**
     * 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      //关闭通道
      ctx.channel().close();
      //打印异常
      cause.printStackTrace();
    }




  }

}