/*
 * Copyright (C) 2020 The Android Open Source Project
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
 * limitations under the License.
 */

package com.android.internal.accessibility.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.accessibility.AccessibilityManager.ShortcutType;

import com.android.internal.accessibility.common.ShortcutConstants.AccessibilityFragmentType;

/**
 * Extension for {@link AccessibilityTarget} with {@link AccessibilityFragmentType#INVISIBLE_TOGGLE}
 * type.
 */
class InvisibleToggleAllowListingFeatureTarget extends AccessibilityTarget {

    InvisibleToggleAllowListingFeatureTarget(Context context, @ShortcutType int shortcutType,
            boolean isShortcutSwitched, String id, int uid, CharSequence label, Drawable icon,
            String key) {
        super(context, shortcutType, AccessibilityFragmentType.INVISIBLE_TOGGLE, isShortcutSwitched,
                id, uid, label, icon, key);
    }
}
