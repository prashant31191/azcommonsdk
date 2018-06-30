package com.azsamplesdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.androidadvance.topsnackbar.TSnackbar;
import com.azsamplesdk.utils.AppFlags;
import com.azsamplesdk.utils.SharePrefrences;
import com.crashlytics.android.Crashlytics;
import com.prashant311.azrefresh.PowerRefreshLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import es.dmoral.toasty.Toasty;
import io.fabric.sdk.android.Fabric;


public class App extends Application {

    private static final String TAG = "==App==";
    static App mInstance;
    public static boolean blnFullscreenActvitity = false;
    public static String DB_NAME = "app_name.db";

    //public static String strBaseHostUrl = "http://asdasd.net/app_name/";
    public static String strBaseHostUrl = "https://www.asdasd.com/";
    public static String APP_MODE = "2";
    public static String APP_PLATFORM = "2";

    static Context mContext;
    public static String DB_PATH = Environment.getExternalStorageDirectory().toString() + "/" + App.APP_FOLDERNAME;
    public static String APP_SD_CARD_PATH = Environment.getExternalStorageDirectory().toString();
    public static String APP_FOLDERNAME = ".app_name";
    public static String sdCardPath = Environment.getExternalStorageDirectory().toString();
    public static String PREF_NAME = "app_name_pref";
    public static SharePrefrences sharePrefrences;

    // public static String _RS = "₹";

    //
    // static Typeface tfMontserrat_alternates_regular, tfOpen_sans_bold, tfTribhuchet_ms;
    //static Typeface tfProximanovaLight, tfProximanovaReg, tfProximanovaSBold, tfProximanovaXBold;


    ///// Please refer blow Comment for update analytics id
    private String GOOGLE_ANALYTICS_ID = "";
    /* ************************************
    *
    * Chnage screen name according to package from
    *   res-> xml -> app_tracker.xml
    *
    * GOOGLE_ANALYTICS_ID is also there
    *
    * **************************************
    * */


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    // application on create methode for the create and int base values
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mInstance = this;
            Fabric.with(this, new Crashlytics());
           // Fresco.initialize(this);
            MultiDex.install(this);
            /*AnalyticsTrackers.initialize(this);
            AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);*/
            mContext = getApplicationContext();
            sharePrefrences = new SharePrefrences(App.this);
            //
            // FONTS
            /*getProximanovaLight();
            getProximanovaReg();
            getProximanovaSBold();*/

            createAppFolder();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static synchronized App getInstance() {
        return mInstance;
    }

/*
    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    public void trackScreenView(String screenName) {
        try {
            Tracker t = getGoogleAnalyticsTracker();

            // Set screen name.
            t.setScreenName(screenName);

            // Send a screen view.
            t.send(new HitBuilders.ScreenViewBuilder().build());

            GoogleAnalytics.getInstance(this).dispatchLocalHits();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }


    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }*/


