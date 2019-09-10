package edu.uiowa

import edu.uiowa.MainActivity.Player
import edu.uiowa.MainActivity.TicTacToe
import org.junit.Test
import org.junit.Assert.*


class tictactoeTest{


    @Test
    fun testput1(){
        var x = TicTacToe()
        x.init()
        x.put(1, Player.O)
        x.put(3, Player.X)
        assertFalse(x.b[0][1]== Player.X)
        assertTrue(x.b[0][0]== Player.Empty)
        assertFalse(x.b[0][2]== Player.X)
    }

    @Test
    fun testput2(){
        var x = TicTacToe()
        x.init()
        x.put(1, Player.O)
        x.put(6, Player.X)
        assertTrue(x.b[0][1]== Player.O)
        assertTrue(x.b[2][0]== Player.X)
    }

    @Test
    fun testput3(){
        var x = TicTacToe()
        x.init()
        x.put(8, Player.X)
        assertTrue(x.b[2][2]== Player.X)}

    @Test
    fun testCheckwin1(){
        var x = TicTacToe()
        x.init()
        assertFalse(x.CheckWinner()== Player.X)
        assertFalse(x.CheckWinner()== Player.X)
    }
    @Test
    fun testCheckwin2(){
        var x = TicTacToe()
        x.init()
        x.put(6, Player.X)
        x.put(7, Player.X)
        x.put(8, Player.X)
        assertFalse(x.CheckWinner()== Player.Empty)
        assertTrue(x.CheckWinner()== Player.X)
    }

    @Test
    fun testCheckwin3(){
        var x = TicTacToe()
        x.init()
        x.put(0, Player.X)
        x.put(4, Player.X)
        x.put(8, Player.X)
        assertFalse(x.CheckWinner()== Player.Empty)
        assertTrue(x.CheckWinner()== Player.X)
    }

    @Test
    fun testCheckmove1(){
        var x = TicTacToe()
        x.init()
        x.put(7, Player.X)
        x.put(8, Player.X)
        x.put(0,Player.X)
        assertTrue(x.CheckMove(6))
        assertFalse(x.CheckMove(7))
    }

    @Test
    fun testCheckmove2(){
        var x = TicTacToe()
        x.init()
        x.put(0, Player.X)
        x.put(1, Player.X)
        x.put(8, Player.X)
        assertTrue(x.CheckMove(6))
        assertTrue(x.CheckMove(2))
        assertFalse(x.CheckMove(1))
        assertFalse(x.CheckMove(8))
    }

    @Test
    fun testCheckmove3(){
        var x = TicTacToe()
        x.init()
        x.put(7, Player.O)
        x.put(1, Player.X)
        x.put(8, Player.O)
        assertTrue(x.CheckMove(5))
        assertFalse(x.CheckMove(1))
        assertFalse(x.CheckMove(7))
        assertFalse(x.CheckMove(8))
        assertTrue(x.CheckMove(0))
    }

}