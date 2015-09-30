/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.systemui.statusbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.PhoneStatusBar;

public class EmptyShadeView extends StackScrollerDecorView {

    String customText;
    Handler mHandler;

    class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler handler) {
            super(handler);
        }

        void observe() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System
                    .getUriFor(Settings.System.EMPTY_SHADE_TEXT), false, this);
        }

        @Override
        public void onChange(boolean selfChange) {
            updateText();
        }
    }

    public EmptyShadeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateText();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateText();
        ((TextView) findViewById(R.id.no_notifications)).setText(customText);
    }

    @Override
    protected View findContentView() {
        return findViewById(R.id.no_notifications);
    }

    private void updateText() {
        ContentResolver resolver = mContext.getContentResolver();

        customText = Settings.System.getStringForUser(mContext.getContentResolver(),
                Settings.System.EMPTY_SHADE_TEXT, UserHandle.USER_CURRENT);
    }
}
