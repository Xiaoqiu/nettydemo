package decoder;
/**
 * @author kate
 * @create 2019/2/26
 * @since 1.0.0
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @ClassName InteferToStringEncoder
 * @Description 编码器
 * @Author Huang Xiaoqiu
 * @Date 2019/2/26 08:11
 * @Version 1.0.0
 **/
public class InteferToStringEncoder extends MessageToMessageEncoder<Integer> {

  @Override
  public void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
    out.add(String.valueOf(msg));
  }
}