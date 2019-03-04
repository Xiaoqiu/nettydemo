package http_https;
/**
 * @author kate
 * @create 2019/2/28
 * @since 1.0.0
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HttpAggregatorInitializer
 * @Description 添加HTTP/HTTPS 消息聚合ChannelHandler
 * @Author Huang Xiaoqiu
 * @Date 2019/2/28 08:57
 * @Version 1.0.0
 **/
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
  private final boolean isClient;

  public HttpAggregatorInitializer(boolean isClient) {
    this.isClient = isClient;
  }

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    if (isClient) {
      //客户端, 添加HttpClientCodec
      pipeline.addLast("codec" , new HttpClientCodec());
    } else {
      //服务器，添加HttpServerCodec
      pipeline.addLast("codec", new HttpServerCodec());
    }
    // 将消息消息大小为512KB的HttpObjectAggregator添加到ChannelPipeline
    pipeline.addLast("aggregator",new HttpObjectAggregator(512 * 1024));

  }
}