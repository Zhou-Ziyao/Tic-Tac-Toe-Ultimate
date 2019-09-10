package edu.uiowa.Javafx

import edu.uiowa.Javafx.Simple.Gameboard
import edu.uiowa.Javafx.Simple.pveGameboard
import edu.uiowa.Javafx.Ultimate.ultimateGameboard
import edu.uiowa.Javafx.Ultimate.ultimatePVEGameBoard
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage



var current_stage:Stage =Stage()


class StartPage:Application(){
    override fun start(primaryStage: Stage) {
        current_stage = primaryStage
        primaryStage?.title="Tic-Tac-Toe"

        val vbox= VBox()
        vbox.setPrefSize(600.0,1200.0)
        val hbox1 = HBox()
        hbox1.setPadding(Insets(15.0,12.0,15.0,12.0))
        hbox1.setSpacing(10.0)
        vbox.children.add(hbox1)


        val mylabel = Label("Simple Tic-Tac-Toe")
        hbox1.children.add(mylabel)

        val hbox2 = HBox()
        hbox2.setPadding(Insets(15.0,12.0,15.0,12.0))
        hbox2.setSpacing(10.0)
        vbox.children.add(hbox2)

        val b = Button("Play against a friend")
        b.onAction = buttonController1
        hbox2.children.add(b)


        val hbox3 =HBox()
        hbox3.setPadding(Insets(15.0,12.0,15.0,12.0))
        hbox3.setSpacing(10.0)
        vbox.children.add(hbox3)

        val c =Button("Play against a computer")
        c.onAction = buttonController2
        hbox3.children.add(c)


        val hbox4 = HBox()
        hbox4.setPadding(Insets(15.0,12.0,15.0,12.0))
        hbox4.setSpacing(10.0)
        vbox.children.add(hbox4)


        val mylabel2 = Label("Ultimate Tic-Tac-Toe")
        hbox4.children.add(mylabel2)



        val hbox5 = HBox()
        hbox5.setPadding(Insets(15.0,12.0,15.0,12.0))
        hbox5.setSpacing(10.0)
        vbox.children.add(hbox5)

        val d = Button("Play against a friend")
        d.onAction = buttonController3
        hbox5.children.add(d)


        val hbox6 =HBox()
        hbox6.setPadding(Insets(15.0,12.0,15.0,12.0))
        hbox6.setSpacing(10.0)
        vbox.children.add(hbox6)

        val e =Button("Play against a computer")
        e.onAction = buttonController4
        hbox6.children.add(e)


        primaryStage.run {
            this?.scene = Scene(vbox,250.0,350.0)
            this?.show()
        }


    }
}
object buttonController1: EventHandler <ActionEvent> {
    override  fun handle (event: ActionEvent){
        val b = event.source as Button
        current_stage?.hide()
        Gameboard().start(current_stage)
    }
}

object buttonController2:EventHandler<ActionEvent>{
    override fun handle(event: ActionEvent?) {
        val c =event?.source as Button
        current_stage?.hide()
        pveGameboard().start(current_stage)


    }
}


object buttonController3: EventHandler <ActionEvent> {
    override  fun handle (event: ActionEvent){
        val b = event.source as Button
        current_stage?.hide()
        ultimateGameboard().start(current_stage)
    }
}

object buttonController4:EventHandler<ActionEvent>{
    override fun handle(event: ActionEvent?) {
        val c =event?.source as Button
        current_stage?.hide()
        ultimatePVEGameBoard().start(current_stage)


    }
}
