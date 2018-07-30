package net.dongliu.commons.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DigestsTest {

    @Test
    void digest() {
        assertEquals("705A6D0F3256F1B4A360982216F651FF", Digests.md5().digest("test测试").asHex());
        assertEquals("2D9BEAD51FFB9BC26FAED33403E398BE523F5160",
                Digests.sha1().digest("test测试").asHex());
        assertEquals("68978EF277DA2A62C4389488778B203039D6C747B3B13989BE04C0B6F05A1DE6",
                Digests.sha256().digest("test测试").asHex());
        assertEquals("2C36A0CB7203A0C41D24A26CD1E7B91645F0418EE77C2D44410A6BB1FE3957F225BBAC26F2E89BA080255C8B6AF5F041060C0DEB9FCD1D040829F3148F1EB98B",
                Digests.sha512().digest("test测试").asHex());
    }
}