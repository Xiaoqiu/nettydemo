package websocket;
/**
 * @author kate
 * @create 2019/3/3
 * @since 1.0.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName IdleStateHandlerInitializer
 * @Description 发送心跳消息到远程节点的方法，如果在60秒之内没有接收或者发送任何任何的数据，我们将得到通知，
 * 如果没有响应，则链接会被关闭
 * @Author Huang Xiaoqiu
 * @Date 2019/3/3 19:27
 * @Version 1.0.0
 **/
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    //IdleStateHandler 将被触发时发送一个IdleStateEvent事件
    pipeline.addLast(new IdleStateHandler(0,0,60, TimeUnit.SECONDS));
    pipeline.addLast(new HeartbeatHandler());
  }

  /**
   * 实现userEventTriggered()方法以发送心跳消息,
   * 演示如何使用IdleStateHandler来测试远程节点是否仍然还活着，并在它失活时通过关闭连接来释放资源。
   */
  public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    //发送到远程节点的心跳消息
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
        "HEARTBEAT", CharsetUtil.ISO_8859_1
    ));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      if (evt instanceof IdleStateEvent) {
        //发送心跳消息，并发送失败时关闭该链接
        ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
      } else {
        // 不是IdleStateEvent事件，所以将它传递给下一个ChannelInboundHandler
        super.userEventTriggered(ctx,evt);
      }
    }
  }
}