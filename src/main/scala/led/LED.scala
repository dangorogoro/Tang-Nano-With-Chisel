// See README.md for license details.

package led

import chisel3._

class LED extends Module {
  val io = IO(new Bundle {
    val led = Output(UInt(3.W))
  })
  val cnt_max = (24 * 1000 * 1000 - 1).U;
  //val cnt_max = 1.U;
  val cnt_reg = RegInit(0.U(32.W))
  val led_reg = RegInit(0.U(3.W))

  cnt_reg := cnt_reg + 1.U
  when(cnt_reg === cnt_max){
    led_reg := led_reg + 1.U
    cnt_reg := 0.U
  }
  when(led_reg === 7.U){
    led_reg := 0.U
  }
  io.led := led_reg
}