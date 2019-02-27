module net.dongliu.commons {
    requires jdk.charsets;
    requires static org.checkerframework.checker.qual;

    exports net.dongliu.commons;
    exports net.dongliu.commons.collection;
    exports net.dongliu.commons.concurrent;
    exports net.dongliu.commons.exception;
    exports net.dongliu.commons.function;
    exports net.dongliu.commons.hash;
    exports net.dongliu.commons.io;
    exports net.dongliu.commons.reflection;
    exports net.dongliu.commons.regex;
}