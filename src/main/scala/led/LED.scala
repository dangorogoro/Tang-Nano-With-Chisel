// See README.md for license details.

package led

import chisel3._
import chisel3.util._
class LEDTop(var count: Int) extends Module {
  val io = IO(new Bundle {
    val led = Output(UInt(3.W))
  })
  val count_sec = Counter(count)
  val led_reg = RegInit(0.U(3.W))
  count_sec.inc()
  when(count_sec.value === 0.U){
    when(led_reg === 7.U){
      led_reg := 0.U
    }.otherwise{
      led_reg := led_reg + 1.U
    }
  }
  io.led := led_reg
}

class LEDTopWrapper extends RawModule {
  val io = IO(new Bundle{
    val led = Output(UInt(3.W))
    val clock = Input(Clock())
    val nrst = Input(Bool())
  })
  val top = withClockAndReset(io.clock, !io.nrst){ Module(new LEDTop(24 * 1000 * 1000))}
  io.led := top.io.led
}