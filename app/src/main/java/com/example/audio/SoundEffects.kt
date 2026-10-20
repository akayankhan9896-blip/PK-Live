package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SoundEffects {
    private var toneGenerator: ToneGenerator? = null

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
        } catch (_: Exception) {
            toneGenerator = null
        }
    }

    fun playGiftSound(context: Context, isHighTier: Boolean = false) {
        vibrate(context, if (isHighTier) 250 else 60)
        try {
            toneGenerator?.let { tg ->
                CoroutineScope(Dispatchers.Default).launch {
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP2, 100)
                    if (isHighTier) {
                        delay(120)
                        tg.startTone(ToneGenerator.TONE_PROP_ACK, 200)
                    }
                }
            }
        } catch (_: Exception) {}
    }

    fun playLionRoar(context: Context) {
        vibrate(context, 400)
        try {
            toneGenerator?.let { tg ->
                CoroutineScope(Dispatchers.Default).launch {
                    tg.startTone(ToneGenerator.TONE_CDMA_HIGH_L, 150)
                    delay(160)
                    tg.startTone(ToneGenerator.TONE_CDMA_LOW_L, 300)
                }
            }
        } catch (_: Exception) {}
    }

    fun playVictoryFanfare(context: Context) {
        vibrate(context, 300)
        try {
            toneGenerator?.let { tg ->
                CoroutineScope(Dispatchers.Default).launch {
                    tg.startTone(ToneGenerator.TONE_PROP_PROMPT, 120)
                    delay(140)
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP2, 150)
                    delay(160)
                    tg.startTone(ToneGenerator.TONE_PROP_ACK, 250)
                }
            }
        } catch (_: Exception) {}
    }

    fun playCoinSound(context: Context) {
        vibrate(context, 50)
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 80)
        } catch (_: Exception) {}
    }

    private fun vibrate(context: Context, millis: Long) {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            vibrator?.let { v ->
                if (v.hasVibrator()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        v.vibrate(millis)
                    }
                }
            }
        } catch (_: Exception) {}
    }
}
