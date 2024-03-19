import javafx.scene.{Parent, Scene}
import javafx.fxml.FXMLLoader
import javafx.stage.Stage
import javafx.application.Application


object TestApp {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[TestApp], args: _*)
  }
}


class TestApp extends Application {
  override def start(primaryStage: Stage): Unit = {
    val root: Parent = FXMLLoader.load(getClass.getResource("/main.fxml"))
    val scene: Scene = new Scene(root)

    primaryStage.setTitle("Test")
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}