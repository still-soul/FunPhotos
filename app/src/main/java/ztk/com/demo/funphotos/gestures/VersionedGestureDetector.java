package ztk.com.demo.funphotos.gestures;

/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

import android.content.Context;
import android.os.Build;

import ztk.com.demo.funphotos.interfaces.IGestureDetector;
import ztk.com.demo.funphotos.interfaces.OnGestureListener;

public final class VersionedGestureDetector {

    public static IGestureDetector newInstance(Context context,
                                               OnGestureListener listener) {
        final int sdkVersion = Build.VERSION.SDK_INT;
        IGestureDetector detector;

        if (sdkVersion < Build.VERSION_CODES.ECLAIR) {
            detector = new CupcakeIGestureDetector(context);
        } else if (sdkVersion < Build.VERSION_CODES.FROYO) {
            detector = new EclairIGestureDetector(context);
        } else {
            detector = new FroyoIGestureDetector(context);
        }

        detector.setOnGestureListener(listener);

        return detector;
    }

}