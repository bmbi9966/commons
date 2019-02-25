package net.dongliu.commons.net;

import net.dongliu.commons.Strings;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * For building URL
 */
public class URLBuilder {
    private String protocol = "";
    private String host = "";
    // -1 mean not set
    private int port = -1;
    private String path = "";
    // empty str mean not exists
    private String query = "";
    // empty str mean not exists
    private String fragment = "";

    private URLBuilder() {
    }

    /**
     * Create a new URLBuilder from URL
     */
    public static URLBuilder ofURL(String url) throws MalformedURLException {
        return ofURL(new URL(url));
    }

    /**
     * Create a new URLBuilder from URL
     */
    public static URLBuilder ofURL(URL url) {
        requireNonNull(url);
        return new URLBuilder()
                .protocol(url.getProtocol())
                .host(url.getHost())
                .port(url.getPort())
                .path(url.getPath())
                .query(Strings.nullToEmpty(url.getQuery()))
                .fragment(Strings.nullToEmpty(url.getRef()));
    }

    /**
     * Set protocol of url
     */
    public URLBuilder protocol(String protocol) {
        this.protocol = requireNonNull(protocol);
        return this;
    }

    /**
     * Set host of url
     */
    public URLBuilder host(String host) {
        this.host = requireNonNull(host);
        return this;
    }

    /**
     * Set port of url. -1 to clear port
     */
    public URLBuilder port(int port) {
        this.port = checkPort(port);
        return this;
    }

    private static int checkPort(int port) {
        if (port != -1 && port < 0 || port > 65535) {
            throw new IllegalArgumentException("illegal port: " + port);
        }
        return port;
    }

    /**
     * Set path of url. The path must be well encoded.
     */
    public URLBuilder path(String path) {
        this.path = requireNonNull(path);
        return this;
    }

    /**
     * Set query part of url. The query must be well encoded.
     */
    public URLBuilder query(String query) {
        this.query = requireNonNull(query);
        return this;
    }

    /**
     * Set query part of url, by a list of query params.
     * The param name and value will be encoded with charset utf8.
     */
    public URLBuilder query(List<Map.Entry<String, String>> params) {
        this.query = URLUtils.encodeParams(params);
        return this;
    }

    /**
     * Set query part of url, by a list of query params.
     * The param name and value will be encoded.
     */
    public URLBuilder query(List<Map.Entry<String, String>> params, Charset charset) {
        this.query = URLUtils.encodeParams(params, charset);
        return this;
    }

    /**
     * Set fragment(or called ref) of url. The fragment must be well encoded.
     */
    public URLBuilder fragment(String fragment) {
        this.fragment = requireNonNull(fragment);
        return this;
    }

    /**
     * Build a new URL
     */
    public URL buildURL() throws MalformedURLException {
        var fileBuilder = new StringBuilder();
        fileBuilder.append(path);
        if (!query.isEmpty()) {
            fileBuilder.append('?').append(query);
        }
        if (!fragment.isEmpty()) {
            fileBuilder.append('#').append(fragment);
        }
        return new URL(protocol, host, port, fileBuilder.toString());
    }

    /**
     * Build a new url string
     */
    public String buildURLString() throws MalformedURLException {
        return buildURL().toExternalForm();
    }
}
