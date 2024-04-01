package team.h2syj.fish.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import cn.hutool.json.JSONUtil;
import team.h2syj.fish.server.obj.MessageData;

public class Server extends Thread implements Closeable {
    public static final int port = 2333;
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public Server() {
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);

            this.selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (serverSocketChannel.isOpen()) {
            try {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        // 接受客户端
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        // 读取客户端传输
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = socketChannel.read(buffer);

                        if (bytesRead == -1) {
                            // 客户端断开连接
                            socketChannel.close();
                        } else {
                            buffer.flip();
                            byte[] data = new byte[buffer.limit()];
                            buffer.get(data);
                            // 接受到消息
                            String msg = new String(data);
                            MessageData messageData = JSONUtil.toBean(msg, MessageData.class);
                            ServerProcessor processor = messageData.getType().getProcessor();
                            processor.execute(messageData);
                        }
                    }
                }
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (serverSocketChannel.isOpen())
            serverSocketChannel.close();
    }

}
