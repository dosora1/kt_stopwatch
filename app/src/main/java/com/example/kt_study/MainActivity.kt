package com.example.kt_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.example.kt_study.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null   //전역변수로 바인딩변수 선언
    private val binding get() = mBinding!!
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            start()
        }
        binding.btnStop.setOnClickListener {
            pause()
        }
        binding.btnReset.setOnClickListener {
            reset()
        }

    }
    private fun start(){
        if (!isRunning) { //정지 상태일 때만 동작
            isRunning = true
            timerTask = kotlin.concurrent.timer(period = 10) {    // timer() 호출
                time++    // period=10, 0.01초마다 time를 1씩 증가

                val df1 = DecimalFormat("00")
                val df2 = DecimalFormat("00")

                val sec = df1.format(time / 100)    // time/100, 나눗셈의 몫 (초 부분)
                val milli = df2.format(time % 100)    // time%100, 나눗셈의 나머지 (밀리초 부분)

                // UI조작을 위한 메서드
                runOnUiThread {
                    binding.timeTxt.text = "$sec : $milli"    // TextView 세팅
                }
            }
        }
    }
    private fun pause() {
        isRunning = false
        timerTask?.cancel()
    }
    private fun reset() {
        timerTask?.cancel()
        time=0
        isRunning = false
        binding.timeTxt.text = "00 : 00"
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}