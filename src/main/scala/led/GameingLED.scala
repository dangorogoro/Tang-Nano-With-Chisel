// See README.md for license details.

package led

import chisel3._
import chisel3.util._

class GamingLED extends Module {
  val io = IO(new Bundle {
    val led_r = Output(Bool())
    val led_g = Output(Bool())
    val led_b = Output(Bool())
  })
  val r_inc = 23
  val g_inc = 13
  val b_inc = 17
  val max_val = 24 * 1000
  val led_r_thre = RegInit(0.U(32.W))
  val led_g_thre = RegInit(0.U(32.W))
  val led_b_thre = RegInit(0.U(32.W))
  val cnt_reg = RegInit(0.U(32.W))

  cnt_reg := cnt_reg + 1.U
  when(cnt_reg === max_val.U){
    led_r_thre := Mux((led_r_thre + r_inc.U) > max_val.U, led_r_thre + r_inc.U - max_val.U, led_r_thre + r_inc.U)
    led_g_thre := Mux((led_g_thre + g_inc.U) > max_val.U, led_g_thre + g_inc.U - max_val.U, led_g_thre + g_inc.U)
    led_b_thre := Mux((led_b_thre + b_inc.U) > max_val.U, led_b_thre + b_inc.U - max_val.U, led_b_thre + b_inc.U)
    cnt_reg := 0.U
  }
  when(led_r_thre > (max_val / 2).U ){
    when(led_r_thre > cnt_reg){
      io.led_r := 1.U
    }.otherwise{
      io.led_r := 0.U
    }
  }.otherwise{
    when(led_r_thre > cnt_reg){
      io.led_r := 0.U
    }.otherwise{
      io.led_r := 1.U
    }
  }
  when(led_g_thre > (max_val / 2).U ){
    when(led_g_thre > cnt_reg){
      io.led_g := 1.U
    }.otherwise{
      io.led_g := 0.U
    }
  }.otherwise{
    when(led_g_thre > cnt_reg){
      io.led_g := 0.U
    }.otherwise{
      io.led_g := 1.U
    }
  }
  when(led_b_thre > (max_val / 2).U ){
    when(led_b_thre > cnt_reg){
      io.led_b := 1.U
    }.otherwise{
      io.led_b := 0.U
    }
  }.otherwise{
    when(led_b_thre > cnt_reg){
      io.led_b := 0.U
    }.otherwise{
      io.led_b := 1.U
    }
  }
}