package monica.files.socket;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Properties;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import monica.framework.Client;
import monica.framework.tools.Utils;
import monica.framework.transport.TransportFile;

/**
 * 
 * @author lucy@polarcoral.com
 *
 *         2017-08-29
 */
public class SocketClient implements Client {
	public final static String NAME = "socket";
	private volatile boolean isStarted = false;
	static final boolean SSL = System.getProperty("ssl") != null;

	public void start(String ip, int port) throws Exception {
		Properties pps = new Properties();
		File file = Utils.getPropertiesPath("path.properties");
		pps.load(new FileInputStream(file));
		String filePath = pps.getProperty("client.path");
		// Configure SSL.
		final SslContext sslCtx;
		if (SSL) {
			sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} else {
			sslCtx = null;
		}

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new FileClientInitializer(sslCtx));
			Channel ch = b.connect(ip, port).sync().channel();
			File newFile = new File(filePath+File.separator+"019.mp3");
			FileChannel channel = (new FileInputStream(newFile)).getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while (channel.read(byteBuffer) > 0);
			TransportFile transportFile = new TransportFile();
			transportFile.setContent(byteBuffer.array());
			transportFile.setFileName(newFile.getName());
			ChannelFuture f = ch.writeAndFlush(transportFile);
			// Wait until the connection is closed.
			f.sync();

			//Thread.sleep(10000000);
		} finally {
			group.shutdownGracefully();
		}
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public boolean isStarted() {
		return this.isStarted;
	}

}
