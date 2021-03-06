package x.cross.androqr.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nikitagordia.cosin.textAdapters.DefaultBinaryTextAdapter
import kotlinx.coroutines.*
import x.cross.androqr.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {
    companion object{
        private const val TIME_FOR_LOAD = 2_500L
    }


    private val activityScope = CoroutineScope(Dispatchers.Main)
    private lateinit var view: ActivitySplashBinding
    private lateinit var mainIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(view.root)

        supportActionBar?.hide()

        setFullScreen()

        with(view.splashAnim){
            textAdapter = DefaultBinaryTextAdapter()
            speed = 0.005
            rectWidth = 40
            isLoadingData = true
        }

        mainIntent = Intent(this, MainActivity::class.java)
    }

    override fun onStart() {
        super.onStart()

        activityScope.launch {
            delay(TIME_FOR_LOAD)

            startActivity(mainIntent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        activityScope.cancel()
    }

    private fun setFullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}