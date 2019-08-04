package dy.gradle.netty.study.netty.demo4;


import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 类的描述
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/8/4
 */
public class MyProtocolDecoder extends LengthFieldBasedFrameDecoder {

    public MyProtocolDecoder() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = null;
        try {
            byteBuf = (ByteBuf) super.decode(ctx, in);
            if (null == byteBuf){
                return null;
            }

            // decode
            int total = byteBuf.readableBytes();

            byte[] bytes = new byte[total];
            byteBuf.readBytes(bytes);

            // json serialized
            return JSONObject.parseObject(new String(bytes, "UTF-8"), MyProtocol.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != byteBuf) {
                byteBuf.release();
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception{
        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setType(MyProtocol.TYPE_GET_ONLINE_NUM);
        myProtocol.setName("hi");
        myProtocol.add("key", Long.MAX_VALUE);

        String json = JSONObject.toJSONString(myProtocol);
        MyProtocol jsonAfter = JSONObject.parseObject(json, MyProtocol.class);
        System.out.println(jsonAfter.getType());
    }
}
