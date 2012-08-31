import javax.microedition.lcdui.*;

public class frmInfo extends Canvas implements CommandListener {
  static TLabel lbl_reg;
  static TList list;
  static Command cmd_back;

  public frmInfo() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setFullScreenMode(true);
    lbl_reg = new TLabel(0,0,"");
    list = new TList(0,app.FontHeight[app.fontInd],app.width,app.height-app.FontHeight[app.fontInd]-app.bottomPanelHeight);

    cmd_back = new Command("Назад", Command.OK, 1);
    this.addCommand(cmd_back);
    this.addCommand(app.cmd_help);
    this.addCommand(app.cmd_exit);
    setCommandListener(this);

  }

  protected void drawBack() {
    app.gr.setColor(0xffffff);
    app.gr.fillRect(0, 0, getWidth(), getHeight());
    app.gr.setColor(0x00ff00);
    app.gr.fillRect(0,0,app.width,app.FontHeight[app.fontInd]);
    app.gr.setColor(0x0000ff);
    app.gr.fillRect(0, app.height-app.bottomPanelHeight, app.width, app.height-app.bottomPanelHeight);
    app.gr.setColor(0);
  }

  protected void paint(Graphics g) {
    drawBack();
    lbl_reg.repaint();
    list.repaint();
    g.drawImage(app.buffer, 0, 0, Graphics.TOP|Graphics.LEFT);
  }

  private void goBack() {
    list.start=0;
    app.instance.displayable.drawBack();
    app.instance.changeForm(app.instance.displayable);
  }

  public void commandAction(Command command, Displayable displayable) {
    if (command == cmd_back) {
      goBack();
    } else if (command == app.cmd_help) {
      Display.getDisplay(app.instance).setCurrent(app.alert_help,Display.getDisplay(app.instance).getCurrent());
    } else if (command == app.cmd_exit) {
      app.quitApp();
    }
  }

  protected void keyPressed(int keyCode) {
    switch (getGameAction(keyCode)) {
      case UP:
        if (keyCode!=KEY_NUM2 & list.up()) {
          repaint();
          keyCode=0;
        }
        break;
      case DOWN:
        if (keyCode!=KEY_NUM8 & list.down()) {
          repaint();
          keyCode=0;
        }
        break;
      case LEFT:
        if (keyCode!=KEY_NUM4 & list.left()) {
          repaint();
          keyCode=0;
        }
        break;
      case RIGHT:
        if (keyCode!=KEY_NUM6 & list.right()) {
          repaint();
          keyCode=0;
        }
        break;
      case FIRE:
        if (keyCode!=KEY_NUM5 & keyCode!=-12 & keyCode!=-4) {
          goBack();
        }
        break;
    }
  }

}
