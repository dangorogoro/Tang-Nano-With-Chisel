
package lcd

import chisel3._
import chisel3.util._

class PllBox extends BlackBox with HasBlackBoxResource {
  val io = IO(new Bundle() {
    val clkin = Input(Bool())    
    val clkout = Output(Bool())
    val clkoutd = Output(Bool())
  })
  setResource("/gowin_pll.v")
}