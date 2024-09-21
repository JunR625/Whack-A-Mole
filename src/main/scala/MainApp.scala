import MainApp.root.{cursor, onMousePressed, onMouseReleased}
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.image.Image
import scalafx.scene.{ImageCursor, Scene}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import util.Cursor

object MainApp extends JFXApp {
  // Load the FXML file
  val resource = getClass.getResource("Game.fxml")
  val loader = new FXMLLoader(resource, NoDependencyResolver)
  loader.load()
  val root: scalafx.scene.Parent = loader.getRoot[jfxs.layout.AnchorPane]

  // ------Creating cursor------------------
  //New default cursor
  val hammerImage = new Image(getClass.getResourceAsStream("/hammer.png"))
  val ownCursor = new ImageCursor(hammerImage)

  //Mouse clicked cursor
  val clickedHammerImage = new Image(getClass.getResourceAsStream("/clickedHammer.png"))
  val clickedCursor = new ImageCursor(clickedHammerImage)

  // Change the cursor image when the mouse is clicked
  onMousePressed = _ => cursor = clickedCursor

  // Revert back to the normal cursor when the mouse is released
  onMouseReleased = _ => cursor = ownCursor

  // ------------Set up the primary stage----------------
  stage = new PrimaryStage {
    title = "Whack-A-Mole"
    scene = new Scene(root)
  }
  Cursor.normalAndClickedCursor(stage.scene())
}