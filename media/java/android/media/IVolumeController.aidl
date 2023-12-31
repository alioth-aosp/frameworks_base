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
 * limitations under the License.
 */

package android.media;

/**
 * AIDL for the AudioService to report interesting events to a volume control
 * dialog in another process.
 * @hide
 */
oneway interface IVolumeController {

    void displaySafeVolumeWarning(int flags);

    void volumeChanged(int streamType, int flags);

    void masterMuteChanged(int flags);

    void setLayoutDirection(int layoutDirection);

    void dismiss();

    /**
     * Change the a11y mode.
     * @param a11yMode one of {@link VolumePolicy#A11Y_MODE_MEDIA_A11Y_VOLUME},
     *     {@link VolumePolicy#A11Y_MODE_INDEPENDENT_A11Y_VOLUME}
     */
    void setA11yMode(int mode);

    /**
     * Display a sound-dose related warning.
     * This method will never be called if the CSD (Computed Sound Dose) feature is
     * not enabled. See com.android.android.server.audio.SoundDoseHelper for the state of
     * the feature.
     * @param warning the type of warning to display, values are one of
     *        {@link android.media.AudioManager#CSD_WARNING_DOSE_REACHED_1X},
     *        {@link android.media.AudioManager#CSD_WARNING_DOSE_REPEATED_5X},
     *        {@link android.media.AudioManager#CSD_WARNING_MOMENTARY_EXPOSURE},
     *        {@link android.media.AudioManager#CSD_WARNING_ACCUMULATION_START}.
     * @param displayDurationMs the time expressed in milliseconds after which the dialog will be
     *        automatically dismissed, or -1 if there is no automatic timeout.
     */
    void displayCsdWarning(int warning, int displayDurationMs);
}
