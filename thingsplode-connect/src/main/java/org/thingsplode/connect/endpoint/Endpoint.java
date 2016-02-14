/*
 * Copyright 2016 tamas.csaba@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsplode.connect.endpoint;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollServerDomainSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.unix.DomainSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.net.SocketAddress;
import org.thingsplode.connect.core.Transport;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author tamas.csaba@gmail.com
 */
public class Endpoint {

    Logger logger = LoggerFactory.getLogger(Endpoint.class);
    public static final String ALL_CHANNEL_GROUP_NAME = "all-open-channels";
    private static int TERMINATION_TIMEOUT = 60;//number of seconds to wait after the worker threads are shutted down and until those are finishing the last bit of the execution.
    private EventLoopGroup masterGroup = null;//the eventloop group used for the server socket
    private EventLoopGroup workerGroup = null;//the event loop group used for the connected clients
    private final ChannelGroup channelRegistry = new DefaultChannelGroup(ALL_CHANNEL_GROUP_NAME, GlobalEventExecutor.INSTANCE);//all active channels are listed here
    private String endpointId;
    private Transport transport;
    private final ServerBootstrap bootstrap = new ServerBootstrap();

    private Endpoint() {
    }

    private Endpoint(String id, Transport trp) {
        this.endpointId = id;
        this.transport = trp;
    }

    /**
     *
     * @param endpointId
     * @param transport
     * @return
     * @throws java.lang.InterruptedException
     */
    public static Endpoint create(String endpointId, Transport transport) throws InterruptedException {
        Endpoint ep = new Endpoint(endpointId, transport);

        try {
            ep.initGroups();
            ep.bootstrap.group(ep.masterGroup, ep.workerGroup);
            if (transport.getTransportType().equals(Transport.TransportType.SOCKET)) {
                ep.bootstrap.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
            } else {
                ep.bootstrap.channel(EpollServerDomainSocketChannel.class).childHandler(new ChannelInitializer<DomainSocketChannel>() {
                    @Override
                    protected void initChannel(DomainSocketChannel ch) throws Exception {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
            }
            ep.bootstrap.option(ChannelOption.SO_BACKLOG, 3).childOption(ChannelOption.SO_KEEPALIVE, true);
        } catch (Exception ex) {
            ep.logger.error(Endpoint.class.getSimpleName() + " interrupted due to: " + ex.getMessage(), ex);
        } finally {
            ep.logger.debug("Adding shutdown hook for the endpoint.");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ep.logger.info("Stopping endpoint [%s].", endpointId);
                ep.stop();
            }));
        }
        ep.start();
        return ep;
    }

    /**
     *
     * @throws InterruptedException
     */
    private void start() throws InterruptedException {
        for (SocketAddress addr : transport.getSocketAddresses()) {
            ChannelFuture channelFuture = bootstrap.bind(addr).sync();
            Channel channel = channelFuture.await().channel();
            channelRegistry.add(channel);
        }
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void initGroups() throws InterruptedException {
        if (masterGroup == null && workerGroup == null) {
            masterGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            return;
        }

        if (workerGroup.isShuttingDown()) {
            logger.debug("Worker Event Loop Group is awaiting termination.");
            workerGroup.awaitTermination(TERMINATION_TIMEOUT, TimeUnit.SECONDS);
        }

        if (masterGroup.isShuttingDown()) {
            logger.debug("Master Event Loop Group is awaiting termination.");
            masterGroup.awaitTermination(TERMINATION_TIMEOUT, TimeUnit.SECONDS);
        }

        if (masterGroup.isShutdown()) {
            logger.debug("Creating new Master Event Loop Group.");
            masterGroup = new NioEventLoopGroup();
        } else {
            logger.debug("Master Event Loop Group will not be reinitialized because it is not yet shut down.");
        }
        if (workerGroup.isShutdown()) {
            logger.debug("Creating new Worker Event Loop Group.");
            workerGroup = new NioEventLoopGroup();
        } else {
            logger.debug("Worker Event Loop Group will not be reinitialized because it is not yet shut down.");
        }
    }

    public Endpoint publish(String serviceID, Object serviceInstance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
