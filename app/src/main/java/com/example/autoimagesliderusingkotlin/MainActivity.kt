package com.example.autoimagesliderusingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar

import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var dotLayout : LinearLayout
    lateinit var mPager : ViewPager
    lateinit var toolbar: Toolbar
    var path : IntArray = intArrayOf(R.drawable.ironman,R.drawable.captain_america,R.drawable.thor,R.drawable.hulk)
    lateinit var dots : Array<ImageView>
    lateinit var adapter : PagerView

    var currentPager : Int = 0
    lateinit var timer: Timer
    val DELAY_MS : Long = 1500
    val PERIOD_MS : Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mPager = findViewById(R.id.pager)
        adapter = PagerView(this, path)
        mPager.adapter = adapter
        dotLayout = findViewById(R.id.dotLayout)
        createDots(0)
        updatePager()
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                currentPager = position
                createDots(position)
            }
        })
    }

    fun updatePager()
    {
        var handler = Handler()
        val Update : Runnable = Runnable {
            if (currentPager == path.size)
            {
                currentPager = 0
            }
            mPager.setCurrentItem(currentPager++, true)
        }
        timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                handler.post(Update)
            }
        },DELAY_MS, PERIOD_MS)
    }

    fun createDots(position: Int)
    {
        if (dotLayout!=null)
        {
            dotLayout.removeAllViews()
        }
        dots = Array(path.size,{i -> ImageView(this)})

        for (i in 0..path.size - 1)
        {
            dots[i] = ImageView(this)
            if (i == position)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots))
            }
            else
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dots))
            }

            var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            params.setMargins(4,0,4,0)
            dotLayout.addView(dots[i], params)
        }
    }
}
