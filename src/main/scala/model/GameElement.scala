package model

import scalafx.scene.image.ImageView
import scalafx.scene.layout.GridPane
import scala.util.Random

// Base class representing a game element (mole or bomb)
abstract class GameElement(imageView: ImageView, gridPane: GridPane) {
  private val random = new Random()

  // Method to set the position of the game element
  def setPosition(column: Int, row: Int): Unit = {
    GridPane.setColumnIndex(imageView, column)
    GridPane.setRowIndex(imageView, row)
  }

  // Accessor for the ImageView
  def getImageView: ImageView = imageView

  // Method to register the click event
  def enableClickToMove(): Unit = {
    imageView.onMouseClicked = _ => {
      moveToRandomPosition()
    }
  }

  // Method to move the element to a random position within the grid
  def moveToRandomPosition(): Unit = {
    val newColumn = random.nextInt(3)
    val newRow = random.nextInt(3)
    setPosition(newColumn, newRow)
  }
}

