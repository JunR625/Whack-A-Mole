package util

import scalafx.scene.ImageCursor
import scalafx.scene.image.Image
import scalafx.scene.Scene

object Cursor {
  private val hammerImage = new Image(getClass.getResourceAsStream("/hammer.png"))
  private val clickedHammerImage = new Image(getClass.getResourceAsStream("/clickedHammer.png"))

  val defaultCursor = new ImageCursor(hammerImage)
  val clickedCursor = new ImageCursor(clickedHammerImage)

  def normalAndClickedCursor(scene: Scene): Unit = {
    scene.cursor = defaultCursor

    scene.onMousePressed = _ => scene.cursor = clickedCursor
    scene.onMouseReleased = _ => scene.cursor = defaultCursor
  }
}