
package lcd

import chisel3._

object LCDMain extends App {
  chisel3.Driver.execute(Array("--target-dir", "out/lcd"), () => new LCDTopWrapper())
}