package co.xware_tech.tictactoe.gamefragment

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton

class GameViewModel:ViewModel(){
    var userTurn = MutableLiveData(true)

    fun changeTurn(){
        userTurn.value=userTurn.value?.not()
    }
    fun resetTurn(){
        userTurn.value=true
    }
    fun randomNumber(range:Int):Int{
        return (0..range).shuffled().last()
    }
}