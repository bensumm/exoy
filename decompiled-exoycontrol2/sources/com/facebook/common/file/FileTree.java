package com.facebook.common.file;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class FileTree {
    public static void walkFileTree(File directory, FileTreeVisitor visitor) {
        visitor.preVisitDirectory(directory);
        File[] fileArrListFiles = directory.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    walkFileTree(file, visitor);
                } else {
                    visitor.visitFile(file);
                }
            }
        }
        visitor.postVisitDirectory(directory);
    }

    public static boolean deleteContents(File directory) {
        File[] fileArrListFiles = directory.listFiles();
        boolean zDeleteRecursively = true;
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                zDeleteRecursively &= deleteRecursively(file);
            }
        }
        return zDeleteRecursively;
    }

    public static boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            deleteContents(file);
        }
        return file.delete();
    }
}
