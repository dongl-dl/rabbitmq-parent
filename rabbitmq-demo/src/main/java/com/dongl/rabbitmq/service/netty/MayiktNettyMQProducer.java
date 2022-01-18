package com.dongl.rabbitmq.service.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MayiktNettyMQProducer {
    public void connect(int port, String host) throws Exception {
        //配置客户端NIO 线程组
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        try {
            client.group(group)
                    // 设置为Netty客户端
                    .channel(NioSocketChannel.class)
                    /**
                     * ChannelOption.TCP_NODELAY参数对应于套接字选项中的TCP_NODELAY,该参数的使用与Nagle算法有关。
                     * Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次，因此在数据包不足的时候会等待其他数据的到来，组装成大的数据包进行发送，虽然该算法有效提高了网络的有效负载，但是却造成了延时。
                     * 而该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输。和TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量最大的时候，一次性发送数据，适用于文件传输。
                     */
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
////                            1. 演示LineBasedFrameDecoder编码器
//                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                            ch.pipeline().addLast(new StringDecoder());
                        }
                    });

            //绑定端口, 异步连接操作
            ChannelFuture future = client.connect(host, port).sync();
            //等待客户端连接端口关闭
            future.channel().closeFuture().sync();
        } finally {
            //优雅关闭 线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 9008;
        MayiktNettyMQProducer client = new MayiktNettyMQProducer();
        try {
            client.connect(port, "127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class NettyClientHandler extends ChannelInboundHandlerAdapter {


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            JSONObject data = new JSONObject();
            data.put("type", "producer");
            JSONObject msg = new JSONObject();
            msg.put("userId", "123456");
            msg.put("age", "23");
            data.put("msg", msg);
            // 生产发送数据
            byte[] req = data.toJSONString().getBytes();
            ByteBuf firstMSG = Unpooled.buffer(req.length);
            firstMSG.writeBytes(req);
            ctx.writeAndFlush(firstMSG);
        }

        /**
         * 客户端读取到服务器端数据
         *
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "UTF-8");
            System.out.println("客户端接收到服务器端请求:" + body);
        }

        // tcp属于双向传输

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
