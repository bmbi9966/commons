package net.dongliu.commons.reflect;

import org.junit.jupiter.api.Test;

public class ProxiesTest {

    @Test
    public void newProxy() {
        Runnable runnable = Proxies.newProxy(Runnable.class, (proxy, method, args) -> null);
        runnable.run();
    }
}