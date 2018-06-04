package com.wilson.exec;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.launcher.CommandLauncher;
import org.apache.commons.exec.launcher.Java13CommandLauncher;

/**
 * 自定义执行器.
 *
 * @author zhangweilong
 * @create 11/29/17 00:17
 **/
public class MyJava13Executor extends DefaultExecutor {

    @Override
    protected Process launch(CommandLine command, Map<String, String> env, File dir) throws IOException {
        CommandLauncher launcher =  new Java13CommandLauncher();
//        return super.launch(command, env, dir);

//        if (this.launcher == null) {
//            throw new IllegalStateException("CommandLauncher can not be null");
//        }

        if (dir != null && !dir.exists()) {
            throw new IOException(dir + " doesn't exist.");
        }
        return launcher.exec(command, env, dir);
    }
}
