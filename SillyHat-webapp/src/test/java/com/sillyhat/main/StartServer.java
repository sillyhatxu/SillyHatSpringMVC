package com.sillyhat.main;

import com.sillyhat.jetty.factory.JettyFactory;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartServer {
    
    private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

    public static final int PORT = 8108;
//    public static final int PORT = 8109;

    public static final String CONTEXT = "/SillyHatSpringMVC";

    public static final String BASE_URL = "http://localhost:" + PORT + CONTEXT;

    public static final String webappPath = System.getProperty("user.dir") + "/SillyHat-webapp/src/main/webapp";

    public static void main(String[] args) throws Exception {
        System.setProperty("spring.profiles.active", "development");
        try {
            JettyFactory jettyFactory = JettyFactory.getInstance();
            //使用自定义webapp路径，适用于module方式maven项目
            Server server = jettyFactory.createServerInSource(PORT, CONTEXT,webappPath);
            //使用默认webapp路径，适用于普通maven项目
//			Server server = jettyFactory.createServerInSource(PORT, CONTEXT);
            server.start();// 启动Jetty
            logger.info("[INFO] Server running at http://localhost:" + PORT + CONTEXT);
            logger.info("Start End!");
            //运行cmd，启动成功后打开浏览器页面
            Runtime.getRuntime().exec("cmd.exe /c start " + BASE_URL);
            logger.info("[HINT] Hit Enter to reload the application quickly");
            // 等待用户输入回车重载应用.
            while (true) {
                char c = (char) System.in.read();
                if (c == '\n') {
                    jettyFactory.reloadContext(server);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}