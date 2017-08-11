package com.sillyhat.main;

import com.sillyhat.jetty.factory.JettyFactory;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class StartServer {
    
    private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

//    public static final int PORT = 8108;
    public static final int PORT = 8109;

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
            if(isMacPlatform()){
                //mac环境下打开浏览器
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
                openURL.invoke(null, new Object[] { BASE_URL });
            }else{
                //Windows 运行cmd，启动成功后打开浏览器页面
                Runtime.getRuntime().exec("cmd.exe /c start " + BASE_URL);
            }
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


    private static final String MAC_ID = "Mac";
    private static boolean isMacPlatform() {
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(MAC_ID))
            return true;
        else
            return false;
    }

//    import java.lang.reflect.Method;
//
//    public class OpenUrl {
//
//        public static void openURL(String url) {
//            try {
//                browse(url);
//            } catch (Exception e) {
//            }
//        }
//
//        private static void browse(String url) throws Exception {
//            // 获取操作系统的名字
//            String osName = System.getProperty("os.name", "");
//            if (osName.startsWith("Mac OS")) {
//                // 苹果
//                Class fileMgr = Class.forName("com.apple.eio.FileManager");
//                Method openURL = fileMgr.getDeclaredMethod("openURL",
//                        new Class[] { String.class });
//                openURL.invoke(null, new Object[] { url });
//            } else if (osName.startsWith("Windows")) {
//                // windows
//                Runtime.getRuntime().exec(
//                        "rundll32 url.dll,FileProtocolHandler " + url);
//            } else {
//                // Unix or Linux
//                String[] browsers = { "firefox", "opera", "konqueror", "epiphany",
//                        "mozilla", "netscape" };
//                String browser = null;
//                for (int count = 0; count < browsers.length && browser == null; count++)
//                    // 执行代码，在brower有值后跳出，
//                    // 这里是如果进程创建成功了，==0是表示正常结束。
//                    if (Runtime.getRuntime()
//                            .exec(new String[] { "which", browsers[count] })
//                            .waitFor() == 0)
//                        browser = browsers[count];
//                if (browser == null)
//                    throw new Exception("Could not find web browser");
//                else
//                    // 这个值在上面已经成功的得到了一个进程。
//                    Runtime.getRuntime().exec(new String[] { browser, url });
//            }
//        }
//    }
}