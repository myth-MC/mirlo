package me.u8092.watchdog.util;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.List;

public class MessagerUtil {
    public static byte[] byteArray(List<String> bytes) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for(String b : bytes) {
            out.writeUTF(b);
        }

        return out.toByteArray();
    }

    public static byte[] byteArray(String bytesSeparatedByComma) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        List<String> bytes = List.of(bytesSeparatedByComma.split(","));

        for(String b : bytes) {
            out.writeUTF(b);
        }

        return out.toByteArray();
    }
}
