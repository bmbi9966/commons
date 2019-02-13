package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SysTest {

    @Test
    void osName() {
        assertNotNull(Sys.osName());
    }

    @Test
    void osType() {
        assertNotNull(Sys.osType());
    }

    @Test
    void userHome() {
        assertNotNull(Sys.userHome());
    }

    @Test
    void userHomePath() {
        assertNotNull(Sys.userHomePath());
    }

    @Test
    void tmpDir() {
        assertNotNull(Sys.tmpDir());
    }

    @Test
    void tmpDirPath() {
        assertNotNull(Sys.tmpDirPath());
    }

    @Test
    void userName() {
        assertNotNull(Sys.userName());
    }

    @Test
    void javaSpecVersionName() {
        assertNotNull(Sys.javaSpecVersionName());
    }

    @Test
    void workDir() {
        assertNotNull(Sys.workDir());
    }

    @Test
    void workDirPath() {
        assertNotNull(Sys.workDirPath());
    }

    @Test
    void lineSeparator() {
        assertNotNull(Sys.lineSeparator());
    }

    @Test
    void javaSpecVersion() {
        assertNotNull(Sys.javaSpecVersion());
    }

    @Test
    void javaVersionName() {
        assertNotNull(Sys.javaVersionName());
    }
}