
package lcd

import chisel3._
import chisel3.util._
 import scala.io.Source
class PictureMem extends Module {
  val io = IO(new Bundle {
    val x = Input(UInt(8.W))
    val y = Input(UInt(8.W))
    val data = Output(UInt(16.W))
  })
  val pic_x = 160
  val raw_list = Source.fromFile("po.txt").getLines.toList
  val list = raw_list.map(x => Integer.parseInt(x.replace("0x",""),16).asUInt(16.W))
  val mem = VecInit(list)
  io.data := mem(io.y * pic_x.U + io.x)
}
//http://simplesandsamples.com/strtol.scala.html