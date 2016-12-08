package com.mumu.meishijia.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 7mu on 2016/5/12.
 */
public class HttpRequestParams implements Serializable {
    public final static String APPLICATION_OCTET_STREAM =
            "application/octet-stream";

    public final static String APPLICATION_JSON =
            "application/json";

    protected final static String LOG_TAG = "HttpRequestParams";
    protected boolean isRepeatable;
    protected boolean useJsonStreamer;
    protected boolean autoCloseInputStreams;
    public final ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<String, String>();
    protected final ConcurrentHashMap<String, StreamWrapper> streamParams = new ConcurrentHashMap<String, StreamWrapper>();
    protected final ConcurrentHashMap<String, FileWrapper> fileParams = new ConcurrentHashMap<String, FileWrapper>();
//    protected String contentEncoding = HTTP.UTF_8;

    /**
     * Sets content encoding for return value of {@link #getParamString()} and {@link
     * #createFormEntity()} <p>&nbsp;</p> Default encoding is "UTF-8"
     *
     * @param encoding String constant from {@link org.apache.http.protocol.HTTP}
     */
//    public void setContentEncoding(final String encoding) {
//        if (encoding != null) {
//            this.contentEncoding = encoding;
//        } else {
//            Log.d(LOG_TAG, "setContentEncoding called with null attribute");
//        }
//    }

    /**
     * Constructs a new empty {@code HttpRequestParams} instance.
     */
    public HttpRequestParams() {
        this((Map<String, String>) null);
    }

