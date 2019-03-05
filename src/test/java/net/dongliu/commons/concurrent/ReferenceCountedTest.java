package net.dongliu.commons.concurrent;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReferenceCountedTest {

    @Test
    void test() {
        var resource = Mockito.spy(MyResource.class);
        Mockito.doNothing().when(resource).destroy();
        assertEquals(1, resource.refCount());
        resource.retain();
        assertEquals(2, resource.refCount());
        assertThrows(IllegalArgumentException.class, () -> resource.retain(-1));
        assertThrows(IllegalArgumentException.class, () -> resource.release(-1));
        resource.release();
        assertThrows(IllegalStateException.class, () -> resource.release(10));
        assertEquals(1, resource.refCount());
        resource.release();
        assertEquals(0, resource.refCount());
        Mockito.verify(resource).destroy();
        assertThrows(IllegalStateException.class, resource::retain);
        assertThrows(IllegalStateException.class, resource::release);
    }

    @Test
    void refCount() {

    }

    @Test
    void retain() {
    }

    @Test
    void release() {
    }

    private static class MyResource extends ReferenceCounted<MyResource> {

        public MyResource() {
        }

        @Override
        protected void destroy() {

        }
    }
}