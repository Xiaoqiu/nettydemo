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
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName HttpConpressionInitializer
 * @Description 添加自动压缩HTTP消息ChannelHandler
 * @Author Huang Xiaoqiu
 * @Date 2019/2/28 09:28
 * @Version 1.0.0
 **/
public class HttpConpressionInitializer extends ChannelInitializer<Channel> {
  private final boolean isClient;

  public HttpConpressionInitializer(boolean isClient) {
    this.isClient = isClient;
  }

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    if (isClient) {
      //客户端，解压
      pipeline.addLast("codec", new HttpClientCodec());
      pipeline.addLast("decompressor",new HttpContentDecompressor());
    } else {
      //服务端，压缩数据
      pipeline.addLast("codex",new HttpServerCodec());
      pipeline.addLast("compressor",new HttpContentCompressor());
    }
  }
}