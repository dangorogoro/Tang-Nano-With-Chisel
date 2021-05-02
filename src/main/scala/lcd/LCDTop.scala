
package lcd

import chisel3._
import chisel3.util._

class LCDTop extends Module {
  val io = IO(new Bundle {
    val vga_nrst = Input(Bool())
    val vga_b = Output(UInt(5.W))
    val vga_g = Output(UInt(6.W))
    val vga_r = Output(UInt(5.W))
    val vga_de = Output(Bool())
    val vga_hsync = Output(Bool())
    val vga_vsync = Output(Bool())
    val vga_clk = Input(Bool())
    
    val led_r = Output(Bool())
    val led_g = Output(Bool())
    val led_b = Output(Bool())
  })
  val x = RegInit(0.U(16.W))
  val y = RegInit(0.U(16.W))
  val count_sec = Counter(200 * 1000 * 1000)
  val led_r_val = RegInit(1.U(1.W))
  val led_g_val = RegInit(1.U(1.W))
  val led_b_val = RegInit(1.U(1.W))
  val vga_r_val = RegInit(0.U(5.W))
  val vga_g_val = RegInit(0.U(6.W))
  val vga_b_val = RegInit(0.U(5.W))
  val vga_module = withClockAndReset(io.vga_clk.asClock, !io.vga_nrst){ Module(new VGAModule) }
  
  x := vga_module.io.x
  y := vga_module.io.y
  io.vga_de := vga_module.io.vga_de
  io.vga_hsync := vga_module.io.vga_hsync
  io.vga_vsync := vga_module.io.vga_vsync


  val width = 800
  val height = 480

  val x_grid = 8
  val y_grid = 4
  val x_cell_size = width / x_grid
  val y_cell_size = height / y_grid
  
  when((y <= y_cell_size.U) || ((((2 * y_cell_size).U <= y) && y <= (3 * y_cell_size).U))){
    when((0.U <= x) && (x <= x_cell_size.U)){
      vga_r_val := 31.U
      vga_g_val := 63.U
      vga_b_val := 31.U
    }.elsewhen(((1 * x_cell_size).U < x) && (x <= (2 * x_cell_size).U)){
      vga_r_val := 31.U
      vga_g_val := 63.U
      vga_b_val := 0.U
    }.elsewhen(((2 * x_cell_size).U < x) && (x <= (3 * x_cell_size).U)){
      vga_r_val := 31.U
      vga_g_val := 0.U
      vga_b_val := 31.U
    }.elsewhen(((3 * x_cell_size).U < x) && (x <= (4 * x_cell_size).U)){
      vga_r_val := 31.U
      vga_g_val := 0.U
      vga_b_val := 0.U
    }.elsewhen(((4 * x_cell_size).U < x) && (x <= (5 * x_cell_size).U)){
      vga_r_val := 0.U
      vga_g_val := 63.U
      vga_b_val := 31.U
    }.elsewhen(((5 * x_cell_size).U < x) && (x <= (6 * x_cell_size).U)){
      vga_r_val := 0.U
      vga_g_val := 63.U
      vga_b_val := 0.U
    }.elsewhen(((6 * x_cell_size).U < x) && (x <= (7 * x_cell_size).U)){
      vga_r_val := 0.U
      vga_g_val := 0.U
      vga_b_val := 31.U
    }.otherwise{
      vga_r_val := 0.U
      vga_g_val := 0.U
      vga_b_val := 0.U
    }
  }.otherwise{
    when((0.U <= x) && (x <= x_cell_size.U)){
      vga_r_val := 0.U
      vga_g_val := 0.U
      vga_b_val := 0.U
    }.elsewhen(((1 * x_cell_size).U < x) && (x <= (2 * x_cell_size).U)){
      vga_r_val := 0.U
      vga_g_val := 0.U
      vga_b_val := 31.U
    }.elsewhen(((2 * x_cell_size).U < x) && (x <= (3 * x_cell_size).U)){
      vga_r_val := 0.U
      vga_g_val := 63.U
      vga_b_val := 0.U
    }.elsewhen(((3 * x_cell_size).U < x) && (x <= (4 * x_cell_size).U)){
      vga_r_val := 0.U
      vga_g_val := 63.U
      vga_b_val := 31.U
    }.elsewhen(((4 * x_cell_size).U < x) && (x <= (5 * x_cell_size).U)){
      vga_r_val := 31.U
      vga_g_val := 0.U
      vga_b_val := 0.U
    }.elsewhen(((5 * x_cell_size).U < x) && (x <= (6 * x_cell_size).U)){
      vga_r_val := 31.U
      vga_g_val := 0.U
      vga_b_val := 31.U
    }.elsewhen(((6 * x_cell_size).U < x) && (x <= (7 * x_cell_size).U)){
      vga_r_val := 31.U
      vga_g_val := 63.U
      vga_b_val := 0.U
    }.otherwise{
      vga_r_val := 31.U
      vga_g_val := 63.U
      vga_b_val := 31.U
    }
  }
  count_sec.inc()
  when(count_sec.value === 0.U){
    led_r_val := !led_r_val
  }
  led_g_val := vga_module.io.debug
  when(x <= 40.U){
    led_b_val := 0.U
  }.otherwise{
    led_b_val := 1.U
  }
  io.led_r := led_r_val
  io.led_g := led_g_val
  io.led_b := led_b_val
  io.vga_r := vga_r_val
  io.vga_g := vga_g_val
  io.vga_b := vga_b_val
}
class LCDTopWrapper extends RawModule {
  val io = IO(new Bundle {
    val vga_nrst = Input(Bool())
    val vga_b = Output(UInt(5.W))
    val vga_g = Output(UInt(6.W))
    val vga_r = Output(UInt(5.W))
    val vga_de = Output(Bool())
    val vga_hsync = Output(Bool())
    val vga_vsync = Output(Bool())
    val vga_clk = Output(Bool())

    val led_r = Output(Bool())
    val led_g = Output(Bool())
    val led_b = Output(Bool())
    val xtal = Input(Clock())
    val nrst = Input(Bool())
  })

  val pll_box = Module(new RPLL)
  pll_box.io.clkin := io.xtal
  val top = withClockAndReset(pll_box.io.clkout.asClock, !io.nrst){ Module(new LCDTop) }//200MHz
  top.io.vga_clk := pll_box.io.clkoutd //33.333M
  io.vga_clk := pll_box.io.clkoutd
 
  top.io.vga_nrst := io.vga_nrst
  io.vga_b := top.io.vga_b
  io.vga_g := top.io.vga_g
  io.vga_r := top.io.vga_r
  io.vga_hsync := top.io.vga_hsync
  io.vga_vsync := top.io.vga_vsync
  io.vga_de := top.io.vga_de
  
  io.led_r := top.io.led_r
  io.led_g := top.io.led_g
  io.led_b := top.io.led_b
}