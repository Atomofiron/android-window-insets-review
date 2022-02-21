package app.atomofiron.insets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RadioGroup
import android.widget.ToggleButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var childrenLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = 0x01000000

        childrenLayout = findViewById(R.id.children)
        val toggleRed = findViewById<ToggleButton>(R.id.toggle_red)

        val inflater = LayoutInflater.from(this)
        val layoutGreen = inflater.inflate(R.layout.layout_green, childrenLayout, false)
        val layoutBlue = inflater.inflate(R.layout.layout_blue, childrenLayout, false)
        val layoutRed = inflater.inflate(R.layout.layout_red, childrenLayout, false)

        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_none -> {
                    childrenLayout.removeView(layoutBlue)
                    childrenLayout.removeView(layoutGreen)
                }
                R.id.radio_green -> {
                    childrenLayout.removeView(layoutBlue)
                    addContainer(layoutGreen, index = 0)
                }
                R.id.radio_blue -> {
                    childrenLayout.removeView(layoutGreen)
                    addContainer(layoutBlue, index = 0)
                }
            }
        }
        toggleRed.setOnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> addContainer(layoutRed)
                else -> childrenLayout.removeView(layoutRed)
            }
        }
    }

    fun Any.poop(s: String) {
        Log.e("atomofiron", "[${this.javaClass.simpleName} $s]")
    }

    private fun addContainer(container: View, index: Int? = null) {
        when (index) {
            null -> childrenLayout.addView(container)
            else -> childrenLayout.addView(container, index)
        }
        container.requestApplyInsets()
        // зачем requestApplyInsets()
        // https://habr.com/ru/company/oleg-bunin/blog/488196/
    }
}