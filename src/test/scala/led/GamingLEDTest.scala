// See README.md for license details.

package led

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class GamingLEDTester(c: GamingLED) extends PeekPokeTester(c) {
  private val led = c
  var cnt = 0
  for(i <- 1 to 30 by 1) {
    step(1)
    cnt = (cnt + 1) % 8
    //expect(led.io.led, 100)
  }
}
object GamingLEDTest extends App {
  iotesters.Driver.execute(args, () => new GamingLED) {
    c => new GamingLEDTester(c)
  }
}
