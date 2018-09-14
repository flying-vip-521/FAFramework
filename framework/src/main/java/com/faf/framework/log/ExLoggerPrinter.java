package com.faf.framework.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

import static com.faf.framework.log.BaseLogger.*;
import static com.faf.framework.log.Utils.checkNotNull;

/**
 * @ClassName: ExLoggerPrinter
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/14 17:26
 */
public class ExLoggerPrinter extends LoggerPrinter implements ExPrinter {
    @Override
    public void d(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        log(L.DEBUG, tag, null, message, args);
    }

    @Override
    public void d(@NonNull String tag, @Nullable Object object) {
        log(DEBUG, tag, null, Utils.toString(object));

    }

    @Override
    public void e(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        e(tag, null, message, args);
    }

    @Override
    public void e(@NonNull String tag, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        log(ERROR, tag, throwable, message, args);

    }

    @Override
    public void w(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        log(WARN, tag, null, message, args);
    }

    @Override
    public void i(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        log(INFO, tag, null, message, args);

    }

    @Override
    public void v(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        log(VERBOSE, tag, null, message, args);
    }

    @Override
    public void wtf(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        log(ASSERT, tag, null, message, args);
    }

    @Override
    public void json(@NonNull String tag, @Nullable String json) {
        if (Utils.isEmpty(json)) {
            d(tag, "Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(tag, message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(tag, message);
                return;
            }
            e(tag, "Invalid Json");
        } catch (JSONException e) {
            e(tag, "Invalid Json");
        }
    }

    @Override
    public void xml(@NonNull String tag, @Nullable String xml) {
        if (Utils.isEmpty(xml)) {
            d(tag, "Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(tag, xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(tag, "Invalid xml");
        }
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void log(int priority, String tag,
                                  @Nullable Throwable throwable,
                                  @NonNull String msg,
                                  @Nullable Object... args) {
        checkNotNull(msg);

        String message = createMessage(msg, args);
        log(priority, tag, message, throwable);
    }
}
