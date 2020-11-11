
package lcd

import chisel3._
import chisel3.util._

class RPLL extends BlackBox with HasBlackBoxResource {
  val io = IO(new Bundle() {
    val clkin = Input(Clock())    
    val clkout = Output(Bool())
    val clkoutd = Output(Bool())
  })
  setResource("/rpll.v")
}