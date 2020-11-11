// See README.md for license details.

package lcd

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class LCDTopUnitTester(c: LCDTop) extends PeekPokeTester(c) {
  private val led = c
  var cnt = 0
  for(i <- 1 to 20 by 1) {
    step(1)
    cnt = (cnt + 1)
    expect(led.io.vga_b, cnt)
  }
}
object LCDTopUnitTest extends App {
  iotesters.Driver.execute(args, () => new LCDTop) {
    c => new LCDTopUnitTester(c)
  }
}
