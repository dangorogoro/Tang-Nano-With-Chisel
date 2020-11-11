// See README.md for license details.

package lcd

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class VGAUnitTester(c: VGAModule) extends PeekPokeTester(c) {
  private val led = c
  var cnt = 0
  for(i <- 1 to 2000 by 1) {
    step(1)
    cnt = (cnt + 1)
    expect(led.io.x, cnt)
  }
}
object VGAUnitTest extends App {
  iotesters.Driver.execute(args, () => new VGAModule) {
    c => new VGAUnitTester(c)
  }
}
