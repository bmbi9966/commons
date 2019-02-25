package net.dongliu.commons.net;

import net.dongliu.commons.collection.Lists;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Objects.requireNonNull;

/**
 * Utils for URL
 */
public class URLUtils {

    /**
     * Parse a encoded query or form string with charset UTF8, return params.
     */
    public static List<Entry<String, String>> parseParams(String query) {
        return parseParams(query, StandardCharsets.UTF_8);
    }

    /**
     * Parse a encoded query or form string, return params.
     */
    public static List<Entry<String, String>> parseParams(String query, Charset charset) {
        requireNonNull(query);
        requireNonNull(charset);
        if (query.isEmpty()) {
            return List.of();
        }
        String[] segments = query.split("&");
        var params = new ArrayList<Entry<String, String>>(segments.length);
        for (String segment : segments) {
            int idx = segment.indexOf('=');
            Map.Entry<String, String> param;
            if (idx < 0) {
                param = Map.entry("", URLDecoder.decode(segment, charset));
            } else {
                param = Map.entry(URLDecoder.decode(segment.substring(0, idx), charset),
                        URLDecoder.decode(segment.substring(idx + 1), charset));
            }
            params.add(param);
        }
        return Collections.unmodifiableList(params);
    }

    /**
     * Encode params to encoded query or form string, with charset UTF8.
     */
    public static String encodeParams(List<Entry<String, String>> params) {
        return encodeParams(params, StandardCharsets.UTF_8);
    }

    /**
     * Encode params to encoded query or form string.
     */
    public static String encodeParams(List<Entry<String, String>> params, Charset charset) {
        var sb = new StringBuilder();
        encodeParams(sb, params, charset);
        return sb.toString();
    }

    /**
     * Encode params to encoded query or form string.
     *
     * @param sb the string builder to append
     */
    public static void encodeParams(Appendable sb, List<Entry<String, String>> params, Charset charset) {
        requireNonNull(sb);
        requireNonNull(params);
        requireNonNull(charset);
        Lists.forEach(params, (param, last) -> {
            try {
                sb.append(URLEncoder.encode(param.getKey(), charset))
                        .append('=')
                        .append(URLEncoder.encode(param.getValue(), charset));
                if (!last) {
                    sb.append('&');
                }

            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });

    }

    /**
     * Resolve URL, return a new url.
     * The baseUrl is a illegal url, the other url may be relative path, or absolute path, or a full URL.
     */
    public static String resolve(String baseUrl, String otherUrl) throws MalformedURLException {
        return resolve(new URL(baseUrl), otherUrl).toExternalForm();
    }

    /**
     * Resolve URL, return a new url.
     * The baseUrl is a illegal url, the other url may be relative path, or absolute path, or a full URL.
     */
    public static URL resolve(URL baseUrl, String otherUrl) throws MalformedURLException {
        requireNonNull(baseUrl);
        requireNonNull(otherUrl);
        if (otherUrl.isEmpty()) {
            return baseUrl;
        }

        // absolute url
        if (otherUrl.contains(":")) {
            return new URL(otherUrl);
        }

        char firstChar = otherUrl.charAt(0);
        if (firstChar == '/') {
            if (otherUrl.length() >= 2 && otherUrl.charAt(1) == '/') {
                // protocol relative url
                return new URL(baseUrl.getProtocol() + ":" + otherUrl);
            }
            //absolute path
            return new URL(baseUrl.getProtocol(), baseUrl.getHost(), baseUrl.getPort(), otherUrl);
        }


        if (firstChar == '#') {
            // anchor
            String file = baseUrl.getFile();
            if (file.isEmpty()) {
                file = "/";
            }
            return new URL(baseUrl.getProtocol(), baseUrl.getHost(), baseUrl.getPort(), file + otherUrl);
        }

        // relative url, only calculate ./ & ../ at prefix
        return joinRelativeUrl(baseUrl, otherUrl, otherUrl);
    }

    private static URL joinRelativeUrl(URL baseUrl, String otherUrl, String relativePath) throws MalformedURLException {
        int lastRelativeIndex = -1;
        int relativeIdx;
        int depth = 1;
        outer:
        while ((relativeIdx = relativePath.indexOf('/', lastRelativeIndex + 1)) != -1) {
            String segment = relativePath.substring(lastRelativeIndex + 1, relativeIdx);
            switch (segment) {
                case ".":
                    break;
                case "..":
                    depth++;
                    break;
                default:
                    break outer;
            }
            lastRelativeIndex = relativeIdx;
        }

        String path = baseUrl.getPath();
        if (path.isEmpty()) {
            path = "/";
        }
        int pathIdx;
        int pathPrevIdx = path.length();
        while ((pathIdx = path.lastIndexOf('/', pathPrevIdx - 1)) != -1) {
            pathPrevIdx = pathIdx;
            if (--depth == 0) {
                break;
            }
        }
        if (depth > 0) {
            throw new MalformedURLException("unable to concat url " + baseUrl + " and " + otherUrl);
        }

        String newPath = path.substring(0, pathPrevIdx) + '/' + relativePath.substring(lastRelativeIndex + 1);
        return new URL(baseUrl.getProtocol(), baseUrl.getHost(), baseUrl.getPort(), newPath);
    }
}
