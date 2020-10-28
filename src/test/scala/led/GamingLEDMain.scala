
package led

import chisel3._

object GamingLEDMain extends App {
  chisel3.Driver.execute(Array[String](), () => new GamingLED())
}