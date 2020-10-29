
package lcd

import chisel3._
import chisel3.util._

class LCDTop extends Module {
  val io = IO(new Bundle {
    val lcd_b = Output(UInt(4.W))
    val lcd_g = Output(UInt(5.W))
    val lcd_r = Output(UInt(4.W))
    val lcd_de = Output(Bool())
    val lcd_hsync = Output(Bool())
    val lcd_vsync = Output(Bool())
    val lcd_pixel_clk = Input(Bool())

    val pll_clkin = Input(Bool())    
    val pll_clkout = Output(Bool())
    val pll_clkoutd = Output(Bool())
  })
  val vga_module = Module(new VGAModule())
  io.lcd_b := vga_module.io.lcd_b
  io.lcd_g := vga_module.io.lcd_g
  io.lcd_r := vga_module.io.lcd_r
  io.lcd_de := vga_module.io.lcd_de
  io.lcd_hsync := vga_module.io.lcd_hsync
  io.lcd_vsync := vga_module.io.lcd_vsync

  vga_module.io.lcd_pixel_clk := io.lcd_pixel_clk


  val pll_box = Module(new PllBox())
  io.pll_clkout     := pll_box.io.clkout
  io.pll_clkoutd    := pll_box.io.clkoutd
  pll_box.io.clkin  := io.pll_clkin
}