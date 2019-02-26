package decoder;
/**
 * @author kate
 * @create 2019/2/25
 * @since 1.0.0
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.util.List;

/**
 * @ClassName IntegerToStringDecoder
 * @Description POJO类型转换为另一种POJO类型的解码器
 * @Author Huang Xiaoqiu
 * @Date 2019/2/25 15:59
 * @Version 1.0.0
 **/
public class IntegerToStringDecoder extends MessageToMessageDecoder {
  @Override
  protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
    out.add(String.valueOf(msg));
  }
}