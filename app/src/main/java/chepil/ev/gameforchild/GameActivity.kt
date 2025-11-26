package chepil.ev.gameforchild

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import kotlin.Int
import kotlin.collections.mutableListOf
import kotlin.random.Random
import android.widget.Button
import androidx.core.graphics.drawable.toDrawable
import kotlinx.coroutines.delay

class GameActivity : AppCompatActivity() {

    var colorImages = mutableListOf(R.drawable.watermelon,
        R.drawable.moon,
        R.drawable.seed,
        R.drawable.tree,
        R.drawable.fence,
        R.drawable.beehive,
        R.drawable.monstera,
        R.drawable.mushroom,
        R.drawable.mushrooms,
        R.drawable.sunflower,
        R.drawable.buterflies,
        R.drawable.animals,
        R.drawable.coloraxe,
        R.drawable.flower,
        R.drawable.sun,
        R.drawable.acorn,
        R.drawable.spraybottle,
        R.drawable.skateshoes,
        R.drawable.wateringcan
    )
    var randomImages = mutableListOf(R.drawable.watermelon,
        R.drawable.moon,
        R.drawable.seed,
        R.drawable.tree,
        R.drawable.fence,
        R.drawable.beehive,
        R.drawable.monstera,
        R.drawable.mushroom,
        R.drawable.mushrooms,
        R.drawable.sunflower,
        R.drawable.buterflies,
        R.drawable.animals,
        R.drawable.coloraxe,
        R.drawable.flower,
        R.drawable.sun,
        R.drawable.acorn,
        R.drawable.spraybottle,
        R.drawable.skateshoes,
        R.drawable.wateringcan
    )
    var idCurrentShadowImage = 0
    var allRandomShadowImages = mutableListOf(0,0,0,0)
    var currentColorImage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<ImageView>(R.id.firstShadow).setColorFilter(Color.DKGRAY)
        findViewById<ImageView>(R.id.secondShadow).setColorFilter(Color.DKGRAY)
        findViewById<ImageView>(R.id.thirdShadow).setColorFilter(Color.DKGRAY)
        findViewById<ImageView>(R.id.fourthShadow).setColorFilter(Color.DKGRAY)

        startNewGame()
    }

    fun nextLevel(){
        try{
            if (colorImages.count() > 2 )
                currentColorImage = colorImages.random()
            else{
                var alert = AlertDialog.Builder(this)
                alert.setTitle("Игра окончена")
                alert.setMessage("Хотите начать заново?")
                alert.setPositiveButton("Да") {
                        _, _ -> startNewGame()
                }
                alert.setNegativeButton("Нет") {_,_ ->
                    stopGame()
                }
                alert.show()
            }

            findViewById<ImageView>(R.id.colorImage).setImageResource(currentColorImage)

            setRandomShadowImages()
        }catch (e: Exception){
        }

    }

    fun startNewGame(){
        colorImages.clear()
        colorImages.addAll(randomImages)
        nextLevel()
    }
    fun setRandomShadowImages(){
        try{
            allRandomShadowImages = mutableListOf(0,0,0,0)

            var random: Int
            for(i in 0..3){
                while (true){
                    random = randomImages.random()
                    if (!allRandomShadowImages.contains(random) && random != currentColorImage){
                        allRandomShadowImages[i] = random
                        break
                    }
                }
            }
            idCurrentShadowImage = Random.nextInt(0,4)
            allRandomShadowImages[idCurrentShadowImage] = currentColorImage

            findViewById<ImageView>(R.id.firstShadow).setImageResource(allRandomShadowImages[0])
            findViewById<ImageView>(R.id.secondShadow).setImageResource(allRandomShadowImages[1])
            findViewById<ImageView>(R.id.thirdShadow).setImageResource(allRandomShadowImages[2])
            findViewById<ImageView>(R.id.fourthShadow).setImageResource(allRandomShadowImages[3])

        }catch (e: Exception ){

        }
    }
    fun skipLevel(view: View) {
        nextLevel()
    }
    fun stopGame() {
        var intent = Intent(this@GameActivity, MainActivity::class.java)
        startActivity(intent)
    }

    fun checkAnswer(answer: Int){
        try{
            if (answer == idCurrentShadowImage){
                colorImages.remove(currentColorImage)
                findViewById<ImageView>(R.id.firstShadow).setBackgroundColor(Color.TRANSPARENT)
                findViewById<ImageView>(R.id.secondShadow).setBackgroundColor(Color.TRANSPARENT)
                findViewById<ImageView>(R.id.thirdShadow).setBackgroundColor(Color.TRANSPARENT)
                findViewById<ImageView>(R.id.fourthShadow).setBackgroundColor(Color.TRANSPARENT)
                nextLevel()
            }
            else{
                when(answer) {
                    0 -> findViewById<ImageView>(R.id.firstShadow).setBackgroundColor(Color.RED)
                    1 -> findViewById<ImageView>(R.id.secondShadow).setBackgroundColor(Color.RED)
                    2 -> findViewById<ImageView>(R.id.thirdShadow).setBackgroundColor(Color.RED)
                    else -> findViewById<ImageView>(R.id.fourthShadow).setBackgroundColor(Color.RED)
                }



            }
        }catch (e: Exception){
            findViewById<TextView>(R.id.infoText).text = e.toString()
        }

    }
    fun fourthShadowButton(view: View) {
        checkAnswer(3)
    }
    fun thirdShadowButton(view: View) {
        checkAnswer(2)
    }
    fun secondShadowButton(view: View) {
        checkAnswer(1)
    }
    fun firstShadowButton(view: View) {
        checkAnswer(0)
    }


}