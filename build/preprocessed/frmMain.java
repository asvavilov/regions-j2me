import javax.microedition.lcdui.*;

public class frmMain extends Canvas implements CommandListener
{
  static TTablo tablo;
  static TLabel lbl_head1;
  static TLabel lbl_region;
  static Command cmd_info;

    public frmMain()
    {
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        setFullScreenMode(true);

        app.width = getWidth();
        app.height = getHeight();
        /*app.width=132;
        app.height=176;*/
        
        app.fontInd=(app.width>200 ? 1 : 0);//!вычислять в зависимости от размера экрана
        
        cmd_info = new Command("Информация", Command.OK, 1);
        app.cmd_help = new Command("Справка", Command.HELP, 1);
        app.cmd_exit = new Command("Выход", Command.EXIT, 1);
        this.addCommand(cmd_info);
        this.addCommand(app.cmd_help);
        this.addCommand(app.cmd_exit);
        setCommandListener(this);

        app.bottomPanelHeight=3;
        tablo=new TTablo(app.width / 2, app.height - app.bottomPanelHeight - 3 * app.FontHeight[app.fontInd] - 3);
        lbl_head1=new TLabel((app.width-10*app.FontWidth[app.fontInd])/2,0,"\"РЕГИОНЫ\"");
        lbl_region=new TLabel(0,app.height - app.bottomPanelHeight - 3 * app.FontHeight[app.fontInd],"");

        app.alert_help=new Alert("О программе");
        app.alert_help.setTimeout(Alert.FOREVER);
        app.alert_help.setString("\"Регионы\" (c) 2006-2008 Вавилов Александр, shurik83@mail.ru\nУПРАВЛЕНИЕ:\nДля просмотра регионов вводите соответствующий номер с клавиатуры или пролистывайте с помощью джойстика; для просмотра справочной информации по региону нажмите джойстик.\nСправочная информация по региону пролистывается с помощью джойстика; для возврата к списку нажмите джойстик.\n# - сброс\n* - смена шрифта");

    }

    private void goInfo() {
      if (app.RegNum.length()==2 & app.RegNum.getInt()!=0) {
        frmInfo.lbl_reg.text = "Регион #" + app.RegNum.getStr();
        try {
          frmInfo.list.setText(app.region.getInfo(app.RegNum.getStr()));
        }
        catch (ArrayIndexOutOfBoundsException e) {
          frmInfo.list.setText("Нет данных");
        }
        app.changeForm(app.displayable2);
      }
    }

    public void commandAction(Command command, Displayable displayable) {
      if (command == cmd_info) {
        goInfo();
      } else if (command == app.cmd_help) {
        Display.getDisplay(app.instance).setCurrent(app.alert_help,Display.getDisplay(app.instance).getCurrent());
      } else if (command == app.cmd_exit) {
        app.quitApp();
      }
    }

    protected void keyPressed(int keyCode)
    {
      switch (getGameAction(keyCode)) {
        case LEFT:
        case UP:
          if (keyCode!=KEY_NUM4 & keyCode!=KEY_NUM2) {
            if (app.RegNum.length()!=1) {
              int n=app.RegNum.getInt();
              if (n <= 1) {
                app.RegNum.set(data.sa.length);
              } else {
                app.RegNum.set(n-1);
              }
            }
            keyCode=0;
          }
          break;
        case RIGHT:
        case DOWN:
          if (keyCode!=KEY_NUM6 & keyCode!=KEY_NUM8) {
            if (app.RegNum.length()!=1) {
              int n = app.RegNum.getInt();
              if (n >= data.sa.length) {
                app.RegNum.set(1);
              } else {
                app.RegNum.set(n + 1);
              }
            }
            keyCode=0;
          }
          break;
        case FIRE:
          if (keyCode!=KEY_NUM5 & keyCode!=-12 & keyCode!=-4) {
            goInfo();
          }
          break;
      }
      switch(keyCode)
        {
        case KEY_STAR:
          //!смена шрифта: перерасчет атрибутов объектов
          app.fontInd=(app.fontInd==0 ? 1 : 0);
          lbl_region.posY=app.height - app.bottomPanelHeight - 3 * app.FontHeight[app.fontInd];
          frmInfo.list.posY=app.FontHeight[app.fontInd];
          frmInfo.list.height=app.height-app.FontHeight[app.fontInd]-app.bottomPanelHeight;
          frmInfo.list.rows=frmInfo.list.height/app.FontHeight[app.fontInd];
          break;
        case KEY_POUND:
            app.RegNum.clear();
            lbl_region.text="";
            break;
        case KEY_NUM1:
            app.RegNum.add(1);
            break;
        case KEY_NUM2:
            app.RegNum.add(2);
            break;
        case KEY_NUM3:
            app.RegNum.add(3);
            break;
        case KEY_NUM4:
            app.RegNum.add(4);
            break;
        case KEY_NUM5:
            app.RegNum.add(5);
            break;
        case KEY_NUM6:
            app.RegNum.add(6);
            break;
        case KEY_NUM7:
            app.RegNum.add(7);
            break;
        case KEY_NUM8:
            app.RegNum.add(8);
            break;
        case KEY_NUM9:
            if (app.RegNum.length()==1) {
              app.RegNum.add(9);
            }
            break;
        case KEY_NUM0:
            app.RegNum.add(0);
            break;
        }
        if (app.RegNum.length()==2 & app.RegNum.getInt()!=0) {
          int ind=app.RegNum.getInt();
          try {
            lbl_region.text = data.sa[ind - 1];
          } catch (ArrayIndexOutOfBoundsException e) {
            lbl_region.text="Нет данных";
          }
        } else {
          lbl_region.text="";
        }
        repaint();
    }

    protected void drawBack() {
      app.gr.setColor(0xffffff);
      app.gr.fillRect(0, 0, app.width, app.height);
      app.gr.setColor(0x0000ff);
      app.gr.fillRect(0, app.height-app.bottomPanelHeight, app.width, app.height-app.bottomPanelHeight);
      app.gr.setColor(0xff0000);
      app.gr.drawLine(0, app.height - app.bottomPanelHeight - 3 * app.FontHeight[app.fontInd] - 1, app.width, app.height - app.bottomPanelHeight - 3 * app.FontHeight[app.fontInd] - 1);
    }

    protected void paint(Graphics g)
    {
      drawBack();
      tablo.repaint();
      lbl_head1.repaint();
      lbl_region.repaint();
      g.drawImage(app.buffer,0,0,Graphics.TOP|Graphics.LEFT);
    }
}
