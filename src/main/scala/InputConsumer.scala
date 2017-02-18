import java.awt.Point
import java.util.Date

import org.jnativehook.GlobalScreen.postNativeEvent
import org.jnativehook.keyboard.{NativeKeyEvent, NativeKeyListener}
import org.jnativehook.mouse.NativeMouseEvent.{BUTTON1, BUTTON3, BUTTON4, BUTTON5, NATIVE_MOUSE_PRESSED, NATIVE_MOUSE_RELEASED}
import org.jnativehook.mouse.{NativeMouseEvent, NativeMouseListener, NativeMouseMotionListener}

class InputConsumer(statusChangeCallback: StatusChangeCallback) extends NativeMouseListener
  with NativeKeyListener
  with NativeMouseMotionListener {

  var enabled = true
  var cursorPosition = new Point(0, 0) // Used on keyboard events, where do not have mouse coordinates


  override def nativeMouseClicked(nativeEvent: NativeMouseEvent): Unit = {
    /*ignore*/
  }

  def onMouse1(event: NativeMouseEvent, action: () => Unit): Unit = {

    if (event.getButton == BUTTON1) {
      action.apply()
    }
  }

  private def notifyHold(): Unit = {
    statusChangeCallback.statusChanged("Button1: Holding")
  }

  private def notifyRelease(): Unit = {
    statusChangeCallback.statusChanged("Button1: Released")
  }

  def hold(point: Point, button: Int): Unit = {
    if (enabled) {
      postNativeEvent(mouseEvent(NATIVE_MOUSE_PRESSED, point, button))
    }
  }

  def release(position: Point, button: Int): Unit = {
    if (enabled) {
      postNativeEvent(mouseEvent(NATIVE_MOUSE_RELEASED, position, button))
    }
  }

  def exit(): Unit = {
    pause()
    statusChangeCallback.engineExit()
  }

  def mouseEvent(eventId: Int, point: Point, button: Int): NativeMouseEvent = {
    new NativeMouseEvent(eventId, new Date().getTime, 0x00, point.x, point.y, 1, BUTTON1)
  }

  def pause(): Unit = {
    release(cursorPosition, BUTTON1)
  }

  def togglePause(): Unit = {
    if (enabled) {
      pause()
      statusChangeCallback.statusChanged("Paused")
    } else {
      statusChangeCallback.statusChanged("Ready")
    }
    enabled = !enabled
  }

  override def nativeKeyTyped(e: NativeKeyEvent): Unit = {
    e.getKeyChar match {
      case 'z' => hold(cursorPosition, BUTTON1)
      case 'x' => release(cursorPosition, BUTTON1)
      case 'p' => togglePause()
      case 'm' => exit()
      case _ => /*ignore*/
    }
  }

  def trackCursorPosition(e: NativeMouseEvent): Unit = {
    cursorPosition = e.getPoint
  }

  override def nativeMouseMoved(e: NativeMouseEvent): Unit = {
    trackCursorPosition(e)
  }

  override def nativeMouseDragged(e: NativeMouseEvent): Unit = {
    trackCursorPosition(e)
  }


  override def nativeMousePressed(nativeEvent: NativeMouseEvent): Unit = {
    onMouse1(nativeEvent, () => notifyHold())
  }

  override def nativeMouseReleased(nativeEvent: NativeMouseEvent): Unit = {
    onMouse1(nativeEvent, () => notifyRelease())

  }

  override def nativeKeyPressed(e: NativeKeyEvent): Unit = {
    /*ignore*/
  }

  override def nativeKeyReleased(e: NativeKeyEvent): Unit = {
    /*ignore*/
  }


}
