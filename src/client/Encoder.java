package client;

import server.management.ThreadMessage;

import javax.swing.*;
import java.util.Formatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

class Encoder {
    String ID;
    String Username;
    Boolean isActionAccepted;
    String Action;
    SubMessage subMessage;
    public static String encodeToJSON(String ID,String Username,Boolean isActionAccepted,String action,String message, String recipient){
        Encoder Encoding = new Encoder();
        Encoding.ID = ID;
        Encoding.Username = Username;
        Encoding.isActionAccepted = isActionAccepted;
        Encoding.Action = action;
        Encoder.SubMessage subMessage = new Encoder.SubMessage();
        subMessage.message = message;
        subMessage.to = recipient;
        subMessage.from = Username;
        Encoding.subMessage = subMessage;
        return Encoding.toString();

    }
    public static String[][] decodeJSON(String json){
        String str = json.substring(1, json.length() - 1);
        String[] tempArray = str.split(",");
        int x = tempArray.length;
        int y = 2;
        String[][] trueArray = new String[x][y];
        for (int i = 0; i < tempArray.length; i++){
            String tempStr;
            tempStr = tempArray[i].split(":")[0];
            trueArray[i][0] = tempStr;
            tempStr = tempArray[i].split(":")[1];
            trueArray[i][1] = tempStr;
        }
        return trueArray;
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        Formatter formatter = new Formatter(string);
        formatter.format("{%n");
        formatter.format("\"sendParams\": {".indent(4));
        formatter.format("\"ID\": \"%s\",".indent(8), ID);
        formatter.format("\"Username\": \"%s\",".indent(8), Username);
        formatter.format("\"acceptedAction\": \"%s\",".indent(8), isActionAccepted);
        formatter.format("\"Action\": \"%s\",".indent(8), Action);
        formatter.format("\"subMessage\": %s".indent(8), subMessage);
        formatter.format("}".indent(4));
        formatter.format("}");
        formatter.flush();
        return string.toString();
    }

    static class SubMessage {
        String message, to, from;

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            Formatter formatter = new Formatter(string);
            formatter.format("{%n");
            formatter.format("\"message\": \"%s\",".indent(12), message);
            formatter.format("\"to\": \"%s\",".indent(12), to);
            formatter.format("\"from\": \"%s\"".indent(12), from);
            formatter.format("}".indent(8).stripTrailing());
            formatter.flush();
            return string.toString();
        }
    }
}
