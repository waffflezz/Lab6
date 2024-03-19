package rsa

import javafx.collections.{FXCollections, ObservableList}
import utils.MillerRabin

import scala.util.Random


object RSA {
  def primes(n: BigInt, m: BigInt): ObservableList[BigInt] = {
    val observableList: ObservableList[BigInt] = FXCollections.observableArrayList()
    for (i <- n to m) {
      if (MillerRabin.miller_rabin(i)) {
        observableList.add(i)
      }
    }

    observableList
  }

  def generatePrime(bitLength: Int): BigInt = {
    BigInt.probablePrime(bitLength, Random)
  }
}


class RSA(p: BigInt, q: BigInt) {
  val phi = (p.-(BigInt(1))).*(q.-(BigInt(1)))
  val n = p * q
  println(p, q)

  var publicKey = BigInt(3)
  publicKey = generatePublicKey()
  val privateKey = publicKey.modInverse(phi)

  def generatePublicKey(): BigInt = {
    while (phi.gcd(publicKey).intValue > 1) {
      publicKey = publicKey.+(BigInt(2))
    }
    publicKey
  }

  def encrypt(message: BigInt): BigInt = {
    message.modPow(publicKey, n)
  }

  def decrypt(cipher: BigInt): BigInt = {
    cipher.modPow(privateKey, n)
  }

  def encrypt(message: Array[Byte]): Array[BigInt] = {
    message.map(x => Array[Byte](x)).map(enc)
  }

  def enc(message: Array[Byte]): BigInt = {
    BigInt(message).modPow(publicKey, n)
  }

  def decrypt(cipher: Array[BigInt]): Array[Byte] = {
    cipher.map(decrypt).flatMap(_.toByteArray)
  }

  override def toString: String = {
    println("p: " + p)
    println("q: " + q)
    println("phi:" + phi)
    println("n: " + n)
    println("public: " + publicKey)
    println("private: " + privateKey)
    "rsa.RSA Algorithm"
  }
}
