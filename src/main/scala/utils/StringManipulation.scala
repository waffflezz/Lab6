package utils

import java.nio.charset.StandardCharsets

object StringManipulation {
  def splitStringToBytes(str: String, chunkSize: Int): Array[Byte] = {
    val bytes = str.getBytes(StandardCharsets.UTF_8)
    val chunks = bytes.grouped(chunkSize).toArray
    chunks.flatten
  }
}
