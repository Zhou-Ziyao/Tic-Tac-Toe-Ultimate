package edu.uiowa.Javafx.Ultimate

import edu.uiowa.Javafx.StartPage
import edu.uiowa.MainActivity.Player
import edu.uiowa.MainActivity.ultimate
import javafx.application.Application
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.text.Font


var imageStage:Stage = Stage()

class ultimateGameboard : Application() {

    private val game = ultimate()
    private var countX = 0
    private var countO = 0
    private var countD = 0
    private var currentPlayer = Player.X


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

    private var pane = GridPane()
    private var buttons = arrayOf<Array<Array<Array<Button>>>>()
    private var gridpanes = arrayListOf<GridPane>()
    private var winbuttons = arrayListOf<Button>()



    override fun start(primaryStage: Stage) {
        imageStage = primaryStage
        pane = GridPane()
        init()
        pane.add(label, 0, 3, 3, 1)
        pane.add(reset,0,5,5,1)
        pane.add(label2,0,6,3,1)
        pane.add(quit,2,4,4,2)
        pane.add(Goback,0,7,3,1)
        showTurn()
        val scene = Scene(pane)
        imageStage?.scene = scene
        imageStage?.title = "Ultimate Tic Tac Toe"

        imageStage?.show()
    }




    override fun init(){
        // remove buttons left by previous game
        buttons.forEach {
            it.forEach {
                it.forEach {
                    it.forEach {
                        pane.children.remove(it)
                    }
                }
            }
        }
        gridpanes.forEach {
            pane.children.remove(it)
        }
        gridpanes.clear()


        buttons = Array(3) { i ->
            Array(3) { j ->
                val gridpane =GridPane()
                pane.add(gridpane, j, i)
                gridpane.padding = Insets(5.0)
                gridpanes.add(gridpane)
                Array(3) { k ->
                    Array(3) { l ->
                        Button(" ").apply {

                            this.setMaxSize(40.0, 40.0)
                            this.setMinSize(40.0, 40.0)
                            font=Font.font(15.0)
                            this.onAction= EventHandler {
                                Move((i*3+k)*9+(j*3+l))
                            }
                            gridpane.add(this, l, k)
                        }
                    }
                }
            }
        }
        winbuttons.forEach {
            pane.children.remove(it)
        }
        winbuttons.clear()
        currentPlayer = Player.X
        game.init()
    }


    private fun Move(pos: Int) {
        if (currentPlayer != Player.Empty && game.CheckMove(pos)) {
            game.put(pos, currentPlayer)
            val x=pos%9
            val y=pos/9
            buttons[y / 3][x / 3][y%3][x%3].setText( currentPlayer.string())

            val localwinner = game.CheckLocalWinner(x/3,y/3)
            if(localwinner != Player.Empty){
                val xx=x/3
                val yy=y/3
                val text=when(localwinner) {
                    Player.X -> "X wins"
                    Player.O -> "O wins"
                    Player.Draw -> "Draw"
                    else -> ""
                }
                val b = buttons[yy][xx]
                // if the local board is game over, set a 3*3 button to show result
                pane.add(Button(text).apply {
                    this.setMaxSize(120.0,120.0)
                    this.setMinSize(120.0,120.0)
                    font = Font.font(20.0)
                    winbuttons.add(this)
                    this.setStyle("-fx-background-color: transparent;");
                }, xx, yy)

                game.lastmovePos=null
            }else if (game.CheckLocalWinner(x%3,y%3) != Player.Empty) {
                game.lastmovePos = null
            }

            // make the local board 3*3 buttons disabled if the local board is not legal to move
            for (i in 0..2) {
                for (j in 0..2) {
                    if (game.CheckLocalWinner(j, i) == Player.Empty&& (game.lastmovePos == null|| game.lastmovePos == i*3+j)){
                        for (a in 0..2) {
                            for (b in 0..2) {
                                buttons[i][j][a][b].isDisable = false
                            }
                        }
                    } else{

                        for (a in 0..2) {
                            for (b in 0..2) {
                                buttons[i][j][a][b].isDisable = true
                            }
                        }
                    }
                }
            }

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
}


