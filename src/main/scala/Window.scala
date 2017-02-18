import java.awt.BorderLayout
import java.awt.BorderLayout.{CENTER, PAGE_END, PAGE_START}
import javax.swing.JFrame.EXIT_ON_CLOSE
import javax.swing.{JFrame, ImageIcon, JLabel, JPanel, BorderFactory}


class Window extends JFrame with StatusChangeCallback {

  private val instructions = "<html><br>'m' - Exit<br>'z' - Start<br>'x' - Stop<br>'p' - Play/Pause</html>"
  private val icon = new ImageIcon("src/main/resources/mch.png")
  private val lblLogo = new JLabel(icon)
  private val lblStatus = new JLabel("Waiting")
  private val panel = new JPanel()
  private val lblInstructions = new JLabel(instructions)

  setDefaultCloseOperation(EXIT_ON_CLOSE)
  setTitle("MyClickHolder")
  setResizable(false)
  setIconImage(icon.getImage)

  panel.setLayout(new BorderLayout())
  panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50))
  panel.add(lblLogo, PAGE_START)
  panel.add(lblStatus, CENTER)
  panel.add(lblInstructions, PAGE_END)

  add(panel)

  pack()
  setVisible(true)

  override def statusChanged(status: String): Unit = {
    lblStatus.setText(status)
  }

  override def engineExit(): Unit = {
    System.exit(0)
  }
}
