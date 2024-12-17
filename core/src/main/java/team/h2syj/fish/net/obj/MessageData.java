package team.h2syj.fish.net.obj;

import cn.hutool.core.convert.Convert;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageData {
    private Type type;
    private Object data;

    @Getter
    public enum Type {
        PING,
        PONG,
        /**
         * 服务端接收到连接请求 </br>
         * {@link ConnectionData}
         */
        连接成功,
        /**
         * 服务端接收到n个请求后达到指定人数发送的开启游戏指令
         */
        游戏开始,
        /**
         * 服务端发送需要渲染的内容到客户端，客户端收到后输出在控制台
         */
        渲染内容,
        /**
         * 服务端发送消息给客户端等待输入
         */
        等待输入,
        /**
         * 客户端输入完成返回给服务端输入内容
         */
        输入完成,
        /**
         * 服务发送游戏结束指令终止连接
         */
        游戏结束,;

        public MessageData initMessage() {
            MessageData messageData = new MessageData();
            messageData.setType(this);
            return messageData;
        }
    }

    public <T> T convert(Class<T> clazz) {
        return Convert.convert(clazz, data, null);
    }
}
