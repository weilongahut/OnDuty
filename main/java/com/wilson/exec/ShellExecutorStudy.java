package com.wilson.exec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * shell执行类研究.
 *
 * @author zhangweilong
 * @create 11/28/17 23:12
 **/
public class ShellExecutorStudy {

    public static void main(String[] args) {

//        runCommandLine("echo test1 >> /tmp/test/testShell.txt");

//        runCommandWithBos("echo test1");

        runCommandArray();



    }



    private static void runCommandArray() {
//        String [] cmd = {
//            "/bin/bash",
//            "-c",
//            "echo test_command_array >> /tmp/test/testShell.txt"
//        };
//        try {
//            Runtime.getRuntime().exec(cmd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        CommandLine commandLine = CommandLine.parse("/bin/bash -c \'echo test_command_array_with_command_line >> /tmp/test/testShell.txt\'");
//        commandLine.addArgument("-c");

//        commandLine.addArgument("echo test_command_array_with_command_line >> /tmp/test/testShell.txt");
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.execute(commandLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runCommandWithBos(String command) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CommandLine cmdLine = CommandLine.parse("echo test_with_bos");
        PumpStreamHandler psh = new PumpStreamHandler(bos, System.out);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(psh);
        try {
            executor.execute(cmdLine);
            System.out.println(bos.toString());
            Path path = Paths.get("/tmp/test/testShell.txt");
            Files.write(path, bos.toByteArray(), StandardOpenOption.APPEND);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void runCommandLine(String command) {

        CommandLine commandLine = CommandLine.parse(command);
//        commandLine.addArgument(" test > /tmp/test/testShell.txt");
        System.out.println(commandLine.getExecutable());
//        DefaultExecutor executor = new DefaultExecutor();
        DefaultExecutor executor = new MyJava13Executor();
        try {
            executor.execute(commandLine);
            Runtime.getRuntime().exec("echo test1 >> /tmp/test/testShell.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