    private void createAppFolder() {
        try {
            sdCardPath = Environment.getExternalStorageDirectory().toString();
            File file2 = new File(sdCardPath + "/" + App.APP_FOLDERNAME + "");
            if (!file2.exists()) {
                if (!file2.mkdirs()) {
                    System.out.println("==Create Directory " + App.APP_FOLDERNAME + "====");
                } else {
                    System.out.println("==No--1Create Directory " + App.APP_FOLDERNAME + "====");
                }
            } else {
                System.out.println("== already created---No--2Create Directory " + App.APP_FOLDERNAME + "====");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String setLabelText(String newString, String defaultString) {
        if (newString != null) {
            return newString;
        } else {
            showLog("==setLabelText====LABEL===null===newString====set default text==");
            return defaultString;
        }
    }


    public static String setAlertText(String newString, String defaultString) {
        if (newString != null) {
            return newString;
        } else {
            showLog("==setAlertText===null===newString====set default text==");
            return defaultString;
        }
    }


    public static long getMilliSeconds(String convert_date_string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(convert_date_string);
            long timeInMilliseconds = mDate.getTime();

            System.out.println("Date :: " + convert_date_string);
            System.out.println("Date in milli :: " + timeInMilliseconds);

            // Convert receive date string to UTC time zone
            // String input = "Sat Feb 17 2012";
//            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(convert_date_string);
//            long milliseconds = date.getTime();
//            long millisecondsFromNow = milliseconds - (new Date()).getTime();
            // Toast.makeText(this, "Milliseconds to future date="+millisecondsFromNow, Toast.LENGTH_SHORT).show();

            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    // 16 Apr 2018 20:00:00 dd MMM yyyy HH:mm:ss
    public static long getCountDownMilliSeconds(String str_date_time, String strDateTimeFormate) {
        SimpleDateFormat sdf = new SimpleDateFormat(strDateTimeFormate);
        try {
            //   sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            //-- sdf.setTimeZone(TimeZone.getTimeZone("IST"));

            try {
                Date mDate = sdf.parse(str_date_time);
                long timeInMilliseconds = mDate.getTime();
                System.out.println("Date time:: " + str_date_time);
                System.out.println("Date time in milli :: " + timeInMilliseconds);
                // return timeInMilliseconds;
                return (timeInMilliseconds); // /100000;
            } catch (ParseException e) {
                e.printStackTrace();
            }


          /*  //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(str_date_time);
            long timeInMilliseconds = mDate.getTime();

            System.out.println("Date :: " + str_date_time);
            System.out.println("Date in milli :: " + timeInMilliseconds);*/

            // Convert receive date string to UTC time zone
            // String input = "Sat Feb 17 2012";
//            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(convert_date_string);
//            long milliseconds = date.getTime();
//            long millisecondsFromNow = milliseconds - (new Date()).getTime();
            // Toast.makeText(this, "Milliseconds to future date="+millisecondsFromNow, Toast.LENGTH_SHORT).show();

            //return timeInMilliseconds / 1000;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static String getddMMMyyyy(String convert_date_string) {
        String final_date = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy h:mm a");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static String getddMMM(String convert_date_string) {
        String final_date = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static String getddMMMMyyyy(String convert_date_string) {
        String final_date = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static String getddMMMMyyyyWithoutTime(String convert_date_string) {
        String final_date = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static String getyyyyMMdd(String convert_date_string) {
        String final_date = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }

    public static Date getDateFromDDMMMYYY(String strDate) {
        try {
            Date date;
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd MMM yyyy");
            date = formatter.parse(strDate);

            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }

    }


    public static String getTime(String convert_date_string) {
        String final_date = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static String getTimeAgo(String convert_date_string) {
        String finalDate = "";
        try {
            // Convert receive date string to UTC time zone
            SimpleDateFormat utc_time_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utc_time_format.setTimeZone(TimeZone.getTimeZone("UTC"));
            String utc_time_date = convert_date_string;
            Date dt_utc_format = null;
            dt_utc_format = utc_time_format.parse(utc_time_date);

            SimpleDateFormat local_formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_utc = local_formate.format(dt_utc_format);
            App.showLog("==1111==UTC time zone date==" + date_utc + "//**//");

            // Convert UTC timezone date to device local time zone
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            inputFormat.setTimeZone(tz);
            String inputDateStr = date_utc;
            App.showLog("==2222==Local time zone date==" + inputDateStr + "//**//");
            Date dt = null;
            dt = inputFormat.parse(inputDateStr);

            // Get timestamp from deive's local time zone date and time
            long timeStamp = 0l;
            if (inputDateStr != null && inputDateStr.length() > 0) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = (Date) formatter.parse(inputDateStr);
                timeStamp = date.getTime();
            }

            if (timeStamp < 1000000000000L) {
                // if timestamp given in seconds, convert to millis
                timeStamp *= 1000;
            }

            // Diff current timestamp  and Receive string timestamp
            long diff = 0;
            diff = new Date().getTime() - timeStamp;
//            App.showLog("==diff==" + diff);
//            App.showLog("==timeStamp==" + timeStamp);
            double seconds = Math.abs(diff) / 1000;
            double minutes = seconds / 60;
            double hours = minutes / 60;
            double days = hours / 24;
            double years = days / 365;

//            App.showLog("==seconds==" + seconds);
//            App.showLog("==minutes==" + minutes);
//            App.showLog("==hours==" + hours);


            long now = System.currentTimeMillis();
            if (timeStamp > now || timeStamp <= 0) {
                finalDate = "In the future";
            } else if (seconds > 2 && seconds < 60) {
                finalDate = "Just now";
            } else if (seconds > 60 && minutes < 2) {
                finalDate = "1 minute ago";
            } else if (minutes > 2 && minutes < 60) {
                finalDate = ((int) minutes) + " minutes ago";
            } else if (minutes == 60) {
                finalDate = ((int) minutes) + " hour ago";
            } else if (minutes > 60 && minutes < 61) {
                finalDate = ((int) minutes) + " hour ago";
            } else if (minutes > 61 && hours < 24) {
                if (((int) hours) == 1) {
                    finalDate = ((int) hours) + " hour ago";
                } else {
                    finalDate = ((int) hours) + " hours ago";
                }
            } else if (hours > 24 && hours < 36) {
                SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a");
                format1.setTimeZone(tz);
                finalDate = format1.format(dt);
                finalDate = "Yesterday at " + finalDate;
            } else {
                SimpleDateFormat format1 = new SimpleDateFormat("d");
                String date = format1.format(dt);
                App.showLogTAG("==getTimeAgo==date==" + date);

                if (date.endsWith("1") && !date.endsWith("11")) {
                    format1 = new SimpleDateFormat("d'st' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                } else if (date.endsWith("2") && !date.endsWith("12")) {
                    format1 = new SimpleDateFormat("d'nd' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                } else if (date.endsWith("3") && !date.endsWith("13")) {
                    format1 = new SimpleDateFormat("d'rd' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                } else {
                    format1 = new SimpleDateFormat("d'th' MMMM yyyy, hh:mm a");
                    format1.setTimeZone(tz);
                }
                finalDate = format1.format(dt);
                finalDate = "" + finalDate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalDate;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getCurrentDateTime() {
        String current_date = "";
        Calendar c = Calendar.getInstance();
        //System.out.println("Current time => " + c.getTime());

        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        current_date = postFormater.format(c.getTime());

        return current_date;
    }


  /*  public static Typeface getProximanovaLight() {
        tfProximanovaLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/proximanovaLight.TTF");
        return tfProximanovaLight;
    }

    public static Typeface getProximanovaReg() {
        tfProximanovaReg = Typeface.createFromAsset(mContext.getAssets(), "fonts/proximanovaReg.TTF");
        return tfProximanovaReg;
    }

    public static Typeface getProximanovaSBold() {
        tfProximanovaSBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/proximanovaSBold.TTF");
        return tfProximanovaSBold;
    }

    public static Typeface getProximanovaXBold() {
        tfProximanovaXBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/proximaNovaXbold.otf");
        return tfProximanovaXBold;
    }*/


    public static void GenerateKeyHash() {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES); //GypUQe9I2FJr2sVzdm1ExpuWc4U= android pc -2 key
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                App.showLog("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   /* public static void customInfoToast (Context context, String message, Typeface typeface){
        Toasty.Config.getInstance()
                .setTextColor(context.getResources().getColor(R.color.clrWhite))
                .setToastTypeface(typeface)
                .setTextSize(11)
                .apply();

        Toasty.info(context, message, Toast.LENGTH_LONG, true).show();
//        Toasty.Config.getInstance()
//                .setErrorColor(@ColorInt int errorColor) // optional
//                .setInfoColor(@ColorInt int infoColor) // optional
//                .setSuccessColor(@ColorInt int successColor) // optional
//                .setWarningColor(@ColorInt int warningColor) // optional
//                .setTextColor(@ColorInt int textColor) // optional
//                .tintIcon(boolean tintIcon) // optional (apply textColor also to the icon)
//                .setToastTypeface(Typeface typeface) // optional
//                .setTextSize(int sizeInSp) // optional
//                .apply(); // required
    }*/

   /* public static void customSuccessToast (Context context, String message, Typeface typeface){
        Toasty.Config.getInstance()
                .setTextColor(context.getResources().getColor(R.color.clrWhite))
                .setToastTypeface(typeface)
                .setTextSize(11)
                .apply();

        Toasty.success(context, message, Toast.LENGTH_LONG, true).show();
//        Toasty.Config.getInstance()
//                .setErrorColor(@ColorInt int errorColor) // optional
//                .setInfoColor(@ColorInt int infoColor) // optional
//                .setSuccessColor(@ColorInt int successColor) // optional
//                .setWarningColor(@ColorInt int warningColor) // optional
//                .setTextColor(@ColorInt int textColor) // optional
//                .tintIcon(boolean tintIcon) // optional (apply textColor also to the icon)
//                .setToastTypeface(Typeface typeface) // optional
//                .setTextSize(int sizeInSp) // optional
//                .apply(); // required
    }*/

 /*   public static void customErrorToast (Context context, String message, Typeface typeface){
        Toasty.Config.getInstance()
                .setTextColor(context.getResources().getColor(R.color.clrWhite))
                .setToastTypeface(typeface)
                .setTextSize(11)
                .apply();

        Toasty.error(context, message, Toast.LENGTH_LONG, true).show();
//        Toasty.Config.getInstance()
//                .setErrorColor(@ColorInt int errorColor) // optional
//                .setInfoColor(@ColorInt int infoColor) // optional
//                .setSuccessColor(@ColorInt int successColor) // optional
//                .setWarningColor(@ColorInt int warningColor) // optional
//                .setTextColor(@ColorInt int textColor) // optional
//                .tintIcon(boolean tintIcon) // optional (apply textColor also to the icon)
//                .setToastTypeface(Typeface typeface) // optional
//                .setTextSize(int sizeInSp) // optional
//                .apply(); // required
    }*/


    public static int convertDpToPx(Context context, int dp) {
        return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }


    public static void showToastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context context, String strMessage) {
        Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
    }


    /*public static void showCustomSnackBarShort(View view, String strMessage) {
        try {
            TSnackbar snackbar = TSnackbar.make(view,
                    strMessage,
                    TSnackbar.LENGTH_SHORT);
            snackbar.setActionTextColor(ContextCompat.getColor(mContext, R.color.clrPurple));

            View snackbarView = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)snackbarView.getLayoutParams();
            params.setMargins(0, 50, 0, 0);
            snackbarView.setLayoutParams(params);
            snackbarView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.clrGrayBg));

            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.clrPurple));
            snackbar.show();

        } catch (Exception e) {e.printStackTrace();}
    }*/

   /* public static void showCustomSnackBarLong(View view, String strMessage) {
        try {
            TSnackbar snackbar = TSnackbar.make(view,
                    strMessage,
                    TSnackbar.LENGTH_LONG);
            snackbar.setActionTextColor(ContextCompat.getColor(mContext, R.color.clrPurple));

            View snackbarView = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)snackbarView.getLayoutParams();
            params.setMargins(0, 50, 0, 0);
            snackbarView.setLayoutParams(params);
            snackbarView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.clrGrayBg));

            TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.clrPurple));
            snackbar.show();

        } catch (Exception e) {e.printStackTrace();}
    }*/


    public static void showSnackBarLong(View view, String strMessage) {
        Snackbar.make(view, strMessage, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackBar(View view, String strMessage) {
        try {
            Snackbar snackbar = Snackbar.make(view, strMessage, Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Toast.makeText(mContext, strMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void stopLoading(PowerRefreshLayout powerRefreshLayout)
    {
        try{
            powerRefreshLayout.stopRefresh(true);
            powerRefreshLayout.stopLoadMore(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLog(String strMessage) {
        if (APP_MODE.equalsIgnoreCase("2"))
            Log.v("==App==", "--strMessage--" + strMessage);
    }

    public static void showLogTAG(String strMessage) {
        if (APP_MODE.equalsIgnoreCase("2"))
            Log.e("==TAG==", "--screen--" + strMessage);
    }

    public static void showLogResponce(String strTag, String strMessage) {
        if (APP_MODE.equalsIgnoreCase("2"))
            Log.w("==RESPONSE==" + strTag, "--strResponse--" + strMessage);
    }

    public static void showLogApi(String strMessage) {
        if (APP_MODE.equalsIgnoreCase("2"))
            Log.v("==App==", "--strMessage--" + strMessage);
    }

    public static void showLog(String strTag, String strMessage) {
        if (APP_MODE.equalsIgnoreCase("2"))
            Log.v("==App==strTag==" + strTag, "--strMessage--" + strMessage);
    }


    public static void setTaskBarColored(Activity context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // window.setStatusBarColor(Color.BLUE);
            window.setStatusBarColor(ContextCompat.getColor(context, color));
        }
    }

    public static boolean isInternetAvail(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean isInternetAvailWithMessage(View view, Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }

        App.showSnackBar(view, AppFlags.strMsgNetError);
        return false;
    }


    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static String getOnlyDigits(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }

    public static String getOnlyStrings(String s) {
        Pattern pattern = Pattern.compile("[^a-z A-Z]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }

    public static String getOnlyAlfaNumeric(String s) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll(" ");
        return number;
    }


    public void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboardMy(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static void myStartActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void myFinishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public static void myFinishStartActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public static void setStatusBarTranslucent(boolean makeTranslucent, Activity activity) {
        if (makeTranslucent) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public static String getMonthNameTime(String convert_date_string) {
        String final_date = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM @ hh:mm a");
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                //String outputDateStr = outputFormat.format(date);
                final_date = outputFormat.format(date);
                final_date.toLowerCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static File getFileFromBitmap(Bitmap bitmap) {
        try {
            //create a file to write bitmap data
            sdCardPath = Environment.getExternalStorageDirectory().toString();
            File f = new File(sdCardPath + "/" + App.APP_FOLDERNAME + "", "profile.png");
            //  File f = new File(context.getCacheDir(), filename);
            f.createNewFile();

//Convert bitmap to byte array

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            return f;

        } catch (Exception e) {
            // Log exception
            return null;
        }
    }


    public static Boolean isAppIsRunningTop(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.d("current task :", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClass().getSimpleName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if (componentInfo.getPackageName().equalsIgnoreCase(context.getPackageName())) {
            //Activity in foreground, broadcast intent

            App.showLog("====isAppIsRunningTop===###===App is running===###=====");
            return true;
        } else {
            //Activity Not Running
            //Generate Notification
            App.showLog("===isAppIsRunningTop====###===App is not running===###=======");
            return false;
        }
    }

    public static boolean isAppKill(Context context) {
        ActivityManager aManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfo = aManager.getRunningAppProcesses();
        for (int i = 0; i < processInfo.size(); i++) {
            if (processInfo.get(i).processName.equals(context.getPackageName())) {
                //Kill app

                App.showLog("===isAppKill==###===App is not running===###=======");
                return false;
            }
        }
        //Start app
        App.showLog("===isAppKill==###===App is running===###=====");
        return true;
    }


    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        App.showLog("=====####=====isAppIsInBackground====####====" + isInBackground);
        return isInBackground;
    }


    public static boolean isCheckReachLocation(int rangeMeter, double sDLat, double sDLon, double eDLat, double eDLon) {
        float distanceInMeters = 0;

        Location startLocation = new Location("Start");
        startLocation.setLatitude(sDLat);
        startLocation.setLongitude(sDLon);

        Location targetLocation = new Location("Ending");
        targetLocation.setLatitude(eDLat);
        targetLocation.setLongitude(eDLon);

        distanceInMeters = (targetLocation.distanceTo(startLocation));


        // distance = locationA.distanceTo(locationB);   // in meters
        //  distance = locationA.distanceTo(locationB)/1000;   // in km

        String strCalMeters = String.format("%.02f", distanceInMeters);

        App.showLog("===checkReachLocation====strCalMeters====meters====" + strCalMeters);

        if (distanceInMeters > rangeMeter) {
            return false;
        } else {
            App.showLog("====-----REACHED----=====checkReachLocation====strCalMeters====meters====" + strCalMeters);
            return true;
        }
    }


    public static String getDistanceInKM(double sDLat, double sDLon, double eDLat, double eDLon) {
        float distanceInMeters = 0;

        Location startLocation = new Location("Start");
        startLocation.setLatitude(sDLat);
        startLocation.setLongitude(sDLon);

        Location targetLocation = new Location("Ending");
        targetLocation.setLatitude(eDLat);
        targetLocation.setLongitude(eDLon);

        distanceInMeters = (targetLocation.distanceTo(startLocation) / 1000);


        // distance = locationA.distanceTo(locationB);   // in meters
        //  distance = locationA.distanceTo(locationB)/1000;   // in km

        String strCalKM = String.format("%.02f", distanceInMeters);

        App.showLog("======KM====" + strCalKM);

        return strCalKM;
    }


    public static String getDistanceInMeter(double sDLat, double sDLon, double eDLat, double eDLon) {
        float distanceInMeters = 0;

        Location startLocation = new Location("Start");
        startLocation.setLatitude(sDLat);
        startLocation.setLongitude(sDLon);

        Location targetLocation = new Location("Ending");
        targetLocation.setLatitude(eDLat);
        targetLocation.setLongitude(eDLon);

        distanceInMeters = (targetLocation.distanceTo(startLocation));


        // distance = locationA.distanceTo(locationB);   // in meters
        //  distance = locationA.distanceTo(locationB)/1000;   // in km

        String strCalMeter = String.format("%.02f", distanceInMeters);

        App.showLog("======METER====" + strCalMeter);

        return strCalMeter;
    }

/*
    public static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
    }


    public static Retrofit getRetrofitBuilder() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .baseUrl(App.strBaseHostUrl)
                .client(getClient()) // it's optional for adding client
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getRetrofitApiService() {
        return getRetrofitBuilder().create(ApiService.class);
    }

    public static RequestBody createPartFromString(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    public static RequestBody createPartFromFile(File file) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file);
    }

    public static HashMap<String, RequestBody> addCommonHashmap() {
        HashMap<String, RequestBody> commonHashMap = new HashMap<>();

        App.showLog("====strUserId====" + App.sharePrefrences.getStringPref(PreferencesKeys.strUserId));
        App.showLog("===strAccessToken=====" + App.sharePrefrences.getStringPref(PreferencesKeys.strAccessToken));

        RequestBody r_app_mode = App.createPartFromString(App.APP_MODE);
        RequestBody r_app_platform = App.createPartFromString(App.APP_PLATFORM);
        RequestBody r_android_token = App.createPartFromString(App.sharePrefrences.getStringPref(PreferencesKeys.strDeviceId));
        RequestBody r_device_id = App.createPartFromString(App.sharePrefrences.getStringPref(PreferencesKeys.strDeviceId));

        RequestBody r_user_id = App.createPartFromString(App.sharePrefrences.getStringPref(PreferencesKeys.strUserId));
        RequestBody r_access_token = App.createPartFromString(App.sharePrefrences.getStringPref(PreferencesKeys.strAccessToken));
        //  RequestBody r_language = App.createPartFromString(App.sharePrefrences.getStringPref(PreferencesKeys.strLid));


        commonHashMap.put("appmode", r_app_mode);
        commonHashMap.put("platform", r_app_platform);
        commonHashMap.put("androidtoken", r_android_token);
        commonHashMap.put("device_id", r_device_id);
        commonHashMap.put("user_id", r_user_id);
        commonHashMap.put("accesstoken", r_access_token);
        return commonHashMap;
    }


    public static void showLogApiRespose(String op, Response response) {
        if(APP_MODE.equalsIgnoreCase("2")) {
            String strResponse = new Gson().toJson(response.body());
            Log.d("=op==>" + op, "response==>" + strResponse);
        }
    }

    public static void setTypefaceMaterialEditText(MaterialEditText materialEditText) {
        try {

            materialEditText.setAccentTypeface(App.getProximanovaReg());
            materialEditText.setTypeface(App.getProximanovaSBold());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static boolean isNumeric(String str) {
        try {
            float aFloat = Float.parseFloat(str);
        } catch (Exception exc) {
            return false;
        }
        return true;
    }

    public static float getStringToFloat(String str) {
        float aFloat = 0;
        try {
            aFloat = Float.parseFloat(str);
        } catch (Exception exc) {
            return aFloat;
        }
        return aFloat;
    }

    public static int getStringToInt(String str) {
        int aInt = 0;
        try {
            aInt = Integer.parseInt(str);
        } catch (Exception exc) {
            return aInt;
        }
        return aInt;
    }


    public static String getRoundFromFloat(float fData) {
        try {
            DecimalFormat df = new DecimalFormat("#.#");
            double d = Double.parseDouble("" + fData);

            return "" + df.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return "" + fData;
        }
    }


    public static String getRoundFromFloat2(float fData) {

        String stringVariable = "" + fData;
        try {

            if (stringVariable.endsWith(".0"))
                stringVariable = stringVariable.replace(".0", "");

            return stringVariable;
        } catch (Exception e) {
            e.printStackTrace();
            return stringVariable;
        }
    }

    public static String getAppendUrlData(String url) {

       /* App.showLog("====strUserId===="+App.sharePrefrences.getStringPref(PreferencesKeys.strUserId));
        App.showLog("===strAccessToken====="+App.sharePrefrences.getStringPref(PreferencesKeys.strAccessToken));
*/

        //String finalUrl = url + "?user_id=" + App.sharePrefrences.getStringPref(PreferencesKeys.strUserId) + "&token=" + App.sharePrefrences.getStringPref(PreferencesKeys.strAccessToken);
        String finalUrl = url + "/mobile";
        App.showLog("=finalUrl=" + finalUrl);
        return finalUrl;
    }

    public static void getDownloadPDFFile(Context context, String filepath_url) {
        try {
            //String url = "http://www.example.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(filepath_url));
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      /*Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            SCREEN_HEIGHT = size.y;
            SCREEN_WIDTH = size.x;



             private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }



    private String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

            */


    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
