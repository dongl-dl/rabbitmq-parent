package com.dongl.rabbitmq.service.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @ClassName NettyMQServer2021
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
public class MayiktNettyMQServer {
    public void bind(int port) throws Exception {
        /**
         * Netty 抽象出两组线程池BossGroup和WorkerGroup
         * BossGroup专门负责接收客户端的连接, WorkerGroup专门负责网络的读写。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    // 设定NioServerSocketChannel 为服务器端
                    .channel(NioServerSocketChannel.class)
                    //BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，
                    //用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                    .option(ChannelOption.SO_BACKLOG, 100)
                    // 服务器端监听数据回调Handler
                    .childHandler(new ChildChannelHandler());
            //绑定端口, 同步等待成功;
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("当前服务器端启动成功...");
            //等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅关闭 线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // 设置异步回调监听
            ch.pipeline().addLast(new MayiktServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 9008;
        new MayiktNettyMQServer().bind(port);
    }

    private static final String type_consumer = "consumer";

    private static final String type_producer = "producer";
    private static LinkedBlockingDeque<String> msgs = new LinkedBlockingDeque<>();
    private static ArrayList<ChannelHandlerContext> ctxs = new ArrayList<>();

    // 生产者投递消息的：topicName
    public class MayiktServerHandler extends SimpleChannelInboundHandler<Object> {

        /**
         * 服务器接收客户端请求
         *
         * @param ctx
         * @param data
         * @throws Exception
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object data)
                throws Exception {
            JSONObject clientMsg = getData(data);
            String type = clientMsg.getString("type");
            switch (type) {
                case type_producer:
                    producer(clientMsg);
                    break;
                case type_consumer:
                    consumer(ctx);
                    break;
            }
        }

        private void consumer(ChannelHandlerContext ctx) {
            // 保存消费者连接
            ctxs.add(ctx);
            // 主动拉取mq服务器端缓存中没有被消费的消息
            String data = msgs.poll();
            if (StringUtils.isEmpty(data)) {
                return;
            }
            // 将该消息发送给消费者
            byte[] req = data.getBytes();
            ByteBuf firstMSG = Unpooled.buffer(req.length);
            firstMSG.writeBytes(req);
            ctx.writeAndFlush(firstMSG);
        }

        private void producer(JSONObject clientMsg) {
            // 缓存生产者投递 消息
            String msg = clientMsg.getString("msg");
            msgs.offer(msg);

            //需要将该消息推送消费者
            ctxs.forEach((ctx) -> {
                // 将该消息发送给消费者
                String data = msgs.poll();
                if (data == null) {
                    return;
                }
                byte[] req = data.getBytes();
                ByteBuf firstMSG = Unpooled.buffer(req.length);
                firstMSG.writeBytes(req);
                ctx.writeAndFlush(firstMSG);
            });
        }

        private JSONObject getData(Object data) throws UnsupportedEncodingException {
            ByteBuf buf = (ByteBuf) data;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "UTF-8");
            return JSONObject.parseObject(body);
        }


        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {

            ctx.close();
        }
    }
}
