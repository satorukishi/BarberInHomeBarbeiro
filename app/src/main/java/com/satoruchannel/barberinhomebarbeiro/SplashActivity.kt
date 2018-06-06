package com.satoruchannel.barberinhomebarbeiro

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashActivity:AppCompatActivity() {
    //Tempo que nosso splashscreen ficará visivel
    private val SPLASH_DISPLAY_LENGTH = 3500L

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Executando o método que iniciará nossa animação
        carregar()
    }

    private fun carregar() {
        val anim = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splash)
        anim.reset()
        //Pegando o nosso objeto criado no layout
        val iv = findViewById(R.id.splash) as ImageView
        if (iv != null)
        {
            iv.clearAnimation()
            iv.startAnimation(anim)
        }
        Handler().postDelayed(object:Runnable {
            public override fun run() {
                // Após o tempo definido irá executar a próxima tela
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                this@SplashActivity.finish()
            }
        }, SPLASH_DISPLAY_LENGTH)
    }
}