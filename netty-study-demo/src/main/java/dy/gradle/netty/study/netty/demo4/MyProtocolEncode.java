package dy.gradle.netty.study.netty.demo4;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器，协议 -> byteBuf
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class MyProtocolEncode extends MessageToByteEncoder<MyProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
        try {

            String msgJson = JSONObject.toJSONString(msg);
            byte[] msgBytes = msgJson.getBytes("UTF-8");

            // 先填入一个4字节int，表明有效数据的长度
            out.writeInt(msgBytes.length);
            out.writeBytes(msgBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
