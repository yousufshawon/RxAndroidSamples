package com.yousuf.shawon.rxandroidsamples.util;

/**
 * Created by Yousuf on 10/25/2017.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;
import android.util.TypedValue;
import com.yousuf.shawon.rxandroidsamples.MainActivity;

/**
 * This class contains static utility methods.
 */
public class Utils {

  // Prevents instantiation.
  private Utils() {}

  /**
   * Enables strict mode. This should only be called when debugging the application and is useful
   * for finding some potential bugs or best practice violations.
   */
  @TargetApi(11)
  public static void enableStrictMode() {
    // Strict mode is only available on gingerbread or later
    if (Utils.hasGingerbread()) {

      // Enable all thread strict mode policies
      StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
          new StrictMode.ThreadPolicy.Builder()
              .detectAll()
              .penaltyLog();

      // Enable all VM strict mode policies
      StrictMode.VmPolicy.Builder vmPolicyBuilder =
          new StrictMode.VmPolicy.Builder()
              .detectAll()
              .penaltyLog();

      // Honeycomb introduced some additional strict mode features
      if (Utils.hasHoneycomb()) {
        // Flash screen when thread policy is violated
        threadPolicyBuilder.penaltyFlashScreen();
        // For each activity class, set an instance limit of 1. Any more instances and
        // there could be a memory leak.
        vmPolicyBuilder
            .setClassInstanceLimit(MainActivity.class, 1);
        // .setClassInstanceLimit(ContactDetailActivity.class, 1);
      }

      // Use builders to enable strict mode policies
      StrictMode.setThreadPolicy(threadPolicyBuilder.build());
      StrictMode.setVmPolicy(vmPolicyBuilder.build());
    }
  }

  /**
   * Uses static final constants to detect if the device's platform version is Gingerbread or
   * later.
   */
  public static boolean hasGingerbread() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
  }

  /**
   * Uses static final constants to detect if the device's platform version is Honeycomb or
   * later.
   */
  public static boolean hasHoneycomb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * Uses static final constants to detect if the device's platform version is Honeycomb MR1 or
   * later.
   */
  public static boolean hasHoneycombMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }

  /**
   * Uses static final constants to detect if the device's platform version is ICS or
   * later.
   */
  public static boolean hasICS() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }


  /**
   * Converting dp to pixel
   */
  public static int dpToPx( Context context, int dp) {
    Resources r = context.getResources();
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
  }


  public static long getRandomNumber() {
    return (long) (Math.random() * 1000);
  }


}

