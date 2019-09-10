package edu.uiowa.MainActivity
//enum class Player
//easy to get string and switch player
enum class Player{X,O,Empty,Draw;

    fun string():String =
            when (this) {
                X -> "X"
                O -> "O"
                Empty -> " "
                Draw ->"Draw"
            }

    fun next():Player=
            when(this){
                X -> O
                O -> X
                else -> X
            }

}