    /**
     * Constructs a new HttpRequestParams instance containing the key/value string params from the
     * specified map.
     *
     * @param source the source key/value string map to add.
     */
    public HttpRequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Constructs a new HttpRequestParams instance and populate it with a single initial key/value
     * string param.
     *
     * @param key   the key name for the intial param.
     * @param value the value string for the initial param.
     */
    public HttpRequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {{
            put(key, value);
        }});
    }

    /**
     * Constructs a new HttpRequestParams instance and populate it with multiple initial key/value
     * string param.
     *
     * @param keysAndValues a sequence of keys and values. Objects are automatically converted to
     *                      Strings (including the value {@code null}).
     * @throws IllegalArgumentException if the number of arguments isn't even.
     */
    public HttpRequestParams(Object... keysAndValues) {
        int len = keysAndValues.length;
        if (len % 2 != 0)
            throw new IllegalArgumentException("Supplied arguments must be even");
        for (int i = 0; i < len; i += 2) {
            String key = String.valueOf(keysAndValues[i]);
            String val = String.valueOf(keysAndValues[i + 1]);
            put(key, val);
        }
    }

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    /**
     * Adds a file to the request.
     *
     * @param key  the key name for the new param.
     * @param file the file to add.
     * @throws java.io.FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, File file) throws FileNotFoundException {
        put(key, file, null, null);
    }

    /**
     * Adds a file to the request with custom provided file name
     *
     * @param key            the key name for the new param.
     * @param file           the file to add.
     * @param customFileName file name to use instead of real file name
     * @throws java.io.FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, String customFileName, File file) throws FileNotFoundException {
        put(key, file, null, customFileName);
    }

    /**
     * Adds a file to the request with custom provided file content-type
     *
     * @param key         the key name for the new param.
     * @param file        the file to add.
     * @param contentType the content type of the file, eg. application/json
     * @throws java.io.FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, File file, String contentType) throws FileNotFoundException {
        put(key, file, contentType, null);
    }

    /**
     * Adds a file to the request with both custom provided file content-type and file name
     *
     * @param key            the key name for the new param.
     * @param file           the file to add.
     * @param contentType    the content type of the file, eg. application/json
     * @param customFileName file name to use instead of real file name
     * @throws java.io.FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, File file, String contentType, String customFileName) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (key != null) {
            fileParams.put(key, FileWrapper.newInstance(file, contentType, customFileName));
        }
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     */
    public void put(String key, InputStream stream) {
        put(key, stream, null);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     * @param name   the name of the stream.
     */
    public void put(String key, InputStream stream, String name) {
        put(key, stream, name, null);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key         the key name for the new param.
     * @param stream      the input stream to add.
     * @param name        the name of the stream.
     * @param contentType the content type of the file, eg. application/json
     */
    public void put(String key, InputStream stream, String name, String contentType) {
        put(key, stream, name, contentType, autoCloseInputStreams);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key         the key name for the new param.
     * @param stream      the input stream to add.
     * @param name        the name of the stream.
     * @param contentType the content type of the file, eg. application/json
     * @param autoClose   close input stream automatically on successful upload
     */
    public void put(String key, InputStream stream, String name, String contentType, boolean autoClose) {
        if (key != null && stream != null) {
            streamParams.put(key, StreamWrapper.newInstance(stream, name, contentType, autoClose));
        }
    }


    /**
     * Adds a int value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value int for the new param.
     */
    public void put(String key, int value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    /**
     * Adds a long value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value long for the new param.
     */
    public void put(String key, long value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
    }

    /**
     * Removes a parameter from the request.
     *
     * @param key the key name for the parameter to remove.
     */
    public void remove(String key) {
        urlParams.remove(key);
        streamParams.remove(key);
        fileParams.remove(key);
    }

    /**
     * Check if a parameter is defined.
     *
     * @param key the key name for the parameter to check existence.
     * @return Boolean
     */
    public boolean has(String key) {
        return urlParams.get(key) != null ||
                streamParams.get(key) != null ||
                fileParams.get(key) != null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        for (ConcurrentHashMap.Entry<String, StreamWrapper> entry : streamParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append("STREAM");
        }

        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append("FILE");
        }

        return result.toString();
    }

    public void setHttpEntityIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    public void setUseJsonStreamer(boolean useJsonStreamer) {
        this.useJsonStreamer = useJsonStreamer;
    }

    /**
     * Set global flag which determines whether to automatically close input streams on successful
     * upload.
     *
     * @param flag boolean whether to automatically close input streams
     */
    public void setAutoCloseInputStreams(boolean flag) {
        autoCloseInputStreams = flag;
    }

//    protected List<BasicNameValuePair> getParamsList() {
//        List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();
//
//        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
//            lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//        }
//
//        return lparams;
//    }
//
//    private List<BasicNameValuePair> getParamsList(String key, Object value) {
//        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
//        if (value instanceof Map) {
//            Map map = (Map) value;
//            List list = new ArrayList<Object>(map.keySet());
//            // Ensure consistent ordering in query string
//            if (list.size() > 0 && list.get(0) instanceof Comparable) {
//                Collections.sort(list);
//            }
//            for (Object nestedKey : list) {
//                if (nestedKey instanceof String) {
//                    Object nestedValue = map.get(nestedKey);
//                    if (nestedValue != null) {
//                        params.addAll(getParamsList(key == null ? (String) nestedKey : String.format("%s[%s]", key, nestedKey),
//                                nestedValue));
//                    }
//                }
//            }
//        } else if (value instanceof List) {
//            List list = (List) value;
//            int listSize = list.size();
//            for (int nestedValueIndex = 0; nestedValueIndex < listSize; nestedValueIndex++) {
//                params.addAll(getParamsList(String.format("%s[%d]", key, nestedValueIndex), list.get(nestedValueIndex)));
//            }
//        } else if (value instanceof Object[]) {
//            Object[] array = (Object[]) value;
//            int arrayLength = array.length;
//            for (int nestedValueIndex = 0; nestedValueIndex < arrayLength; nestedValueIndex++) {
//                params.addAll(getParamsList(String.format("%s[%d]", key, nestedValueIndex), array[nestedValueIndex]));
//            }
//        } else if (value instanceof Set) {
//            Set set = (Set) value;
//            for (Object nestedValue : set) {
//                params.addAll(getParamsList(key, nestedValue));
//            }
//        } else {
//            params.add(new BasicNameValuePair(key, value.toString()));
//        }
//        return params;
//    }
//
//    protected String getParamString() {
//        return URLEncodedUtils.format(getParamsList(), contentEncoding);
//    }

    public static class FileWrapper {
        public final File file;
        public final String contentType;
        public final String customFileName;

        public FileWrapper(File file, String contentType, String customFileName) {
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }

        static FileWrapper newInstance(File file, String contentType, String customFileName) {
            return new FileWrapper(
                    file,
                    contentType == null ? APPLICATION_OCTET_STREAM : contentType,
                    customFileName);
        }
    }

    public static class StreamWrapper {
        public final InputStream inputStream;
        public final String name;
        public final String contentType;
        public final boolean autoClose;

        public StreamWrapper(InputStream inputStream, String name, String contentType, boolean autoClose) {
            this.inputStream = inputStream;
            this.name = name;
            this.contentType = contentType;
            this.autoClose = autoClose;
        }

        static StreamWrapper newInstance(InputStream inputStream, String name, String contentType, boolean autoClose) {
            return new StreamWrapper(
                    inputStream,
                    name,
                    contentType == null ? APPLICATION_OCTET_STREAM : contentType,
                    autoClose);
        }
    }
}
