package http_https;
/**
 * @author kate
 * @create 2019/2/28
 * @since 1.0.0
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;

/**
 * @ClassName HttpPipelineInitializer
 * @Description 将ChannelHandler添加到ChannelPipeline
 * @Author Huang Xiaoqiu
 * @Date 2019/2/28 08:43
 * @Version 1.0.0
 **/
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
  private final boolean client;

  public HttpPipelineInitializer(boolean client) {
    this.client = client;
  }

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();


    if (client) {
      //如果是客户端，加HttpResponseDecoder处理来自服务器的响应。
      pipeline.addLast("decoder",new HttpResponseDecoder());

      //如果是客户端，加HttpRequestEncoder以向服务器发送请求。
      pipeline.addLast("encoder",new HttpRequestEncoder());
    } else {
      //如果是服务端，加HttpRequestDecoder以接收来自客户端的请求。
      pipeline.addLast("decoder", new HttpRequestDecoder());
      //如果是服务端，加HttpResponseEncoder以向客户端发送响应。
      pipeline.addLast("encoder",new HttpResponseEncoder());
    }
  }
}