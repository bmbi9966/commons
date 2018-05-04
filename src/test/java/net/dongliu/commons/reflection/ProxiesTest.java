package net.dongliu.commons.reflection;

import org.junit.Test;

public class ProxiesTest {

    @Test
    public void newProxy() {
        Runnable runnable = Proxies.newProxy(Runnable.class, (proxy, method, args) -> null);
        runnable.run();
    }
}