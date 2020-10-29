
package lcd

import chisel3._
import chisel3.util._

class VGAModule extends Module {
  val io = IO(new Bundle {
    val lcd_b = Output(UInt(4.W))
    val lcd_g = Output(UInt(5.W))
    val lcd_r = Output(UInt(4.W))
    val lcd_de = Output(Bool())
    val lcd_hsync = Output(Bool())
    val lcd_vsync = Output(Bool())
    val lcd_pixel_clk = Input(Bool())
  })

  val column_count = RegInit(0.U(16.W))
  val row_count = RegInit(0.U(16.W))

  val column_pulse = 1
  val row_pulse = 5

  val column_back_porch = 182
  val column_front_porch = 210
  val row_back_porch = 0
  val row_front_porch = 45
  val max_column = 800 + column_front_porch + column_back_porch
  val max_row = 480 + row_front_porch + row_back_porch

  when(column_count === max_column.U){
    row_count := row_count + 1.U
    column_count := 0.U
  }.otherwise{
    when(row_count === max_row.U){
      row_count := 0.U
      column_count := 0.U
    }.otherwise{
      column_count := column_count + 1.U
    }
  }
  io.lcd_hsync := Mux(column_count >= column_pulse.U && column_count <= (max_column - column_front_porch).U, 0.U, 1.U)
  io.lcd_vsync := Mux(row_count >= row_pulse.U && row_count <= (max_row - row_front_porch).U, 0.U, 1.U)
  io.lcd_de := Mux(
    column_count >= column_back_porch.U &&
    column_count <= (max_column - column_front_porch).U &&
    row_count >= row_back_porch.U &&
    row_count <= (max_row - row_front_porch - 1).U,
    1.U, 0.U)
  when(column_count < 200.U){
    io.lcd_b := 1.U
  }.otherwise{
    io.lcd_b := 8.U
  }
  when(column_count < 400.U){
    io.lcd_g := 1.U
  }.otherwise{
    io.lcd_g := 8.U
  }
  when(column_count < 600.U){
    io.lcd_r := 1.U
  }.otherwise{
    io.lcd_r := 8.U
  }
}