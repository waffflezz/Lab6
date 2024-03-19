package controllers

import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.fxml.{FXML, Initializable}
import javafx.scene.input.{Clipboard, ClipboardContent}
import javafx.scene.{control => jfxsc}
import rsa.RSA
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import utils.StringManipulation

import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.ResourceBundle


class GeneratePrimesController extends Initializable{
  var rsa: RSA = _

  @FXML
  var minPrime: jfxsc.TextField = _

  @FXML
  var maxPrime: jfxsc.TextField = _

  @FXML
  var PQList: jfxsc.ListView[BigInt] = _

  @FXML
  var PField: jfxsc.TextField = _

  @FXML
  var QField: jfxsc.TextField = _

  @FXML
  var EField: jfxsc.TextField = _

  @FXML
  var NField: jfxsc.TextField = _

  @FXML
  var DField: jfxsc.TextField = _

  @FXML
  var inputField: jfxsc.TextArea = _

  @FXML
  var outputField: jfxsc.TextArea = _


  @FXML
  def generatePrimes(): Unit = {
    try {
      val minPrimeInt = BigInt(minPrime.getText)
      val maxPrimeInt = BigInt(maxPrime.getText.toInt)
      if (minPrimeInt > maxPrimeInt) throw new ArithmeticException("Min > Max!")

      PQList.setItems(RSA.primes(minPrimeInt, maxPrimeInt))

    } catch {
      case _: ArithmeticException => new Alert(AlertType.Error, "Min > Max!").showAndWait()
      case _ => new Alert(AlertType.Error, "Min or Max not Int!").showAndWait()
    }
  }

  @FXML
  def calculateRSA(): Unit = {
    try {
      val p = BigInt(PField.getText)
      val q = BigInt(QField.getText)

      rsa = new RSA(p, q)
      EField.setText(rsa.publicKey.toString())
      NField.setText(rsa.n.toString())
      DField.setText(rsa.privateKey.toString())

    } catch {
      case _: Exception => new Alert(AlertType.Error, "P or Q not Int!").showAndWait()
    }
  }

  @FXML
  def encode(): Unit = {
    if (rsa == null) {
      new Alert(AlertType.Error, "E, N and D not generate!!!").showAndWait()
      return
    }

    val string = inputField.getText
    val splitString = StringManipulation.splitStringToBytes(string, 20)
    val encodeString = rsa.encrypt(splitString)
    outputField.setText(encodeString.map(_.toInt).mkString(","))
  }

  @FXML
  def decode(): Unit = {
    val bigIntArray = inputField.getText.split(",").map(BigInt(_))
    val decodeBytes = rsa.decrypt(bigIntArray)
    outputField.setText(new String(decodeBytes, StandardCharsets.UTF_8))
  }

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit = {
    PQList.getSelectionModel.selectedItemProperty().addListener(new ChangeListener[BigInt] {
      override def changed(observableValue: ObservableValue[_ <: BigInt], t: BigInt, t1: BigInt): Unit = {
        val currentPrime = PQList.getSelectionModel.getSelectedItem
        if (currentPrime == null) return
        val clipboard = Clipboard.getSystemClipboard
        val content = new ClipboardContent()
        content.putString(currentPrime.toString())
        clipboard.setContent(content)
      }
    })
  }
}
