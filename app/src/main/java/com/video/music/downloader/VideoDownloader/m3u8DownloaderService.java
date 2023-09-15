package com.video.music.downloader.VideoDownloader;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.video.music.downloader.R;
import com.video.music.downloader.VideoDownloader.Configs.SettingsManager;
import com.video.music.downloader.VideoDownloader.Utils.Commons;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.video.music.downloader.VideoDownloader.MyApplication.CHANEL_ID;
public class m3u8DownloaderService extends Service {

    private  String VideoURL;
    private String AudioURL;
    private String FinalOutputFileName;
    String downloadPath;
    private static final String TAG = "M3U8SURAJ";
    private  Context mContext;
    private String outputVideoFile;
    private String outputVideoDir;
    private String outputAudioFile;
    private String outputAudioDir;

    private static final int notif_id=1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        SettingsManager.IsDownloadComplete=false;
        Bundle extras = intent.getExtras();
        if(extras == null) {
            Log.d("Service","null");
        } else {
            Log.d("Service","not null");
            VideoURL = (String) extras.get("URL");
             try
             {
                 AudioURL = (String) extras.get("AudioURL");
             }
             catch (Exception ex)
             {

             }
             FinalOutputFileName =Commons.SanitizeTitle((String) extras.get("FinalOutputFileName")) + "_" +  System.currentTimeMillis() + ".mp4";

        }

        downloadPath = SettingsManager.DOWNLOAD_FOLDER_VIDEO;


        verifyLink();
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate()
    {
        mContext=this;
        startForeground(notif_id, getMyActivityNotification(mContext.getString(R.string.Checking)));
    }

    @Override
    public void onDestroy(){}

