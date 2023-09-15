package com.video.music.downloader.AdsUtils.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by amit singh tomar on 28/07/2017.
 */

public class StringUtils {


    public static boolean FlagForrefresh = false;

    //Download PDF with implicit downloader (ACTION_VIEW).
    public static void downloadPDF(Context context, String URL) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public static boolean isNull(String val) {
        if (val == null || val.equalsIgnoreCase(null) || val.trim().equalsIgnoreCase("") || val.trim().equalsIgnoreCase("null") || val.trim() == "" || val.trim() == "null")
            return true;
        return false;
    }

    public static String removeLastCharacter(String str) {
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String removeFirstCharacter(String str) {
        if (str.length() > 0) {
            str = str.substring(1, str.length());
        }
        return str;
    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    public static String toUpperCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToUpperCase = Character.toUpperCase(currentChar);
            result = result + currentCharToUpperCase;
        }
        return result;
    }

    public static String toLowerCase(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char currentCharToLowerCase = Character.toLowerCase(currentChar);
            result = result + currentCharToLowerCase;
        }
        return result;
    }

    public static String toSentenceCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (int j = 0; j < terminalCharacters.length; j++) {
                if (currentChar == terminalCharacters[j]) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

    public static int parseInt(String strnumber) {
        int finalNumber = 0;
        if (!isNull(strnumber)) {
            try {
                finalNumber = Integer.parseInt(strnumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return finalNumber;
    }

    public static float parseFloat(String strnumber) {
        float finalNumber = 0;
        if (!isNull(strnumber)) {
            try {
                finalNumber = Float.parseFloat(strnumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return finalNumber;
    }

    public static double parseDouble(String strnumber) {
        double finalNumber = 0;
        if (!isNull(strnumber)) {
            try {
                finalNumber = Double.parseDouble(strnumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return finalNumber;
    }

    public static boolean CheckEqualIgnoreCase(String str1, String str2) {
        if (!isNull(str1) && !isNull(str2) && str1.equalsIgnoreCase(str2)) {
            return true;
        }
        return false;
    }

    public static boolean CheckEqualCaseSensitive(String str1, String str2) {
        if (!isNull(str1) && !isNull(str2) && str1.equals(str2)) {
            return true;
        }
        return false;
    }

    public static boolean CheckifStringContains(String mainString, String StrToCheck) {
        if (!isNull(mainString) && !isNull(StrToCheck) && mainString.contains(StrToCheck)) {
            return true;
        }
        return false;
    }

    public boolean checkValidation(String type, int length) {
        boolean result = false;
        if (type.equals("mobile")) {
            result = length != 10 ? false : true;
        } else if (type.equals("address")) {
            result = length <= 25 ? false : true;
        }

        return result;
    }

    public String checkJsonNullStringValue(JSONObject jsonObject, String key) {
        String value = "";
        try {
            value = jsonObject.isNull(key) ? "" : jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }
}
