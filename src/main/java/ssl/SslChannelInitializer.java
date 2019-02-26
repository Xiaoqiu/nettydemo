package ssl;
/**
 * @author kate
 * @create 2019/2/26
 * @since 1.0.0
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @ClassName SslChannelInitializer
 * @Description ssl的Channel初始化
 * @Author Huang Xiaoqiu
 * @Date 2019/2/26 16:40
 * @Version 1.0.0
 **/
public class SslChannelInitializer extends ChannelInitializer<Channel> {
  private final SslContext context;
  private final boolean startTls;

  /**
   * 传入使用的SslContext，如果startTls设置为true,第一个写入的消息将不会被加密
   * （客户端应该设置为true）
   * @param context
   * @param startTls
   */
  public SslChannelInitializer(SslContext context, boolean startTls) {
    this.context = context;
    this.startTls = startTls;
  }

  /**
   *
   * @param ch
   * @throws Exception
   */
  @Override
  protected void initChannel(Channel ch) throws Exception {
    //对于每个SslHandler实例，都使用Channel的ByteBufAllocator从SslContext获取一个新的SSLEngine
    SSLEngine engine = context.newEngine(ch.alloc());
    //将SslHandler作为第一个ChannelHandler添加到ChannelPiPeline中
    //SslHandler一般都是第一个ChannelHandler
    ch.pipeline().addFirst("ssl",
        new SslHandler(engine,startTls));

  }
}