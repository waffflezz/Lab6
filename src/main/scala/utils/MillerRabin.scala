package utils

object MillerRabin {
  private def miller_rabin_pass(a: BigInt, n: BigInt): Boolean = {
    var d: BigInt = 0
    var s: BigInt = 0
    var a_to_power: BigInt = 0

    d = n - 1
    s = 0
    while (d % 2 == 0) {
      d >>= 1
      s += 1
    }

    a_to_power = a.modPow(d, n)
    if (a_to_power == 1) {
      return true
    }
    for (_ <- 1 to s.intValue) {
      if (a_to_power == n - 1) {
        return true
      }
      a_to_power = (a_to_power * a_to_power) % n
    }
    a_to_power == n - 1
  }

  def miller_rabin(n: BigInt): Boolean = {
    val k: Int = 20
    for (_: Int <- 1 to k) {
      var a: BigInt = 0
      val rand: scala.util.Random = new scala.util.Random()
      while (a == 0) {
        a = new BigInt(new java.math.BigInteger("" + (rand.nextDouble() * n.doubleValue).toInt))
      }
      if (!miller_rabin_pass(a, n)) {
        return false
      }
    }
    true
  }
}
