package edu.uiowa.Javafx.Simple



import edu.uiowa.Javafx.StartPage
import edu.uiowa.MainActivity.Player
import edu.uiowa.MainActivity.TicTacToe
import javafx.application.Application
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import java.util.*
import kotlin.concurrent.schedule




class pveGameboard : Application() {

    private val game = TicTacToe()
    private var countX = 0
    private var countO = 0
    private var countD = 0
    private var currentPlayer = Player.X


    private val buttons = Array(9) { pos ->
        Button(" ").apply {
            this.setMaxSize(100.0, 100.0)
            this.setMinSize(100.0, 100.0)
            font = Font.font(30.0)
            this.onAction = EventHandler {
                Move(pos)
            }
        }
    }

    private val reset =Button("Reset ").apply { this.setMaxSize(160.0,80.0)
        this.setMinSize(160.0,80.0)
        font =Font.font(30.0)
        this.onAction= EventHandler {
            init()
        }}


    private val label = Label().apply {
        font = Font.font(20.0)
    }

    private val label2 = Label().apply {
        font = Font.font(20.0)
    }

    private val quit =Button("Quit").apply {  this.setMaxSize(160.0,80.0)
        this.setMinSize(160.0,80.0)
        font =Font.font(30.0)
        val buttonhandler = ButtonControl()
        this.onAction = buttonhandler

    }
    private val Goback = Button("Goback to previous page").apply{this.setMaxSize(160.0,80.0)
        this.setMinSize(400.0,80.0)
        font = Font.font(20.0)
        val goback = GoBack()
        this.onAction = goback}

    class ButtonControl:EventHandler<ActionEvent>{
        override fun handle(event: ActionEvent) {
            Platform.exit()
        }
    }

    class GoBack:EventHandler<ActionEvent>{
        override fun handle(event: ActionEvent) {
            imageStage?.hide()
            StartPage().start(imageStage)
        }
    }



    override fun start(primaryStage: Stage) {
        imageStage = primaryStage
        val pane = GridPane()
        for (i in 0..8) {
            pane.add(buttons[i], i % 3, i / 3)
        }
        pane.add(label, 0, 3, 3, 1)
        pane.add(reset,0,5,5,1)
        pane.add(label2,0,6,6,1)
        pane.add(quit,2,4,4,2)
        pane.add(Goback,0,7,7,1)
        showTurn()
        val scene = Scene(pane)
        imageStage?.scene = scene
        imageStage?.title = "Tic Tac Toe"

        imageStage?.show()
    }




    override fun init(){
        for (i in 0..8){
            buttons[i].setText(Player.Empty.string())
        }
        currentPlayer = Player.X
        game.b= Array(3) { Array(3){ Player.Empty} }
    }


    private fun Move(pos: Int) {
        if (currentPlayer != Player.Empty && game.CheckMove(pos)) {
            game.put(pos, currentPlayer)
            buttons[pos].setText( currentPlayer.string())


            val winner = game.CheckWinner()
            if (winner == Player.X){
                countX+=1
            }
            if(winner == Player.O){
                countO +=1
            }
            if(winner ==Player.Draw){
                countD += 1
            }
            if (winner == Player.Empty) {
                if (currentPlayer== Player.X){
                    currentPlayer = Player.O

                }else{
                    currentPlayer= Player.X
                }
                showTurn()
                takeTurn()
            } else {
                label.text = when (winner) {
                    Player.X-> "Player X wins!"
                    Player.O -> "Player O wins!"
                    Player.Draw -> "Draw"
                    else -> "Keep playing"
                }
                showMessage(label.text)
                currentPlayer = Player.Empty
                showScore()

            }
        }
    }

    private fun showTurn() {
        label.text = "Player ${currentPlayer.string()}'s turn"
        label2.text="PlayerX score: ${countX} \n " +
                "PlayerO score:${countO}\n" +
                "Draw: ${countD}"
    }

    private fun showScore(){
        label2.text="PlayerX score: ${countX} \n " +
                "PlayerO score:${countO}\n" +
                "Draw: ${countD}"
    }

    private fun showMessage(msg:String){
        Alert(Alert.AlertType.INFORMATION).apply {
            this.title = "Game Over"
            if (msg == "Draw"){
                this.headerText = "Game Over"
            } else {
                this.headerText = "Congratulations !"
            }
            this.contentText = msg
            this.show()
        }
    }

    private fun takeTurn() {
        if(currentPlayer==Player.O){

            Timer("ai Move", false).schedule(500L) {
                Platform.runLater {

                    Move(game.ainextMove())
                }
            }
        }else {}
    }
}

