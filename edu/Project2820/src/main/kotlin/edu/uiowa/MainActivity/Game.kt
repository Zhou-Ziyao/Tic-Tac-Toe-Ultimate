package edu.uiowa.MainActivity

//create this class for ai use
//determine the bestmove
abstract class Game {

    abstract fun init()

    abstract fun CheckMove(pos: Int): Boolean

    abstract fun put(pos: Int, player: Player)

    abstract fun CheckWinner(): Player?

    abstract fun getcell(pos: Int): Player

    abstract fun getPossibleMoves(): ArrayList<Int>

    abstract fun copy(): Game
}
