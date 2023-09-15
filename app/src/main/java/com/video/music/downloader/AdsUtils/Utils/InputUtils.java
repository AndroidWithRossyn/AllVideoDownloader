package com.video.music.downloader.AdsUtils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collection;

public class InputUtils {

    public static void hideKeyboard(Context activity, View view) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (imm != null) {

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String toCamelCase(final String init) {
        if (init == null)
            return null;

        final StringBuilder ret = new StringBuilder(init.length());

        for (final String word : init.split(" ")) {
            if (!word.isEmpty()) {
                ret.append(Character.toUpperCase(word.charAt(0)));
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == init.length()))
                ret.append(" ");
        }

        return ret.toString();
    }

    public static Drawable createRepeatableDrawable(Activity activity, int imageId) {

        // TODO: yet to write code
        return null;
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{5,16}$";
        String regexSimple = "^(?!.*\\s).{5,16}$";
        if (InputUtils.isNull(password)) {
            return false;
        } else {
            return password.matches(regexSimple);
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dpi = (metrics.densityDpi / 160f);

        if (dpi == 1.5 && metrics.widthPixels == 480) {
            dpi = 1.30f;
        }

        float px = dp * dpi;
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float dpi = (metrics.densityDpi / 160f);

        float dp = px / dpi;
        return dp;
    }

    public static void setTextToTextView(TextView textview, String msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (textview != null) {
                textview.setText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextToTextView(TextView textview, CharSequence msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (textview != null) {
                textview.setText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setHTMLTextToTextView(TextView textview, String msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (textview != null) {
                textview.setText(Html.fromHtml(msg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextToTextView(EditText edttext, String msg) {
        try {
            if (isNull(msg)) {
                msg = "";
            }
            if (edttext != null) {
                edttext.setText(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNull(String val) {
        if (val == null || val.equalsIgnoreCase(null) || val.trim().equalsIgnoreCase("") || val.trim().equalsIgnoreCase("null") || val.trim() == "" || val.trim() == "null")
            return true;
        return false;
    }

    public static boolean isNull(Collection obj) {
        if (obj == null || obj.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    public static boolean isNull(int number) {
        if (number == 0) {
            return true;
        }
        return false;
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

    public static String parseString(int number) {
        String finalString = "";
        try {
            finalString = String.valueOf(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return finalString;
    }

    public static String parseString(double number) {
        String finalString = "";
        try {
            finalString = String.valueOf(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return finalString;
    }

    public static boolean CheckEqualIgnoreCase(String str1, String str2) {
        if (!isNull(str1) && !isNull(str2) && str1.equalsIgnoreCase(str2)) {
            return true;
        }
        return false;
    }

    public static boolean CheckEqualCaseSensitive(String str1, String str2) {
        if (str1 != null && str2 != null && str1.equals(str2)) {
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

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

}
