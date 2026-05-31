package com.facebook.common.file;

import com.facebook.common.internal.Preconditions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class FileUtils {
    public static void mkdirs(File directory) throws CreateDirectoryException {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                return;
            }
            if (!directory.delete()) {
                throw new CreateDirectoryException(directory.getAbsolutePath(), new FileDeleteException(directory.getAbsolutePath()));
            }
        }
        if (!directory.mkdirs() && !directory.isDirectory()) {
            throw new CreateDirectoryException(directory.getAbsolutePath());
        }
    }

    public static void rename(File source, File target) throws RenameException {
        Throwable fileDeleteException;
        Preconditions.checkNotNull(source);
        Preconditions.checkNotNull(target);
        target.delete();
        if (source.renameTo(target)) {
            return;
        }
        if (target.exists()) {
            fileDeleteException = new FileDeleteException(target.getAbsolutePath());
        } else if (!source.getParentFile().exists()) {
            fileDeleteException = new ParentDirNotFoundException(source.getAbsolutePath());
        } else {
            fileDeleteException = !source.exists() ? new FileNotFoundException(source.getAbsolutePath()) : null;
        }
        throw new RenameException("Unknown error renaming " + source.getAbsolutePath() + " to " + target.getAbsolutePath(), fileDeleteException);
    }

    public static class CreateDirectoryException extends IOException {
        public CreateDirectoryException(String message) {
            super(message);
        }

        public CreateDirectoryException(String message, Throwable innerException) {
            super(message);
            initCause(innerException);
        }
    }

    public static class ParentDirNotFoundException extends FileNotFoundException {
        public ParentDirNotFoundException(String message) {
            super(message);
        }
    }

    public static class FileDeleteException extends IOException {
        public FileDeleteException(String message) {
            super(message);
        }
    }

    public static class RenameException extends IOException {
        public RenameException(String message) {
            super(message);
        }

        public RenameException(String message, @Nullable Throwable innerException) {
            super(message);
            initCause(innerException);
        }
    }
}
