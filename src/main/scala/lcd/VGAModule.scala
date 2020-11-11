
package lcd

import chisel3._
import chisel3.util._

class VGAModule extends Module {
  val io = IO(new Bundle {
    val x = Output(UInt(16.W))
    val y = Output(UInt(16.W))
    val vga_de = Output(Bool())
    val vga_hsync = Output(Bool())
    val vga_vsync = Output(Bool())
    val debug = Output(Bool())
    val debug_x = Output(UInt(16.W))
    val debug_y = Output(UInt(16.W))
  })

  val x_count = RegInit(0.U(16.W))
  val y_count = RegInit(0.U(16.W))

  val y_back_porch = 6
  val y_front_porch = 62
  val y_sync = 5

  val x_back_porch = 182
  val x_front_porch = 210
  val x_sync = 1

  val max_x = 800 + x_front_porch + x_back_porch
  val max_y = 480 + y_front_porch + y_back_porch

  when(x_count >= max_x.U){
    y_count := y_count + 1.U
    x_count := 0.U
  }.otherwise{
    when(y_count >= max_y.U){
      y_count := 0.U
      x_count := 0.U
    }.otherwise{
      x_count := x_count + 1.U
    }
  }
  io.vga_vsync := Mux((y_count >= y_sync.U) && (y_count <= (max_y).U), 0.U, 1.U)
  io.vga_hsync := Mux((x_count >= x_sync.U) && (x_count <= (max_x - x_front_porch).U), 0.U, 1.U)
  io.vga_de := Mux(
    (x_count >= x_back_porch.U) &&
    (x_count <= (max_x - x_front_porch).U) &&
    (y_count >= y_back_porch.U) &&
    (y_count <= (max_y - y_front_porch - 1).U),
    1.U, 0.U)
  io.x := Mux(x_count >= x_back_porch.U, x_count - x_back_porch.U, 0.U)
  io.y := Mux(y_count >= y_back_porch.U, y_count - y_back_porch.U, 0.U)

  val count = Counter(33 * 1000 * 1000 / 2)
  count.inc()
  val debug_val = RegInit(0.U(1.W))
  when(count.value === 0.U){
    debug_val := !debug_val
  }
  io.debug := debug_val
  io.debug_x := x_count
  io.debug_y := y_count
}