trait StatusChangeCallback {

  def statusChanged(status: String): Unit

  def engineExit(): Unit

}
