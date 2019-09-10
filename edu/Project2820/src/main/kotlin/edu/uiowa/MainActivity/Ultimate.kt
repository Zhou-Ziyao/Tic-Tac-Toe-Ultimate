package edu.uiowa.MainActivity

import java.util.Random



class ultimate:Game(){

    var b = Array(9) { Array(9){ Player.Empty } } // 9*9 empty board
    var lastmovePos: Int? = null

    //refill board with player empty
    override fun init(){
        for (i in 0..8){
            b[i].fill(Player.Empty)
        }
        lastmovePos = null

    }

    //copy the whole board and return
    override fun copy(): Game {
        val a = ultimate()
        for (y in 0..8) {
            for (x in 0..8) {
                a.b[y][x] = b[y][x]
            }
        }
        a.lastmovePos = lastmovePos
        return a
    }


    //get possible moves for ai
    override fun getPossibleMoves(): ArrayList<Int> {
        val list = arrayListOf<Int>()
        for (pos in 0..80){
            if (CheckMove(pos)) {
                list.add(pos)
            }
        }

        return list
    }

    //check winner for local
    fun CheckLocalWinner(x:Int,y:Int): Player {
        val xx =x*3
        val yy =y*3
        // check horizontal and vertical
        for (i in 0..2) {
            if (b[yy][xx+i] != Player.Empty && b[yy][xx+i] == b[yy+1][xx+i] && b[yy][xx+i] == b[yy+2][xx+i]) {
                return b[yy][xx+i]
            }
            if (b[yy+i][xx] != Player.Empty && b[yy+i][xx] == b[yy+i][xx+1] && b[yy+i][xx] == b[yy+i][xx+2]) {
                return b[yy+i][xx]
            }
        }
        //check diagonal
        if (b[yy][xx] == b[yy+1][xx+1] && b[yy][xx] == b[yy+2][xx+2]&& b[yy][xx]!= Player.Empty) {
            return b[yy][xx]
        }
        if (b[yy][xx+2] == b[yy+1][xx+1] && b[yy][xx+2] == b[yy+2][xx]&& b[yy+2][xx]!= Player.Empty) {
            return b[yy][xx+2]
        }
        //check horizontal and vertical
        var count = 0
        for (i in yy..yy+2){
            for (j in xx..xx+2){
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


    //check winner for whole game
    override fun CheckWinner(): Player {
        val b =Array(3){ Array(3) {Player.Empty}}
        for (i in 0..2) {
            for(j in 0 ..2) {
                b[i][j]=CheckLocalWinner(i,j)
            }
        }
        // check horizontal and vertical
        for (i in 0..2) {
            if (b[0][i] != Player.Empty && b[0][i] != Player.Draw && b[0][i] == b[1][i] && b[0][i] == b[2][i]) {
                return b[0][i]
            }
            if (b[i][0] != Player.Empty && b[i][0] != Player.Empty && b[i][0] == b[i][1] && b[i][0] == b[i][2]) {
                return b[i][0]
            }
        }
        //check diagonal
        if (b[0][0] == b[1][1] && b[0][0] == b[2][2]&& b[0][0]!= Player.Empty && b[0][0] !=Player.Draw) {
            return b[0][0]
        }
        if (b[0][2] == b[1][1] && b[0][2] == b[2][0]&& b[2][0]!= Player.Empty &&b[2][0]!= Player.Draw) {
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
        if(lastmovePos == null){
            return  b[pos / 9][pos % 9] == Player.Empty
        }
        val xx=lastmovePos!!%3
        val yy =lastmovePos!! / 3
        val xxx= pos % 9
        val yyy = pos / 9
        return b[yyy][xxx] == Player.Empty&& xx==xxx/3&& yy== yyy/3&& CheckLocalWinner(xx,yy)== Player.Empty
    }

    //put value into cell
    override fun put(pos:Int,player: Player){
        val x=pos % 9
        val y=pos /9
        b[y][x] = player

        lastmovePos = y%3 * 3 + x%3
    }

    //get the content in the cell
    override fun getcell(pos:Int):Player{
        return  b[pos / 9][pos % 9]
    }

    fun ainextMove():Int {
        if (lastmovePos == null) {
            // choose the best local board from the global board
            val b = Array(3) { i ->
                Array(3) { j ->
                    CheckLocalWinner(j, i)
                }
            }
            val globalpos = chooseNextMove(b)

            // and choose the best move the local board
            val xx=globalpos % 3 * 3
            val yy=globalpos / 3 * 3
            val b1 = Array(3) { i ->
                Array(3) { j ->
                    this.b[yy + i][xx + j]
                }
            }
            val pos=chooseNextMove(b1)
            return (pos / 3 + yy) * 9 + (pos % 3 + xx)
        } else {
            // choose the best move from the local board
            val xx=lastmovePos!! % 3 * 3
            val yy=lastmovePos!! / 3 * 3
            val b = Array(3) { i ->
                Array(3) { j ->
                    b[yy + i][xx + j]
                }
            }
            val pos=chooseNextMove(b)
            return (pos / 3 + yy) * 9 + (pos % 3 + xx)
        }
    }

    fun chooseNextMove(b: Array<Array<Player>>):Int {
        var r = BestMove(b, Player.O)  // complete a row of X and win if possible
        if (r < 0)
            r = BestMove(b, Player.X)  // or try to block O from winning
        if (r < 0) {  // otherwise move randomly
            do
                r = Random().nextInt(9)
            while (b[r / 3][r % 3] !== Player.Empty)
        }
        return r
    }

    //check the best position for ai
    fun BestMove(b: Array<Array<Player>>, player: Player): Int {
        //all the ways to win in simple tictactoe game
        val rows = arrayOf(intArrayOf(0, 2), intArrayOf(3, 5), intArrayOf(6, 8), intArrayOf(0, 6), intArrayOf(1, 7), intArrayOf(2, 8), intArrayOf(0, 8), intArrayOf(2, 6))
        for (i in 0..7) {
            val result = find1Row(b, player, rows[i][0], rows[i][1])
            if (result >= 0)
                return result
        }
        return -1
    }

    //check which pos can pin
    fun find1Row(b: Array<Array<Player>>, player: Player, x: Int, y: Int): Int {
        val z = (x + y) / 2  // middle spot
        if (b[x / 3][x % 3] == player && b[y / 3][y % 3] == player && b[z / 3][z % 3] === Player.Empty)
            return z
        if (b[x / 3][x % 3] === player && b[z / 3][z % 3] === player && b[y / 3][y % 3] === Player.Empty)
            return y
        return if (b[y / 3][y % 3] === player && b[z / 3][z % 3] === player && b[x / 3][x % 3] === Player.Empty) x else -1
    }


}

