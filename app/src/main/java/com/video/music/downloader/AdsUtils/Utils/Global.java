package com.video.music.downloader.AdsUtils.Utils;

import static android.os.Build.VERSION.SDK_INT;

import static androidx.viewbinding.BuildConfig.DEBUG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.video.music.downloader.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Global {

    public static String PE_BTech = "3";
    public static String Prepaid = "PREPAID";
    public static int selectedPosition;
    public static int selectedRemarksID;
    public static boolean todisplaytimerforPosition = false;
    public static boolean callAPIEditOrder = false;
    public static boolean toDisplayTimerFlag = false;
    public static boolean TimerFlag = true;
    public static boolean displayedtimer = false;
    public String fontDefault = "OPENSANS-REGULAR_3.TTF";
    public String fontRegular = "OPENSANS-REGULAR_3.TTF";
    public String fontLight = "OPENSANS-LIGHT_3.TTF";
    public String fontBold = "OPENSANS-BOLD_2.TTF";
    public String fontSemiBold = "OPENSANS-SEMIBOLD_2.TTF";
    public String fontItalic = "OPENSANS-ITALIC_2.TTF";
    public String tableAarogyam = "Aarogyam";
    public String tableProfile = "Profile";
    public String tableTests = "Tests";
    public String tableOffer = "Offer";
    public String tableOfferCart = "OfferCart";
    public String tableThyronomicOfferCart = "ThyronomicOfferCart";
    public String tableAarogyamChilds = "Aarogyam_childs";
    public String tableProfileChilds = "Profile_childs";
    public String tableTestsChilds = "Tests_childs";
    public String tableOfferChilds = "Offer_childs";
    //Live ---------------------------
    public String tableOfferCartChilds = "OfferCart_childs";
    public String tableThyronomicOfferCartChilds = "ThyronomicOfferCart_childs";
    public String tableCart = "Cart";
    ProgressDialog progressDialog;
    private Context context;
    public static String ProfilePath = "Anims/anim1.png";

    public Global(Context context) {
        this.context = context;
    }

    public static void showCustomStaticToast(Context context, String message) {

        if (context != null && !InputUtils.isNull(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean isLatestVersion() {
        return SDK_INT >= Build.VERSION_CODES.R;
    }
    public static Uri getContentMediaUri() {
        if (isLatestVersion()) {
            return MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
    }




    public static String getPath(final Context context, final Uri uri) {
        if (DEBUG)
            Log.d("TAG" + " File -",
                    "Authority: " + uri.getAuthority() +
                            ", Fragment: " + uri.getFragment() +
                            ", Port: " + uri.getPort() +
                            ", Query: " + uri.getQuery() +
                            ", Scheme: " + uri.getScheme() +
                            ", Host: " + uri.getHost() +
                            ", Segments: " + uri.getPathSegments().toString()
            );

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument(uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri);
            }
            // ExternalStorageProvider
            else if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    // TODO handle non-primary volumes
                    return "/storage/" + split[0] + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = getContentMediaUri();
                } else if ("video".equals(type)) {
                    contentUri = getContentVideoUri();
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static Uri getContentVideoUri() {
        if (isLatestVersion()) {
            return MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
    }


    public static boolean isEmptyStr(String str) {
        if (str == null || str.trim().isEmpty() || str.equalsIgnoreCase(""))
            return true;
        return false;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG)
                    DatabaseUtils.dumpCursor(cursor);

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;

    public static boolean isLocalStorageDocument(Uri uri) {
        return AUTHORITY.equals(uri.getAuthority());
    }



    public static Date returnDate(String putDate) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            date = sdf.parse(putDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public static String getCurrentDateandTime() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }


    public static void DsaResponseModel() {

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

    public static void iconImage(Bitmap bitmap) {

    }


    public static int getCurrentVersionCode(Context pContext) {
        int currentAppVersion = 0;
        try {
            currentAppVersion = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentAppVersion;
    }

    public static String getCurrentAppVersionName(Context pContext) {
        String versionName = "";
        try {
            PackageInfo packageInfo = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getSerialnum(Context pContext) {
        String imeiNo = "";
        try {
            imeiNo = Settings.Secure.getString(pContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeiNo;
    }

    public static boolean isArrayListNull(ArrayList arrayList) {
        try {
            if (arrayList == null) {
                return true;
            } else if (arrayList != null && arrayList.size() <= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public String convertNumberToPrice(String s) {
        Double price = Double.parseDouble(s);
        Locale locale = new Locale("en", "IN");
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        if (checkForApi14()) symbols.setCurrencySymbol("\u20B9"); // Don't use null.
        else symbols.setCurrencySymbol("\u20A8"); // Don't use null.
        formatter.setDecimalFormatSymbols(symbols);
        formatter.setMaximumFractionDigits(0);
        //MessageLogger.PrintMsg(formatter.format(price));
        s = formatter.format(price);
        return s;
    }

    public Boolean checkForApi11() {
        Boolean boolStatus = false;
        int currentapiVersion = SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            boolStatus = true;
        } else {
            boolStatus = false;
        }
        return boolStatus;
    }

    public Boolean checkForApi14() {
        Boolean boolStatus = false;
        int currentapiVersion = SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            boolStatus = true;
        } else {
            boolStatus = false;
        }
        return boolStatus;
    }

    public Boolean checkForApi21() {
        Boolean boolStatus = false;
        int currentapiVersion = SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            boolStatus = true;
        } else {
            boolStatus = false;
        }
        return boolStatus;
    }


    public void showProgressDialog(Activity activity, String msg) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        try {
            if (progressDialog != null && !progressDialog.isShowing())
                if (!activity.isFinishing()) {
                    progressDialog.show();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(Context context, String msg) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(null);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        try {
            if (progressDialog != null && !progressDialog.isShowing()) progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(Activity activity, String msg, boolean IsCancelable) {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(null);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(IsCancelable);

        try {
            if (progressDialog != null && !progressDialog.isShowing())

                if (!activity.isFinishing()) {
                    progressDialog.show();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog(Activity activity) {

        try {
            if (activity != null && !activity.isFinishing() && progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String formatDate(String currentFormat, String outputFormat, String date) {

        SimpleDateFormat curFormater = new SimpleDateFormat(currentFormat);
        SimpleDateFormat postFormater = new SimpleDateFormat(outputFormat);
        //SimpleDateFormat postFormater = new SimpleDateFormat("MMMM dd, yyyy");
        //SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(date);
            date = postFormater.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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

    public void showalert_OK(String message, Context context) {

        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message).setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showAlert_OK_WithTitle(String message, Context context, String title) {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title).setMessage(message).setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public interface OnBarcodeDialogSubmitClickListener {
        public void onSubmitButtonClicked(String barcode);
    }

    public static void sout(String TagToString, Object whatToPrint) {
        if (BuildConfig.DEBUG) {
            System.out.println(TagToString + " " + whatToPrint);
        }
    }



 /*   public static GlideUrl getTheImage(Context context, String imageID) {
        //TODO this method hits and return the imager response of api
        return new GlideUrl(EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_BASE)) + "get-image?id=" + imageID + "&p=de", new LazyHeaders.Builder().addHeader("X-RapidAPI-Key", EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_KEY))).addHeader("X-RapidAPI-Host", EncryptionUtils.Dcrp_Hex(context.getString(R.string.CRICKBUZZ_API_HOST))).build());
    }*/

    public static boolean isClassNull(Class classtocheck) {
        return classtocheck == null;
    }

    public static RecyclerView.LayoutManager getManagerWithOrientation(Context context, int orientation) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(orientation);
        return manager;
    }




}
