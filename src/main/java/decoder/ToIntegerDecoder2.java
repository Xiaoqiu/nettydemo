package decoder;
/**
 * @author kate
 * @create 2019/2/25
 * @since 1.0.0
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.http.HttpObjectDecoder;

import java.util.List;

/**
 * @ClassName ToIntegerDecoder2
 * @Description 字节解码为消息
 * @Author Huang Xiaoqiu
 * @Date 2019/2/25 15:41
 * @Version 1.0.0
 **/
public class ToIntegerDecoder2 extends ReplayingDecoder {

  //ByteBuf 为 ReplayingDecoderByteBuf
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    //从入站ByteBuf读取一个int,并添加到List
    // 1 如果没有足够字节可用，readInt()抛出Error,并在基类中捕获并处理
    // 2 当有更多的数据可供读取时，该decode()方法会被再次调用
    // 3 不是所有的ByteBuf操作可用
    // 4 ReplayingDecoder慢于ByteToMessageDecoder
    out.add(in.readInt());
  }
}