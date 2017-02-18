import java.util.logging.Level.OFF
import java.util.logging.{Level, Logger}

import org.jnativehook.GlobalScreen

object Main {

  def main(args: Array[String]): Unit = {

    Logger
      .getLogger(classOf[GlobalScreen].getPackage.getName)
      .setLevel(OFF)

    GlobalScreen.registerNativeHook()

    val window = new Window()
    val consumer = new InputConsumer(window)

    GlobalScreen.addNativeMouseListener(consumer)
    GlobalScreen.addNativeKeyListener(consumer)
    GlobalScreen.addNativeMouseMotionListener(consumer)

  }

}