    class RetrieveFeedTask extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... urls)
        {
            URL url = null;
            try {
                url = new URL(VideoURL);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();
                if (statusCode ==  200) {
                    InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    String chunks ;
                    boolean NextLineMightbeNewideoUrl=false;
                    while((chunks = buff.readLine()) != null)
                    {
                        if(chunks.contains("MEDIA:TYPE=AUDIO") && chunks.contains(".m3u8") )
                        {
                           try
                           {
                               String requiredString = chunks.substring(chunks.indexOf("URI=\"") + 5, chunks.indexOf(".m3u8\"")) + ".m3u8";
                               String fileName = VideoURL.substring(VideoURL.lastIndexOf('/') + 1);
                               String AudioFIleUrl=VideoURL.replace(fileName,requiredString);
                               //AudioURL= AudioFIleUrl;
                           }catch (Exception ex)
                           {
                               String LeftPad=chunks.substring(chunks.indexOf("URI=\"") + 5);
                              // String requiredString = LeftPad.substring(0, LeftPad.indexOf(".\""));
                               //String fileName = VideoURL.substring(VideoURL.lastIndexOf('/') + 1);
                               String AudioFIleUrl=LeftPad;
                               //AudioURL= AudioFIleUrl;
                           }
                        }
                        // start - Did this for Hungama
                        if(chunks.startsWith("#EXT-X-STREAM-INF"))
                        {
                            NextLineMightbeNewideoUrl=true;
                        }
                        else
                        {
                            if(NextLineMightbeNewideoUrl==true)
                            {
                                if(chunks.contains(".m3u8"))
                                {
                                    URL aURL = new URL(VideoURL);
                                    String LeftPad= aURL.getProtocol() + "://" + aURL.getAuthority();
                                    String TmpVideoLisn=LeftPad + chunks;
                                    NextLineMightbeNewideoUrl=false;

                                    try
                                    {
                                        url = new URL(TmpVideoLisn);
                                        HttpURLConnection urlConnectionsecond = (HttpURLConnection)url.openConnection();
                                        urlConnectionsecond.setRequestMethod("GET");
                                        int statusCodesecond = urlConnectionsecond.getResponseCode();
                                        if (statusCodesecond !=  200)
                                        {
                                            String _replacable=VideoURL.split("/")[ VideoURL.split("/").length -1 ];
                                            VideoURL=VideoURL.replace(_replacable,chunks);
                                        }
                                        else
                                        {
                                            VideoURL=TmpVideoLisn;
                                        }
                                    }
                                    catch (Exception ex)
                                    {
                                        String _replacable=VideoURL.split("/")[ VideoURL.split("/").length -1 ];
                                        VideoURL=VideoURL.replace(_replacable,chunks);
                                    }



                                }
                                else
                                {
                                    String _replacable=VideoURL.split("/")[ VideoURL.split("/").length -1 ];
                                    VideoURL=VideoURL.replace(_replacable,chunks);
                                    NextLineMightbeNewideoUrl=false;
                                }
                            }
                        }
                        // end - Did this for Hungama
                    }
                }
                return "";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        protected void onPostExecute(String feed) {

            download_Video();
        }
    }
    private void verifyLink()
    {
        if(AudioURL ==null ||  AudioURL.equals("")  )
        {
            new RetrieveFeedTask().execute();
        }
        else
        {
            download_Video();
        }
    }



    private Notification getMyActivityNotification(String text){
        CharSequence title =getResources().getString(R.string.app_name);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class), 0);

        return new NotificationCompat.Builder(this,CHANEL_ID)
                .setContentTitle(title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentText(text)
                .setSound(null)
                .addAction(R.mipmap.ic_launcher_round,mContext.getString(R.string.Dismiss),makePendingIntent("quit_action"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent).build();
    }

    public PendingIntent makePendingIntent(String name)
    {
        Intent intent = new Intent(this, NotificationReciver.class);
        intent.setAction(name);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        return pendingIntent;
    }

    private void updateNotification(String text) {

        Notification notification = getMyActivityNotification(text);
        notification.sound=null;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notif_id, notification);
    }

    private void setFinalResult(boolean IsSuccess)
    {
        if(IsSuccess)
        {
            updateNotification(mContext.getString(R.string.Complete));
            SettingsManager.IsDownloadComplete=true;
        }
        else
        {
            updateNotification(getString(R.string.Failed));
            SettingsManager.IsDownloadComplete=true;
        }
        deleteTmpDirs();
    }
    private void deleteTmpDirs()
    {
        if(outputVideoDir !=null)
        {
            File _v=new File(outputVideoDir);
            if(_v.exists())
            {
              //  deleteDir(_v);
            }
        }
        if(outputAudioDir !=null)
        {
            File _a=new File(outputAudioDir);
            if(_a.exists())
            {
                //deleteDir(_a);
            }
        }
    }

    private void CheckforAudio(){
        if ( AudioURL==null || AudioURL.equals(""))
        {
            finishhTask();
        }
        else
        {
            download_audio();
        }
    }

    private void download_Video() {
        new m3u8Downloader(VideoURL, mContext, downloadPath) {
            @Override
            public void onDownloaderError(String ErrorMessage) {
                updateNotification(mContext.getString(R.string.DownloadvideoError) + ErrorMessage);
                setFinalResult(false);
            }

            @Override
            public void onDownloaderSuccess(String FilePath, String BaseFileDir) {
                updateNotification(mContext.getString(R.string.DownloadvideoCompleteat) + FilePath);
                outputVideoFile=FilePath;
                outputVideoDir=BaseFileDir;

                CheckforAudio();
            }

            @Override
            public void onDownloaderProgress(float Percentage) {

                    final int _per=(int) (Percentage * 10);
                    final int _exPer=_per *10;
                    updateNotification(  mContext.getString(R.string.Downloadingvideo)+" - " + _exPer +" % " + mContext.getString(R.string.Complete));

            }
        }.start();
    }

    private void download_audio()
    {
        new m3u8Downloader(AudioURL,mContext , downloadPath) {
            @Override
            public void onDownloaderError(String ErrorMessage) {
                updateNotification(mContext.getString(R.string.DownloadAudioError) + ErrorMessage);
                setFinalResult(false);
            }

            @Override
            public void onDownloaderSuccess(String FilePath, String BaseFileDir) {
                outputAudioFile=FilePath;
                outputAudioDir=BaseFileDir;
                combineVideoAndAudio();
            }

            @Override
            public void onDownloaderProgress(float Percentage) {
                final int _per=(int) (Percentage * 10);
                updateNotification(mContext.getString(R.string.DownloadingAudio) +" - " + _per + " % " + mContext.getString(R.string.Complete)) ;
            }
        }.start();
        updateNotification(mContext.getString(R.string.DownloadingAudio));
    }

    private void combineVideoAndAudio()
    {


//        String cmd = String.format("-i "+outputVideoFile+" -i "+ outputAudioFile +" -c:v copy -c:a aac "+downloadPath+"/" + FinalOutputFileName);
//        String[] command = cmd.split(" ");
//
//        FFmpeg.executeAsync(command, new ExecuteCallback() {
//            @Override
//            public void apply(long executionId, int returnCode) {
//                if (returnCode == RETURN_CODE_SUCCESS) {
//                    //onDownloaderSuccess(targetDownloadDir + "/" + DownloadTmpFileName + ".mp4",targetDownloadDir);
//                    //Log.i(Config.TAG, "Async command execution completed successfully.");
//                    updateNotification(mContext.getString(R.string.MeargingfilesSuccess));
//                    finishhTask();
//                } else if (returnCode == RETURN_CODE_CANCEL) {
//                    Log.i(TAG, "Async command execution cancelled by user.");
//                } else {
//                    //onDownloaderError("Error- " + returnCode);
//                    updateNotification(mContext.getString(R.string.MeargingfilesFailed));
//                    Log.d(TAG, String.format("Async command execution failed with returnCode=%d.", returnCode));
//                }
//            }
//        });


//        FFmpeg ffmpeg = FFmpeg.getInstance(mContext);
//        try {
//            ffmpeg.execute(command, new FFmpegExecuteResponseHandler() {
//                @Override
//                public void onSuccess(String message) {
//                    Log.i(TAG, "Combine onSuccess: " + message);
//                    updateNotification(mContext.getString(R.string.MeargingfilesSuccess));
//                    finishhTask();
//                }
//
//                @Override
//                public void onProgress(String message) {
//                    Log.i(TAG, "Combine onProgress: " + message);
//                    updateNotification(mContext.getString(R.string.MeargingfilesPleaseWait));
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Log.i(TAG, "Combine onFailure: " + message);
//                    updateNotification(mContext.getString(R.string.MeargingfilesFailed));
//                    setFinalResult(false);
//                    finishhTask();
//                }
//
//                @Override
//                public void onStart()
//                {
//                }
//
//                @Override
//                public void onFinish() {
//                    Log.i(TAG, "Combine Finished: " );
//                }
//            });
//        } catch (FFmpegCommandAlreadyRunningException e) {
//            e.printStackTrace();
//            updateNotification(mContext.getString(R.string.MeargingfilesFailed));
//            setFinalResult(false);
//            finishhTask();
//            Log.i(TAG, "Combine onError: " + e.getMessage());
//        }

    }

    private void finishhTask()
    {
        File file = new File(downloadPath+ "/" + FinalOutputFileName );
        if(file.exists())
        {
            deleteTmpDirs();
            setFinalResult(true);
        }
        else
        {
            File sourceLocation= new File ( outputVideoFile);
            File targetLocation= file;

            try {
                InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(targetLocation);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                File _v=new File(outputVideoDir);
                deleteDir(_v);
                setFinalResult(true);
                updateNotification(mContext.getString(R.string.DownloadComplete));
            }
            catch (Exception ex)
            {
                updateNotification(mContext.getString(R.string.CoppingfileError) + ex);
                setFinalResult(false);
            }

        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }



}
