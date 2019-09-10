package edu.uiowa.MainActivity

import java.util.Random



class TicTacToe:Game(){

    var b = Array(3) { Array(3){ Player.Empty } } // 3*3 empty board


    //refill board with player.empty
    override fun init(){
        for (i in 0..2){
            b[i].fill(Player.Empty)
        }

    }
    //copy the whole game
    override fun copy(): Game {
        val new = TicTacToe()
        for (y in 0..2) {
            for (x in 0..2) {
                new.b[y][x] = b[y][x]
            }
        }
        return new
    }
    //check the possible moves for player
    override fun getPossibleMoves(): ArrayList<Int> {
        val list = arrayListOf<Int>()
        for (pos in 1..9){
            if (b[pos/3][pos%3] == Player.Empty) {
                list.add(pos)
            }
        }

        return list
    }

    override fun CheckWinner(): Player {
        // check horizontal and vertical
        for (i in 0..2) {
            if (b[0][i] != Player.Empty && b[0][i] == b[1][i] && b[0][i] == b[2][i]) {
                return b[0][i]
            }
            if (b[i][0] != Player.Empty && b[i][0] == b[i][1] && b[i][0] == b[i][2]) {
                return b[i][0]
            }
        }
        //check diagonal
        if (b[0][0] == b[1][1] && b[0][0] == b[2][2]&& b[0][0]!= Player.Empty) {
            return b[0][0]
        }
        if (b[0][2] == b[1][1] && b[0][2] == b[2][0]&& b[2][0]!= Player.Empty) {
            return b[0][2]
        }
        //check horizontal and vertical
        var count = 0
        for (i in 0..2){
            for (j in 0..2){
                if (b[i][j]== Player.Empty){
                    count += 1
                }
            }
        }
        //if board is full, so the game status would be draw
        if (count == 0){
            return Player.Draw
        }
        return Player.Empty
    }

    //see if it is legal to put
    override fun CheckMove(pos: Int):Boolean {
        return  b[pos / 3][pos % 3] == Player.Empty
    }

    //put value into cell
    override fun put(pos:Int,player: Player){
        b[pos / 3][pos % 3] = player

    }

    //get the content in the cell
    override fun getcell(pos:Int):Player{
        return  b[pos / 3][pos % 3]
    }

    //make decisions for computer
    fun ainextMove():Int {
        var r = BestMove(Player.O)  // complete a row of X and win if possible
        if (r < 0)
            r = BestMove(Player.X)  // or try to block O from winning
        if (r < 0) {  // otherwise move randomly
            do
                r = Random().nextInt(9)
            while (b[r / 3][r % 3] !== Player.Empty)
        }
        return r
    }

    //check the best position for ai
    fun BestMove(player: Player): Int {
        //all the ways to win in simple tictactoe game
        val rows = arrayOf(intArrayOf(0, 2), intArrayOf(3, 5), intArrayOf(6, 8), intArrayOf(0, 6), intArrayOf(1, 7), intArrayOf(2, 8), intArrayOf(0, 8), intArrayOf(2, 6))
        for (i in 0..7) {
            val result = find1Row(player, rows[i][0], rows[i][1])
            if (result >= 0)
                return result
        }
        return -1
    }

    //check which pos can pin
    fun find1Row(player: Player, x: Int, y: Int): Int {
        val z = (x + y) / 2  // middle spot
        if (b[x / 3][x % 3] == player && b[y / 3][y % 3] == player && b[z / 3][z % 3] === Player.Empty)
            return z
        if (b[x / 3][x % 3] === player && b[z / 3][z % 3] === player && b[y / 3][y % 3] === Player.Empty)
            return y
        return if (b[y / 3][y % 3] === player && b[z / 3][z % 3] === player && b[x / 3][x % 3] === Player.Empty) x else -1
    }


}

//first vision that print outputs for debug.
//{
//fun start(){
//    val keyboard = Scanner(System.`in`)
//    print("StartGame(Y/N)\n")
//    var a = keyboard.next().toLowerCase()
//    if (a =="n"){
//        return
//    }
//    while (a != "n"){
//        try {
//            when (a){
//                "y" ->  Start()
//            }}catch (e: Throwable){
//            print("Error: please enter Y or N.\n You need start another game. \n")
//        }
//        print("StartAnotherGame(Y/N)\n")
//        a = keyboard.next().toLowerCase()}
//
//}
//
//fun Start(){
//    init()
//    var currentPlayer = Player.O
//
//    val scanner = Scanner(System.`in`)
//
//    while (true) {
//        DisplayBoard()
//        println()
//        when (CheckWinner()) {
//            Player.X -> {
//                println("Player X wins!")
//                countX +=1
//                return
//            }
//            Player.O -> {
//                println("Player O wins!")
//                countO+=1
//                return
//            }
//            Player.Draw -> {
//                println("Draw")
//                countD+=1
//                return
//            }
//        }
//        currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
//        println("Player $currentPlayer's turn\n")
//        var pos: Int
//        while (true) {
//            print("Enter a position (1~9): \n")
//            pos = scanner.nextInt()
//            if (pos !in 1..9) {
//                println("Invalid positoin!\n")
//            } else if (!CheckMove(pos - 1)) {
//                println("Occuppied pos, try another one!\n")
//            } else {
//                break
//            }
//        }
//        put(pos - 1, currentPlayer)
//    }
//
//fun DisplayBoard() {
//    for (i in 0.. 2) {
//        for (j in 0..2) {
//            print(b[i][j]) //print out the values for each position and match to the right symbol
//        }}}
//}}