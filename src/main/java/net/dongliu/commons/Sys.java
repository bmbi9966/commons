package net.dongliu.commons;

import net.dongliu.commons.exception.UnknownSpecificationVersionException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * Util methods to get sys properties.
 */
public class Sys {

    /**
     * Get Java Runtime Environment specification version, the return string as 1.5, 1.8, 9, 11
     */
    public static String javaSpecVersionName() {
        return System.getProperty("java.specification.version");
    }

    private static final Lazy<SpecificationVersion> specVersion = Lazy.of(() ->
            SpecificationVersion.parse(javaSpecVersionName()));

    /**
     * Get Java Runtime Environment specification version
     *
     * @throws UnknownSpecificationVersionException if version name is unknown
     */
    public static SpecificationVersion javaSpecVersion() {
        return specVersion.get();
    }

    /**
     * Java specification version
     */
    public enum SpecificationVersion {
        v2("1.2"), v3("1.3"), v4("1.4"), v5("1.5"), v6("1.6"), v7("1.7"), v8("1.8"),
        v9("9"), v10("10"), v11("11"), v12("12"), v13("13"), v14("14"), v15("14"), v16("14"), v17("14");
        private final String versionName;

        SpecificationVersion(String versionName) {
            this.versionName = versionName;
        }

        /**
         * the version name string
         */
        public String versionName() {
            return versionName;
        }

        /**
         * If this version is before the given version
         */
        public boolean before(SpecificationVersion version) {
            return this.ordinal() < version.ordinal();
        }

        /**
         * If this version is after the given version
         */
        public boolean after(SpecificationVersion version) {
            return this.ordinal() > version.ordinal();
        }

        /**
         * SpecificationVersion from version name.
         *
         * @throws UnknownSpecificationVersionException if version name is unknown
         * @throws NullPointerException                 if argument is null
         */
        static SpecificationVersion parse(String versionName) {
            requireNonNull(versionName);
            for (SpecificationVersion value : SpecificationVersion.values()) {
                if (value.versionName.equals(versionName)) {
                    return value;
                }
            }
            throw new UnknownSpecificationVersionException(versionName);
        }
    }

    /**
     * Get Jdk version name
     */
    public static String javaVersionName() {
        return System.getProperty("java.version");
    }

    /**
     * Get operator system name
     */
    public static String osName() {
        return System.getProperty("os.name");
    }

    private static final Lazy<OSType> osType = Lazy.of(() -> {
        String OS = osName().toLowerCase(Locale.ENGLISH);
        OSType type;
        if ((OS.contains("mac")) || (OS.contains("darwin"))) {
            type = OSType.macOS;
        } else if (OS.contains("win")) {
            type = OSType.windows;
        } else if (OS.contains("nux")) {
            type = OSType.unix;
        } else {
            type = OSType.other;
        }
        return type;
    });

    /**
     * Get operator system type
     */
    public static OSType osType() {
        return osType.get();
    }

    /**
     * Operation system type
     */
    public enum OSType {
        windows, macOS, unix, other
    }

    /**
     * Get current login user name
     */
    public static String userName() {
        return System.getProperty("user.name");
    }

    /**
     * Get user home dir
     */
    public static String userHome() {
        return System.getProperty("user.home");
    }

    /**
     * Get user home dir as Path
     */
    public static Path userHomePath() {
        return Paths.get(System.getProperty("user.home"));
    }

    /**
     * The work directory of current java process
     */
    public static String workDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Get work directory of current java process as Path
     */
    public static Path workDirPath() {
        return Paths.get(System.getProperty("user.dir"));
    }

    /**
     * Get the line lineSeparator of current os, or process
     */
    public static String lineSeparator() {
        return System.lineSeparator();
    }

    /**
     * Get tmp dir current process use
     */
    public static String tmpDir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Get tmp dir as Path
     */
    public static Path tmpDirPath() {
        return Paths.get(System.getProperty("java.io.tmpdir"));
    }
}
