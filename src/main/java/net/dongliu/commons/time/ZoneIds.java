package net.dongliu.commons.time;

import net.dongliu.commons.Lazy;

import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Common ZoneIds
 */
public class ZoneIds {

    /**
     * Return the timezone of current os
     */
    public static ZoneId system() {
        return ZoneId.systemDefault();
    }

    /**
     * The time-zone for UTC, with an ID of 'Z'.
     */
    public static ZoneId UTC() {
        return ZoneOffset.UTC;
    }

    private static final Lazy<ZoneId> asiaShangHai = Lazy.of(() -> ZoneId.of("Asia/Shanghai"));

    /**
     * The time zone Asia/Shanghai
     */
    public static ZoneId asiaShangHai() {
        return asiaShangHai.get();
    }

    /**
     * The time-zone China Taiwan Time
     */
    public static ZoneId CTT() {
        return asiaShangHai.get();
    }

    private static final Lazy<ZoneId> AGT = Lazy.of(() -> ZoneId.of("America/Argentina/Buenos_Aires"));

    /**
     * The time-zone America/Argentina/Buenos_Aires
     */
    public static ZoneId americaBuenosAires() {
        return AGT.get();
    }

    /**
     * The time-zone Argentina Standard Time
     */
    public static ZoneId AGT() {
        return AGT.get();
    }

    private static final Lazy<ZoneId> PST = Lazy.of(() -> ZoneId.of("America/Los_Angeles"));

    /**
     * The time-zone America/Los_Angeles
     */
    public static ZoneId americaLosAngeles() {
        return PST.get();
    }

    /**
     * The time-zone Pacific Standard Time  (USA)
     */
    public static ZoneId PST() {
        return PST.get();
    }


    private static final Lazy<ZoneId> CST = Lazy.of(() -> ZoneId.of("America/Chicago"));

    /**
     * The time-zone America/Chicago
     */
    public static ZoneId americaChicago() {
        return CST.get();
    }

    /**
     * The time-zone Central Standard Time  (USA)
     *
     * @deprecated for USA Central Standard Time, using {@link #americaChicago()}; For China Standard Time, using {@link #asiaShangHai()}
     */
    @Deprecated
    public static ZoneId CST() {
        return CST.get();
    }

    private static final Lazy<ZoneId> EST = Lazy.of(() -> ZoneId.of("-05:00"));

    /**
     * The time-zone Eastern Standard Time (USA)
     */
    public static ZoneId EST() {
        return EST.get();
    }

    private static final Lazy<ZoneId> MST = Lazy.of(() -> ZoneId.of("-07:00"));

    /**
     * The time-zone Mountain Standard Time (USA)
     */
    public static ZoneId MST() {
        return MST.get();
    }

    private static final Lazy<ZoneId> JST = Lazy.of(() -> ZoneId.of("Asia/Tokyo"));

    /**
     * The time-zone Asia/Tokyo
     */
    public static ZoneId asiaTokyo() {
        return JST.get();
    }

    /**
     * The time-zone Japan Standard Time
     */
    public static ZoneId JST() {
        return JST.get();
    }


    private static final Lazy<ZoneId> VST = Lazy.of(() -> ZoneId.of("Asia/Ho_Chi_Minh"));

    /**
     * The time-zone Asia/Ho_Chi_Minh
     */
    public static ZoneId asiaHoChiMinh() {
        return VST.get();
    }

    /**
     * The time-zone Vietnam Standard Time
     */
    public static ZoneId VST() {
        return VST.get();
    }

    private static final Lazy<ZoneId> ACT = Lazy.of(() -> ZoneId.of("Australia/Darwin"));

    /**
     * The time-zone Australia/Darwin
     */
    public static ZoneId australiaDarwin() {
        return ACT.get();
    }

    /**
     * The time-zone Australian Central Time
     */
    public static ZoneId ACT() {
        return ACT.get();
    }

    private static final Lazy<ZoneId> AET = Lazy.of(() -> ZoneId.of("Australia/Sydney"));

    /**
     * The time-zone Australia/Sydney
     */
    public static ZoneId australiaSydney() {
        return AET.get();
    }

    /**
     * The time-zone Australian Eastern Time
     */
    public static ZoneId AET() {
        return AET.get();
    }
}
