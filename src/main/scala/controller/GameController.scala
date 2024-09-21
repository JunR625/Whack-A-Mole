package controller

import model.{Bomb, Mole}
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.GridPane
import scalafx.stage.StageStyle
import scalafx.util.Duration
import scalafxml.core.macros.sfxml

import scala.util.Random

@sfxml
class GameController(
                      private val gridPane: GridPane,
                      private val moleImageView: ImageView,
                      private val bombImageView: ImageView,
                      private val bombImageView1: ImageView,
                      private val scoreLabel: Label

                    ) {

  // Instantiate the mole and bomb using existing ImageViews
  private val mole = new Mole(moleImageView, gridPane)
  private val bomb = new Bomb(bombImageView, gridPane)
  private val bomb1 = new Bomb(bombImageView1, gridPane)

  // Random number generator to determine new positions
  private val random = new Random()

  // Enable click-to-move behavior for both mole
  mole.enableClickToMove()

  // Define a timeline that will move the mole and bomb periodically
  private val timer = new Timeline {
    cycleCount = Timeline.Indefinite
    keyFrames = Seq(
      KeyFrame(Duration(6), onFinished = _ => moveMoleAndBomb()) // Move every second
    )
  }

  // Start the timeline when the controller is initialized
  timer.play()

  // Method to move the mole and bomb to random positions within the grid
  private def moveMoleAndBomb(): Unit = {
    if (!gameOver){
      // Get a random position in the grid
      val newMolePosition = (random.nextInt(3), random.nextInt(3)) // Grid is 3x3 so range is 0 to 2
      val newBombPosition = (random.nextInt(3), random.nextInt(3))
      val newBomb1Position = (random.nextInt(3), random.nextInt(3))

      // Ensure the mole and bomb do not overlap in the same cell
      if (newMolePosition == newBombPosition || newMolePosition == newBomb1Position || newBombPosition == newBomb1Position) {
        moveMoleAndBomb() // Keep choosing new positions until they do not overlap
      } else {
        // Move the mole and bomb to new positions
        mole.setPosition(newMolePosition._1, newMolePosition._2)
        bomb.setPosition(newBombPosition._1, newBombPosition._2)
        bomb1.setPosition(newBomb1Position._1, newBomb1Position._2)
      }
    }
  }

  private var currentScore: Int = 0
  // Method to increment the score and update the score label
  private def incrementScore(): Unit = {
    currentScore += 1
    scoreLabel.text = s"Score: $currentScore"
    println(s"Total Score: $currentScore")
  }

  private var gameOver: Boolean = false
  private def stopGame(): Unit = {
    gameOver = true
    timer.stop() // Stop timer moving
    bombImageView.image = new Image(getClass.getResourceAsStream("/exploded.png")) // Change to exploded image
    bombImageView1.image = new Image(getClass.getResourceAsStream("/exploded.png"))
    moleImageView.image = new Image(getClass.getResourceAsStream("/laughingMole.png")) //change to laughingmole image
    showGameOverPopup()
  }
  mole.getImageView.onMouseClicked = _ => { //when clicked, call the incrementScore and move mole
    if (!gameOver) {
      incrementScore()
      mole.moveToRandomPosition() // Move the mole to a new random position after it's clicked
      //Have to move it again here bcuz player can click multiple times before timer expire
    }
  }

  // Add a click event specifically for the bomb to stop the game and change the image
  bomb.getImageView.onMouseClicked = _ => {
    if (!gameOver) {
      stopGame()
    }
  }
  bomb1.getImageView.onMouseClicked = _ => {
    if (!gameOver) {
      stopGame()
    }
  }

  private def showGameOverPopup(): Unit = {
    val alert = new Alert(AlertType.Confirmation) {
      initStyle(StageStyle.Utility)
      title = "Game Over!"
      headerText = s"Your score: $currentScore"
      contentText = "Would you like to play again?"
    }

    val result = alert.showAndWait()
    result match {
      case Some(ButtonType.OK) => resetGame()
      case _ => // Do nothing, game remains stopped
    }
  }

  private def resetGame(): Unit = {
    gameOver = false
    currentScore = 0
    scoreLabel.text = "Score: 0"
    moleImageView.image = new Image(getClass.getResourceAsStream("/Mole.png"))
    bombImageView.image = new Image(getClass.getResourceAsStream("/Bomb.png"))
    bombImageView1.image = new Image(getClass.getResourceAsStream("/Bomb.png"))
    moveMoleAndBomb()
    timer.play()
  }
}
