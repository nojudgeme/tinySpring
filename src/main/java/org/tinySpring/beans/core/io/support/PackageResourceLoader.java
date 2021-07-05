package org.tinySpring.beans.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tinySpring.beans.core.io.FileSystemResource;
import org.tinySpring.beans.core.io.Resource;
import org.tinySpring.utils.Assert;
import org.tinySpring.utils.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class PackageResourceLoader {

    private static final Log LOGGER = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
        ;
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "classLoader must not be null");
        this.classLoader = classLoader;
    }

    public Resource[] load(String basePackage) {
        Assert.notNull(basePackage, "basePackage must not be null");
        //类名转化为路径名 如org.tinySpring.test.service->org/tinySpring/test/service
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader classLoader = getClassLoader();
        URL resourceUrl = classLoader.getResource(location);
        File rootDir = new File(resourceUrl.getFile());
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for (File matchingFile : matchingFiles) {
            result[i++] = new FileSystemResource(matchingFile);
        }
        return result;
    }

    protected Set<File> retrieveMatchingFiles(File rootDir) {
        if (!rootDir.exists()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it's not exist");
            }
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it's not denote a directory");
            }
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("has not read permissions for [" + rootDir.getAbsolutePath() + "]");
            }
            return Collections.emptySet();
        }
        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    protected void doRetrieveMatchingFiles(File dir, Set<File> result) {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("can't retrieve contents of directory [ " + dir.getAbsolutePath() + "]");
            }
            return;
        }
        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (!content.canRead()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("has not read permissions for [" + content.getAbsolutePath() + "]");
                    }
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
