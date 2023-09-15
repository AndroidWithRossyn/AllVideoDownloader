package com.video.music.downloader.AdsUtils.Utils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.video.music.downloader.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class MessageLogger {

    public static final String APP_ID = "DHB";
    public static final int LOG_LEVEL_VERBOSE = 5;
    public static final int LOG_LEVEL_WARNING = 4;
    public static final int LOG_LEVEL_DEBUG = 3;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_ERROR = 1;
    public static final int LOG_LEVEL_OFF = 0;
    public static final int CURRENT_LOG_LEVEL = LOG_LEVEL_VERBOSE;
    public static String logDir = "/com.dhb";
    public static String logFileName = "/log.txt";
    public static boolean writeLogsToFile = false;

    public static void log(Activity mActivity, String message, int logLevel) {
        if (logLevel > CURRENT_LOG_LEVEL) {
            return;
        } else {

            try {
                String LoggerTag = mActivity.getClass().getSimpleName();
                String printlogstr = "PrintLog: ";
                if (BuildConfig.DEBUG) {
                    if (logLevel == LOG_LEVEL_ERROR) {
                        Log.e(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_INFO) {
                        Log.i(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_DEBUG) {
                        Log.d(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_WARNING) {
                        Log.w(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_VERBOSE) {
                        Log.v(LoggerTag, printlogstr + "" + message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (writeLogsToFile) {
                writeToFile(message);
            }
        }
    }

    public static void log(String TAG, String message, int logLevel) {
        if (logLevel > CURRENT_LOG_LEVEL) {
            return;
        } else {

            try {
                String LoggerTag = TAG;
                String printlogstr = "PrintLog: ";
                if (BuildConfig.DEBUG) {
                    if (logLevel == LOG_LEVEL_ERROR) {
                        Log.e(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_INFO) {
                        Log.i(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_DEBUG) {
                        Log.d(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_WARNING) {
                        Log.w(LoggerTag, printlogstr + "" + message);
                    } else if (logLevel == LOG_LEVEL_VERBOSE) {
                        Log.v(LoggerTag, printlogstr + "" + message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (writeLogsToFile) {
                writeToFile(message);
            }
        }
    }

    private static void writeToFile(String message) {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + logDir);
            dir.mkdirs();
            File file = new File(dir, logFileName);
            PrintWriter writer = new PrintWriter(new BufferedWriter(
                    new FileWriter(file, true), 8 * 1024));
            writer.println(APP_ID + " " + new Date().toString() + " : "
                    + message);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verbose(Activity mActivity, String message) {
        log(mActivity, message, LOG_LEVEL_VERBOSE);
    }

    public static void debug(Activity mActivity, String message) {
        log(mActivity, message, LOG_LEVEL_DEBUG);
    }

    public static void error(Activity mActivity, String message) {
        log(mActivity, message, LOG_LEVEL_ERROR);
    }

    public static void info(Activity mActivity, String message) {
        log(mActivity, message, LOG_LEVEL_INFO);
    }

    public static void warning(Activity mActivity, String message) {
        log(mActivity, message, LOG_LEVEL_WARNING);
    }


    // TODO -------Below methods are made to replace old code-------

    public static void LogError(String Tag, String message) {
        log(Tag, message, LOG_LEVEL_ERROR);
    }

    public static void LogDebug(String Tag, String message) {
        log(Tag, message, LOG_LEVEL_DEBUG);
    }

    public static void LogVerbose(String Tag, String message) {
        log(Tag, message, LOG_LEVEL_VERBOSE);
    }


    public static void PrintMsg(String message) {
        if (BuildConfig.DEBUG) {
            System.out.println(message);
        }
    }

}