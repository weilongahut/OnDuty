package com.wilson.io;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

/**
 * Files研究.
 * 生成文件之后如果设置文件的读写权限
 * @author zhangweilong
 * @create 11/28/17 09:41
 **/
public class FilesAPI {

    public static void main(String[] args) {
        Path path = Paths.get("/Users/didi/Documents/test_file/test.txt");
        try {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class,
                LinkOption.NOFOLLOW_LINKS);

            // 设置只允许owner读写
            Set<PosixFilePermission> permissionSet = new HashSet<>();
            permissionSet.add(PosixFilePermission.OWNER_WRITE);
            permissionSet.add(PosixFilePermission.OWNER_READ);
            Files.setPosixFilePermissions(path, permissionSet);
            System.out.println(attributes.permissions());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
